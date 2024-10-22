package dat.controllers;

import dat.config.HibernateConfig;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

/**
 * Purpose:
 *
 * @Author: Anton Friis Stengaard
 */
public class HaikuController implements IController{

    private final HaikuDAO dao;

    public HaikuController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = HaikuDAO.getInstance(emf);
    }
    @Override
    public void read(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // DTO
        HaikuDTO haikuDTO = dao.read(id);
        // response
        ctx.res().setStatus(200);
        ctx.json(haikuDTO, HaikuDTO.class);
    }

    @Override
    public void readAll(Context ctx) {
        // List of DTOS
        List<HaikuDTO> haikuDTOS = dao.readAll();
        // response
        ctx.res().setStatus(200);
        ctx.json(haikuDTOS, HaikuDTO.class);
    }

    @Override
    public void create(Context ctx) {
        // request
        HaikuDTO jsonRequest = ctx.bodyAsClass(HaikuDTO.class);
        // DTO
        HaikuDTO haikuDTO = dao.create(jsonRequest);
        // response
        ctx.res().setStatus(201);
        ctx.json(haikuDTO, HaikuDTO.class);
    }

    @Override
    public void update(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // dto
        HaikuDTO haikuDTO = dao.update(id, validateEntity(ctx));
        // response
        ctx.res().setStatus(200);
        ctx.json(haikuDTO, Haiku.class);
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
    public boolean validatePrimaryKey(Object o) {
        return dao.validatePrimaryKey(integer);
    }

    @Override
    public Object validateEntity(Context ctx) {
        return ctx.bodyValidator(HaikuDTO.class)
                .check( h -> h.getHaikuAuthor() != null && !h.getHaikuAuthor().isEmpty(), "Haiku author must be set")
                .check( h -> h.getHaikuParts() != null && !h.getHaikuParts().isEmpty(), "Haiku parts must be set")
                .check( h -> h.getHaikuDateCreated() != null, "Haiku creation date must be set")
                .get();
    }
}