package task1;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * JUnit 5 + rest assured tests
 */
public class JsonplaceholderRestAPITest {

    @BeforeAll
    static void baseUri() {
        io.restassured.RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    void albums_containsOmnisLaborumOdio() {
        List<Map<String, Object>> albums = given()
                .when()
                .get("/albums")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("");

        boolean found = albums.stream()
                .anyMatch(a -> "omnis laborum odio".equals(a.get("title")));
        assertTrue(found, "Expected an album titled \"omnis laborum odio\"");
    }

    @Test
    void comments_atLeastTwoHundredEntries() {
        List<?> comments = given()
                .when()
                .get("/comments")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("");

        assertTrue(comments.size() >= 200, "Expected at least 200 comments");
    }

    @Test
    void users_containsErvinHowellWithExpectedFields() {
        List<Map<String, Object>> users = given()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("");

        Map<String, Object> match = users.stream()
                .filter(u -> "Ervin Howell".equals(u.get("name")))
                .findFirst()
                .orElseThrow(() -> new AssertionError("User Ervin Howell not found"));

        assertEquals("Antonette", match.get("username"));

        @SuppressWarnings("unchecked")
        Map<String, Object> address = (Map<String, Object>) match.get("address");
        assertEquals("90566-7771", address.get("zipcode"));
    }

    @Test
    void comments_containsEmailsEndingInBiz() {
        List<Map<String, Object>> comments = given()
                .when()
                .get("/comments")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("");

        boolean hasBiz = comments.stream()
                .map(c -> (String) c.get("email"))
                .anyMatch(e -> e != null && e.endsWith(".biz"));

        assertTrue(hasBiz, "Expected at least one comment with email ending in .biz");
    }

    @Test
    void posts_createNewPost() {
        String body = """
                {
                  "userId": 11,
                  "id": 101,
                  "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                  "body": "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/posts")
                .then()
                .statusCode(201);
    }
}
