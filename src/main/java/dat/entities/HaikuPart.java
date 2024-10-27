package dat.entities;

import dat.dtos.HaikuPartDTO;
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
public class HaikuPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false, name = "fiveSyllables")
    private boolean fiveSyllables;

    @ManyToMany(mappedBy = "haikuParts")
    private List<Haiku> haikus;

    public HaikuPart(HaikuPartDTO part) {
        if (part.getId() != null) this.id = part.getId();
        this.content = part.getContent();
        this.fiveSyllables = part.isFiveSyllables();
    }

    public HaikuPart(long id, String content, boolean fiveSyllables) {
        this.id = id;
        this.content = content;
        this.fiveSyllables = fiveSyllables;
    }
}
