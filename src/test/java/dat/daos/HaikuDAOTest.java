package dat.daos;

import dat.config.HibernateConfig;
import dat.dtos.HaikuDTO;
import dat.dtos.HaikuPartDTO;
import dat.entities.HaikuPart;
import dat.security.entities.Role;
import dat.security.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestInstance(Lifecycle.PER_CLASS)
class HaikuDAOTest {
    private static HaikuDAO haikuDAO;
    private HaikuDTO haikuDTO;

    @BeforeAll
    void beforeAll() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
        haikuDAO = HaikuDAO.getInstance(emf);

        Role userRole = new Role("USER");
        User user = new User("Jon", "hello", Set.of(userRole));

        HaikuPartDTO part1 = HaikuPartDTO.builder()
                .content("Line 1")
                .isFiveSyllables(true)
                .build();

        HaikuPartDTO part2 = HaikuPartDTO.builder()
                .content("Line 2")
                .isFiveSyllables(false)
                .build();

        HaikuPartDTO part3 = HaikuPartDTO.builder()
                .content("Line 3")
                .isFiveSyllables(false)
                .build();

        haikuDTO = HaikuDTO.builder()
                .user(user)
                .author("Test Author")
                .dateCreated(LocalDate.of(2023, 1, 1))
                .haikuParts(List.of(part1, part2, part3))
                .build();

        haikuDAO.create(haikuDTO);
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(userRole);
            em.persist(user);
            em.persist(new HaikuPart(part1));
            em.persist(new HaikuPart(part2));
            em.persist(new HaikuPart(part3));
            em.getTransaction().commit();
    }

}

@AfterAll
public void tearDown() {
    if (haikuDTO != null) haikuDAO.delete(haikuDTO.getId());
}

@Test
void read() {
    HaikuDTO fetchedHaikuDTO = haikuDAO.read(haikuDTO.getId());

    assertThat(fetchedHaikuDTO, is(notNullValue()));
    assertThat(fetchedHaikuDTO.getAuthor(), is("Test Author"));
    assertThat(fetchedHaikuDTO.getHaikuParts(), hasSize(2));
}

@Test
void readAll() {
    List<HaikuDTO> haikus = haikuDAO.readAll();

    assertThat(haikus, is(not(empty())));
    assertThat(haikus, hasItem(hasProperty("id", equalTo(haikuDTO.getId()))));
}

@Test
void create() {
    Role userRole = new Role("USER");
    User user = new User("Jon", "hello", Set.of(userRole));

    HaikuPartDTO part1 = HaikuPartDTO.builder()
            .id(1L)
            .content("Line 1")
            .isFiveSyllables(true)
            .build();

    HaikuPartDTO part2 = HaikuPartDTO.builder()
            .id(2L)
            .content("Line 2")
            .isFiveSyllables(false)
            .build();

    haikuDTO = HaikuDTO.builder()
            .id(1L)
            .user(user)
            .author("Test Author")
            .dateCreated(LocalDate.of(2023, 1, 1))
            .haikuParts(List.of(part1, part2))
            .build();

    assertThat(haikuDTO, is(notNullValue()));
    assertThat(haikuDTO.getAuthor(), is("New Author"));
    assertThat(haikuDTO.getHaikuParts(), hasSize(2));

    haikuDAO.delete(haikuDTO.getId());
}


@Test
void update() {
    haikuDTO.setAuthor("Updated Author");
    HaikuDTO updatedHaiku = haikuDAO.update(haikuDTO.getId(), haikuDTO);

    assertThat(updatedHaiku, is(notNullValue()));
    assertThat(updatedHaiku.getAuthor(), is("Updated Author"));
}

@Test
void delete() {
    haikuDAO.delete(haikuDTO.getId());

    HaikuDTO deletedHaiku = haikuDAO.read(haikuDTO.getId());
    assertThat(deletedHaiku, is(nullValue()));
}
}

