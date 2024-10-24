package dat.daos;

import dat.dtos.CollectionDTO;
import dat.entities.Collection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class CollectionDAO implements IDAO<CollectionDTO, Integer> {

    private static CollectionDAO instance;
    private static EntityManagerFactory emf;

    public static CollectionDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CollectionDAO();
        }
        return instance;
    }

    @Override
    public CollectionDTO read(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Collection collection = em.find(Collection.class, integer);
            return new CollectionDTO(collection);
        }
    }

    @Override
    public List<CollectionDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<CollectionDTO> query = em.createQuery("SELECT new dat.dtos.CollectionDTO(c) FROM Collection c", CollectionDTO.class);
            return query.getResultList();
        }
    }

    @Override
    public CollectionDTO create(CollectionDTO collectionDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Collection collection = new Collection(collectionDTO);
            em.persist(collection);
            em.getTransaction().commit();
            return new CollectionDTO(collection);
        }
    }

    @Override
    public CollectionDTO update(Integer integer, CollectionDTO collectionDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Collection c = em.find(Collection.class, integer);
            c.setName(collectionDTO.getName());
            c.setHaikus(collectionDTO.getHaikus());
            Collection mergedCollection = em.merge(c);
            em.getTransaction().commit();
            return mergedCollection != null ? new CollectionDTO(mergedCollection) : null;
        }
    }

    @Override
    public void delete(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Collection collection = em.find(Collection.class, integer);
            if (collection != null) {
                em.remove(collection);
            }
            em.getTransaction().commit();
        }
    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Collection collection = em.find(Collection.class, integer);
            return collection != null;
        }
    }
}