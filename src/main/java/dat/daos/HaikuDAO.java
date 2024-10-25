package dat.daos;

import dat.dtos.HaikuDTO;
import dat.entities.Haiku;
import dat.entities.HaikuPart;
import dat.exceptions.DatabaseException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class HaikuDAO implements IDAO<HaikuDTO, Long> {
    private static HaikuDAO instance;
    private static EntityManagerFactory emf;

    public static HaikuDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HaikuDAO();
        }
        return instance;
    }

    @Override
    public HaikuDTO read(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Haiku haiku = em.find(Haiku.class, id);
            return new HaikuDTO(haiku);
        } catch (Exception e) {
            throw new DatabaseException(500, "Error reading haiku from the database", e.getMessage());
        }
    }

    @Override
    public List<HaikuDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<HaikuDTO> query = em.createQuery("SELECT new dat.dtos.HaikuDTO(h) FROM Haiku h", HaikuDTO.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new DatabaseException(500, "Error reading haikus from the database", e.getMessage());
        }
    }

    @Override
    public HaikuDTO create(HaikuDTO haikuDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Haiku haiku = new Haiku(haikuDTO);

            for (HaikuPart part : haiku.getHaikuParts()) {
                if (part.getId() == null) {
                    em.persist(part);
                } else {
                    em.merge(part);
                }
            }

            Haiku mergedHaiku = em.merge(haiku); 
            em.getTransaction().commit();
            return new HaikuDTO(haiku);
        } catch (Exception e) {
            throw new DatabaseException(500, "Error creating haiku", e.getMessage());
        }
    }

    @Override
    public HaikuDTO update(Long id, HaikuDTO haikuDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Haiku h = em.find(Haiku.class, id);
            if (h == null) {
                throw new DatabaseException(404, "Haiku not found for update");
            }
            h.setHaikuParts(haikuDTO.getHaikuParts().stream().map(HaikuPart::new).toList());
            h.setAuthor(haikuDTO.getAuthor());
            h.setDateCreated(haikuDTO.getDateCreated());
            Haiku mergedHaiku = em.merge(h);
            em.getTransaction().commit();
            return new HaikuDTO(mergedHaiku);
        } catch (Exception e) {
            throw new DatabaseException(500, "Error updating haiku", e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Haiku haiku = em.find(Haiku.class, id);
            if (haiku == null) {
                throw new DatabaseException(404, "Haiku not found for deletion");
            }
            em.remove(haiku);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new DatabaseException(500, "Error deleting haiku", e.getMessage());
        }
    }

    @Override
    public boolean validatePrimaryKey(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Haiku haiku = em.find(Haiku.class, id);
            return haiku != null;
        } catch (Exception e) {
            throw new DatabaseException(500, "Error validating primary key", e.getMessage());
        }
    }
}

