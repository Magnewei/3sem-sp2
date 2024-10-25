package dat.daos;


import dat.dtos.HaikuDTO;
import dat.dtos.RatingDTO;
import dat.entities.Haiku;
import dat.entities.Rating;
import dat.exceptions.DatabaseException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class RatingDAO implements IDAO<RatingDTO, Long> {

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
    public RatingDTO read(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Rating rating = em.find(Rating.class, id);
            return new RatingDTO(rating);
        } catch (Exception e) {
            throw new DatabaseException(500, "Error reading Rating from the database", e.getMessage());
        }
    }

    @Override
    public List<RatingDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<RatingDTO> query = em.createQuery("SELECT new dat.dtos.RatingDTO(h) FROM Rating h", RatingDTO.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new DatabaseException(500, "Error reading Ratings from the database", e.getMessage());
        }
    }

    @Override
    public RatingDTO create(RatingDTO ratingDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Haiku haiku = em.find(Haiku.class, ratingDTO.getHaikuId());
            if (haiku == null) {
                throw new DatabaseException(404, "Haiku not found with id: " + ratingDTO.getHaikuId());
            }
            Rating rating = new Rating(ratingDTO, haiku);
            em.persist(rating);
            em.getTransaction().commit();
            return new RatingDTO(rating);
        } catch (Exception e) {
            throw new DatabaseException(500, "Could not create rating", e.getMessage());
        }
    }

    @Override
    public RatingDTO update(Long id, RatingDTO ratingDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Rating h = em.find(Rating.class, id);

            if (h == null) {
                throw new DatabaseException(404, "Rating not found for update");
            }
            h.setScore(ratingDTO.getScore());

            Rating mergedRating = em.merge(h);
            em.getTransaction().commit();
            return mergedRating != null ? new RatingDTO(mergedRating) : null;
        } catch (Exception e) {
            throw new DatabaseException(500, "Error updating rating", e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Rating rating = em.find(Rating.class, id);
            if (rating == null) {
                throw new DatabaseException(404, "Rating not found for deletion");
            }
            em.remove(rating);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new DatabaseException(500, "Error deleting rating", e.getMessage());
        }
    }

    @Override
    public boolean validatePrimaryKey(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Rating rating = em.find(Rating.class, id);
            return rating != null;
        } catch (Exception e) {
            throw new DatabaseException(500, "Error validating primary key", e.getMessage());
        }
    }
}
