package dat.dtos;

import dat.entities.Hotel;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class HaikuDTO {
    private Long id;
    private List<HaikuParts> haikuParts;
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

    public static List<HaikuDTO> toHaikuDTOList(List<Haiku> haikus) {
        return haikus.stream().map(HaikuDTO::new).collect(Collectors.toList());
    }
}
