package dat.daos;

import dat.dtos.HaikuDTO;
import dat.dtos.HaikuPartDTO;
import dat.entities.Haiku;
import dat.entities.HaikuPart;
import dat.exceptions.DatabaseException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class HaikuPartDAO implements IDAO<HaikuPartDTO, Integer> {

    private static HaikuPartDAO instance;
    private static EntityManagerFactory emf;

    public static HaikuPartDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HaikuPartDAO();
        }
        return instance;
    }

    public HaikuDTO addHaikuPartToHaiku(Integer id, HaikuPartDTO haikuPartDTO ) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            HaikuPart haikuPart = new HaikuPart(haikuPartDTO);
            Haiku haiku = em.find(Haiku.class, id);
            haiku.addHaikuPart(haikuPart);
            em.persist(haikuPart);
            Haiku mergedHaiku = em.merge(haiku);
            em.getTransaction().commit();
            return new HaikuDTO(mergedHaiku);
        } catch (Exception e) {
            throw new DatabaseException(500, "Error adding HaikuPart to Haiku", e.getCause());
        }
    }

    @Override
    public HaikuPartDTO read(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            HaikuPart haikuPart = em.find(HaikuPart.class, integer);
            return haikuPart != null ? new HaikuPartDTO(haikuPart) : null;
        } catch (Exception e) {
            throw new DatabaseException(500, "Error reading HaikuPart from the database", e.getCause());
        }
    }

    @Override
    public List<HaikuPartDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<HaikuPartDTO> query = em.createQuery("SELECT new dat.dtos.HaikuPartDTO(h) FROM HaikuPart h", HaikuPartDTO.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new DatabaseException(500, "Error reading HaikuParts from the database", e.getCause());
        }
    }

    @Override
    public HaikuPartDTO create(HaikuPartDTO haikuPartDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            HaikuPart haikuPart = new HaikuPart(haikuPartDTO);
            em.persist(haikuPart);
            em.getTransaction().commit();
            return new HaikuPartDTO(haikuPart);
        } catch (Exception e) {
            throw new DatabaseException(500, "Error creating HaikuPart", e.getCause());
        }
    }

    @Override
    public HaikuPartDTO update(Integer integer, HaikuPartDTO haikuPartDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            HaikuPart h = em.find(HaikuPart.class, integer);
            if (h == null) {
                throw new DatabaseException(404, "HaikuPart not found for update", null);
            }
            h.setContent(haikuPartDTO.getContent());
            h.setFiveSyllables(haikuPartDTO.isFiveSyllables());
            h.setHaikus(haikuPartDTO.getHaikus());
            HaikuPart mergedHaikuPart = em.merge(h);
            em.getTransaction().commit();
            return new HaikuPartDTO(mergedHaikuPart);
        } catch (Exception e) {
            throw new DatabaseException(500, "Error updating HaikuPart", e.getCause());
        }
    }

    @Override
    public void delete(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            HaikuPart haikuPart = em.find(HaikuPart.class, integer);
            if (haikuPart != null) {
                em.remove(haikuPart);
            } else {
                throw new DatabaseException(404, "HaikuPart not found for deletion", null);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new DatabaseException(500, "Error deleting HaikuPart", e.getCause());
        }
    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            HaikuPart haikuPart = em.find(HaikuPart.class, integer);
            return haikuPart != null;
        } catch (Exception e) {
            throw new DatabaseException(500, "Error validating primary key", e.getCause());
        }
    }
}
