package steps;

import api.ApiHelperChat2Pay;
import data.models.response.merchantbillinginfo.State;
import data.models.response.paymentgatewaysettingsresponse.BillingType;
import data.models.response.paymentgatewaysettingsresponse.CardNetwork;
import data.models.response.paymentgatewaysettingsresponse.Country;
import data.models.response.paymentgatewaysettingsresponse.Currency;
import data.models.response.paymentgatewaysettingsresponse.IntegrationType;
import data.models.response.paymentgatewaysettingsresponse.Locale;
import data.models.response.paymentgatewaysettingsresponse.PaymentType;
import data.models.response.paymentgatewaysettingsresponse.TransactionType;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.NoSuchSessionException;

import java.util.Map;
import java.util.NoSuchElementException;

import static api.ApiHelperPaymentConfigurationSupport.getBillingType;
import static api.ApiHelperPaymentConfigurationSupport.getCardNetwork;
import static api.ApiHelperPaymentConfigurationSupport.getCountry;
import static api.ApiHelperPaymentConfigurationSupport.getCurrency;
import static api.ApiHelperPaymentConfigurationSupport.getIntegrationType;
import static api.ApiHelperPaymentConfigurationSupport.getLocale;
import static api.ApiHelperPaymentConfigurationSupport.getPaymentType;
import static api.ApiHelperPaymentConfigurationSupport.getState;
import static api.ApiHelperPaymentConfigurationSupport.getTransactionType;
import static api.ApiHelperPayments.getPaymentGatewaySettingsResponse;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PaymentConfigurationSupportEndpointsSteps extends GeneralSteps {
    private static final String TOKEN = ApiHelperChat2Pay.token.get();
    private Response response;

    @Then("^User gets 'Billing Type'$")
    public void getBillingTypes(Map<String, String> dataMap) {
        response = getBillingType(TOKEN);

        if (getResponseCode(dataMap) == 200) {
            BillingType billingType = response.jsonPath().getList("", BillingType.class)
                    .stream().filter(bt -> bt.getId() == parseInt(dataMap.get("o.id")))
                    .findFirst().orElseThrow(NoSuchElementException::new);
            assertThat(billingType.getName()).isEqualToIgnoringCase(dataMap.get("o.name"));
        } else {
            Assertions.fail(format("The response code is not as expected %s", getResponseCode(dataMap)));
        }
    }

    @Then("^User gets 'Card Network'$")
    public void getCardNetworks(Map<String, String> dataMap) {
        response = getCardNetwork(TOKEN);

        if (getResponseCode(dataMap) == 200) {
            CardNetwork cardNetwork = response.jsonPath().getList("", CardNetwork.class)
                    .stream().filter(bt -> bt.getId() == parseInt(dataMap.get("o.id")))
                    .findFirst().orElseThrow(NoSuchElementException::new);
            assertThat(cardNetwork.getName()).isEqualToIgnoringCase(dataMap.get("o.name"));
        } else {
            Assertions.fail(format("The response code is not as expected %s", getResponseCode(dataMap)));
        }
    }

    @Then("^User gets 'Country'$")
    public void getCountries(Map<String, String> dataMap) {
        response = getCountry(TOKEN);

        if (getResponseCode(dataMap) == 200) {
            Country country = response.jsonPath().getList("", Country.class)
                    .stream().filter(bt -> bt.getId() == parseInt(dataMap.get("o.id")))
                    .findFirst().orElseThrow(NoSuchElementException::new);
            assertThat(country.getName()).isEqualToIgnoringCase(dataMap.get("o.name"));
        } else {
            Assertions.fail(format("The response code is not as expected %s", getResponseCode(dataMap)));
        }
    }

    @Then("^User gets 'Currency'$")
    public void getCurrencies(Map<String, String> dataMap) {
        IntegrationType integrationType = getIntegrationType(TOKEN)
                .jsonPath().getList("", IntegrationType.class)
                .stream().filter(bt -> bt.getName().equalsIgnoreCase(dataMap.get("i.paymentIntegrationType")))
                .findFirst().orElseThrow(NoSuchElementException::new);

        response = getCurrency(TOKEN, String.valueOf(integrationType.getId()));

        if (getResponseCode(dataMap) == 200) {
            Currency currency = response.jsonPath().getList("", Currency.class)
                    .stream().filter(bt -> bt.getId() == parseInt(dataMap.get("o.id")))
                    .findFirst().orElseThrow(NoSuchElementException::new);
            softly.assertThat(currency.getName()).isEqualToIgnoringCase(dataMap.get("o.name"));
            softly.assertThat(currency.getIso()).isEqualToIgnoringCase(dataMap.get("o.iso"));
            softly.assertThat(currency.getSymbol()).isEqualTo(dataMap.get("o.symbol"));
            softly.assertAll();
        } else {
            Assertions.fail(format("The response code is not as expected %s", getResponseCode(dataMap)));
        }
    }

    @Then("^User gets 'Integration Type'$")
    public void getIntegrationTypes(Map<String, String> dataMap) {
        response = getIntegrationType(TOKEN);

        if (getResponseCode(dataMap) == 200) {
            IntegrationType integrationType = response.jsonPath().getList("", IntegrationType.class)
                    .stream().filter(bt -> bt.getId() == parseInt(dataMap.get("o.id")))
                    .findFirst().orElseThrow(NoSuchElementException::new);
            assertThat(integrationType.getName()).isEqualToIgnoringCase(dataMap.get("o.name"));
        } else {
            Assertions.fail(format("The response code is not as expected %s", getResponseCode(dataMap)));
        }
    }

    @Then("^User gets 'Locale'$")
    public void getLocales(Map<String, String> dataMap) {
        String paymentGatewayId = String.valueOf(getPaymentGatewaySettingsResponse(widgetId.get()).getPaymentGatewayId());
        response = getLocale(TOKEN, paymentGatewayId);

        if (getResponseCode(dataMap) == 200) {
            Locale locale = response.jsonPath().getList("", Locale.class)
                    .stream().filter(bt -> bt.getId() == parseInt(dataMap.get("o.id")))
                    .findFirst().orElseThrow(NoSuchElementException::new);
            assertThat(locale.getName()).isEqualToIgnoringCase(dataMap.get("o.name"));
        } else {
            Assertions.fail(format("The response code is not as expected %s", getResponseCode(dataMap)));
        }
    }

    @Then("^User gets 'Payment Type'$")
    public void getPaymentTypes(Map<String, String> dataMap) {
        response = getPaymentType(TOKEN);

        if (getResponseCode(dataMap) == 200) {
            PaymentType paymentType = response.jsonPath().getList("", PaymentType.class)
                    .stream().filter(bt -> bt.getId() == parseInt(dataMap.get("o.id")))
                    .findFirst().orElseThrow(NoSuchSessionException::new);
            softly.assertThat(paymentType.getName()).isEqualToIgnoringCase(dataMap.get("o.name"));
            softly.assertThat(paymentType.getDescription()).isEqualToIgnoringCase(dataMap.get("o.description"));
            softly.assertThat(paymentType.getDisplayName()).isEqualToIgnoringCase(dataMap.get("o.displayName"));
            softly.assertAll();
        } else {
            Assertions.fail(format("The response code is not as expected %s", getResponseCode(dataMap)));
        }
    }

    @Then("^User gets 'State'$")
    public void getStates(Map<String, String> dataMap) {
        response = getState(TOKEN);

        if (getResponseCode(dataMap) == 200) {
            State state = response.jsonPath().getList("", State.class)
                    .stream().filter(bt -> bt.getId() == parseInt(dataMap.get("o.id")))
                    .findFirst().orElseThrow(NoSuchSessionException::new);

            assertThat(state.getName()).isEqualToIgnoringCase(dataMap.get("o.name"));
        } else {
            Assertions.fail(format("The response code is not as expected %s", getResponseCode(dataMap)));
        }
    }

    @Then("^User gets 'Transaction Type'$")
    public void getTransactionTypes(Map<String, String> dataMap) {
        response = getTransactionType(TOKEN);

        if (getResponseCode(dataMap) == 200) {
            TransactionType state = response.jsonPath().getList("", TransactionType.class)
                    .stream().filter(bt -> bt.getId() == parseInt(dataMap.get("o.id")))
                    .findFirst().orElseThrow(NoSuchSessionException::new);

            assertThat(state.getName()).isEqualToIgnoringCase(dataMap.get("o.name"));
        } else {
            Assertions.fail(format("The response code is not as expected %s", getResponseCode(dataMap)));
        }
    }
}
