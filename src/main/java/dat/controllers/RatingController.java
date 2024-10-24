package dat.controllers;

import dat.config.HibernateConfig;
import dat.daos.RatingDAO;
import dat.dtos.HaikuPartDTO;
import dat.dtos.RatingDTO;
import dat.entities.Rating;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class RatingController implements IController<RatingDTO, Integer> {

    private final RatingDAO dao;

    public RatingController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = RatingDAO.getInstance(emf);
    }

    @Override
    public void read(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // DTO
        RatingDTO ratingPartDTO = dao.read(id);
        // response
        ctx.res().setStatus(200);
        ctx.json(ratingPartDTO, HaikuPartDTO.class);
    }

    @Override
    public void readAll(Context ctx) {
        // List of DTOS
        List<RatingDTO> ratingpartDTO = dao.readAll();
        // response
        ctx.res().setStatus(200);
        ctx.json(ratingpartDTO, HaikuPartDTO.class);
    }

    @Override
    public void create(Context ctx) {
        // request
        RatingDTO jsonRequest = ctx.bodyAsClass(RatingDTO.class);
        // DTO
        RatingDTO ratingPartDTO = dao.create(jsonRequest);
        // response
        ctx.res().setStatus(201);
        ctx.json(ratingPartDTO, HaikuPartDTO.class);
    }

    @Override
    public void update(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // dto
        RatingDTO ratingPartDTO = dao.update(id, validateEntity(ctx));
        // response
        ctx.res().setStatus(200);
        ctx.json(ratingPartDTO, Rating.class);
    }

    @Override
    public void delete(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        dao.delete(id);
        // response
        ctx.res().setStatus(204);
    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        return dao.validatePrimaryKey(integer);
    }

    @Override
    public RatingDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(RatingDTO.class)
                .check(h -> h.getScore() >= 0 && h.getScore() <= 10, "Rating must be between 0 and 10")
                .get();
    }

}
