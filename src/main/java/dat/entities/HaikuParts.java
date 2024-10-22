package dat.entities;

import dat.dtos.HaikuPartsDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "haiku_parts")
public class HaikuParts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "haiku_id")
    private Haiku haiku;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean isFiveSyllables;

    @ManyToMany(mappedBy = "haikuParts")
    private List<Haiku> haikus;

    public HaikuParts(HaikuPartsDTO part) {
        this.id = part.getId();
        this.content = part.getContent();
        this.isFiveSyllables = part.isFiveSyllables();
    }
}