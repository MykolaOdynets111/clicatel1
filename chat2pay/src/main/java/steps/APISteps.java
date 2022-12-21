package steps;

import api.clients.TransactionsHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Map;

public class APISteps {


    @Given("^User fetch token and accountID for an existed account$")
    public void fetchTokenAndAccountIDPOST() throws JsonProcessingException {
        TransactionsHelper.fetchTokenAndAccountIDPOST();
    }

    @When("^User is logged in to unity$")
    public void logInToUnity() throws JsonProcessingException {
        TransactionsHelper.logInToUnity();
    }

    @When("^User gets widgetId for (.*) form$")
    public void getWidgetId(String widgetName) {
        TransactionsHelper.getWidgetId(widgetName);
    }

    @When("^User gets paymentGatewaySettingsId for widget$")
    public void getPaymentGatewaySettingsId() {
        TransactionsHelper.getPaymentGatewaySettingsId();
    }

    @When("^User gets application Id for widget$")
    public void getApplicationId() {
        TransactionsHelper.getApplicationId();
    }

    @When("^User gets activation key for widget$")
    public void getActivationKey() {
        TransactionsHelper.getActivationKey();
    }

    @When("^User sets data in the payment body$")
    public void setPaymentBody(Map<String, String> dataMap) throws JsonProcessingException {
        TransactionsHelper.setPaymentBody(dataMap);
    }

    @Then("^User gets a correct payment link$")
    public void userCanGetAPaymentLink() {
        TransactionsHelper.userCanGetAPaymentLink();
    }

    @Then("^User gets an error for payment link creation$")
    public void userCanNotGetAPaymentLink() {
        TransactionsHelper.userCanNotGetAPaymentLink();
    }

    @Then("^The payment has (.*) status code$")
    public void checkWorkingPaymentLink(int statusCode) {
        TransactionsHelper.checkWorkingPaymentLink(statusCode);
    }

}
