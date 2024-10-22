package dat.dtos;

import dat.entities.Haiku;
import dat.entities.HaikuPart;
import dat.entities.User;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class HaikuDTO {
    private Long id;
    private List<HaikuPart> haikuParts;
    private String author;
    private LocalDate dateCreated;
    private User user;


    public HaikuDTO(Haiku haiku) {
        this.id = haiku.getId();
        this.haikuParts = haiku.getHaikuParts();
        this.author = haiku.getAuthor();
        this.dateCreated = haiku.getDateCreated();
        this.user = haiku.getUser();
    }

    public HaikuDTO(Long id, List<HaikuPart> haikuParts, String author, LocalDate dateCreated, User user) {
        this.id = id;
        this.haikuParts = haikuParts;
        this.author = author;
        this.dateCreated = dateCreated;
        this.user = user;
    }

    public static List<HaikuDTO> toHaikuDTOList(List<Haiku> haikus) {
        return haikus.stream().map(HaikuDTO::new).collect(Collectors.toList());
    }

}
