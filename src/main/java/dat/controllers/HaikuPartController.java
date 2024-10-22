package dat.controllers;

import dat.config.HibernateConfig;
import dat.daos.HaikuDAO;
import dat.dtos.HaikuDTO;
import dat.dtos.HaikuPartDTO;
import dat.entities.HaikuPart;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import dat.daos.HaikuPartDAO;

import java.util.List;

/**
 * Purpose:
 *
 * @Author: Anton Friis Stengaard
 */
public class HaikuPartController implements IController<HaikuPartDTO, Integer> {

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
    public boolean validatePrimaryKey(Integer integer) {
        return dao.validatePrimaryKey(integer);
    }

    @Override
    public HaikuPartDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(HaikuPartDTO.class)
                .check( h -> h.getContent() != null && !h.getContent().isEmpty(), "Haiku Part content must be set")
                .get();
    }
}
