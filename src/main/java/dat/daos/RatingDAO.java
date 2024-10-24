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
        } catch (Exception e) {
            throw new DatabaseException(500, "Error reading Rating from the database", e.getCause());
        }
    }

    @Override
    public List<RatingDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<RatingDTO> query = em.createQuery("SELECT new dat.dtos.RatingDTO(h) FROM Rating h", RatingDTO.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new DatabaseException(500, "Error reading Ratings from the database", e.getCause());
        }
    }

    @Override
    public RatingDTO create(RatingDTO ratingDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Haiku haiku = em.find(Haiku.class, ratingDTO.getHaikuId());
            if (haiku == null) {
                throw new DatabaseException(404, "Haiku not found with id: " + ratingDTO.getHaikuId(), null);
            }
            Rating rating = new Rating(ratingDTO, haiku);
            em.persist(rating);
            em.getTransaction().commit();
            return new RatingDTO(rating);
        } catch (Exception e) {
            throw new DatabaseException(500, "Could not create rating", e.getCause());
        }
    }

    @Override
    public RatingDTO update(Integer integer, RatingDTO ratingDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Rating h = em.find(Rating.class, integer);

            if (h == null) {
                throw new DatabaseException(404, "Rating not found for update", null);
            }
            h.setScore(ratingDTO.getScore());

            Rating mergedRating = em.merge(h);
            em.getTransaction().commit();
            return mergedRating != null ? new RatingDTO(mergedRating) : null;
        } catch (Exception e) {
            throw new DatabaseException(500, "Error updating rating", e.getCause());
        }
    }

    @Override
    public void delete(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Rating rating = em.find(Rating.class, integer);
            if (rating == null) {
                throw new DatabaseException(404, "Rating not found for deletion", null);
            }
            em.remove(rating);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new DatabaseException(500, "Error deleting rating", e.getCause());
        }
    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Rating rating = em.find(Rating.class, integer);
            return rating != null;
        } catch (Exception e) {
            throw new DatabaseException(500, "Error validating primary key", e.getCause());
        }
    }
}
