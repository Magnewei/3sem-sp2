package dat.dtos;

import java.util.List;
import java.util.stream.Collectors;

import dat.entities.Haiku;
import dat.entities.HaikuPart;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HaikuPartDTO {
    private long id;
    private String content;
    private boolean isFiveSyllables;
    private List<Haiku> haikus;

    public HaikuPartDTO(HaikuPart haikuParts){
        this.id=haikuParts.getId();
        this.content=haikuParts.getContent();
        this.isFiveSyllables=haikuParts.isFiveSyllables();
    }

    public HaikuPartDTO(long id, String content, boolean isFiveSyllables){
        this.id=id;
        this.content=content;
        this.isFiveSyllables=isFiveSyllables;
    }

    public HaikuPartDTO(String content, boolean isFiveSyllables){
        this.content=content;
        this.isFiveSyllables=isFiveSyllables;
    }

    public HaikuPartDTO(long id, String content, boolean isFiveSyllables, List<Haiku> haikus){
        this.id = id;
        this.content = content;
        this.isFiveSyllables = isFiveSyllables;
        this.haikus = haikus;
    }

    public static List<HaikuPartDTO> toHaikuPartDTOList(List<HaikuPart> haikus) {
        return haikus.stream().map(HaikuPartDTO::new).collect(Collectors.toList());
    }
}
