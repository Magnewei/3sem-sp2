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

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "haiku_haiku_parts",
            joinColumns = @JoinColumn(name = "haiku_id"),
            inverseJoinColumns = @JoinColumn(name = "haiku_parts_id")
    )
    private List<HaikuPart> haikuParts = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "collection_id")
    private List<Collection> collections = new ArrayList<>();

    @OneToOne(mappedBy = "haiku")
    private Rating rating;

    public Haiku(HaikuDTO haikuDTO) {
        this.author = haikuDTO.getAuthor();
        this.dateCreated = haikuDTO.getDateCreated();
        if (haikuDTO.getId() != null) this.id = haikuDTO.getId();
        if (haikuDTO.getUser() != null) this.user = haikuDTO.getUser();
        if (haikuDTO.getHaikuParts() != null) {
            haikuDTO.getHaikuParts().forEach(part -> haikuParts.add(new HaikuPart(part)));
        }
    }
}
