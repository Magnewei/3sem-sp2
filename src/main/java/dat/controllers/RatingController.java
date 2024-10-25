package dat.controllers;

import dat.config.HibernateConfig;
import dat.daos.RatingDAO;
import dat.dtos.RatingDTO;
import dat.entities.Rating;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class RatingController implements IController<RatingDTO, Long> {

    private final RatingDAO dao;

    public RatingController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = RatingDAO.getInstance(emf);
    }

    @Override
    public void read(Context ctx) {
        long id = ctx.pathParamAsClass("id", Long.class).check(this::validatePrimaryKey, "Not a valid id").get();
        RatingDTO ratingDTO = dao.read(id);
        ctx.json(ratingDTO);
    }

    @Override
    public void readAll(Context ctx) {
        List<RatingDTO> ratingDTOs = dao.readAll();
        ctx.json(ratingDTOs);
    }

    @Override
    public void create(Context ctx) {
        RatingDTO jsonRequest = ctx.bodyAsClass(RatingDTO.class);
        RatingDTO ratingDTO = dao.create(jsonRequest);
        ctx.status(201).json(ratingDTO);
    }

    @Override
    public void update(Context ctx) {
        long id = ctx.pathParamAsClass("id", Long.class).check(this::validatePrimaryKey, "Not a valid id").get();
        RatingDTO ratingDTO = dao.update(id, validateEntity(ctx));
        ctx.json(ratingDTO);
    }

    @Override
    public void delete(Context ctx) {
        long id = ctx.pathParamAsClass("id", Long.class).check(this::validatePrimaryKey, "Not a valid id").get();
        dao.delete(id);
        ctx.status(204);
    }

    @Override
    public boolean validatePrimaryKey(Long id) {
        return dao.validatePrimaryKey(id);
    }

    @Override
    public RatingDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(RatingDTO.class)
                .check(r -> r.getScore() >= 0 && r.getScore() <= 10, "Rating must be between 0 and 10")
                .check(r -> r.getOriginality() >= 0 && r.getOriginality() <= 10, "Originality must be between 0 and 10")
                .check(r -> r.getSpicyness() >= 0 && r.getSpicyness() <= 10, "Spicyness must be between 0 and 10")
                .get();
    }

    public void sortByPopularity(@NotNull Context context) {
        List<RatingDTO> ratings = dao.readAll();
        ratings.sort(Comparator.comparingDouble(RatingDTO::getScore).reversed());
        context.json(ratings);
    }

    public void sortByOriginality(@NotNull Context context) {
        List<RatingDTO> ratings = dao.readAll();
        ratings.sort(Comparator.comparingDouble(RatingDTO::getOriginality).reversed());
        context.json(ratings);
    }

    public void sortBySpicyness(@NotNull Context context) {
        List<RatingDTO> ratings = dao.readAll();
        ratings.sort(Comparator.comparingDouble(RatingDTO::getSpicyness).reversed());
        context.json(ratings);
    }

    public void getLowestRated(@NotNull Context context) {
        List<RatingDTO> ratings = dao.readAll();
        ratings.sort(Comparator.comparingDouble(RatingDTO::getScore));
        context.json(ratings);
    }
}
