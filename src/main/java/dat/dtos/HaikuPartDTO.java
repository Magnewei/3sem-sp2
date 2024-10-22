package dat.dtos;

import java.util.List;
import java.util.stream.Collectors;

import dat.entities.Haiku;
import dat.entities.HaikuPart;
import lombok.Data;

@Data
public class HaikuPartDTO {
    private Long id;
    private String content;
    private boolean isFiveSyllables;
    private List<Haiku> haikus;

    public HaikuPartDTO(HaikuPart haikuPart){
        this.id= haikuPart.getId();
        this.content= haikuPart.getContent();
        this.isFiveSyllables= haikuPart.isFiveSyllables();
    }

    public HaikuPartDTO(Long id, String content, boolean isFiveSyllables){
        this.id=id;
        this.content=content;
        this.isFiveSyllables=isFiveSyllables;
    }

    public static List<HaikuPartDTO> toHaikuPartDTOList(List<HaikuPart> haikus) {
        return haikus.stream().map(HaikuDTO::new).collect(Collectors.toList());
    }
}
