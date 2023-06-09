package steps;

import api.ApiHelperPayments;
import api.ApiHelperWidgets;
import data.models.request.PaymentBody;
import data.models.response.PaymentLinkResponse;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

import java.util.Map;

import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static utils.Validator.checkResponseCode;
import static utils.Validator.validateErrorResponse;

public class PaymentsSteps extends GeneralSteps {

    public static final String EXPIRED_LINK = "629905f6-e916-4490-a547-bf8f0d5fb9f4";

    @When("^User gets paymentGatewaySettingsId for widget$")
    public void getPaymentGatewaySettingsId() {
        getPaymentSettingsId(widgetId.get());
    }

    @When("^User gets 'Payment Gateway Settings Id' for (.*) widget$")
    public void getPaymentGatewaySettingsId(String name) {
        getPaymentSettingsId(ApiHelperWidgets.getWidgetId(name));
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
                checkResponseCode(response, getExpectedCode(dataMap));
                PaymentLinkResponse paymentLinkResponse = response.as(PaymentLinkResponse.class);
                assertThat(paymentLinkResponse.getTransactionStatus())
                        .as(format("transaction status is not equals to %s", paymentLinkResponse.getTransactionStatus()))
                        .isEqualTo(dataMap.get("o.transactionStatus"));
                break;
            case "alreadyCancelled":
                ApiHelperPayments.cancelPaymentLink(paymentLinkRef.get(), activationKey.get());
                response = ApiHelperPayments.cancelPaymentLink(paymentLinkRef.get(), activationKey.get());
                validateErrorResponse(response, dataMap);
                break;
            case "nonExisted":
                response = ApiHelperPayments.cancelPaymentLink("nonExistedLink", activationKey.get());
                validateErrorResponse(response, dataMap);
                break;
            case "expired":
                response = ApiHelperPayments.cancelPaymentLink(EXPIRED_LINK, activationKey.get());
                validateErrorResponse(response, dataMap);
                break;
            default:
                Assertions.fail(format("Expected status %s is not existed", status));
        }
    }

    @Then("^User receives the order to email$")
    public void receiptOrder(Map<String, String> dataMap) {
        Response response;
        String status = dataMap.get("i.receiptLinkRef");
        switch (status) {
            case "valid":
                response = ApiHelperPayments.receivePaymentLink(paymentLinkRef.get(), activationKey.get());
                checkResponseCode(response, getExpectedCode(dataMap));
                PaymentLinkResponse paymentLinkResponse = response.as(PaymentLinkResponse.class);
                assertThat(paymentLinkResponse.getTransactionStatus())
                        .as(format("transaction status is not equals to %s", paymentLinkResponse.getTransactionStatus()))
                        .isEqualTo(dataMap.get("o.transactionStatus"));
                break;
            case "sentfailed":
                response = ApiHelperPayments.receivePaymentLink(paymentLinkRef.get(), activationKey.get());
                validateErrorResponse(response, dataMap);
                break;
            case "nonExisted":
                response = ApiHelperPayments.receivePaymentLink("nonExistedLink", activationKey.get());
                validateErrorResponse(response, dataMap);
                break;
            default:
                Assertions.fail(format("Expected status %s is not existed", status));
        }
    }

    @Then("^User gets a correct payment link with status code (.*) and (.*)$")
    public void userCanGetAPaymentLink(String statusCode, String transactionStatus) {
        Response response = ApiHelperPayments.userGetAPaymentLinkResponse(paymentBody.get(), activationKey.get());
        checkResponseCode(response, Integer.parseInt(statusCode));
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
        validateErrorResponse(response, dataMap);
    }

    @Then("^The payment has success status code$")
    public void checkWorkingPaymentLink() {
        ApiHelperPayments.checkWorkingPaymentLink(paymentLink.get());
    }
}
