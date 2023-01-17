package steps;

import api.clients.ApiHelperTransactions;
import api.models.request.PaymentBody;
import api.models.response.failedresponce.UnsuccessfulResponse;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;

import java.time.LocalDateTime;
import java.util.Map;

import static api.clients.ApiHelperTransactions.getC2PConfigurationResponse;
import static java.lang.String.format;
import static org.assertj.core.api.Fail.fail;

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

    @Then("^User get the C2P configuration")
    public void getC2PConfiguration(Map<String, String> valuesMap) {
        SoftAssertions softly = new SoftAssertions();
        Response response = getC2PConfigurationResponse(valuesMap.get("activationKey"));
        int statusCode = response.getStatusCode();
        int expectedResponseCode = Integer.parseInt(valuesMap.get("statusCode"));

        if (expectedResponseCode == statusCode) {
//            if (statusCode == 200) {
//
//        } else

                if (expectedResponseCode == 401) {
                    UnsuccessfulResponse unsuccessful = response.as(UnsuccessfulResponse.class);

                    softly.assertThat(valuesMap.get("status")).isEqualTo(unsuccessful.status);
                    softly.assertThat(valuesMap.get("error")).isEqualTo(unsuccessful.error);
                    softly.assertThat(valuesMap.get("path")).isEqualTo(unsuccessful.path);
                    softly.assertThat(unsuccessful.getTimestamp()).isEqualTo(LocalDateTime.now());
                }
                softly.assertAll();
            }
//        }
        else {
            fail(format("Expected response code %s but was %s", expectedResponseCode, statusCode));
        }
    }
}
