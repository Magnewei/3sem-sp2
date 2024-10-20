package app.persistence.daos;

import app.dtos.ExampleDTO;
import app.persistence.entities.ExampleEntity;
import jakarta.persistence.EntityManagerFactory;

public class ExampleDAO extends AbstractDAO<ExampleDTO, ExampleEntity> {

    public ExampleDAO(EntityManagerFactory emf, Class<ExampleDTO> hotelDTOClass, Class<ExampleEntity> hotelClass) {
        super(emf, hotelDTOClass, hotelClass);
    }

    @Override
    public ExampleEntity toEntity(ExampleDTO dto) {
        return null;
    }

    @Override
    public ExampleDTO toDTO(ExampleEntity entity) {
        return null;
    }

    @Override
    public Object getId(ExampleDTO dto) {
        return null;
    }
}