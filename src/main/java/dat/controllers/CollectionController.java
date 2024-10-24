package dat.controllers;

import dat.config.HibernateConfig;
import dat.daos.CollectionDAO;
import dat.dtos.CollectionDTO;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class CollectionController implements IController<CollectionDTO, Long> {

    private final CollectionDAO dao;

    public CollectionController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = CollectionDAO.getInstance(emf);
    }

    @Override
    public void read(Context ctx) {
        long id = ctx.pathParamAsClass("id", Long.class).check(this::validatePrimaryKey, "Not a valid id").get();
        CollectionDTO collectionDTO = dao.read(id);
        ctx.json(collectionDTO);
    }

    @Override
    public void readAll(Context ctx) {
        List<CollectionDTO> collectionDTOS = dao.readAll();
        ctx.json(collectionDTOS);
    }

    @Override
    public void create(Context ctx) {
        CollectionDTO jsonRequest = ctx.bodyAsClass(CollectionDTO.class);
        CollectionDTO collectionDTO = dao.create(jsonRequest);
        ctx.status(201).json(collectionDTO);
    }

    @Override
    public void update(Context ctx) {
        long id = ctx.pathParamAsClass("id", Long.class).check(this::validatePrimaryKey, "Not a valid id").get();
        CollectionDTO collectionDTO = dao.update(id, validateEntity(ctx));
        ctx.json(collectionDTO);
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
    public CollectionDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(CollectionDTO.class)
                .check(c -> c.getName() != null && !c.getName().isEmpty(), "Collection name must be set")
                .check(c -> c.getHaikus() != null && !c.getHaikus().isEmpty(), "Collection haikus must be set")
                .get();
    }
}
