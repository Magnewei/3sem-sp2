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
import java.util.function.Function;

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
        }
    }

    @Override
    public HaikuPartDTO read(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            HaikuPart haikuPart = em.find(HaikuPart.class, integer);
            return haikuPart != null ? new HaikuPartDTO(haikuPart) : null;
        }
    }

    @Override
    public List<HaikuPartDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<HaikuPartDTO> query = em.createQuery("SELECT new dat.dtos.HaikuPartDTO(h) FROM HaikuPart h", HaikuPartDTO.class);
            return query.getResultList();
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
        }
    }

    @Override
    public HaikuPartDTO update(Integer integer, HaikuPartDTO haikuPartDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            HaikuPart h = em.find(HaikuPart.class, integer);
            h.setContent(haikuPartDTO.getContent());
            h.setFiveSyllables(haikuPartDTO.isFiveSyllables());
            h.setHaikus(haikuPartDTO.getHaikus());
            HaikuPart mergedHaikuPart = em.merge(h);
            em.getTransaction().commit();
            return new HaikuPartDTO(mergedHaikuPart);
        }
    }

    @Override
    public void delete(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            HaikuPart haikuPart = em.find(HaikuPart.class, integer);
            if (haikuPart != null){
                em.remove(haikuPart);
            }
            em.getTransaction().commit();
        }
    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            HaikuPart haikuPart = em.find(HaikuPart.class, integer);
            return haikuPart != null;
        }
    }
}
