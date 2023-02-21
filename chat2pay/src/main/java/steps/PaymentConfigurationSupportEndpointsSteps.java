package steps;

import api.clients.ApiHelperChat2Pay;
import api.models.response.paymentgatewaysettingsresponse.BillingType;
import api.models.response.paymentgatewaysettingsresponse.CardNetwork;
import api.models.response.paymentgatewaysettingsresponse.Country;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import java.util.Map;

import static api.clients.ApiHelperPaymentConfigurationSupport.getBillingType;
import static api.clients.ApiHelperPaymentConfigurationSupport.getCardNetwork;
import static api.clients.ApiHelperPaymentConfigurationSupport.getCountry;
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
                    .findFirst().ifPresent(b ->
                            assertThat(b.getName()).isEqualToIgnoringCase(dataMap.get("o.name")));
        }
    }

    @Then("^User gets 'Country'$")
    public void getCountries(Map<String, String> dataMap) {
        response = getCountry(TOKEN);

        if (getResponseCode(dataMap) == 200) {
            response.jsonPath().getList("", Country.class)
                    .stream().filter(bt -> bt.getId() == parseInt(dataMap.get("o.id")))
                    .findFirst().ifPresent(b ->
                            assertThat(b.getName()).isEqualToIgnoringCase(dataMap.get("o.name")));
        }
    }
}
