package dat.entities;

import dat.dtos.CollectionDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Purpose:
 *
 * @Author: Anton Friis Stengaard
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "collections")
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "collections")
    private List<Haiku> haikus = new ArrayList<>();

    public Collection(CollectionDTO collectionDTO) {
        if (collectionDTO.getId() != null) this.id = collectionDTO.getId();
        this.name = collectionDTO.getName();
        this.haikus = collectionDTO.getHaikus();
    }

    public CollectionDTO toDto() {
        return new CollectionDTO(id, name, haikus);
    }

}
