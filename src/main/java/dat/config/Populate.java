package dat.config;

import dat.daos.HaikuDAO;
import dat.dtos.HaikuDTO;
import dat.dtos.HaikuPartDTO;
import dat.entities.Haiku;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Populate {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Haiku haiku = createVanillaHaiku();
            HaikuDAO dao = HaikuDAO.getInstance(emf);
            dao.create(new HaikuDTO(haiku));
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @NotNull
    private static Haiku createVanillaHaiku() {
        List<HaikuPartDTO> haikuParts = new ArrayList<>();
        haikuParts.add(new HaikuPartDTO("Breath mingles, we burn", true));
        haikuParts.add(new HaikuPartDTO("Bodies move in sultry waves", false));
        haikuParts.add(new HaikuPartDTO("Night surrenders slow", true));

        HaikuDTO haiku = new HaikuDTO(0L, haikuParts, "Gods lesson", LocalDate.now(), null);
        return new Haiku(haiku);
    }
}