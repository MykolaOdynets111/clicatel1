package steps;

import api.ApiHelperChat2Pay;
import data.models.request.WidgetBody;
import data.models.response.c2pconfiguration.ConfigurationBody;
import data.models.response.c2pconfiguration.ConfigurationIntegrator;
import data.models.response.c2pconfiguration.SupportedCurrency;
import data.models.response.widget.Widget;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

import java.util.Map;
import java.util.NoSuchElementException;

import static api.ApiHelperAccounts.getAccountSettingsResponse;
import static api.ApiHelperApiKeysManagement.getApiKeysManagement;
import static api.ApiHelperConfigurations.getC2PConfigurationResponse;
import static api.ApiHelperWidgets.getWidgetsContent;
import static data.enums.WidgetsInfo.GENERAL_WIDGET_NAME;
import static datetimeutils.DateTimeHelper.parseToLocalDate;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static utils.Validator.verifyUnauthorisedResponse;

public class ConfigurationSteps extends GeneralSteps {

    public static final String TOKEN = ApiHelperChat2Pay.token.get();

    @Then("^User get the C2P configuration")
    public void getC2PConfiguration(Map<String, String> dataMap) {
        Response response = getC2PConfigurationResponse(dataMap.get("i.activationKey"));
        int statusCode = getResponseCode(response);
        int expectedResponseCode = getExpectedCode(dataMap);

        if (expectedResponseCode == statusCode) {
            if (statusCode == 200) {
                ConfigurationBody configuration = response.as(ConfigurationBody.class);

                softly.assertThat(configuration.getUpdatedTime()).isNotNull();
                softly.assertThat(Boolean.valueOf(dataMap.get("o.whatsappChannelEnabled"))).isEqualTo(configuration.whatsappChannelEnabled);
                softly.assertThat(Boolean.valueOf(dataMap.get("o.smsChannelEnabled"))).isEqualTo(configuration.smsChannelEnabled);
                softly.assertThat(dataMap.get("o.apiKey")).isEqualTo(configuration.apiKey);
                softly.assertThat(dataMap.get("o.environment")).isEqualTo(configuration.environment);

                String supportedCurrencyId = dataMap.get("o.supportedCurrency.id");
                SupportedCurrency currency = configuration.supportedCurrencies.stream()
                        .filter(c -> c.id.equals(parseInt(supportedCurrencyId))).findFirst().orElseThrow(() ->
                                new NoSuchElementException(format("Supported Currency with id %s is absent", supportedCurrencyId)));
                softly.assertThat(dataMap.get("o.supportedCurrencies.iso")).isEqualTo(currency.iso);
                softly.assertThat(dataMap.get("o.supportedCurrencies.name")).isEqualTo(currency.name);
                softly.assertThat(dataMap.get("o.supportedCurrencies.symbol")).isEqualTo(currency.symbol);
                softly.assertThat(Boolean.valueOf(dataMap.get("o.supportedCurrencies.isDefault"))).isEqualTo(currency.isDefault);

                String integrationId = dataMap.get("o.integration.id");
                ConfigurationIntegrator integrator = configuration.integrators.stream()
                        .filter(c -> c.applicationId.equals(integrationId)).findFirst().orElseThrow(() ->
                                new NoSuchElementException(format("Integration with id %s is absent", integrationId)));
                softly.assertThat(dataMap.get("o.integration.name")).isEqualTo(integrator.name);
                softly.assertThat(dataMap.get("o.integration.status")).isEqualTo(integrator.status);
                softly.assertThat(dataMap.get("o.integration.type")).isEqualTo(integrator.type);

            } else if (expectedResponseCode == 401) {
                verifyUnauthorisedResponse(dataMap, response);
            }
            softly.assertAll();
        } else {
            Assertions.fail(format("Expected response code %s but was %s", expectedResponseCode, statusCode));
        }
    }

    @Then("^User get the widget configuration")
    public void getWidgetConfiguration(Map<String, String> dataMap) {
        switch (dataMap.get("i.widgetId")) {
            case "valid":
                WidgetBody widgetBody = new WidgetBody();
                String accountId = getAccountSettingsResponse(getActivationKey(dataMap)).jsonPath().getString("accountId");
                Widget widget = getWidgetsContent().getWidgets().stream().filter(w -> w.getId().equals(createdWidgetId.get()))
                        .findFirst().orElseThrow(() -> new NoSuchElementException("There is no such widget: " + createdWidgetId.get()));
                String apiKey = getApiKeysManagement(createdWidgetId.get(), ApiHelperChat2Pay.token.get()).jsonPath().getList("apiKey").get(0).toString();
                ConfigurationBody configuration = getC2PConfigurationResponse(apiKey).as(ConfigurationBody.class);

                softly.assertThat(widget.getName()).startsWith(GENERAL_WIDGET_NAME.name);
                softly.assertThat(widget.getApiKey()).isEqualToIgnoringCase(apiKey);
                softly.assertThat(widget.getStatus()).isEqualToIgnoringCase(dataMap.get("o.status"));
                softly.assertThat(widget.getType()).isEqualTo(widgetBody.getType());
                softly.assertThat(widget.getEnvironment()).isEqualTo(widgetBody.getEnvironment());
                softly.assertThat(widget.getConfigStatus().getId()).isEqualTo(widgetBody.getConfigStatus().getId());
                softly.assertThat(widget.getConfigStatus().getName()).isEqualToIgnoringCase(widgetBody.getConfigStatus().getName());
                softly.assertThat(widget.getAccountId()).isEqualTo(accountId);
                softly.assertThat(parseToLocalDate(widget.getCreatedTime())).isToday();
                softly.assertThat(parseToLocalDate(widget.getModifiedTime())).isToday();

                SupportedCurrency currency = configuration.getSupportedCurrencies().stream().findAny()
                        .orElseThrow(NoSuchElementException::new);
                softly.assertThat(currency.getId()).isEqualTo(156);
                softly.assertThat(currency.getIso()).isEqualTo("ZAR");
                softly.assertThat(currency.getName()).isEqualToIgnoringCase(dataMap.get("o.defaultCurrency"));
                softly.assertThat(currency.getSymbol()).isEqualTo("R");
                softly.assertThat(currency.isDefault).isEqualTo(true);

                ConfigurationIntegrator integrator = configuration.getIntegrators().stream().findAny()
                        .orElseThrow(() -> new NoSuchElementException("Integration is absent"));
                softly.assertThat(integrator.getName()).startsWith(GENERAL_WIDGET_NAME.name);
                softly.assertThat(integrator.getApplicationId()).isNotNull();
                softly.assertThat(integrator.getType()).isEqualTo("APPLICATION");
                softly.assertThat(integrator.getStatus()).isEqualTo("ACTIVATED");
                softly.assertAll();
                break;
            case "invalid":
                softly.assertThat(parseInt(dataMap.get("o.responseCode"))).isEqualTo(404);
                softly.assertThat(dataMap.get("o.errors")).isEqualTo("NOT_FOUND");
                softly.assertAll();
                break;
            default:
                Assertions.fail(format("The response code is not as expected %s", getExpectedCode(dataMap)));
        }
    }
}
