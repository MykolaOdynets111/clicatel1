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
import org.assertj.core.api.Assertions;
import utils.Validator;

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
                .getPaymentGatewaySettingsId());
    }

    @When("^User gets application Id for widget$")
    public void getApplicationId() {
        applicationID.set(TransactionsHelper
                .getIntegrationResponse(widgetId.get())
                .getIntegrator()
                .getApplicationUuid());
    }

    @When("^User gets activation key for widget$")
    public void getActivationKey() {
        activationKey.set(TransactionsHelper.getActivationKey(widgetId.get())
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
        String status = dataMap.get("i.paymentLinkRef");
        switch (status) {
            case "valid":
                response = TransactionsHelper.cancelPaymentLink(paymentLinkRef.get(), activationKey.get());
                checkResponseCode(response, dataMap.get("o.responseCode"));
                CancelPaymentLinkResponse cancelPaymentLinkResponse = response
                        .as(CancelPaymentLinkResponse.class);
                assertThat(cancelPaymentLinkResponse.getTransactionStatus())
                        .as(format("transaction status is not equals to %s", cancelPaymentLinkResponse.getTransactionStatus()))
                        .isEqualTo(dataMap.get("o.transactionStatus"));
                break;
            case "alreadyCancelled":
                TransactionsHelper.cancelPaymentLink(paymentLinkRef.get(), activationKey.get());
                response = TransactionsHelper.cancelPaymentLink(paymentLinkRef.get(), activationKey.get());
                Validator.validateErrorResponse(response, dataMap);
                break;
            case "nonExisted":
                response = TransactionsHelper.cancelPaymentLink("nonExistedLink", activationKey.get());
                Validator.validateErrorResponse(response, dataMap);
                break;
            case "expired":
                Thread.sleep(100000);
                response = TransactionsHelper.cancelPaymentLink(paymentLinkRef.get(), activationKey.get());
                Validator.validateErrorResponse(response, dataMap);
                break;
            default:
                Assertions.fail(format("Expected status %s is not existed", status));
        }
    }

    @Then("^User gets a correct payment link with status code (.*)$")
    public void userCanGetAPaymentLink(String statusCode) {
        Response response = TransactionsHelper.userGetAPaymentLinkResponse(paymentBody.get(), activationKey.get());
        checkResponseCode(response, statusCode);
        PaymentLinkResponse paymentLinkResponse = response
                .as(PaymentLinkResponse.class);
        paymentLink.set(paymentLinkResponse.getPaymentLink());
        paymentLinkRef.set(paymentLinkResponse.paymentLinkRef);
    }

    @Then("^User gets an error for payment link creation with status code (.*)$")
    public void userCanNotGetAPaymentLink(String statusCode) {
        Response response = TransactionsHelper.userGetAPaymentLinkResponse(paymentBody.get(), activationKey.get());
        checkResponseCode(response, statusCode);
    }

    @Then("^The payment has success status code$")
    public void checkWorkingPaymentLink() {
        TransactionsHelper.checkWorkingPaymentLink(paymentLink.get());
    }

    private void checkResponseCode(Response response, String code) {
        assertThat(response.statusCode())
                .as(format("status code is not equals to %s", code))
                .isEqualTo(Integer.valueOf(code));
    }
}
