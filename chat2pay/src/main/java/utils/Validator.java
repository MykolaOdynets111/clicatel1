package utils;

import api.models.response.failedresponse.ErrorResponse;
import datamodelclasses.validateobjects.ErrorValidatorObject;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Validator {

    public static void validateErrorResponse(Response response, Map<String, String> data) {
        SoftAssertions softly = new SoftAssertions();
        ErrorValidatorObject errorData = new ErrorValidatorObject(data);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        List<String> errors = Stream.of(singletonList(errorResponse.error), errorResponse.getErrors())
                .flatMap(Collection::stream).collect(Collectors.toList());

        softly.assertThat(errorResponse.status).isEqualTo(errorData.getResponseCode());
        softly.assertThat(errors).contains(errorData.getErrorMessage());
        softly.assertAll();
    }

    public static void checkResponseCode(Response response, String code) {
        assertThat(response.statusCode())
                .as(format("Status code is not equals to %s", code))
                .isEqualTo(Integer.valueOf(code));
    }
}