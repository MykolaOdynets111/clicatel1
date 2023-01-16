package steps;

import api.clients.Chat2PayApiHelper;
import api.clients.TransactionsHelper;
import api.models.request.PaymentBody;
import api.models.response.CancelPaymentLinkResponse;
import api.models.response.ErrorResponse;
import api.models.response.PaymentLinkResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.Map;

import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class APISteps {

    public static final ThreadLocal<PaymentBody> paymentBody = new ThreadLocal<>();
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
    public void cancelPayment(Map<String, String> dataMap) throws InterruptedException {
        Response response;
        switch (dataMap.get("i.paymentLinkRef")) {
            case "valid":
                response = TransactionsHelper.cancelPaymentLink(paymentLinkRef.get(), activationKey.get());
                checkResponseCode(response, dataMap);
                CancelPaymentLinkResponse cancelPaymentLinkResponse = response
                        .as(CancelPaymentLinkResponse.class);
                assertThat(cancelPaymentLinkResponse.getTransactionStatus())
                        .as(format("transaction status is not equals to %s", cancelPaymentLinkResponse.getTransactionStatus()))
                        .isEqualTo(dataMap.get("o.transactionStatus"));
                break;
            case "alreadyCancelled":
                TransactionsHelper.cancelPaymentLink(paymentLinkRef.get(), activationKey.get());
                response = TransactionsHelper.cancelPaymentLink(paymentLinkRef.get(), activationKey.get());
                checkResponseCode(response, dataMap);
                ErrorResponse errorResponse = response.as(ErrorResponse.class);
                assertThat(errorResponse.getReason())
                        .as(format("reason is not equals to %s", errorResponse.getReason()))
                        .isEqualTo(dataMap.get("o.reason"));
                assertThat(errorResponse.getStatus())
                        .as(format("status is not equals to %s", errorResponse.getStatus()))
                        .isEqualTo(dataMap.get("o.status"));
                break;
            case "nonexisted":
                response = TransactionsHelper.cancelPaymentLink("nonExistedLink", activationKey.get());
                checkResponseCode(response, dataMap);
                ErrorResponse errorResponse2 = response.as(ErrorResponse.class);
                assertThat(errorResponse2.getStatus())
                        .as(format("status is not equals to %s", errorResponse2.getStatus()))
                        .isEqualTo(dataMap.get("o.status"));
                break;
            case "expired":
                Thread.sleep(10000);
                response = TransactionsHelper.cancelPaymentLink(paymentLinkRef.get(), activationKey.get());
                checkResponseCode(response, dataMap);
                ErrorResponse errorResponse3 = response.as(ErrorResponse.class);
                assertThat(errorResponse3.getReason())
                        .as(format("reason is not equals to %s", errorResponse3.getReason()))
                        .isEqualTo(dataMap.get("o.reason"));
                assertThat(errorResponse3.getStatus())
                        .as(format("status is not equals to %s", errorResponse3.getStatus()))
                        .isEqualTo(dataMap.get("o.status"));
                break;
        }
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

    private Response checkGetPaymentLinkResponseCode(int code) {
        Response response = TransactionsHelper.userGetAPaymentLinkResponse(paymentBody.get(), activationKey.get());
        assertThat(response.statusCode())
                .as(format("status code is not equals to %s", code))
                .isEqualTo(code);
        return response;
    }

    private void checkResponseCode(Response response, Map<String, String> dataMap) {
        assertThat(response.statusCode())
                .as(format("status code is not equals to %s", dataMap.get("o.responsecode")))
                .isEqualTo(Integer.valueOf(dataMap.get("o.responsecode")));
    }
}
