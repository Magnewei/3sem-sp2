package dat.dtos;

import dat.entities.Haiku;
import dat.entities.HaikuPart;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class HaikuPartDTO {
    private Long id;
    private String content;
    private boolean fiveSyllables;
    private List<Haiku> haikus;

    public HaikuPartDTO(HaikuPart haikuParts) {
        this.id = haikuParts.getId();
        this.content = haikuParts.getContent();
        this.fiveSyllables = haikuParts.isFiveSyllables();
    }

    public HaikuPartDTO(Long id, String content, boolean fiveSyllables) {
        if (id != null) this.id = id;
        this.content = content;
        this.fiveSyllables = fiveSyllables;
    }

    public HaikuPartDTO(String content, boolean fiveSyllables) {
        this.content = content;
        this.fiveSyllables = fiveSyllables;
    }

    public HaikuPartDTO(Long id, String content, boolean fiveSyllables, List<Haiku> haikus) {
        if(id != null) this.id = id;
        this.content = content;
        this.fiveSyllables = fiveSyllables;
        this.haikus = haikus;
    }

    public static List<HaikuPartDTO> toHaikuPartDTOList(List<HaikuPart> haikus) {
        return haikus.stream().map(HaikuPartDTO::new).collect(Collectors.toList());
    }
}
