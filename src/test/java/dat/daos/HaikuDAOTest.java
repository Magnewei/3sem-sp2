package dat.daos;

import dat.config.HibernateConfig;
import dat.dtos.HaikuDTO;
import dat.dtos.HaikuPartDTO;
import dat.entities.Haiku;
import dat.entities.HaikuPart;
import dat.security.entities.Role;
import dat.security.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class HaikuDAOTest {
    private static EntityManagerFactory emf;
    private static HaikuDAO haikuDAO;
    private HaikuDTO haikuDTO;

    @BeforeAll
    static void beforeAll() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        haikuDAO = HaikuDAO.getInstance(emf);
    }

    @BeforeEach
    void setUp() {
        User user = new User("Jon", "Dådyr", Set.of(new Role("user")));

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

        part2.setHaikus(List.of(new Haiku(haikuDTO)));
        part1.setHaikus(List.of(new Haiku(haikuDTO)));


        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(user);
        em.merge(new HaikuPart(part1));
        em.merge(new HaikuPart(part2));
        em.getTransaction().commit();
        em.close();

        haikuDTO = haikuDAO.create(haikuDTO);


    }

    @AfterEach
    void tearDown() {
        if (haikuDTO != null) {
            haikuDAO.delete(haikuDTO.getId());
        }
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
    void $create() {
        HaikuPartDTO part1 = HaikuPartDTO.builder()
                .content("New Line 1")
                .isFiveSyllables(true)
                .build();

        HaikuPartDTO part2 = HaikuPartDTO.builder()
                .content("New Line 2")
                .isFiveSyllables(false)
                .build();

        HaikuDTO newHaikuDTO = HaikuDTO.builder()
                .author("New Author")
                .haikuParts(List.of(part1, part2))
                .build();

        HaikuDTO createdHaiku = haikuDAO.create(newHaikuDTO);

        assertThat(createdHaiku, is(notNullValue()));
        assertThat(createdHaiku.getAuthor(), is("New Author"));
        assertThat(createdHaiku.getHaikuParts(), hasSize(2));

        haikuDAO.delete(createdHaiku.getId());
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

