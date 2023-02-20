package steps;

import api.clients.ApiHelperChat2Pay;
import api.models.response.paymentgatewaysettingsresponse.BillingType;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import java.util.Map;

import static api.clients.ApiHelperPaymentConfigurationSupport.getBillingType;
import static api.clients.ApiHelperPaymentConfigurationSupport.getCardNetwork;
import static java.lang.Integer.parseInt;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PaymentConfigurationSupportEndpointsSteps extends GeneralSteps {
    private static final String TOKEN = ApiHelperChat2Pay.token.get();

    private Response response;

    @Then("^User gets 'Billing Type'$")
    public void getBillingTypes(Map<String, String> dataMap) {
        response = getBillingType(TOKEN);
        int expectedResponseCode = parseInt(getResponseCode(dataMap));

        if (expectedResponseCode == 200) {
            response.jsonPath().getList("", BillingType.class)
                    .stream().filter(bt -> bt.getId() == parseInt(dataMap.get("o.id")))
                    .findFirst().ifPresent(b ->
                            assertThat(b.getName()).isEqualToIgnoringCase(dataMap.get("o.name")));
        }
    }
    @Then("^User gets 'Card Network'$")
    public void getCardNetworks(Map<String, String> dataMap) {
        response = getCardNetwork(TOKEN);
        int expectedResponseCode = parseInt(getResponseCode(dataMap));

        if (expectedResponseCode == 200) {
            response.jsonPath().getList("", BillingType.class)
                    .stream().filter(bt -> bt.getId() == parseInt(dataMap.get("o.id")))
                    .findFirst().ifPresent(b ->
                            assertThat(b.getName()).isEqualToIgnoringCase(dataMap.get("o.name")));
        }
    }
}
