package dat.daos;

import dat.config.HibernateConfig;
import dat.dtos.HaikuDTO;
import dat.dtos.HaikuPartDTO;
import dat.entities.Haiku;
import dat.entities.HaikuPart;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

        assertNotNull(fetchedHaikuDTO);
        assertEquals("Test Author", fetchedHaikuDTO.getAuthor());
        assertEquals(2, fetchedHaikuDTO.getHaikuParts().size());
    }

    @Test
    void readAll() {
        List<HaikuDTO> haikus = haikuDAO.readAll();

        assertNotNull(haikus);
        assertFalse(haikus.isEmpty());
        assertTrue(haikus.stream().anyMatch(h -> h.getId().equals(haikuDTO.getId())));
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

        assertNotNull(createdHaiku);
        assertEquals("New Author", createdHaiku.getAuthor());
        assertEquals(2, createdHaiku.getHaikuParts().size());

        haikuDAO.delete(createdHaiku.getId().intValue());
    }

    @Test
    void update() {
        haikuDTO.setAuthor("Updated Author");
        HaikuDTO updatedHaiku = haikuDAO.update(haikuDTO.getId().intValue(), haikuDTO);

        assertNotNull(updatedHaiku);
        assertEquals("Updated Author", updatedHaiku.getAuthor());
    }

    @Test
    void delete() {
        haikuDAO.delete(haikuDTO.getId().intValue());

        HaikuDTO deletedHaiku = haikuDAO.read(haikuDTO.getId().intValue());
        assertNull(deletedHaiku);
    }
}
