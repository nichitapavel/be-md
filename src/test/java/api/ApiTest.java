package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;


public class ApiTest {
    String authToken;
    String id;

    public ApiTest() {
        RestAssured.baseURI = "https://api.github.com/gists";
        this.authToken = System.getProperty("token");
    }

    @Test
    public void postGist() {
        System.out.println("\npostGist------------------------------GIST: " + Gists.getInstance().getID());
        System.out.println("\npostGist------------------------------ApiTest: " + this.id);
        String jsonString = "{\"description\": \"be-md\",\"public\": false,\"files\": {\"be_md.txt\": {\"content\": \"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua\"}}}";
        this.id = RestAssured.
                given().
                    auth().oauth2(this.authToken).
                    contentType(ContentType.JSON).
                    body(jsonString).
                when().
                    post().
                then().
                    statusCode(201).
                    contentType(ContentType.JSON).
                    body("id", notNullValue()).
                extract().
                    path("id");

        Gists.getInstance().setID(this.id);
    }

    @Test
    public void getGist() {
        System.out.println("\ngetGist------------------------------GIST: " + Gists.getInstance().getID());
        System.out.println("\ngetGist------------------------------ApiTest: " + this.id);
         RestAssured.
            given().
                auth().oauth2(this.authToken).
            when().
                get("/" + Gists.getInstance().getID()).
            then().
                statusCode(200).
                contentType(ContentType.JSON);
    }

    @Test
    public void validateGist() {
        System.out.println("\nvalidateGist------------------------------GIST: " + Gists.getInstance().getID());
        System.out.println("\nvalidateGist------------------------------ApiTest: " + this.id);
        RestAssured.
            given().
                auth().oauth2(this.authToken).
            when().
                get("/" + Gists.getInstance().getID()).
            then().
            contentType(ContentType.JSON).
                body(matchesJsonSchemaInClasspath("api/gist-json-schema.json"));
    }

    @Test
    public void deleteGist() {
        System.out.println("\ndeleteGist------------------------------GIST: " + Gists.getInstance().getID());
        System.out.println("\ndeleteGist------------------------------ApiTest: " + this.id);
        RestAssured.
            given().
                auth().oauth2(this.authToken).
            when().
                delete("/" + Gists.getInstance().getID()).
            then().
                statusCode(204);
    }

    @Test
    public void getDeletedGist() {
        System.out.println("\ngetDeletedGist------------------------------GIST: " + Gists.getInstance().getID());
        System.out.println("\ngetDeletedGist------------------------------ApiTest: " + this.id);
        RestAssured.
            given().
                auth().oauth2(this.authToken).
            when().
                get("/" + Gists.getInstance().getID()).
            then().
                statusCode(404);
    }
}
