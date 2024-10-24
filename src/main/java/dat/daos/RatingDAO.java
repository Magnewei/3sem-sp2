package dat.daos;

import dat.dtos.HaikuDTO;
import dat.dtos.HaikuPartDTO;
import dat.dtos.RatingDTO;
import dat.entities.Haiku;
import dat.entities.HaikuPart;
import dat.entities.Rating;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Set;

public class RatingDAO implements IDAO<RatingDTO, Integer> {

    private static RatingDAO instance;
    private static EntityManagerFactory emf;



    public static RatingDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new RatingDAO();
        }
        return instance;
    }

    @Override
    public RatingDTO read(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Rating rating = em.find(Rating.class, integer);
            return new RatingDTO(rating);
        }
    }

    @Override
    public List<RatingDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<RatingDTO> query = em.createQuery("SELECT new dat.dtos.RatingDTO(h) FROM Rating h", RatingDTO.class);
            return query.getResultList();
        }
    }

    @Override
    public RatingDTO create(RatingDTO ratingDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Haiku haiku = em.find(Haiku.class, ratingDTO.getHaikuId());
            if (haiku == null) {
                throw new RuntimeException("Haiku not found with id: " + ratingDTO.getHaikuId());
            }
            Rating rating = new Rating(ratingDTO, haiku);
            em.persist(rating);
            em.getTransaction().commit();
            return new RatingDTO(rating);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Could not create haiku");
        }
    }

    @Override
    public RatingDTO update(Integer integer, RatingDTO ratingDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Rating h = em.find(Rating.class, integer);
            h.setRating(ratingDTO.getRating());

            Rating mergedRating = em.merge(h);
            em.getTransaction().commit();
            return mergedRating != null ? new RatingDTO(mergedRating) : null;
        }
    }

    @Override
    public void delete(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Rating rating = em.find(Rating.class, integer);
            if (rating != null) {
                em.remove(rating);
            }
            em.getTransaction().commit();
        }
    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Haiku haiku = em.find(Haiku.class, integer);
            return haiku != null;
        }
    }

}

