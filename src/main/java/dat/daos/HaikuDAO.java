package dat.daos;

import dat.dtos.HaikuDTO;
import dat.dtos.HaikuPartDTO;
import dat.entities.Haiku;
import dat.entities.HaikuPart;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class HaikuDAO implements IDAO<HaikuDTO, Integer> {

    private static HaikuDAO instance;
    private static EntityManagerFactory emf;

    Set<HaikuPart> calParts = getCalParts();
    Set<HaikuPart> hilParts = getHilParts();

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
            em.persist(haiku);
            em.getTransaction().commit();
            return new HaikuDTO(haiku);
        }
    }

    @Override
    public HaikuDTO update(Integer integer, HaikuDTO haikuDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Haiku h = em.find(Haiku.class, integer);
            h.setHaikuParts(haikuDTO.getHaikuParts());
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

    private static Set<HaikuPartDTO> getCalParts() {
        HaikuPartDTO cal1 = new HaikuPartDTO(1L, "Kevin at the beach", true);
        HaikuPartDTO cal2 = new HaikuPartDTO(2L, "Getting whole body burned", false);
        HaikuPartDTO cal3 = new HaikuPartDTO(3L, "Beach killed Kevin", true);

        HaikuPartDTO[] haikuPartArray = {cal1,cal2,cal3};
        return Set.of(haikuPartArray);
    }

    private static Set<HaikuPartDTO> getHilParts() {
        HaikuPartDTO hil1 = new HaikuPartDTO(4L, "Kevin gets a soul", true);
        HaikuPartDTO hil2 = new HaikuPartDTO(5L, "A mortal life sacrificed", false);
        HaikuPartDTO hil3 = new HaikuPartDTO(6L, "Ginger life easy", true);

        HaikuPartDTO[] haikuPartArray = {hil1,hil2,hil3};
        return Set.of(haikuPartArray);
    }
}
