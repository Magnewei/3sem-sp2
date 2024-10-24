package restassured;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class HaikuPartRestAssured {

    @BeforeAll
    public static void setup() {
        // Set up RestAssured base URI and port
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080; // Change this to the correct port where your app is running
        RestAssured.basePath = "/api"; // Base path to the API
    }

    @Test
    public void testCreateHaikuPart() {
        String haikuPartJson = """
            {
                "content": "This is line 1",
                "fiveSyllables": true
            }
        """;

        // Create a new HaikuPart and verify success
        given()
                .contentType(ContentType.JSON)
                .body(haikuPartJson)
                .when()
                .post("/haikuparts")
                .then()
                .statusCode(201) // HTTP 201 Created
                .body("content", equalTo("This is line 1"))
                .body("fiveSyllables", is(true));
    }

    @Test
    public void testGetHaikuPart() {
        // Assuming the ID of the HaikuPart is 1
        int haikuPartId = 1;

        // Retrieve the HaikuPart by its ID
        when()
                .get("/haikuparts/{id}", haikuPartId)
                .then()
                .statusCode(200)
                .body("content", notNullValue())
                .body("fiveSyllables", is(true));
    }

    @Test
    public void testUpdateHaikuPart() {
        String updatedHaikuPartJson = """
            {
                "content": "Updated line content",
                "fiveSyllables": false
            }
        """;

        // Assuming the ID of the HaikuPart is 1
        int haikuPartId = 1;

        // Update the HaikuPart and verify success
        given()
                .contentType(ContentType.JSON)
                .body(updatedHaikuPartJson)
                .when()
                .put("/haikuparts/{id}", haikuPartId)
                .then()
                .statusCode(200) // HTTP 200 OK
                .body("content", equalTo("Updated line content"))
                .body("fiveSyllables", is(false));
    }

    @Test
    public void testDeleteHaikuPart() {
        // Assuming the ID of the HaikuPart to delete is 1
        int haikuPartId = 1;

        // Delete the HaikuPart and verify success
        when()
                .delete("/haikuparts/{id}", haikuPartId)
                .then()
                .statusCode(204); // HTTP 204 No Content
    }

    @Test
    public void testGetAllHaikuParts() {
        // Get all HaikuParts and verify the response
        when()
                .get("/haikuparts")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0)); // Assert that the list of HaikuParts is not empty
    }
}
