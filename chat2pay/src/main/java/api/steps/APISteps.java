package api.steps;

import api.clients.TransactionsClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class APISteps {


    @Given("User fetch token and accountID for an existed account")
    public void fetchTokenAndAccountIDPOST() throws JsonProcessingException {
        TransactionsClient.fetchTokenAndAccountIDPOST();
    }

    @When("User is logged in to unity")
    public void logInToUnity() throws JsonProcessingException {
        TransactionsClient.logInToUnity();
    }

    @When("^User gets widgetId for (.*) form$")
    public void getWidgetId(String widgetName) {
        TransactionsClient.getWidgetId(widgetName);
    }

    @When("^User gets paymentGatewaySettingsId for widget$")
    public void getPaymentGatewaySettingsId() {
        TransactionsClient.getPaymentGatewaySettingsId();
    }

    @When("^User gets application Id for widget$")
    public void getApplicationId() {
        TransactionsClient.getApplicationId();
    }

    @When("^User gets activation key for widget$")
    public void getActivationKey() {
        TransactionsClient.getActivationKey();
    }

    @Then("^User can get a correct payment link$")
    public void userCanGetAPaymentLink() throws JsonProcessingException {
        TransactionsClient.userCanGetAPaymentLink();
    }

    @Then("^The payment link is working$")
    public void checkWorkingPaymentLink() {
        TransactionsClient.checkWorkingPaymentLink();
    }

}
