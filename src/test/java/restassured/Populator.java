package restassured;

import dat.dtos.HaikuDTO;
import dat.dtos.HaikuPartDTO;
import dat.entities.Haiku;
import dat.security.entities.User;
import dat.security.entities.Role;
import dk.bugelhartmann.UserDTO;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.List;

public class Populator {

    public static UserDTO[] populateUsers(EntityManagerFactory emf) {
        User user = new User("usertest", "user123");
        User admin = new User("admintest", "admin123");
        Role userRole = new Role("USER");
        Role adminRole = new Role("ADMIN");

        user.addRole(userRole);
        admin.addRole(adminRole);

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.getTransaction().commit();
        }

        UserDTO userDTO = new UserDTO(user.getUsername(), "user123");
        UserDTO adminDTO = new UserDTO(admin.getUsername(), "admin123");
        return new UserDTO[]{userDTO, adminDTO};
    }

    public static Haiku[] populateHaikus(EntityManagerFactory emf) {
        HaikuDTO haikuDTO1 = HaikuDTO.builder()
                .author("Author One")
                .dateCreated(LocalDate.now())
                .haikuParts(List.of(
                        HaikuPartDTO.builder().content("A light snowfall").fiveSyllables(true).build(),
                        HaikuPartDTO.builder().content("Silent footsteps pass").fiveSyllables(false).build(),
                        HaikuPartDTO.builder().content("In the morning calm").fiveSyllables(true).build()
                ))
                .build();
        HaikuDTO haikuDTO2 = HaikuDTO.builder()
                .author("Author Two")
                .dateCreated(LocalDate.now().minusDays(1))
                .haikuParts(List.of(
                        HaikuPartDTO.builder().content("Waves crashing softly").fiveSyllables(true).build(),
                        HaikuPartDTO.builder().content("Against the dark shore").fiveSyllables(false).build(),
                        HaikuPartDTO.builder().content("Fading with the night").fiveSyllables(true).build()
                ))
                .build();
        Haiku haiku1 = new Haiku(haikuDTO1);
        Haiku haiku2 = new Haiku(haikuDTO2);

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(haiku1);
            em.persist(haiku2);
            em.getTransaction().commit();
        }

        return new Haiku[]{haiku1, haiku2};
    }
}
