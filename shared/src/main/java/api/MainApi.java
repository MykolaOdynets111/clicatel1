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
    protected static ResponseBody postQueryWithoutAuth(String endpoint, Object body, int responseCode) {
        Response response = postWithoutAuth(endpoint, body);

        return validate(response, responseCode);
    }

    @NotNull
    protected static ResponseBody putQueryWithoutAuth(String endpoint, Object body, int responseCode) {
        Response response = putWithoutAuthh(endpoint, body);

        return validate(response, responseCode);
    }

    @NotNull
    protected static ResponseBody getQuery(String endpoint, String authToken, int responseCode) {
        Response response = get(endpoint, authToken);

        return validate(response, responseCode);
    }

    @NotNull

    protected static ResponseBody getQueryWithoutAuth(String endpoint, int responseCode) {

        Response response = getWithoutAuth(endpoint);
        return validate(response, responseCode);
    }

    @NotNull
    protected static ResponseBody putQuerywithAuthNoBody(String endpoint, String authToken, int responseCode) {
        Response response = put(endpoint, authToken);
        return validate(response, responseCode);
    }

    @NotNull
    protected static ResponseBody deleteQueryWithAuth(String endpoint, String authToken, int responseCode) {

        Response response = deleteWithAuth(endpoint, authToken);
        return validate(response, responseCode);
    }

    private static Response putWithoutAuthh(String endpoint, Object body) {
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(body)
                .put(endpoint);
    }

    private static Response deleteWithAuth(String endpoint, String authToken) {
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", authToken)
                .delete(endpoint);
    }

    @NotNull
    protected static ResponseBody putQuerywithAuthAndBody(String endpoint, String authToken, Object body, int responseCode) {
        Response response = putwithBodyAndAuth(endpoint, authToken, body);
        return validate(response, responseCode);
    }


    protected static Response put(String endpoint, String authToken) {
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", authToken)
                .put(endpoint);
    }

    @NotNull
    protected static Response putwithBodyAndAuth(String endpoint, String authToken, Object body) {
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", authToken)
                .body(body)
                .put(endpoint);
    }

    @NotNull
    protected static ResponseBody getQueryForAdminConfigurationSecret(String endpoint, String authToken, int responseCode) {
        Response response = getForAdminConfigurationSecret(endpoint, authToken);

        return validate(response, responseCode);
    }

    @NotNull
    protected static ResponseBody postQueryAdminMC2TokenAuth(String endpoint, Object body, String authToken, int responseCode) {
        Response response = postAdminMC2TokenAuth(endpoint, body, authToken);

        return validate(response, responseCode);
    }

    private static ResponseBody validate(Response response, int responseCode) {
        if (response.getStatusCode() != responseCode) {
            fail("Couldn't get the value \n"
                    + "Status code: " + response.statusCode() + "\n"
                    + "Error message: " + response.getBody().asString());
        }
        return Objects.requireNonNull(response.getBody());
    }


    protected static ResponseBody getQueryForInternalApi(String endpoint, String mc2ID,String internalProductToken, int responseCode) {

        Response response = getForInternalAPI(endpoint, mc2ID, internalProductToken);

        return validate(response, responseCode);
    }

    protected static Response post(String endpoint, Object body, String authToken) {
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

    private static Response getWithoutAuth(String endpoint) {
        return RestAssured.given().log().all()
                .accept(ContentType.JSON)
                .get(endpoint);
    }

    private static Response getForInternalAPI(String endpoint,String mc2ID,String internalProductToken) {
        return RestAssured.given().log().all()
                .accept(ContentType.JSON)
                .header("internalProductToken", internalProductToken)
                .header("mc2AccountId",mc2ID)
                .get(endpoint);
    }

    protected static Response postAdminMC2TokenAuth(String endpoint, Object body, String authToken) {
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("mc2Token", authToken)
                .body(body)
                .post(endpoint);
    }

    private static Response getForAdminConfigurationSecret(String endpoint, String authToken) {
        return RestAssured.given().log().all()
                .accept(ContentType.JSON)
                .header("authKey", authToken)
                .get(endpoint);
    }
}
