package utils;

import api.models.response.failedresponse.ErrorResponse;
import datamodelclasses.validateobjects.ErrorValidatorObject;
import io.restassured.response.Response;

import java.util.Map;

import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.testng.Assert.assertTrue;

public class Validator {

    public static void validateErrorResponse(Response response, Map<String, String> data) {
        ErrorValidatorObject errorData = new ErrorValidatorObject(data);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        String error = errorResponse.getMessage();
        String expectedCode = data.get("o.responseCode");

        assertThat(response.statusCode())
                .as(format("Status code is not equals to %s", expectedCode))
                .isEqualTo(Integer.valueOf(expectedCode));
        assertTrue(error.contains(errorData.getErrorMessage()),
                "Error message is incorrect or not returned /n" +
                        "Error from server:" + error);
    }

    public static void checkResponseCode(Response response, String code) {
        assertThat(response.statusCode())
                .as(format("Status code is not equals to %s", code))
                .isEqualTo(Integer.valueOf(code));
    }
}