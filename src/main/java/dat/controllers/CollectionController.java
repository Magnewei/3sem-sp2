package dat.controllers;

import dat.config.HibernateConfig;
import dat.daos.CollectionDAO;
import dat.dtos.CollectionDTO;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

/**
 * Purpose:
 *
 * @Author: Anton Friis Stengaard
 */
public class CollectionController implements IController<CollectionDTO, Integer> {

    private final CollectionDAO dao;

    public CollectionController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = CollectionDAO.getInstance(emf);
    }

    @Override
    public void read(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // DTO
        CollectionDTO collectionDTO = dao.read(id);
        // response
        ctx.res().setStatus(200);
        ctx.json(collectionDTO, CollectionDTO.class);
    }

    @Override
    public void readAll(Context ctx) {
        // List of DTOS
        List<CollectionDTO> collectionDTOS = dao.readAll();
        // response
        ctx.res().setStatus(200);
        ctx.json(collectionDTOS, CollectionDTO.class);
    }

    @Override
    public void create(Context ctx) {
        // request
        CollectionDTO jsonRequest = ctx.bodyAsClass(CollectionDTO.class);
        // DTO
        CollectionDTO collectionDTO = dao.create(jsonRequest);
        // response
        ctx.res().setStatus(201);
        ctx.json(collectionDTO, CollectionDTO.class);
    }

    @Override
    public void update(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // dto
        CollectionDTO collectionDTO = dao.update(id, validateEntity(ctx));
        // response
        ctx.res().setStatus(200);
        ctx.json(collectionDTO, CollectionDTO.class);
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

    /**
     * Validates the CollectionDTO entity from the request body.
     * If any validation fails, a 400 Bad Request response is returned with an error message.
     */
    @Override
    public CollectionDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(CollectionDTO.class)
                .check(c -> c.getName() != null && !c.getName().isEmpty(), "Collection name must be set")
                .check(c -> c.getHaikus() != null && !c.getHaikus().isEmpty(), "Collection haikus must be set")
                .get();
    }
}