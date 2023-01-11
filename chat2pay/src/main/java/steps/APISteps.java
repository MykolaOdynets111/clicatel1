package steps;

import api.clients.Chat2PayApiHelper;
import api.clients.TransactionsHelper;
import api.models.request.PaymentBody;
import api.models.response.CancelPaymentLinkResponse;
import api.models.response.PaymentLinkResponse;
import drivermanager.ConfigManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import steps.portalsteps.BasePortalSteps;

import java.util.Map;

import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.testng.AssertJUnit.assertFalse;

public class APISteps {

    public static final ThreadLocal<PaymentBody> paymentBody = new ThreadLocal<>();
    public static final ThreadLocal<CancelPaymentLinkResponse> cancelPaymentLinkResponse = new ThreadLocal<>();
    public static final ThreadLocal<String> widgetId = new ThreadLocal<>();
    public static final ThreadLocal<String> paymentGatewaySettingsId = new ThreadLocal<>();
    public static final ThreadLocal<String> applicationID = new ThreadLocal<>();
    public static final ThreadLocal<String> activationKey = new ThreadLocal<>();
    public static final ThreadLocal<String> paymentLink = new ThreadLocal<>();
    public static final ThreadLocal<String> paymentLinkRef = new ThreadLocal<>();


    @Given("^User is logged in to unity$")
    public void logInToUnity() {
        Chat2PayApiHelper.logInToUnity();
    }

    @When("^User gets widgetId for (.*) form$")
    public void getWidgetId(String widgetName) {
        widgetId.set(TransactionsHelper.getWidgetId(widgetName));
    }

    @When("^User gets paymentGatewaySettingsId for widget$")
    public void getPaymentGatewaySettingsId() {
        paymentGatewaySettingsId.set(TransactionsHelper
                .getPaymentGatewaySettingsResponse(widgetId.get())
                .stream().findAny()
                .orElseThrow(() -> new AssertionError("No payment gateway settings found for widget" + widgetId.get()))
                .getPaymentGatewaySettingsId());
    }

    @When("^User gets application Id for widget$")
    public void getApplicationId() {
        applicationID.set(TransactionsHelper
                .getIntegrationResponse(widgetId.get())
                .stream().findAny()
                .orElseThrow(() -> new AssertionError("No application ID found for widget" + widgetId.get()))
                .getIntegrator().getApplicationUuid());
    }

    @When("^User gets activation key for widget$")
    public void getActivationKey() {
        activationKey.set(TransactionsHelper.getActivationKey(widgetId.get())
                .stream().findAny()
                .orElseThrow(() -> new AssertionError("No activation key found for widget" + widgetId.get()))
                .getApiKey());
    }

    @When("^User sets data in the payment body$")
    public void setPaymentBody(Map<String, String> dataMap) {
        paymentBody.set(new PaymentBody(dataMap, paymentGatewaySettingsId.get(), applicationID.get()));
    }

    @When("^User sets valid data in the payment body$")
    public void setValidPaymentBody() {
        paymentBody.set(new PaymentBody(paymentGatewaySettingsId.get(), applicationID.get()));
    }

    @When("^User cancelling the payment link$")
    public void cancelPayment(Map<String, String> dataMap) {
        CancelPaymentLinkResponse response = TransactionsHelper.cancelPaymentLink(paymentLinkRef.get(), activationKey.get())
                .as(CancelPaymentLinkResponse.class);
        cancelPaymentLinkResponse.set(response);
    }

    @Then("^User gets a correct payment link with status code (.*)$")
    public void userCanGetAPaymentLink(int statusCode) {
        PaymentLinkResponse paymentLinkResponse = checkGetPaymentLinkResponseCode(statusCode)
                .as(PaymentLinkResponse.class);
        paymentLink.set(paymentLinkResponse.getPaymentLink());
        paymentLinkRef.set(paymentLinkResponse.paymentLinkRef);
    }

    @Then("^User gets an error for payment link creation with status code (.*)$")
    public void userCanNotGetAPaymentLink(int statusCode) {
        checkGetPaymentLinkResponseCode(statusCode);
    }

    @Then("^The payment has success status code$")
    public void checkWorkingPaymentLink() {
        TransactionsHelper.checkWorkingPaymentLink(paymentLink.get());
    }

    @Then("^User checks Valid Cancel Payment response$")
    public void userChecksValidCancelPaymentResponse(Map<String, String> dataMap) {
        assertThat(cancelPaymentLinkResponse.get().getTransactionStatus())
                .as(format("transaction status is not equals to %s", cancelPaymentLinkResponse.get().getTransactionStatus()))
                .isEqualTo(dataMap.get("o.transactionStatus"));
    }

    private Response checkGetPaymentLinkResponseCode(int code) {
        Response response = TransactionsHelper.userGetAPaymentLinkResponse(paymentBody.get(), activationKey.get());
        assertThat(response.statusCode())
                .as(format("status code is not equals to %s", code))
                .isEqualTo(code);
        return response;
    }
}
