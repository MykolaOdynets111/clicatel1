package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;
import static org.junit.Assert.fail;

public abstract class MainApi {

    @NotNull
    protected static ResponseBody postQuery(String endpoint, Object body, String authToken) {
        Response response = post(endpoint, body, authToken);

        if (response.getStatusCode() != 200) {
            fail("Couldn't post the value \n"
                    + "Status code: " + response.statusCode() + "\n"
                    + "Error message: " + response.getBody().asString());
            return null;
        } else {
            return Objects.requireNonNull(response.getBody());
        }
    }

    @NotNull
    protected static ResponseBody postQueryWithoutAuth(String endpoint, Object body) {
        Response response = postWithoutAuth(endpoint, body);

        if (response.getStatusCode() != 200) {
            fail("Couldn't post the value \n"
                    + "Status code: " + response.statusCode() + "\n"
                    + "Error message: " + response.getBody().asString());
            return null;
        } else {
            return Objects.requireNonNull(response.getBody());
        }
    }

    @NotNull
    protected static ResponseBody getQuery(String endpoint, String authToken) {
        Response response = get(endpoint, authToken);

        if (response.getStatusCode() != 200) {
            fail("Couldn't get the value \n"
                    + "Status code: " + response.statusCode() + "\n"
                    + "Error message: " + response.getBody().asString());
            return null;
        } else {
            return Objects.requireNonNull(response.getBody());
        }
    }

    @NotNull
    protected static ResponseBody getQuery(String endpoint, String authToken, int responseCode) {
        Response response = get(endpoint, authToken);

        if (response.getStatusCode() == responseCode) {
            return Objects.requireNonNull(response.getBody());
        } else {
            fail("Couldn't get the value \n"
                    + "Status code: " + response.statusCode() + "\n"
                    + "Error message: " + response.getBody().asString());
            return null;
        }
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
                .accept(ContentType.JSON)
                .header("Authorization", authToken)
                .get(endpoint);
    }
}
