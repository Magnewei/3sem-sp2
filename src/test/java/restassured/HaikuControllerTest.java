package restassured;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.controllers.HaikuController;
import dat.daos.HaikuDAO;
import dat.dtos.HaikuDTO;
import dat.dtos.HaikuPartDTO;
import dat.entities.Haiku;
import dat.security.controllers.SecurityController;
import dat.security.daos.SecurityDAO;
import dat.security.entities.User;
import dat.security.exceptions.ValidationException;
import dk.bugelhartmann.UserDTO;
import io.javalin.Javalin;
import io.restassured.common.mapper.TypeRef;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HaikuControllerTest {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static final SecurityController securityController = SecurityController.getInstance();
    private static final SecurityDAO securityDAO = new SecurityDAO(emf);
    private static Javalin app;
    private static HaikuDAO haikuDAO;
    private static Haiku[] haikus;
    private static Haiku sampleHaiku1, sampleHaiku2;
    private static UserDTO[] users;
    private static UserDTO userDTO, adminDTO;
    private static String userToken, adminToken;
    private static final String BASE_URL = "http://localhost:7070/api";

    @BeforeAll
    void setUpAll() {
        HibernateConfig.setTest(true);
        haikuDAO = HaikuDAO.getInstance(emf);
        app = ApplicationConfig.startServer(7070);
    }

    @BeforeEach
    void setUp() {
        System.out.println("Populating database with haikus");
        haikus = Populator.populateHaikus(emf);
        sampleHaiku1 = haikus[0];
        sampleHaiku2 = haikus[1];
        haikuDAO.create(new HaikuDTO(sampleHaiku1));
        haikuDAO.create(new HaikuDTO(sampleHaiku2));

        users = Populator.populateUsers(emf);
        userDTO = users[0];
        adminDTO = users[1];
        // Generate tokens
        try {
            UserDTO verifiedUser = securityDAO.getVerifiedUser(userDTO.getUsername(), userDTO.getPassword());
            UserDTO verifiedAdmin = securityDAO.getVerifiedUser(adminDTO.getUsername(), adminDTO.getPassword());
            userToken = "Bearer " + securityController.createToken(verifiedUser);
            adminToken = "Bearer " + securityController.createToken(verifiedAdmin);
        }
        catch (ValidationException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void tearDown() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM HaikuPart").executeUpdate();
            em.createQuery("DELETE FROM Haiku").executeUpdate();
            em.createQuery("DELETE FROM User").executeUpdate();
            em.createQuery("DELETE FROM Role").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    void tearDownAll() {
        ApplicationConfig.stopServer(app);
    }



    @Test
    void createHaiku() {
        String newHaikuJson = """
            {
                "author": "New Author",
                "dateCreated": "2024-10-25",
                "haikuParts": [
                    {"content": "New line one", "fiveSyllables": true},
                    {"content": "New line two", "fiveSyllables": false},
                    {"content": "New line three", "fiveSyllables": true}
                ]
            }
            """;

        given()
                .contentType("application/json")
                .header("Authorization", userToken)
                .body(newHaikuJson)
                .when()
                .post(BASE_URL + "/haikus")
                .then()
                .statusCode(201)
                .body("author", equalTo("New Author"))
                .body("haikuParts.size()", is(3))
                .body("haikuParts[0].content", equalTo("New line one"))
                .body("haikuParts[1].fiveSyllables", equalTo(false));
    }

    @Test
    void readHaikuById() {
        long haikuId = haikuDAO.readAll().get(0).getId();

        HaikuDTO haiku = given()
                .header("Authorization", userToken)
                .when()
                .get(BASE_URL + "/haikus/" + haikuId)
                .then()
                .statusCode(200)
                .extract()
                .as(HaikuDTO.class);

        assertThat(haiku.getAuthor(), is("Author One"));
        assertThat(haiku.getHaikuParts().size(), is(3));
    }

    @Test
    void deleteHaiku() {
        long haikuId = haikuDAO.readAll().get(1).getId();

        given()
                .header("Authorization", adminToken)
                .when()
                .delete(BASE_URL + "/haikus/" + haikuId)
                .then()
                .statusCode(204);

        given()
                .header("Authorization", userToken)
                .when()
                .get(BASE_URL + "/" + haikuId)
                .then()
                .statusCode(404);
    }

    @Test
    void readAllHaikus() {
        System.out.println("usertoken: " + userToken);
        System.out.println("admintoken: " + adminToken);
        List<HaikuDTO> haikus =
                given()
                        .header("Authorization", userToken)
                        .when()
                        .get(BASE_URL + "/haikus")
                        .then()
                        .statusCode(200)
                        .body("size()", is(2))
                        .extract()
                        .as(new TypeRef<List<HaikuDTO>>() {});

        assertThat(haikus.size(), is(2));
        assertThat(haikus.get(0).getAuthor(), is("Author One"));
        assertThat(haikus.get(1).getAuthor(), is("Author Two"));
    }
}
