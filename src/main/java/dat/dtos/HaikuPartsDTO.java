package dat.dtos;

import java.util.List;
import java.util.stream.Collectors;

import dat.entities.Haiku;
import dat.entities.HaikuParts;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HaikuPartDTO {
    private Long id;
    private String content;
    private boolean isFiveSyllables;
    private List<Haiku> haikus;

    public HaikuPartDTO(HaikuPart haikuParts){
        this.id=haikuParts.getId();
        this.content=haikuParts.getContent();
        this.isFiveSyllables=haikuParts.isFiveSyllables();
    }

    public HaikuPartDTO(Long id, String content, boolean isFiveSyllables){
        this.id=id;
        this.content=content;
        this.isFiveSyllables=isFiveSyllables;
    }

    public HaikuPartDTO(String content, boolean isFiveSyllables){
        this.content=content;
        this.isFiveSyllables=isFiveSyllables;
    }

    public static List<HaikuPartDTO> toHaikuPartDTOList(List<HaikuPart> haikus) {
        return haikus.stream().map(HaikuDTO::new).collect(Collectors.toList());
    }
}