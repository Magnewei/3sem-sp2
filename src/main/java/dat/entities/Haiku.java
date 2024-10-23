package dat.entities;

import dat.dtos.HaikuDTO;
import dat.security.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "haikus")
public class Haiku {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private LocalDate dateCreated;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "haiku_haiku_parts", // Join table name
            joinColumns = @JoinColumn(name = "haiku_id"), // Column for this entity (Haiku)
            inverseJoinColumns = @JoinColumn(name = "haiku_parts_id") // Column for the other entity (HaikuParts)
    )
    private List<HaikuPart> haikuParts = new ArrayList<>();

    @OneToOne(mappedBy = "haiku")
    private Rating rating;

    public Haiku(HaikuDTO haikuDTO){
        this.id=haikuDTO.getId();
        this.author=haikuDTO.getAuthor();
        this.dateCreated=haikuDTO.getDateCreated();
        this.user=haikuDTO.getUser();
        if (haikuDTO.getHaikuParts() != null) {
            haikuDTO.getHaikuParts().forEach( part -> haikuParts.add(new HaikuPart(part)));
        }
    }

    public void addHaikuPart(HaikuPart haikuPart) {
        if ( haikuPart != null) {
            this.haikuParts.add(haikuPart);
            List<Haiku> currentHaikus = haikuPart.getHaikus();
            currentHaikus.add(this);
            haikuPart.setHaikus(currentHaikus);

        }
    }

}
