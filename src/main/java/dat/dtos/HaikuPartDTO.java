package dat.dtos;

import dat.entities.HaikuPart;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class HaikuPartDTO {
    private Long id;
    private String content;
    private boolean isFiveSyllables;

    public HaikuPartDTO(HaikuPart haikuPart) {
        this.id = haikuPart.getId();
        this.content = haikuPart.getContent();
        this.isFiveSyllables = haikuPart.isFiveSyllables();
    }

    public HaikuPartDTO(Long id, String content, boolean isFiveSyllables) {
        if (id != null) this.id = id;
        this.content = content;
        this.isFiveSyllables = isFiveSyllables;
    }
}
