package app.persistence.daos;

import java.util.List;

public interface GenericDAO<T, E> {
     T create(T type);

     T delete(T type);

     T getById(int id);

     List<T> getAll();

     T update(T dto);

     E toEntity(T dto);

     T toDTO(E entity);
}
