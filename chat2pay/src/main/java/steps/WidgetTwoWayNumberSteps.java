package steps;

import api.clients.ApiHelperChat2Pay;
import api.clients.ApiHelperTwoWayNumbers;
import api.models.request.widgetconfigurations.TwoWayNumberConfiguration;
import api.models.response.widgetconfigurations.TwoWayNumbersBody;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static utils.Validator.verifyBadRequestResponse;

public class WidgetTwoWayNumberSteps extends GeneralSteps {

    private static final String TOKEN = ApiHelperChat2Pay.token.get();

    private Response response;

    @Then("^User gets two-way numbers$")
    public void getTwoWayNumbers(Map<String, String> valuesMap) {
        String widgetId = valuesMap.get("i.widgetId");

        response = ApiHelperTwoWayNumbers.getTwoWayNumbers(widgetId, TOKEN);
        int statusCode = response.getStatusCode();
        int expectedResponseCode = parseInt(valuesMap.get("o.responseCode"));

        if (expectedResponseCode == statusCode) {
            if (statusCode == 200) {
                List<TwoWayNumbersBody> twoWayNumbersList = response.jsonPath().getList("", TwoWayNumbersBody.class);

                Optional<TwoWayNumbersBody> body = twoWayNumbersList.stream()
                        .filter(n -> n.getNumber().equals(valuesMap.get("o.number")))
                        .findFirst();
                body.ifPresent(twoWayNumbersBody ->
                        assertThat(twoWayNumbersBody.isDefault())
                                .isEqualTo(Boolean.parseBoolean(valuesMap.get("o.default"))));
            } else if (expectedResponseCode == 404) {
                verifyBadRequestResponse(valuesMap, response);
            }
        } else {
            Assertions.fail(format("Expected response code %s but was %s", expectedResponseCode, statusCode));
        }
    }

    @Then("^User updates two-way numbers$")
    public void updatesTwoWayNumbers(Map<String, String> valuesMap) {
        String widgetId = valuesMap.get("i.widgetId");
        List<String> numbers = getListOfElementsFromTruthTable(valuesMap.get("o.numbers"));
        TwoWayNumberConfiguration configuration = TwoWayNumberConfiguration.builder()
                .numbers(numbers)
                .defaultNumber(valuesMap.get("o.defaultNumbers")).build();

        response = ApiHelperTwoWayNumbers.updateTwoWayNumbers(widgetId, configuration, TOKEN);
        int statusCode = response.getStatusCode();
        int expectedResponseCode = parseInt(valuesMap.get("o.responseCode"));

        if (expectedResponseCode == statusCode) {
            if (statusCode == 200) {
                ApiHelperTwoWayNumbers.getTwoWayNumbers(widgetId, TOKEN)
                        .jsonPath().getList("", TwoWayNumbersBody.class).forEach(n -> {
                            boolean isDefault = n.getNumber().equals(valuesMap.get("o.defaultNumbers"));
                            softly.assertThat(n.getNumber()).isIn(numbers);
                            softly.assertThat(n.isDefault()).isEqualTo(isDefault);
                        });
                softly.assertAll();
            } else if (expectedResponseCode == 404) {
                verifyBadRequestResponse(valuesMap, response);
            }
        } else {
            Assertions.fail(format("Expected response code %s but was %s", expectedResponseCode, statusCode));
        }
    }
}
