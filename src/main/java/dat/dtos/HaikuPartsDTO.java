package dat.dtos;

import java.util.List;
import java.util.stream.Collectors;

import dat.entities.Haiku;
import dat.entities.HaikuParts;

public class HaikuPartsDTO {
    private Long id;
    private String content;
    private boolean isFiveSyllables;
    private List<Haiku> haikus;

    public HaikuPartsDTO(HaikuParts haikuParts){
        this.id=haikuParts.getId();
        this.content=haikuParts.getContent();
        this.isFiveSyllables=haikuParts.isFiveSyllables();
    }

    public HaikuPartsDTO(Long id, String content, boolean isFiveSyllables){
        this.id=id;
        this.content=content;
        this.isFiveSyllables=isFiveSyllables;
    }

    public static List<HaikuPartsDTO> toHaikuPartDTOList(List<HaikuParts> haikus) {
        return haikus.stream().map(HaikuDTO::new).collect(Collectors.toList());
    }
}
