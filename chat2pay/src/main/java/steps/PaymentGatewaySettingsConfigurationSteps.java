package steps;

import api.clients.ApiHelperChat2Pay;
import api.clients.ApiHelperPaymentGatewaySettingsConfiguration;
import api.models.request.payments.PaymentGatewaySettingsUnifiedPayments;
import api.models.response.paymentgatewaysettingsresponse.PaymentGatewaySettingsResponse;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

import java.io.File;
import java.time.LocalDate;
import java.util.Map;

import static api.clients.ApiHelperPaymentGatewaySettingsConfiguration.postUnifiedPaymentsSettings;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static utils.Validator.verifyBadRequestResponse;

public class PaymentGatewaySettingsConfigurationSteps extends GeneralSteps {

    private static final String TOKEN = ApiHelperChat2Pay.token.get();
    public final PaymentGatewaySettingsUnifiedPayments unifiedPaymentsSettings = new PaymentGatewaySettingsUnifiedPayments();
    private Response response;

    @Then("^User posts 'Payment Gateway Logo'$")
    public void postLogo(Map<String, String> dataMap) {
        switch (dataMap.get("i.logo")) {
            case "valid":
                File logo = new File(format(System.getProperty("user.dir") + "/src/test/java/resources/logos/%s", "LogoTest.png"));
                response = ApiHelperPaymentGatewaySettingsConfiguration.postLogo(TOKEN, createdWidgetId.get(), logo);

                softly.assertThat(response.getStatusCode()).isEqualTo(200);
                softly.assertThat(response.getBody()).isNotNull();
                softly.assertAll();
                break;
            case "invalid":
                response = ApiHelperPaymentGatewaySettingsConfiguration.postLogo(TOKEN, createdWidgetId.get(), new File("logo"));
                verifyBadRequestResponse(dataMap, response);
                break;
            default:
                Assertions.fail(format("The response code is not as expected %s", getResponseCode(dataMap)));
        }
    }

    @Then("^User gets 'Payment Gateway Logo'$")
    public void getLogo(Map<String, String> dataMap) {
        response = ApiHelperPaymentGatewaySettingsConfiguration.getLogo(TOKEN, getWidgetId(dataMap), paymentGatewaySettingsId.get());

        if (getResponseCode(dataMap) == 200) {
            assertThat(response.getBody()).isNotNull();
        } else if (getResponseCode(dataMap) == 404) {
            verifyBadRequestResponse(dataMap, response);
        } else {
            Assertions.fail(format("The response code is not as expected %s", getResponseCode(dataMap)));
        }
    }

    @Then("^User gets 'Unified Payment Gateway Settings'$")
    public void getPaymentGatewaySettings(Map<String, String> dataMap) {
        Response response = ApiHelperPaymentGatewaySettingsConfiguration.getPaymentsGatewaySettings(TOKEN, createdWidgetId.get());
        switch (getWidgetId(dataMap)) {
            case "valid":
                if (response.statusCode() == 200) {

                    PaymentGatewaySettingsResponse settings = response.as(PaymentGatewaySettingsResponse.class);
                    softly.assertThat(settings.paymentGatewaySettingsId).isEqualTo(paymentGatewaySettingsId.get());
                    softly.assertThat(settings.paymentGatewayId).isEqualTo(Integer.parseInt(dataMap.get("o.paymentGatewayId")));
                    softly.assertThat(settings.paymentIntegrationTypeId).isEqualTo(unifiedPaymentsSettings.getPaymentIntegrationTypeId());
                    softly.assertThat(settings.merchantId).isEqualTo(unifiedPaymentsSettings.getMerchantId());
                    softly.assertThat(settings.saProfileId).isEqualTo(unifiedPaymentsSettings.getSaProfileId());
                    softly.assertThat(settings.shipToCountries).isEqualTo(unifiedPaymentsSettings.getShipToCountriesIds());
                    softly.assertThat(settings.getCreatedTime()).isBeforeOrEqualTo(LocalDate.now());
                    softly.assertThat(settings.getModifiedTime()).isBeforeOrEqualTo(LocalDate.now());
                    softly.assertThat(settings.requestEmail).isEqualTo(unifiedPaymentsSettings.getRequestEmail());
                    softly.assertThat(settings.requestPhone).isEqualTo(unifiedPaymentsSettings.getRequestPhone());
                    softly.assertThat(settings.requestShipping).isEqualTo(unifiedPaymentsSettings.getRequestShipping());
                    softly.assertThat(settings.transactionType).isEqualTo(unifiedPaymentsSettings.getTransactionType());
                    softly.assertThat(settings.restApiAccessKey).isEqualTo(unifiedPaymentsSettings.getRestApiAccessKey());
                    softly.assertThat(settings.restApiSecretKey).isEqualTo(unifiedPaymentsSettings.getRestApiSecretKey());
                    softly.assertThat(settings.billingType).isEqualTo(unifiedPaymentsSettings.getBillingType());
                    softly.assertAll();
                } else {
                    Assertions.fail(format("The response code is not as expected %s", getResponseCode(dataMap)));
                }
                break;
            case "invalid":
                verifyBadRequestResponse(dataMap, response);
        }
    }

    @Then("^User sets up 'Unified Payment Setting' for widget$")
    public void setupUnifiedPaymentSetting() {
        paymentGatewaySettingsId.set(setupUnifiedPaymentsSettings(createdWidgetId.get()));
    }

    private String setupUnifiedPaymentsSettings(String widgetId) {
        Response postQuery = postUnifiedPaymentsSettings(TOKEN, widgetId, unifiedPaymentsSettings);

        assertThat(postQuery.getStatusCode())
                .as("Setup Unified Payments Settings for widget. Expected status is 200!")
                .isEqualTo(200);

        return postQuery.getBody().jsonPath().getString("paymentGatewaySettingsId");
    }
}
