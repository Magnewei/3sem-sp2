package restassured;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class HaikuEndpointTests {
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080/api"; // Change this to your server's base URI
    }

    @Test
    public void testCreateHaiku() {
        String haikuJson = """
            {
                "author": "Test Author",
                "haikuParts": [
                    {"content": "Line 1", "isFiveSyllables": true},
                    {"content": "Line 2", "isFiveSyllables": false}
                ]
            }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(haikuJson)
                .when()
                .post("/haikus")
                .then()
                .statusCode(201) // HTTP 201 Created
                .body("author", equalTo("Test Author"))
                .body("haikuParts.size()", is(2));
    }

    @Test
    public void testGetHaiku() {
        int haikuId = 1;

        when()
                .get("/haikus/{id}", haikuId)
                .then()
                .statusCode(200)
                .body("author", notNullValue())
                .body("haikuParts.size()", greaterThan(0));
    }

    @Test
    public void testUpdateHaiku() {
        String updatedHaikuJson = """
            {
                "author": "Updated Author",
                "haikuParts": [
                    {"content": "Updated Line 1", "isFiveSyllables": true},
                    {"content": "Updated Line 2", "isFiveSyllables": false}
                ]
            }
        """;

        int haikuId = 1;

        // Update the Haiku and assert success
        given()
                .contentType(ContentType.JSON)
                .body(updatedHaikuJson)
                .when()
                .put("/haikus/{id}", haikuId)
                .then()
                .statusCode(200)
                .body("author", equalTo("Updated Author"));
    }

    @Test
    public void testDeleteHaiku() {
        int haikuId = 1;

        // Delete the Haiku and assert success
        when()
                .delete("/haikus/{id}", haikuId)
                .then()
                .statusCode(204); // HTTP 204 No Content
    }

    @Test
    public void testGetAllHaikus() {
        when()
                .get("/haikus")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }
}
