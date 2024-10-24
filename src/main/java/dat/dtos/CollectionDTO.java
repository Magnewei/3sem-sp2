package dat.dtos;

import dat.entities.Collection;
import dat.entities.Haiku;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Purpose:
 *
 * @Author: Anton Friis Stengaard
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionDTO {
    private Long id;
    private String name;
    private List<Haiku> haikus;

    public CollectionDTO(Collection collection) {
        this.id = collection.getId();
        this.name = collection.getName();
        this.haikus = collection.getHaikus();
    }

    public Collection toEntity() {
        return new Collection(id, name, haikus);
    }
}
