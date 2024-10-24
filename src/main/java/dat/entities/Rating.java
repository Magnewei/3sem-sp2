package dat.entities;

import dat.dtos.RatingDTO;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private double score;

    @Column(nullable = false)
    private double spicyness;

    @Column(nullable = false)
    private double originality;

    @OneToOne
    @JoinColumn(name = "haiku_id")
    private Haiku haiku;


    public Rating(RatingDTO ratingDTO, Haiku haiku){
        this.id=ratingDTO.getId();
        this.score =ratingDTO.getScore();
        this.haiku = haiku;
    }
}
