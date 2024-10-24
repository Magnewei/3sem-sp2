package dat.dtos;

import dat.entities.Rating;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Getter
@Setter
public class RatingDTO {
    private Long id;
    private double rating;
    private Long haikuId;

    public RatingDTO(Rating rating) {
        this.id = rating.getId();
        this.rating = rating.getRating();
        this.haikuId = rating.getHaiku().getId();
    }

    public RatingDTO(Long id, double rating, Long haikuId) {
        this.id = id;
        this.rating = rating;
        this.haikuId = haikuId;
    }

    public static List<RatingDTO> toRatingDTOList(List<Rating> ratings) {
        return ratings.stream().map(RatingDTO::new).collect(Collectors.toList());
    }
}
