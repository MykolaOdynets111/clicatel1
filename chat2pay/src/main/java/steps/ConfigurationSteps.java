package steps;

import api.models.response.c2pconfiguration.ConfigurationBody;
import api.models.response.c2pconfiguration.ConfigurationIntegrator;
import api.models.response.c2pconfiguration.SupportedCurrency;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

import java.util.Map;
import java.util.NoSuchElementException;

import static api.clients.ApiHelperConfigurations.getC2PConfigurationResponse;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static utils.Validator.verifyUnauthorisedResponse;

public class ConfigurationSteps extends GeneralSteps {

    @Then("^User get the C2P configuration")
    public void getC2PConfiguration(Map<String, String> valuesMap) {
        Response response = getC2PConfigurationResponse(valuesMap.get("activationKey"));
        int statusCode = response.getStatusCode();
        int expectedResponseCode = parseInt(valuesMap.get("responseCode"));

        if (expectedResponseCode == statusCode) {
            if (statusCode == 200) {
                ConfigurationBody configuration = response.as(ConfigurationBody.class);

                softly.assertThat(configuration.getUpdatedTime()).isNotNull();
                softly.assertThat(Boolean.valueOf(valuesMap.get("whatsappChannelEnabled"))).isEqualTo(configuration.whatsappChannelEnabled);
                softly.assertThat(Boolean.valueOf(valuesMap.get("smsChannelEnabled"))).isEqualTo(configuration.smsChannelEnabled);
                softly.assertThat(valuesMap.get("apiKey")).isEqualTo(configuration.apiKey);
                softly.assertThat(valuesMap.get("environment")).isEqualTo(configuration.environment);

                String supportedCurrencyId = valuesMap.get("supportedCurrency.id");
                SupportedCurrency currency = configuration.supportedCurrencies.stream()
                        .filter(c -> c.id.equals(parseInt(supportedCurrencyId))).findFirst().orElseThrow(() ->
                                new NoSuchElementException(format("Supported Currency with id %s is absent", supportedCurrencyId)));
                softly.assertThat(valuesMap.get("supportedCurrencies.iso")).isEqualTo(currency.iso);
                softly.assertThat(valuesMap.get("supportedCurrencies.name")).isEqualTo(currency.name);
                softly.assertThat(valuesMap.get("supportedCurrencies.symbol")).isEqualTo(currency.symbol);
                softly.assertThat(Boolean.valueOf(valuesMap.get("supportedCurrencies.isDefault"))).isEqualTo(currency.isDefault);

                String integrationId = valuesMap.get("integration.id");
                ConfigurationIntegrator integrator = configuration.integrators.stream()
                        .filter(c -> c.applicationId.equals(integrationId)).findFirst().orElseThrow(() ->
                                new NoSuchElementException(format("Integration with id %s is absent", integrationId)));
                softly.assertThat(valuesMap.get("integration.name")).isEqualTo(integrator.name);
                softly.assertThat(valuesMap.get("integration.status")).isEqualTo(integrator.status);
                softly.assertThat(valuesMap.get("integration.type")).isEqualTo(integrator.type);

            } else if (expectedResponseCode == 401) {
                verifyUnauthorisedResponse(valuesMap, response);
            }
            softly.assertAll();
        } else {
            Assertions.fail(format("Expected response code %s but was %s", expectedResponseCode, statusCode));
        }
    }
}
