package steps;

import api.ApiHelperPaymentGatewaySettingsConfiguration;
import data.models.request.payments.PaymentGatewaySettingsSecureAcceptance;
import data.models.request.payments.PaymentGatewaySettingsUnifiedPayments;
import data.models.response.failedresponse.BadRequestResponse;
import data.models.response.paymentgatewaysettingsresponse.*;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static api.ApiHelperPaymentGatewaySettingsConfiguration.postSecureAcceptanceSettings;
import static api.ApiHelperPaymentGatewaySettingsConfiguration.postUnifiedPaymentsSettings;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static utils.Validator.verifyBadRequestResponse;

public class PaymentGatewaySettingsConfigurationSteps extends GeneralSteps {

    public final PaymentGatewaySettingsUnifiedPayments unifiedPaymentsSettings = new PaymentGatewaySettingsUnifiedPayments();
    public final PaymentGatewaySettingsSecureAcceptance secureAcceptanceSettings = new PaymentGatewaySettingsSecureAcceptance();
    private Response response;

    @Then("^User sets up 'Secure Acceptance Setting' for widget$")
    public void setupSecureAcceptanceSetting() {
        paymentGatewaySettingsId.set(setupSecureAcceptanceSettings(createdWidgetId.get()));
    }

    @Then("^User sets up 'Unified Payment Setting' for widget$")
    public void setupUnifiedPaymentSetting() {
        paymentGatewaySettingsId.set(setupUnifiedPaymentsSettings(createdWidgetId.get()));
    }

    @Then("^User posts 'Payment Gateway Logo'$")
    public void postLogo(Map<String, String> dataMap) {
        File logo = new File(format(System.getProperty("user.dir") + "/src/test/resources/logos/%s", "LogoTest.png"));
        switch (dataMap.get("i.logo")) {
            case "valid":
                response = ApiHelperPaymentGatewaySettingsConfiguration.postLogo(createdWidgetId.get(), logo);
                Logo logoResponse = response.as(Logo.class);
                paymentGatewaySettingsId.set(setupUnifiedPaymentsSettings(createdWidgetId.get(), logoResponse.id));

                softly.assertThat(response.getStatusCode()).isEqualTo(200);
                softly.assertThat(logoResponse.originalFilename).isEqualTo(logo.getName());
                softly.assertThat(logoResponse.getCreatedTime()).isEqualTo(LocalDate.now());
                softly.assertThat(getResponseCode(response)).isEqualTo(200);
                softly.assertThat(response.getBody()).isNotNull();
                softly.assertAll();
                break;
            case "invalid":
                response = ApiHelperPaymentGatewaySettingsConfiguration.getLogo(null, null);
                assertThat(response.as(BadRequestResponse.class).message)
                        .startsWith(dataMap.get("o.path"));
                break;
            default:
                Assertions.fail(format("The response code is not as expected %s", getExpectedCode(dataMap)));
        }
    }

    @Then("^User gets 'Payment Gateway Logo'$")
    public void getLogo(Map<String, String> dataMap) {
        switch (dataMap.get("i.logo")) {
            case "valid":
                response = ApiHelperPaymentGatewaySettingsConfiguration.getLogo(createdWidgetId.get(), paymentGatewaySettingsId.get());
                assertThat(response).isNotNull();
                break;
            case "invalid":
                response = ApiHelperPaymentGatewaySettingsConfiguration.getLogo(null, null);
                assertThat(response.as(BadRequestResponse.class).message)
                        .startsWith(dataMap.get("o.path"));
                break;
            default:
                Assertions.fail(format("The response code is not as expected %s", getResponseCode(dataMap)));
        }
    }

    @Then("^User deletes 'Payment Gateway Logo'$")
    public void deleteLogo(Map<String, String> dataMap) {
        switch (dataMap.get("i.logo")) {
            case "valid":
                response = ApiHelperPaymentGatewaySettingsConfiguration.deleteLogo(createdWidgetId.get(), paymentGatewaySettingsId.get());
                assertThat(response.statusCode()).isEqualTo(200);
                break;
            case "invalid":
                response = ApiHelperPaymentGatewaySettingsConfiguration.deleteLogo(null, null);
                assertThat(response.as(BadRequestResponse.class).message)
                        .startsWith(dataMap.get("o.path"));
                break;
            default:
                Assertions.fail(format("The response code is not as expected %s", getResponseCode(dataMap)));
        }
    }

    @Then("^User gets 'Unified Payment Gateway Settings'$")
    public void getPaymentGatewaySettings(Map<String, String> dataMap) {
        switch (getWidgetId(dataMap)) {
            case "valid":
                response = ApiHelperPaymentGatewaySettingsConfiguration.getPaymentsGatewaySettings(createdWidgetId.get());
                if (response.statusCode() == 200) {
                    PaymentGatewaySettingsResponse settings = response.jsonPath().getList("", PaymentGatewaySettingsResponse.class)
                            .stream().filter(s -> s.getPaymentGatewaySettingsId().equals(paymentGatewaySettingsId.get()))
                            .findFirst().orElseThrow(NoSuchElementException::new);

                    softly.assertThat(settings.paymentGatewayId).isEqualTo(Integer.parseInt(dataMap.get("o.paymentGatewayId")));
                    softly.assertThat(settings.paymentIntegrationTypeId).isEqualTo(unifiedPaymentsSettings.getPaymentIntegrationTypeId());
                    softly.assertThat(settings.merchantId).isEqualTo(unifiedPaymentsSettings.getMerchantId());
                    softly.assertThat(settings.saProfileId).isEqualTo(unifiedPaymentsSettings.getSaProfileId());
                    softly.assertThat(settings.saAccessKey).isEqualTo(unifiedPaymentsSettings.getSaAccessKey());
                    softly.assertThat(settings.shipToCountries).isEqualTo(unifiedPaymentsSettings.getShipToCountriesIds());
                    softly.assertThat(settings.getCreatedTime()).isBeforeOrEqualTo(LocalDate.now());
                    softly.assertThat(settings.getModifiedTime()).isBeforeOrEqualTo(LocalDate.now());
                    softly.assertThat(settings.requestEmail).isEqualTo(unifiedPaymentsSettings.getRequestEmail());
                    softly.assertThat(settings.requestPhone).isEqualTo(unifiedPaymentsSettings.getRequestPhone());
                    softly.assertThat(settings.requestShipping).isEqualTo(unifiedPaymentsSettings.getRequestShipping());
                    softly.assertThat(settings.transactionType).isEqualToIgnoringCase(unifiedPaymentsSettings.getTransactionType());
                    softly.assertThat(settings.restApiAccessKey).isEqualTo(unifiedPaymentsSettings.getRestApiAccessKey());
                    softly.assertThat(settings.restApiSecretKey.substring(0, 3)).isEqualTo(unifiedPaymentsSettings.getRestApiSecretKey().substring(0, 3));
                    softly.assertThat(settings.billingType).isEqualTo(unifiedPaymentsSettings.getBillingType());
                    softly.assertThat(settings.currencies.size()).isEqualTo(unifiedPaymentsSettings.getCurrenciesIds().size());
                    softly.assertThat(settings.currencies.get(0).getId()).isEqualTo(unifiedPaymentsSettings.getCurrenciesIds().get(0));
                    softly.assertThat(settings.paymentTypes.size()).isEqualTo(unifiedPaymentsSettings.getPaymentTypesIds().size());
                    softly.assertThat(settings.paymentTypes.stream().map(PaymentType::getId).collect(Collectors.toList()))
                            .containsAll(unifiedPaymentsSettings.getPaymentTypesIds());
                    softly.assertThat(settings.cardNetworks.size()).isEqualTo(unifiedPaymentsSettings.getCardNetworksIds().size());
                    softly.assertThat(settings.cardNetworks.stream().map(CardNetwork::getId).collect(Collectors.toList()))
                            .containsAll(unifiedPaymentsSettings.getCardNetworksIds());

                    Locale locale = settings.locale;
                    softly.assertThat(locale.getId()).isEqualTo(unifiedPaymentsSettings.localeId);
                    softly.assertThat(locale.getName()).isEqualTo(dataMap.get("o.locale"));

                    Country country = settings.country;
                    softly.assertThat(country.getId()).isEqualTo(unifiedPaymentsSettings.countryId);
                    softly.assertThat(country.getName()).isEqualTo(dataMap.get("o.country"));

                    DefaultCurrency defaultCurrency = settings.defaultCurrency;
                    softly.assertThat(defaultCurrency.getId()).isEqualTo(unifiedPaymentsSettings.defaultCurrencyId);
                    softly.assertThat(defaultCurrency.getName()).isEqualTo(dataMap.get("o.defaultCurrency"));
                    softly.assertAll();
                } else {
                    Assertions.fail(format("The response code is not as expected %s", getExpectedCode(dataMap)));
                }
                break;
            case "invalid":
                response = ApiHelperPaymentGatewaySettingsConfiguration.getPaymentsGatewaySettings(null);
                verifyBadRequestResponse(dataMap, response);
                break;
            default:
                Assertions.fail(format("Expected status %s is not existed", getWidgetId(dataMap)));
        }
    }

    @Then("^User gets 'Secure Acceptance Settings'$")
    public void getSecureAcceptanceSettings(Map<String, String> dataMap) {
        switch (getWidgetId(dataMap)) {
            case "valid":
                response = ApiHelperPaymentGatewaySettingsConfiguration.getPaymentsGatewaySettings(createdWidgetId.get());
                if (response.statusCode() == 200) {
                    SecureAcceptanceGatewaySettingsResponse settings = response.jsonPath().getList("", SecureAcceptanceGatewaySettingsResponse.class)
                            .stream().filter(s -> s.getPaymentGatewaySettingsId().equals(paymentGatewaySettingsId.get()))
                            .findFirst().orElseThrow(NoSuchElementException::new);

                    softly.assertThat(settings.paymentGatewayId).isEqualTo(Integer.parseInt(dataMap.get("o.paymentGatewayId")));
                    softly.assertThat(settings.paymentIntegrationTypeId).isEqualTo(secureAcceptanceSettings.getPaymentIntegrationTypeId());
                    softly.assertThat(settings.merchantId).isEqualTo(secureAcceptanceSettings.getMerchantId());
                    softly.assertThat(settings.saProfileId).isEqualTo(secureAcceptanceSettings.getSaProfileId());
                    softly.assertThat(settings.saAccessKey).isEqualTo(secureAcceptanceSettings.getSaAccessKey());
                    softly.assertThat(settings.getCreatedTime()).isBeforeOrEqualTo(LocalDate.now());
                    softly.assertThat(settings.getModifiedTime()).isBeforeOrEqualTo(LocalDate.now());
                    softly.assertThat(settings.transactionType).isEqualToIgnoringCase(secureAcceptanceSettings.getTransactionType());
                    softly.assertThat(settings.restApiAccessKey).isEqualTo(secureAcceptanceSettings.getRestApiAccessKey());
                    softly.assertThat(settings.restApiSecretKey.substring(0, 3)).isEqualTo(secureAcceptanceSettings.getRestApiSecretKey().substring(0, 3));

                    List<Currency> currencies = settings.currencies;
                    softly.assertThat(currencies.size()).isEqualTo(secureAcceptanceSettings.getCurrenciesIds().size());
                    softly.assertThat(currencies.get(0).getId()).isEqualTo(secureAcceptanceSettings.getCurrenciesIds().get(0));

                    DefaultCurrency defaultCurrency = settings.defaultCurrency;
                    softly.assertThat(defaultCurrency.getId()).isEqualTo(unifiedPaymentsSettings.defaultCurrencyId);
                    softly.assertThat(defaultCurrency.getName()).isEqualTo(dataMap.get("o.defaultCurrency"));
                    softly.assertAll();
                } else {
                    Assertions.fail(format("The response code is not as expected %s", getExpectedCode(dataMap)));
                }
                break;
            case "invalid":
                response = ApiHelperPaymentGatewaySettingsConfiguration.getPaymentsGatewaySettings(null);
                verifyBadRequestResponse(dataMap, response);
                break;
            default:
                Assertions.fail(format("Expected status %s is not existed", getWidgetId(dataMap)));
        }
    }

    @Then("^User deletes 'Payment Gateway Settings'$")
    public void deletePaymentGatewaySettings(Map<String, String> dataMap) {
        switch (getWidgetId(dataMap)) {
            case "valid":
                String paymentGatewaySettingsId = GeneralSteps.paymentGatewaySettingsId.get();
                response = ApiHelperPaymentGatewaySettingsConfiguration.deletePaymentGatewaySettings(createdWidgetId.get(), paymentGatewaySettingsId);
                if (response.statusCode() == 200) {
                    String settingId = response.getBody().jsonPath().getString("paymentGatewaySettingId");

                    assertThat(settingId).isEqualTo(paymentGatewaySettingsId);
                } else {
                    Assertions.fail(format("The response code is not as expected %s", getExpectedCode(dataMap)));
                }
                break;
            case "invalid":
                response = ApiHelperPaymentGatewaySettingsConfiguration.getPaymentsGatewaySettings(null);
                verifyBadRequestResponse(dataMap, response);
                break;
            default:
                Assertions.fail(format("Expected status %s is not existed", getWidgetId(dataMap)));
        }
    }

    private String setupUnifiedPaymentsSettings(String widgetId) {
        Response postQuery = postUnifiedPaymentsSettings(widgetId, unifiedPaymentsSettings);

        assertThat(postQuery.getStatusCode())
                .as("Setup Unified Payments Settings for widget. Expected status is 200!")
                .isEqualTo(200);

        return postQuery.getBody().jsonPath().getString("paymentGatewaySettingsId");
    }

    private String setupUnifiedPaymentsSettings(String widgetId, String logoId) {
        Response postQuery = postUnifiedPaymentsSettings(widgetId, logoId, unifiedPaymentsSettings);

        assertThat(postQuery.getStatusCode())
                .as("Setup Unified Payments Settings for widget. Expected status is 200!")
                .isEqualTo(200);

        return postQuery.getBody().jsonPath().getString("paymentGatewaySettingsId");
    }

    private String setupSecureAcceptanceSettings(String widgetId) {
        Response postQuery = postSecureAcceptanceSettings(widgetId, secureAcceptanceSettings);

        assertThat(postQuery.getStatusCode())
                .as("Setup Secure Acceptance Settings for widget. Expected status is 200!")
                .isEqualTo(200);

        return postQuery.getBody().jsonPath().getString("paymentGatewaySettingsId");
    }
}
