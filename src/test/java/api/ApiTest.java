package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;


public class ApiTest {
    String authToken;

    public ApiTest() {
        RestAssured.baseURI = "https://api.github.com/gists";
        this.authToken = System.getProperty("token");
    }

    public String post_gist() {
        String jsonString = "{\"description\": \"be-md\",\"public\": false,\"files\": {\"be_md.txt\": {\"content\": \"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua\"}}}";
        String id = null;
        id  = RestAssured.
        given().
        auth().oauth2(this.authToken).
        contentType(ContentType.JSON).
        body(jsonString).
        expect()
        .log().all().
                when().
                post().
                then().
                statusCode(201).
                contentType(ContentType.JSON).
                body("id", notNullValue()).
                        extract().
                        path("id");

        return id;
    }

    public void get_gist(String id) {
        RestAssured.
            given().
            auth().oauth2(this.authToken).
            expect()
            .log().all().
                    when().
                    get("/" + id).
                    then().
                    statusCode(200).
                    contentType(ContentType.JSON).
                    assertThat().
                        body(matchesJsonSchemaInClasspath("qa/gist-json-schema.json"));
    }

    public void validate_gist() {

    }
}
