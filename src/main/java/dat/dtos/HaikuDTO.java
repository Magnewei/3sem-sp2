package dat.dtos;

import dat.entities.Haiku;
import dat.entities.HaikuParts;
import dat.entities.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class HaikuDTO {
    private Long id;
    private List<HaikuPartDTO> haikuParts = new ArrayList<>();
    private String author;
    private LocalDate dateCreated;
    private User user;


    public HaikuDTO(Haiku haiku) {
        this.id = haiku.getId();
        this.author = haiku.getAuthor();
        this.dateCreated = haiku.getDateCreated();
        this.user = haiku.getUser();
        if (haiku.getHaikuParts() != null) {
            haiku.getHaikuParts().forEach( part -> haikuParts.add(new HaikuPartDTO(part)));
        }
    }

    public HaikuDTO(Long id, List<HaikuPartDTO> haikuParts, String author, LocalDate dateCreated, User user) {
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