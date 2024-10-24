package dat.dtos;

import dat.entities.Haiku;;
import dat.security.entities.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Builder
public class HaikuDTO {
    private Long id;
    private List<HaikuPartDTO> haikuParts = new ArrayList<>();
    private String author;
    private LocalDate dateCreated;
    private User user;
    private RatingDTO rating;


    public HaikuDTO(Haiku haiku) {
        this.id = haiku.getId();
        this.author = haiku.getAuthor();
        this.dateCreated = haiku.getDateCreated();
        this.user = haiku.getUser();
        if (haiku.getHaikuParts() != null) {
            haiku.getHaikuParts().forEach( part -> haikuParts.add(new HaikuPartDTO(part)));
        }
        if (haiku.getRating() != null) {
            this.rating = new RatingDTO(haiku.getRating());
        }
    }

    public HaikuDTO(Long id, List<HaikuPartDTO> haikuParts, String author, LocalDate dateCreated, User user,RatingDTO rating) {
        this.id = id;
        this.haikuParts = haikuParts;
        this.author = author;
        this.dateCreated = dateCreated;
        this.user = user;
        this.rating=rating;
    }


    public static List<HaikuDTO> toHaikuDTOList(List<Haiku> haikus) {
        return haikus.stream().map(HaikuDTO::new).collect(Collectors.toList());
    }
}
