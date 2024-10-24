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
    private Long haikuId;
    private double score;
    private double spicyness;
    private double originality;

    public RatingDTO(Rating rating) {
        this.id = rating.getId();
        this.score = rating.getScore();
        this.haikuId = rating.getHaiku().getId();
        this.spicyness = rating.getSpicyness();
        this.originality = rating.getOriginality();
    }

    public RatingDTO(Long id, double rating, Long haikuId) {
        this.id = id;
        this.score = rating;
        this.haikuId = haikuId;
    }

    public RatingDTO(Long id, Long haikuId, double rating, double spicyness, double originality) {
        this.id = id;
        this.haikuId = haikuId;
        this.score = rating;
        this.spicyness = spicyness;
        this.originality = originality;
    }

    public static List<RatingDTO> toRatingDTOList(List<Rating> ratings) {
        return ratings.stream().map(RatingDTO::new).collect(Collectors.toList());
    }
}
