package dat.daos;

import dat.daos.HaikuPartDAO;
import dat.dtos.HaikuDTO;
import dat.entities.Haiku;
import dat.entities.HaikuPart;
import dat.dtos.HaikuPartDTO;
import dat.config.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HaikuPartDAOTest {
    private static EntityManagerFactory emf;
    private static HaikuPartDAO haikuPartDAO;
    private HaikuPartDTO haikuPartDTO;

    @BeforeAll
    static void beforeAll() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        haikuPartDAO = HaikuPartDAO.getInstance(emf);
    }

    @BeforeEach
    void setUp() {
        haikuPartDTO = HaikuPartDTO.builder()
                .content("Test Line")
                .isFiveSyllables(true)
                .build();

        haikuPartDTO = haikuPartDAO.create(haikuPartDTO);
    }

    @AfterEach
    void tearDown() {
        // Remove the HaikuPart from the database after every test.
        haikuPartDAO.delete(haikuPartDTO.getId().intValue());
    }

    @Test
    void read() {
        HaikuPartDTO fetchedHaikuPart = haikuPartDAO.read(haikuPartDTO.getId().intValue());

        assertNotNull(fetchedHaikuPart);
        assertEquals("Test Line", fetchedHaikuPart.getContent());
        assertTrue(fetchedHaikuPart.isFiveSyllables());
    }

    @Test
    void readAll() {
        List<HaikuPartDTO> haikuParts = haikuPartDAO.readAll();

        assertNotNull(haikuParts);
        assertFalse(haikuParts.isEmpty());
        assertTrue(haikuParts.stream().anyMatch(h -> h.getId().equals(haikuPartDTO.getId())));
    }

    @Test
    void create() {
        HaikuPartDTO newHaikuPartDTO = HaikuPartDTO.builder()
                .content("New Line")
                .isFiveSyllables(false)
                .build();

        HaikuPartDTO createdHaikuPart = haikuPartDAO.create(newHaikuPartDTO);

        assertNotNull(createdHaikuPart);
        assertEquals("New Line", createdHaikuPart.getContent());
        assertFalse(createdHaikuPart.isFiveSyllables());
        haikuPartDAO.delete(createdHaikuPart.getId().intValue());
    }

    @Test
    void update() {
        haikuPartDTO.setContent("Updated Line");
        haikuPartDTO.setFiveSyllables(false);

        HaikuPartDTO updatedHaikuPart = haikuPartDAO.update(haikuPartDTO.getId().intValue(), haikuPartDTO);

        assertNotNull(updatedHaikuPart);
        assertEquals("Updated Line", updatedHaikuPart.getContent());
        assertFalse(updatedHaikuPart.isFiveSyllables());
    }

    @Test
    void delete() {
        haikuPartDAO.delete(haikuPartDTO.getId().intValue());

        HaikuPartDTO deletedHaikuPart = haikuPartDAO.read(haikuPartDTO.getId().intValue());
        assertNull(deletedHaikuPart);
    }

    @Test
    void addHaikuPartToHaiku() {
        HaikuPartDTO newHaikuPartDTO = new HaikuPartDTO("Additional Line", true);

        HaikuDTO newHaikuDTO = HaikuDTO.builder()
                .author("New Author")
                .build();

        HaikuDTO updatedHaikuDTO = haikuPartDAO.addHaikuPartToHaiku(newHaikuDTO.getId().intValue(), newHaikuPartDTO);

        assertNotNull(updatedHaikuDTO);
        assertEquals(1, updatedHaikuDTO.getHaikuParts().size());
        assertEquals("Additional Line", updatedHaikuDTO.getHaikuParts().get(0).getContent());
    }
}
