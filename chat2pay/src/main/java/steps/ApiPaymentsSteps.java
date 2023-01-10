package steps;

import api.clients.ApiHelperTransactions;
import api.models.request.PaymentBody;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Map;

import static api.clients.ApiHelperTransactions.getC2PConfigurationResponse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ApiPaymentsSteps {

    public static final ThreadLocal<PaymentBody> paymentBody = new ThreadLocal<>();
    public static final ThreadLocal<String> widgetId = new ThreadLocal<>();
    public static final ThreadLocal<String> paymentGatewaySettingsId = new ThreadLocal<>();
    public static final ThreadLocal<String> applicationID = new ThreadLocal<>();
    public static final ThreadLocal<String> activationKey = new ThreadLocal<>();
    public static final ThreadLocal<String> paymentLink = new ThreadLocal<>();

    @When("^User gets widgetId for (.*) form$")
    public void getWidgetId(String widgetName) {
        widgetId.set(ApiHelperTransactions.getWidgetId(widgetName));
    }

    @When("^User gets paymentGatewaySettingsId for widget$")
    public void getPaymentGatewaySettingsId() {
        paymentGatewaySettingsId.set(ApiHelperTransactions.getPaymentGatewaySettingsId(widgetId.get()));
    }

    @When("^User gets application Id for widget$")
    public void getApplicationId() {
        applicationID.set(ApiHelperTransactions.getApplicationId(widgetId.get()));
    }

    @When("^User gets activation key for widget$")
    public void getActivationKey() {
        activationKey.set(ApiHelperTransactions.getActivationKey(widgetId.get()));
    }

    @When("^User sets data in the payment body$")
    public void setPaymentBody(Map<String, String> dataMap) {
        paymentBody.set(new PaymentBody(dataMap, paymentGatewaySettingsId.get(), applicationID.get()));
    }

    @Then("^User gets a correct payment link$")
    public void userCanGetAPaymentLink() {
        paymentLink.set(ApiHelperTransactions.userCanGetAPaymentLink(paymentBody.get(), activationKey.get()));
    }

    @Then("^User gets an error for payment link creation$")
    public void userCanNotGetAPaymentLink() {
        ApiHelperTransactions.userCanNotGetAPaymentLink(paymentBody.get(), activationKey.get());
    }

    @Then("^The payment has success status code$")
    public void checkWorkingPaymentLink() {
        ApiHelperTransactions.checkWorkingPaymentLink(paymentLink.get());
    }

    @Then("^User get the C2P configuration with status code (.*) using (.*) key$")
    public void getC2PConfiguration(int statusCode, String activationKey) {
        assertThat(getC2PConfigurationResponse(activationKey).getStatusCode())
                .as("User gets proper response")
                .isEqualTo(statusCode);
    }
}
