package dat.daos;

import dat.config.HibernateConfig;
import dat.dtos.HaikuDTO;
import dat.dtos.HaikuPartDTO;
import dat.entities.Haiku;
import dat.entities.HaikuPart;
import dat.security.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HaikuDAOTest {
    private static HaikuDAO haikuDAO;
    private HaikuDTO haikuDTO;

    @BeforeAll
    void beforeAll() {
        HibernateConfig.setTest(true);
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
        haikuDAO = HaikuDAO.getInstance(emf);
    }

    @BeforeEach
    void beforeEach() {
        User user = null;

        HaikuPartDTO part1 = HaikuPartDTO.builder()
                .content("Line 1")
                .isFiveSyllables(true)
                .build();

        HaikuPartDTO part2 = HaikuPartDTO.builder()
                .content("Line 2")
                .isFiveSyllables(false)
                .build();

        haikuDTO = HaikuDTO.builder()
                .user(user)
                .author("Test Author")
                .dateCreated(LocalDate.of(2023, 1, 1))
                .haikuParts(List.of(part1, part2))
                .build();

        part2.setHaikus(List.of(new Haiku(haikuDTO)));
        part1.setHaikus(List.of(new Haiku(haikuDTO)));

        haikuDTO = haikuDAO.create(haikuDTO);
    }

    @AfterEach
    void tearDown() {
        if (haikuDTO != null) haikuDAO.delete(haikuDTO.getId());
    }

    @Test
    void read() {
        HaikuDTO fetchedHaikuDTO = haikuDAO.read(haikuDTO.getId());
        assertThat(fetchedHaikuDTO, is(notNullValue()));

        assertThat(fetchedHaikuDTO, is(notNullValue()));
        assertThat(fetchedHaikuDTO.getAuthor(), is("Test Author"));
        assertThat(fetchedHaikuDTO.getHaikuParts(), hasSize(2));
    }

    @Test
    void readAll() {
        List<HaikuDTO> haikus = haikuDAO.readAll();

        assertThat(haikus, is(not(empty())));
    }

    @Test
    void create() {
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

