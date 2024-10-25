package dat.entities;

import dat.dtos.HaikuPartDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
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

    @Column(nullable = false)
    private boolean isFiveSyllables;

    @ManyToMany(mappedBy = "haikuParts", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private List<Haiku> haikus = new ArrayList<>();

    public HaikuPart(HaikuPartDTO part) {
        if (part.getId() != null) this.id = part.getId();
        this.content = part.getContent();
        this.isFiveSyllables = part.isFiveSyllables();
    }
}
