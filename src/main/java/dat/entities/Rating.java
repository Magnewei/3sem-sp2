package dat.entities;

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
    private double rating;

    @Column(nullable = false)
    private double spicyness;

    @Column(nullable = false)
    private double originality;

    @OneToOne
    @JoinColumn(name = "haiku_id")
    private Haiku haiku;
}
