package dat.entities;

import dat.dtos.RatingDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double score;

    @Column(nullable = false)
    private double spicyness;

    @Column(nullable = false)
    private double originality;

    @OneToOne
    @JoinColumn(name = "haiku_id")
    private Haiku haiku;

    public Rating(RatingDTO ratingDTO, Haiku haiku) {
        if (ratingDTO.getId() != null) this.id = ratingDTO.getId();
        this.score = ratingDTO.getScore();
        this.haiku = haiku;
    }
}
