package dat.controllers;

import dat.config.HibernateConfig;
import dat.daos.HaikuDAO;
import dat.dtos.HaikuDTO;
import dat.dtos.RatingDTO;
import dat.entities.Haiku;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class HaikuController implements IController<HaikuDTO, Long> {

    private final HaikuDAO dao;

    public HaikuController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = HaikuDAO.getInstance(emf);
    }

    @Override
    public void read(Context ctx) {
        long id = ctx.pathParamAsClass("id", Long.class).check(this::validatePrimaryKey, "Not a valid id").get();
        HaikuDTO haikuDTO = dao.read(id);
        ctx.json(haikuDTO);
    }

    @Override
    public void readAll(Context ctx) {
        List<HaikuDTO> haikuDTOS = dao.readAll();
        ctx.json(haikuDTOS);
    }

    @Override
    public void create(Context ctx) {
        HaikuDTO jsonRequest = ctx.bodyAsClass(HaikuDTO.class);
        System.out.println(jsonRequest);
        HaikuDTO haikuDTO = dao.create(jsonRequest);
        System.out.println(haikuDTO);
        ctx.status(201).json(haikuDTO);
    }

    @Override
    public void update(Context ctx) {
        long id = ctx.pathParamAsClass("id", Long.class).check(this::validatePrimaryKey, "Not a valid id").get();
        System.out.println("Incoming request body: " + ctx.body());
        HaikuDTO haikuDTO = dao.update(id, validateEntity(ctx));
        ctx.json(haikuDTO);
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
    public HaikuDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(HaikuDTO.class)
                .check(h -> h.getAuthor() != null && !h.getAuthor().isEmpty(), "Haiku author must be set")
                .check(h -> h.getHaikuParts() != null && !h.getHaikuParts().isEmpty(), "Haiku parts must be set")
                .check(h -> h.getDateCreated() != null, "Haiku creation date must be set")
                .check(h -> h.getUser() != null, "Haiku user must be set")
                .check(h -> h.getRating() != null, "Haiku rating must be set")
                .get();
    }

    public void sortByScore(@NotNull Context context) {
        List<HaikuDTO> haikus = dao.readAll();

        // Sort by the score in the rating, handling null ratings safely
        haikus.sort(Comparator.comparingDouble(
                (HaikuDTO haiku) -> haiku.getRating() != null ? haiku.getRating().getScore() : 0
        ).reversed());

        context.json(haikus);  // Return sorted haikus as JSON
    }

    public void sortByOriginality(@NotNull Context context) {
        List<HaikuDTO> haikus = dao.readAll();

        // Sort by the originality in the rating, handling null ratings safely
        haikus.sort(Comparator.comparingDouble(
                (HaikuDTO haiku) -> haiku.getRating() != null ? haiku.getRating().getOriginality() : 0
        ).reversed());

        context.json(haikus);  // Return sorted haikus as JSON
    }

    public void sortBySpicyness(@NotNull Context context) {
        List<HaikuDTO> haikus = dao.readAll();

        // Sort by the spicyness in the rating, handling null ratings safely
        haikus.sort(Comparator.comparingDouble(
                (HaikuDTO haiku) -> haiku.getRating() != null ? haiku.getRating().getSpicyness() : 0
        ).reversed());

        context.json(haikus);  // Return sorted haikus as JSON
    }

    public void getLowestRated(@NotNull Context context) {
        List<HaikuDTO> haikus = dao.readAll();

        // Sort by the score in the rating, handling null ratings safely
        haikus.sort(Comparator.comparingDouble(
                (HaikuDTO haiku) -> haiku.getRating() != null ? haiku.getRating().getScore() : 0
        ));

        context.json(haikus);  // Return sorted haikus as JSON
    }
}
