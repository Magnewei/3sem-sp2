package dat.dtos;

import dat.entities.Haiku;
import dat.security.entities.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Builder
public class HaikuDTO {
    private Long id;
    private List<HaikuPartDTO> haikuParts;
    private String author;
    private LocalDate dateCreated;
    private User user;
    private RatingDTO rating;

    public HaikuDTO(Haiku haiku) {
        this.author = haiku.getAuthor();
        this.dateCreated = haiku.getDateCreated();

        if (haiku.getId() != null) this.id = haiku.getId();
        if (haiku.getUser() != null) this.user = haiku.getUser();
        if (haiku.getHaikuParts() != null) {
            this.haikuParts = haiku.getHaikuParts().stream().map(HaikuPartDTO::new).collect(Collectors.toList());
        }
        if (haiku.getRating() != null) {
            this.rating = new RatingDTO(haiku.getRating());
        }
    }

    public HaikuDTO( String author, LocalDate dateCreated) {
        this.author = author;
        this.dateCreated = dateCreated;
    }

    public HaikuDTO(Long id, List<HaikuPartDTO> haikuParts, String author, LocalDate dateCreated, User user, RatingDTO rating) {
        if (id != null) this.id = id;
        this.haikuParts = haikuParts;
        this.author = author;
        this.dateCreated = dateCreated;
        this.user = user;
        this.rating = rating;
    }
}
