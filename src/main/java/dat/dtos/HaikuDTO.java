package dat.dtos;

import dat.entities.Haiku;
import dat.security.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HaikuDTO {
    private Long id;
    private String author;
    private LocalDate dateCreated;
    private User user;
    private List<HaikuPartDTO> haikuParts = new ArrayList<>();
    private RatingDTO rating;
    private CollectionDTO collection;

    public HaikuDTO(Haiku haiku) {
        if (haiku.getId() != null) this.id = haiku.getId();
        this.author = haiku.getAuthor();
        this.dateCreated = haiku.getDateCreated();
        this.user = haiku.getUser();
        if (haiku.getHaikuParts() != null) {
            haiku.getHaikuParts().forEach(part -> haikuParts.add(new HaikuPartDTO(part)));
        }
        if (haiku.getRating() != null) {
            this.rating = new RatingDTO(haiku.getRating());
        }
    }

    public HaikuDTO(Long id, String author, LocalDate dateCreated, User user, List<HaikuPartDTO> haikuParts) {
        if (id != null) this.id = id;
        this.haikuParts = haikuParts;
        this.author = author;
        this.dateCreated = dateCreated;
        this.user = user;
        this.rating = rating;
    }


    public static List<HaikuDTO> toHaikuDTOList(List<Haiku> haikus) {
        return haikus.stream().map(HaikuDTO::new).collect(Collectors.toList());
    }
}
