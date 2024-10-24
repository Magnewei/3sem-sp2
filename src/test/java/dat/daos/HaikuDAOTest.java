package dat.daos;

import dat.config.HibernateConfig;
import dat.dtos.HaikuDTO;
import dat.dtos.HaikuPartDTO;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class HaikuDAOTest {
    private static EntityManagerFactory emf;
    private static HaikuDAO haikuDAO;
    private HaikuDTO haikuDTO;
/*
    @BeforeAll
    static void beforeAll() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        haikuDAO = HaikuDAO.getInstance(emf);
    }

    @BeforeEach
    void setUp() {
        HaikuPartDTO part1 = HaikuPartDTO.builder()
                .content("Line 1")
                .isFiveSyllables(true)
                .build();

        HaikuPartDTO part2 = HaikuPartDTO.builder()
                .content("Line 2")
                .isFiveSyllables(false)
                .build();

        haikuDTO = HaikuDTO.builder()
                .author("Test Author")
                .haikuParts(List.of(part1, part2))
                .build();

        haikuDTO = haikuDAO.create(haikuDTO);
    }

    @AfterEach
    void tearDown() {
        if (haikuDTO != null) {
            haikuDAO.delete(haikuDTO.getId().intValue());
        }
    }

    @Test
    void read() {
        HaikuDTO fetchedHaikuDTO = haikuDAO.read(haikuDTO.getId().intValue());

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

        haikuDAO.delete(createdHaiku.getId().intValue());
    }

    @Test
    void update() {
        haikuDTO.setAuthor("Updated Author");
        HaikuDTO updatedHaiku = haikuDAO.update(haikuDTO.getId().intValue(), haikuDTO);

        assertThat(updatedHaiku, is(notNullValue()));
        assertThat(updatedHaiku.getAuthor(), is("Updated Author"));
    }

    @Test
    void delete() {
        haikuDAO.delete(haikuDTO.getId().intValue());

        HaikuDTO deletedHaiku = haikuDAO.read(haikuDTO.getId().intValue());
        assertThat(deletedHaiku, is(nullValue()));
    }

 */
}

