package dat.daos;

import dat.dtos.HaikuDTO;
import dat.entities.Haiku;
import dat.entities.HaikuPart;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class HaikuDAO implements IDAO<HaikuDTO, Integer> {

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
    public HaikuDTO read(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Haiku haiku = em.find(Haiku.class, integer);
            return new HaikuDTO(haiku);
        }
    }

    @Override
    public List<HaikuDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<HaikuDTO> query = em.createQuery("SELECT new dat.dtos.HaikuDTO(h) FROM Haiku h", HaikuDTO.class);
            return query.getResultList();
        }
    }

    @Override
    public HaikuDTO create(HaikuDTO haikuDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Haiku haiku = new Haiku(haikuDTO);

            // Save HaikuPart entities first
            for (HaikuPart part : haiku.getHaikuParts()) {
                if (part.getId() == null) {
                    em.persist(part);
                } else {
                    em.merge(part);
                }
            }

            Haiku mergedHaiku = em.merge(haiku); // Use merge instead of persist
            em.getTransaction().commit();
            return new HaikuDTO(mergedHaiku);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Could not create haiku");
        }
    }

    @Override
    public HaikuDTO update(Integer integer, HaikuDTO haikuDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Haiku h = em.find(Haiku.class, integer);
            h.setHaikuParts(haikuDTO.getHaikuParts().stream().map(HaikuPart::new).toList());
            h.setAuthor(haikuDTO.getAuthor());
            h.setDateCreated(haikuDTO.getDateCreated());
            Haiku mergedHaiku = em.merge(h);
            em.getTransaction().commit();
            return mergedHaiku != null ? new HaikuDTO(mergedHaiku) : null;
        }
    }

    @Override
    public void delete(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Haiku haiku = em.find(Haiku.class, integer);
            if (haiku != null) {
                em.remove(haiku);
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