package steps;

import api.models.response.c2pconfiguration.ConfigurationBody;
import api.models.response.failedresponse.ErrorResponse;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import utils.Validator;

import java.time.LocalDate;
import java.util.Map;

import static api.clients.ApiHelperConfigurations.getC2PConfigurationResponse;
import static java.lang.Boolean.getBoolean;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;

public class ConfigurationSteps {

    @Then("^User get the C2P configuration")
    public void getC2PConfiguration(Map<String, String> valuesMap) {
        SoftAssertions softly = new SoftAssertions();
        Response response = getC2PConfigurationResponse(valuesMap.get("activationKey"));
        int statusCode = response.getStatusCode();
        int expectedResponseCode = parseInt(valuesMap.get("responseCode"));

        if (expectedResponseCode == statusCode) {
            if (statusCode == 200) {
                ConfigurationBody configuration = response.as(ConfigurationBody.class);

                softly.assertThat(configuration.updateTime).isNotNull();
                softly.assertThat(getBoolean(valuesMap.get("whatsappChannelEnabled"))).isEqualTo(configuration.whatsappChannelEnabled);
                softly.assertThat(getBoolean(valuesMap.get("smsChannelEnabled"))).isEqualTo(configuration.whatsappChannelEnabled);
                softly.assertThat(valuesMap.get("apiKey")).isEqualTo(configuration.apiKey);
                softly.assertThat(valuesMap.get("environment")).isEqualTo(configuration.environment);
                softly.assertThat(parseInt(valuesMap.get("integrations"))).isEqualTo(configuration.integrators.size());
                softly.assertThat(parseInt(valuesMap.get("supportedCurrencies"))).isEqualTo(configuration.supportedCurrencies.size());
                configuration.supportedCurrencies.forEach(sc ->
                        softly.assertThat(sc.getClass().getFields()).isNotNull());
                configuration.integrators.forEach(i ->
                        softly.assertThat(i.getClass().getFields()).isNotNull());

            } else if (expectedResponseCode == 401) {
                ErrorResponse unsuccessful = response.as(ErrorResponse.class);
                Validator.validateErrorResponse(response, valuesMap);

                softly.assertThat(unsuccessful.getTimestamp()).isEqualTo(LocalDate.now());
            }
            softly.assertAll();
        } else {
            Assertions.fail(format("Expected response code %s but was %s", expectedResponseCode, statusCode));
        }
    }
}
