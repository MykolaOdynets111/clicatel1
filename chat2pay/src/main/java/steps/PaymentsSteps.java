package steps;

import api.clients.ApiHelperPayments;

import api.clients.ApiHelperWidgets;
import api.models.request.PaymentBody;
import api.models.response.CancelPaymentLinkResponse;
import api.models.response.PaymentLinkResponse;
import api.models.response.ReceiptOrderResponse;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import utils.Validator;

import java.util.Map;

import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static utils.Validator.checkResponseCode;

public class PaymentsSteps extends GeneralSteps{

    public static final ThreadLocal<PaymentBody> paymentBody = new ThreadLocal<>();
    public static final ThreadLocal<String> paymentGatewaySettingsId = new ThreadLocal<>();
    public static final ThreadLocal<String> paymentLink = new ThreadLocal<>();
    public static final ThreadLocal<String> paymentLinkRef = new ThreadLocal<>();
    public static final String EXPIRED_LINK = "629905f6-e916-4490-a547-bf8f0d5fb9f4";

    @When("^User gets widgetId for (.*) form$")
    public void getWidgetId(String widgetName) {
        widgetId.set(ApiHelperWidgets.getWidgetId(widgetName));
    }

    @When("^User gets application Id for widget$")
    public void getApplicationId() {
        applicationID.set(ApiHelperWidgets
                .getIntegrationResponse(widgetId.get())
                .getIntegrator()
                .getApplicationUuid());
    }

    @When("^User gets activation key for widget$")
    public void getActivationKey() {
        activationKey.set(ApiHelperWidgets.getActivationKey(widgetId.get()).getApiKey());
    }

    @When("^User gets paymentGatewaySettingsId for widget$")
    public void getPaymentGatewaySettingsId() {
        paymentGatewaySettingsId.set(ApiHelperPayments
                .getPaymentGatewaySettingsResponse(widgetId.get())
                .getPaymentGatewaySettingsId());
    }

    @When("^User sets data in the payment body$")
    public void setPaymentBody(Map<String, String> dataMap) {
        paymentBody.set(new PaymentBody(dataMap, paymentGatewaySettingsId.get(), applicationID.get()));
    }

    @When("^User sets valid data in the payment body$")
    public void setValidPaymentBody() {
        paymentBody.set(new PaymentBody(paymentGatewaySettingsId.get(), applicationID.get()));
    }

    @Then("^User cancelling the payment link$")
    public void cancelPayment(Map<String, String> dataMap) {
        Response response;
        String status = dataMap.get("i.paymentLinkRef");
        switch (status) {
            case "valid":
                response = ApiHelperPayments.cancelPaymentLink(paymentLinkRef.get(), activationKey.get());
                checkResponseCode(response, dataMap.get("o.responseCode"));
                CancelPaymentLinkResponse cancelPaymentLinkResponse = response.as(CancelPaymentLinkResponse.class);
                assertThat(cancelPaymentLinkResponse.getTransactionStatus())
                        .as(format("transaction status is not equals to %s", cancelPaymentLinkResponse.getTransactionStatus()))
                        .isEqualTo(dataMap.get("o.transactionStatus"));
                break;
            case "alreadyCancelled":
                ApiHelperPayments.cancelPaymentLink(paymentLinkRef.get(), activationKey.get());
                response = ApiHelperPayments.cancelPaymentLink(paymentLinkRef.get(), activationKey.get());
                Validator.validateErrorResponse(response, dataMap);
                break;
            case "nonExisted":
                response = ApiHelperPayments.cancelPaymentLink("nonExistedLink", activationKey.get());
                Validator.validateErrorResponse(response, dataMap);
                break;
            case "expired":
                response = ApiHelperPayments.cancelPaymentLink(EXPIRED_LINK, activationKey.get());
                Validator.validateErrorResponse(response, dataMap);
                break;
            default:
                Assertions.fail(format("Expected status %s is not existed", status));
        }
    }

    @Then("^user receives the order to email$")
    public void receiptOrder(Map<String, String> dataMap) {
        Response response;
        String status = dataMap.get("i.receiptLinkRef");
        switch (status) {
            case "valid":
                response = ApiHelperPayments.receivePaymentLink(paymentLinkRef.get(), activationKey.get());
                checkResponseCode(response, dataMap.get("o.responseCode"));
                ReceiptOrderResponse receiptOrderResponse = response.as(ReceiptOrderResponse.class);
                assertThat(receiptOrderResponse.getTransactionStatus())
                        .as(format("transaction status is not equals to %s", receiptOrderResponse.getTransactionStatus()))
                        .isEqualTo(dataMap.get("o.transactionStatus"));
                break;
            case "sentfailed":
                response = ApiHelperPayments.receivePaymentLink(paymentLinkRef.get(), activationKey.get());
                Validator.validateErrorResponse(response, dataMap);
                break;
            case "nonExisted":
                response = ApiHelperPayments.receivePaymentLink("nonExistedLink", activationKey.get());
                Validator.validateErrorResponse(response, dataMap);
                break;
            default:
                Assertions.fail(format("Expected status %s is not existed", status));
        }
    }

    @Then("^User gets a correct payment link with status code (.*) and (.*)$")
    public void userCanGetAPaymentLink(String statusCode, String transactionStatus) {
        Response response = ApiHelperPayments.userGetAPaymentLinkResponse(paymentBody.get(), activationKey.get());
        checkResponseCode(response, statusCode);
        PaymentLinkResponse paymentLinkResponse = response
                .as(PaymentLinkResponse.class);
        paymentLink.set(paymentLinkResponse.getPaymentLink());
        paymentLinkRef.set(paymentLinkResponse.paymentLinkRef);
        assertThat(paymentLinkResponse.getTransactionStatus())
                .as(format("transaction status is not equals to %s", transactionStatus))
                .isEqualTo(transactionStatus);
    }

    @Then("^User gets an error for payment link creation$")
    public void userCanNotGetAPaymentLink(Map<String, String> dataMap) {
        Response response = ApiHelperPayments.userGetAPaymentLinkResponse(paymentBody.get(), activationKey.get());
        Validator.validateErrorResponse(response, dataMap);
    }

    @Then("^The payment has success status code$")
    public void checkWorkingPaymentLink() {
        ApiHelperPayments.checkWorkingPaymentLink(paymentLink.get());
    }
}
