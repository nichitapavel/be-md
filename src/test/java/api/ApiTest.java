package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;


public class ApiTest {
    String authToken;
    String id;
    String jsonString = "{\"description\": \"be-md\",\"public\": false,\"files\": {\"be_md.txt\": {\"content\": \"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua\"}}}";

    public ApiTest() {
        RestAssured.baseURI = "https://api.github.com/gists";
        this.authToken = System.getProperty("token");
    }

    @Test
    public void postGist() {
        this.id = RestAssured.
                given().
                    auth().oauth2(this.authToken).
                    contentType(ContentType.JSON).
                    body(this.jsonString).
                when().
                    post().
                then().
                    statusCode(201).
                    contentType(ContentType.JSON).
                    body("id", notNullValue()).
                extract().
                    path("id");

        RestAssured.
                given().
                    auth().oauth2(this.authToken).
                when().
                    get("/" + this.id).
                then().
                    statusCode(200).
                    contentType(ContentType.JSON);
    }

    @Test
    public void validateGist() {
        this.id = RestAssured.
                given().
                    auth().oauth2(this.authToken).
                    contentType(ContentType.JSON).
                    body(this.jsonString).
                when().
                    post().
                then().
                    statusCode(201).
                extract().
                    path("id");

        RestAssured.
            given().
                auth().oauth2(this.authToken).
            when().
                get("/" + this.id).
            then().
            contentType(ContentType.JSON).
                body(matchesJsonSchemaInClasspath("api/gist-json-schema.json"));
    }

    @Test
    public void deleteGist() {
        this.id = RestAssured.
                given().
                    auth().oauth2(this.authToken).
                    contentType(ContentType.JSON).
                    body(this.jsonString).
                when().
                    post().
                then().
                    statusCode(201).
                extract().
                    path("id");

        RestAssured.
            given().
                auth().oauth2(this.authToken).
            when().
                delete("/" + this.id).
            then().
                statusCode(204);

        RestAssured.
                given().
                    auth().oauth2(this.authToken).
                when().
                    delete("/" + this.id).
                then().
                    statusCode(404);
    }

}
