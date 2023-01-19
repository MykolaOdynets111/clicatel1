package utils;

import api.models.response.ErrorResponse;
import datamodelclasses.validateobjects.ErrorValidatorObject;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.Map;

import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Validator {
    public static void validateErrorResponse(Response response, Map<String, String> data) {
        assertThat(response.statusCode())
                .as(format("status code is not equals to %s", data.get("o.responseCode")))
                .isEqualTo(Integer.valueOf(data.get("o.responseCode")));
        ErrorValidatorObject errorData = new ErrorValidatorObject(data);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        String error = errorResponse.getMessage();
        Assert.assertTrue(error.contains(errorData.getErrorMessage()),
                "Error message is incorrect or not returned /n" +
                        "Error from server:" + error);
    }

}
