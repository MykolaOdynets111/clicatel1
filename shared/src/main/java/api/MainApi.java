package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static org.testng.Assert.fail;

public abstract class MainApi {

    @NotNull
    protected static ResponseBody postQuery(String endpoint, Object body, String authToken, int responseCode) {
        Response response = post(endpoint, body, authToken);

        return validate(response, responseCode);
    }

    @NotNull
    protected static Response postQuery(String endpoint, Object body, String authToken) {
        return post(endpoint, body, authToken);
    }

    @NotNull
    protected static Response deleteQuery(String endpoint, String authToken) {
        return delete(endpoint, authToken);
    }

    @NotNull
    protected static ResponseBody postQueryWithoutAuth(String endpoint, Object body, int responseCode) {
        Response response = postWithoutAuth(endpoint, body);

        return validate(response, responseCode);
    }

    @NotNull
    protected static ResponseBody getQuery(String endpoint, String authToken, int responseCode) {

        Response response = get(endpoint, authToken);

        return validate(response, responseCode);
    }

    @NotNull
    protected static Response getQuery(String endpoint, String authToken) {
        return get(endpoint, authToken);
    }

    private static ResponseBody validate(Response response, int responseCode) {
        if (response.getStatusCode() != responseCode) {
            fail("Couldn't get the value \n"
                    + "Status code: " + response.statusCode() + "\n"
                    + "Error message: " + response.getBody().asString());
        }
        return Objects.requireNonNull(response.getBody());
    }

    private static Response post(String endpoint, Object body, String authToken) {
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", authToken)
                .body(body)
                .post(endpoint);
    }

    private static Response postWithoutAuth(String endpoint, Object body) {
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(body)
                .post(endpoint);
    }

    private static Response get(String endpoint, String authToken) {
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", authToken)
                .get(endpoint);
    }

    private static Response delete(String endpoint, String authToken) {
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", authToken)
                .delete(endpoint);
    }
}
