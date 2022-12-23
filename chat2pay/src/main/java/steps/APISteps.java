package steps;

import api.clients.TransactionsHelper;
import api.models.request.PaymentBody;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONException;

import java.util.Map;

public class APISteps {

    public static final ThreadLocal<PaymentBody> paymentBody = new ThreadLocal<>();
    public static final ThreadLocal<String> token = new ThreadLocal<>();
    public static final ThreadLocal<String> widgetId = new ThreadLocal<>();
    public static final ThreadLocal<String> paymentGatewaySettingsId = new ThreadLocal<>();
    public static final ThreadLocal<String> applicationID = new ThreadLocal<>();
    public static final ThreadLocal<String> activationKey = new ThreadLocal<>();
    public static final ThreadLocal<String> paymentLink = new ThreadLocal<>();


    @Given("^User is logged in to unity$")
    public void logInToUnity() {
        token.set(TransactionsHelper.logInToUnity());
    }

    @When("^User gets widgetId for (.*) form$")
    public void getWidgetId(String widgetName) {
        widgetId.set(TransactionsHelper.getWidgetId(widgetName, token.get()));
    }

    @When("^User gets paymentGatewaySettingsId for widget$")
    public void getPaymentGatewaySettingsId() {
        paymentGatewaySettingsId.set(TransactionsHelper.getPaymentGatewaySettingsId(token.get(), widgetId.get()));
    }

    @When("^User gets application Id for widget$")
    public void getApplicationId() {
        applicationID.set(TransactionsHelper.getApplicationId(token.get(), widgetId.get()));
    }

    @When("^User gets activation key for widget$")
    public void getActivationKey() {
        activationKey.set(TransactionsHelper.getActivationKey(token.get(), widgetId.get()));
    }

    @When("^User sets data in the payment body$")
    public void setPaymentBody(Map<String, String> dataMap) {
        paymentBody.set(new PaymentBody(dataMap, paymentGatewaySettingsId.get(), applicationID.get()));
    }

    @Then("^User gets a correct payment link$")
    public void userCanGetAPaymentLink() {
        paymentLink.set(TransactionsHelper.userCanGetAPaymentLink(paymentBody.get(), activationKey.get()));
    }

    @Then("^User gets an error for payment link creation$")
    public void userCanNotGetAPaymentLink() {
        TransactionsHelper.userCanNotGetAPaymentLink(paymentBody.get(), activationKey.get());
    }

    @Then("^The payment has (.*) status code$")
    public void checkWorkingPaymentLink(int statusCode) {
        TransactionsHelper.checkWorkingPaymentLink(statusCode, token.get(), paymentLink.get());
    }

}
