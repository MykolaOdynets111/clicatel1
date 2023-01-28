package utils;

import api.models.response.failedresponse.ErrorResponse;
import datamodelclasses.validateobjects.ErrorValidatorObject;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;

import java.util.Map;

import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Validator {

    private static SoftAssertions softly = new SoftAssertions();

    public static void validateErrorResponse(Response response, Map<String, String> data) {
        ErrorValidatorObject errorData = new ErrorValidatorObject(data);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        String error = errorResponse.getMessage();
        String expectedCode = data.get("o.responseCode");

        softly.assertThat(response.statusCode())
                .as(format("Status code is not equals to %s", expectedCode))
                .isEqualTo(Integer.valueOf(expectedCode));
        softly.assertThat(error)
                .as(format("Error message is incorrect. Error from server: %s", error))
                .contains(errorData.getErrorMessage());
        if (errorResponse.getErrors() != null) {
            softly.assertThat(errorResponse.getErrors().stream()
                            .anyMatch(e -> e.contains(data.get("o.errors"))))
                    .as(format("Errors is not equals to %s", data.get("o.errors")));
        }
        softly.assertAll();
    }

    public static void checkResponseCode(Response response, String code) {
        assertThat(response.statusCode())
                .as(format("Status code is not equals to %s", code))
                .isEqualTo(Integer.valueOf(code));
    }
}