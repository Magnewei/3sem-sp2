package app.persistence.daos;

import app.exceptions.JpaException;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

abstract class AbstractDAO<T, E> implements GenericDAO<T, E> {
    protected final EntityManagerFactory emf;
    protected Class<E> entityClass;
    protected Class<T> dtoClass;

    public AbstractDAO(EntityManagerFactory emf, Class<T> dtoClass, Class<E> entityClass) {
        this.emf = emf;
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    @Override
    public T create(T dto) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            E entity = toEntity(dto);
            em.persist(entity);
            em.getTransaction().commit();
            return dto;
        } catch (Exception e) {
            throw new JpaException("Error creating entity" + e.getMessage());
        }
    }

    @Override
    public T delete(T dto) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            E entity = em.find(getEntityClass(), getId(dto));
            em.remove(entity);
            em.getTransaction().commit();
            return dto;
        } catch (Exception e) {
            throw new JpaException("Error deleting entity" + e.getMessage());
        }
    }

    @Override
    public T getById(int id) {
        try (var em = emf.createEntityManager()) {
            E entity = em.find(getEntityClass(), id);
            return toDTO(entity);
        } catch (Exception e) {
            throw new JpaException("Error retrieving entity by id" + e.getMessage());
        }
    }

    @Override
    public List<T> getAll() {
        try (var em = emf.createEntityManager()) {
            List<E> entities = em.createQuery("SELECT e FROM " + getEntityClass().getSimpleName() + " e", getEntityClass()).getResultList();
            return entities.stream().map(this::toDTO).toList();
        } catch (Exception e) {
            throw new JpaException("Error retrieving all entities" + e.getMessage());
        }
    }

    @Override
    public T update(T dto) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(toEntity(dto));
            em.getTransaction().commit();
            return dto;
        } catch (Exception e) {
            throw new JpaException("Error updating entity"+ e.getMessage());
        }
    }

    @Override
    public abstract E toEntity(T dto);

    @Override
    public abstract T toDTO(E entity);

    public abstract Object getId(T dto);

    protected Class<E> getEntityClass() {
        return entityClass;
    }
}
