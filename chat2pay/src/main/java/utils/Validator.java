package utils;

import api.models.response.failedresponse.BadRequestResponse;
import api.models.response.failedresponse.ErrorResponse;
import api.models.response.failedresponse.UnauthorisedResponse;
import datamodelclasses.validateobjects.ErrorValidatorObject;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;

import java.time.LocalDate;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Validator {

    private static final SoftAssertions softly = new SoftAssertions();

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

    public static void verifyUnauthorisedResponse(Map<String, String> valuesMap, Response response) {
        UnauthorisedResponse unsuccessful = response.as(UnauthorisedResponse.class);

        softly.assertThat(parseInt(valuesMap.get("o.responseCode"))).isEqualTo(unsuccessful.status);
        softly.assertThat(valuesMap.get("o.errors")).isEqualTo(unsuccessful.error);
        softly.assertThat(valuesMap.get("o.path")).isEqualTo(unsuccessful.path);
        softly.assertThat(unsuccessful.getTimestamp()).isEqualTo(LocalDate.now());
        softly.assertAll();
    }

    public static void verifyBadRequestResponse(Map<String, String> valuesMap, Response response) {
        BadRequestResponse badRequestResponse = response.as(BadRequestResponse.class);

        softly.assertThat(valuesMap.get("o.errors")).isEqualTo(badRequestResponse.status);
        softly.assertThat(valuesMap.get("o.path")).isEqualTo(badRequestResponse.message);
        softly.assertAll();
    }

    public static void checkResponseCode(Response response, String code) {
        assertThat(response.statusCode())
                .as(format("Status code is not equals to %s", code))
                .isEqualTo(Integer.valueOf(code));
    }
}