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
public class HaikuPartController implements IController{

    private final HaikuPartDAO dao;

    public HaikuPartController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = HaikuPartDAO.getInstance(emf);
    }
    @Override
    public void read(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // DTO
        HaikuPartDTO haikuPartDTO = dao.read(id);
        // response
        ctx.res().setStatus(200);
        ctx.json(haikuPartDTO, HaikuPartDTO.class);
    }

    @Override
    public void readAll(Context ctx) {
        // List of DTOS
        List<HaikuPartDTO> haikuPartDTOS = dao.readAll();
        // response
        ctx.res().setStatus(200);
        ctx.json(haikuPartDTOS, HaikuPartDTO.class);
    }

    @Override
    public void create(Context ctx) {
        // request
        HaikuPartDTO jsonRequest = ctx.bodyAsClass(HaikuPartDTO.class);
        // DTO
        HaikuPartDTO haikuPartDTO = dao.create(jsonRequest);
        // response
        ctx.res().setStatus(201);
        ctx.json(haikuPartDTO, HaikuPartDTO.class);
    }

    @Override
    public void update(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // dto
        HaikuPartDTO haikuPartDTO = dao.update(id, validateEntity(ctx));
        // response
        ctx.res().setStatus(200);
        ctx.json(haikuPartDTO, HaikuPart.class);
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
                .check( h -> h.getHaikuPartContent() != null && !h.getHaikuContent().isEmpty(), "Haiku Part content must be set")
                .check( h -> h.getHaikuPartIs5Syllables != null, "Haiku Part is 5 syllables must be set")
                .get();
    }
}
