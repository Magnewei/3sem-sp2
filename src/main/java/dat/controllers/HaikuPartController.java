package dat.controllers;

import dat.config.HibernateConfig;
import dat.daos.HaikuPartDAO;
import dat.dtos.HaikuPartDTO;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class HaikuPartController implements IController<HaikuPartDTO, Integer> {

    private final HaikuPartDAO dao;

    public HaikuPartController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = HaikuPartDAO.getInstance(emf);
    }

    @Override
    public void read(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        HaikuPartDTO haikuPartDTO = dao.read(id);
        ctx.json(haikuPartDTO);
    }

    @Override
    public void readAll(Context ctx) {
        List<HaikuPartDTO> haikuPartDTOS = dao.readAll();
        ctx.json(haikuPartDTOS);
    }

    @Override
    public void create(Context ctx) {
        HaikuPartDTO jsonRequest = ctx.bodyAsClass(HaikuPartDTO.class);
        HaikuPartDTO haikuPartDTO = dao.create(jsonRequest);
        ctx.status(201).json(haikuPartDTO);
    }

    @Override
    public void update(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        HaikuPartDTO haikuPartDTO = dao.update(id, validateEntity(ctx));
        ctx.json(haikuPartDTO);
    }

    @Override
    public void delete(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        dao.delete(id);
        ctx.status(204);
    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        return dao.validatePrimaryKey(integer);
    }

    @Override
    public HaikuPartDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(HaikuPartDTO.class)
                .check(h -> h.getContent() != null && !h.getContent().isEmpty(), "Haiku Part content must be set")
                .get();
    }
}
