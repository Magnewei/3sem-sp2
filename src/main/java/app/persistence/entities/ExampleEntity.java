package app.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users") // table navnet skal være i flertal
public class ExampleEntity {

    @Id
    @GeneratedValue()
    private long Id;

    @Column(name = "name")
    private String name;
}
