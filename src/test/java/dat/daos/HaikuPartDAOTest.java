package dat.daos;

import dat.dtos.HaikuDTO;
import dat.dtos.HaikuPartDTO;
import dat.config.HibernateConfig;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class HaikuPartDAOTest {
    private static EntityManagerFactory emf;
    private static HaikuPartDAO haikuPartDAO;
    private HaikuPartDTO haikuPartDTO;
/*
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

        assertThat(fetchedHaikuPart, is(notNullValue()));
        assertThat(fetchedHaikuPart.getContent(), is("Test Line"));
        assertThat(fetchedHaikuPart.isFiveSyllables(), is(true));
    }

    @Test
    void readAll() {
        List<HaikuPartDTO> haikuParts = haikuPartDAO.readAll();

        assertThat(haikuParts, is(not(empty())));
        assertThat(haikuParts, hasItem(hasProperty("id", equalTo(haikuPartDTO.getId()))));
    }

    @Test
    void create() {
        HaikuPartDTO newHaikuPartDTO = HaikuPartDTO.builder()
                .content("New Line")
                .isFiveSyllables(false)
                .build();

        HaikuPartDTO createdHaikuPart = haikuPartDAO.create(newHaikuPartDTO);

        assertThat(createdHaikuPart, is(notNullValue()));
        assertThat(createdHaikuPart.getContent(), is("New Line"));
        assertThat(createdHaikuPart.isFiveSyllables(), is(false));

        haikuPartDAO.delete(createdHaikuPart.getId().intValue());
    }

    @Test
    void update() {
        haikuPartDTO.setContent("Updated Line");
        haikuPartDTO.setFiveSyllables(false);

        HaikuPartDTO updatedHaikuPart = haikuPartDAO.update(haikuPartDTO.getId().intValue(), haikuPartDTO);

        assertThat(updatedHaikuPart, is(notNullValue()));
        assertThat(updatedHaikuPart.getContent(), is("Updated Line"));
        assertThat(updatedHaikuPart.isFiveSyllables(), is(false));
    }

    @Test
    void delete() {
        haikuPartDAO.delete(haikuPartDTO.getId().intValue());

        HaikuPartDTO deletedHaikuPart = haikuPartDAO.read(haikuPartDTO.getId().intValue());
        assertThat(deletedHaikuPart, is(nullValue()));
    }

    @Test
    void addHaikuPartToHaiku() {
        HaikuPartDTO newHaikuPartDTO = new HaikuPartDTO("Additional Line", true);

        HaikuDTO newHaikuDTO = HaikuDTO.builder()
                .author("New Author")
                .build();

        HaikuDTO updatedHaikuDTO = haikuPartDAO.addHaikuPartToHaiku(newHaikuDTO.getId().intValue(), newHaikuPartDTO);

        assertThat(updatedHaikuDTO, is(notNullValue()));
        assertThat(updatedHaikuDTO.getHaikuParts(), hasSize(1));
        assertThat(updatedHaikuDTO.getHaikuParts().get(0).getContent(), is("Additional Line"));
    }

 */
}
