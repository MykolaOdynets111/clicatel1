package steps;

import api.clients.ApiHelperChat2Pay;
import api.models.response.paymentgatewaysettingsresponse.BillingType;
import api.models.response.paymentgatewaysettingsresponse.CardNetwork;
import api.models.response.paymentgatewaysettingsresponse.Country;
import api.models.response.paymentgatewaysettingsresponse.Currency;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import java.util.Map;

import static api.clients.ApiHelperPaymentConfigurationSupport.getBillingType;
import static api.clients.ApiHelperPaymentConfigurationSupport.getCardNetwork;
import static api.clients.ApiHelperPaymentConfigurationSupport.getCountry;
import static api.clients.ApiHelperPaymentConfigurationSupport.getCurrency;
import static java.lang.Integer.parseInt;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PaymentConfigurationSupportEndpointsSteps extends GeneralSteps {
    private static final String TOKEN = ApiHelperChat2Pay.token.get();
    private Response response;

    @Then("^User gets 'Billing Type'$")
    public void getBillingTypes(Map<String, String> dataMap) {
        response = getBillingType(TOKEN);

        if (getResponseCode(dataMap) == 200) {
            response.jsonPath().getList("", BillingType.class)
                    .stream().filter(bt -> bt.getId() == parseInt(dataMap.get("o.id")))
                    .findFirst().ifPresent(b ->
                            assertThat(b.getName()).isEqualToIgnoringCase(dataMap.get("o.name")));
        }
    }

    @Then("^User gets 'Card Network'$")
    public void getCardNetworks(Map<String, String> dataMap) {
        response = getCardNetwork(TOKEN);

        if (getResponseCode(dataMap) == 200) {
            response.jsonPath().getList("", CardNetwork.class)
                    .stream().filter(bt -> bt.getId() == parseInt(dataMap.get("o.id")))
                    .findFirst().ifPresent(cardNetwork ->
                            assertThat(cardNetwork.getName()).isEqualToIgnoringCase(dataMap.get("o.name")));
        }
    }

    @Then("^User gets 'Country'$")
    public void getCountries(Map<String, String> dataMap) {
        response = getCountry(TOKEN);

        if (getResponseCode(dataMap) == 200) {
            response.jsonPath().getList("", Country.class)
                    .stream().filter(bt -> bt.getId() == parseInt(dataMap.get("o.id")))
                    .findFirst().ifPresent(country ->
                            assertThat(country.getName()).isEqualToIgnoringCase(dataMap.get("o.name")));
        }
    }

    @Then("^User gets 'Currency'$")
    public void getCurrencies(Map<String, String> dataMap) {
        response = getCurrency(TOKEN);

        if (getResponseCode(dataMap) == 200) {
            response.jsonPath().getList("", Currency.class)
                    .stream().filter(bt -> bt.getId() == parseInt(dataMap.get("o.id")))
                    .findFirst().ifPresent(currency -> {
                                softly.assertThat(currency.getName()).isEqualToIgnoringCase(dataMap.get("o.name"));
                                softly.assertThat(currency.getIso()).isEqualToIgnoringCase(dataMap.get("o.iso"));
                                softly.assertThat(currency.getSymbol()).isEqualTo(dataMap.get("o.symbol"));
                                softly.assertAll();
                            }
                    );
        }
    }

//    @Then("^User gets 'Currency'$")
//    public void getCurrencies(Map<String, String> dataMap) {
//        response = getCurrency(TOKEN);
//
//        if (getResponseCode(dataMap) == 200) {
//            response.jsonPath().getList("", Currency.class)
//                    .stream().filter(bt -> bt.getId() == parseInt(dataMap.get("o.id")))
//                    .findFirst().ifPresent(currency -> {
//                                softly.assertThat(currency.getName()).isEqualToIgnoringCase(dataMap.get("o.name"));
//                                softly.assertThat(currency.getIso()).isEqualToIgnoringCase(dataMap.get("o.iso"));
//                                softly.assertThat(currency.getSymbol()).isEqualTo(dataMap.get("o.symbol"));
//                                softly.assertAll();
//                            }
//                    );
//        }
//    }
}
