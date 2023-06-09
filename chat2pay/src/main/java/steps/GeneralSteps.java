package steps;

import api.ApiHelperChat2Pay;
import api.ApiHelperIntegration;
import api.ApiHelperPayments;
import api.ApiHelperWidgets;
import com.github.javafaker.Faker;
import data.models.request.PaymentBody;
import data.models.response.integration.IntegrationResponse;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.logging.Logger;

import static api.ApiHelperIntegration.getIntegrationResponse;
import static api.ApiHelperWidgets.deleteAllGeneratedWidgets;
import static java.lang.Integer.parseInt;

public class GeneralSteps {

    protected static final ThreadLocal<String> widgetId = new ThreadLocal<>();
    protected static final ThreadLocal<PaymentBody> paymentBody = new ThreadLocal<>();
    protected static final ThreadLocal<String> paymentGatewaySettingsId = new ThreadLocal<>();
    protected static final ThreadLocal<String> paymentLink = new ThreadLocal<>();
    protected static final ThreadLocal<String> paymentLinkRef = new ThreadLocal<>();
    protected static final ThreadLocal<String> applicationID = new ThreadLocal<>();
    protected static final ThreadLocal<String> activationKey = new ThreadLocal<>();
    protected static final ThreadLocal<String> createdWidgetId = new ThreadLocal<>();
    protected static final ThreadLocal<String> createdWidgetName = new ThreadLocal<>();
    protected static final ThreadLocal<String> createdCustomerApplicationId = new ThreadLocal<>();
    protected Logger logger = Logger.getLogger(Class.class.getName());

    protected final SoftAssertions softly = new SoftAssertions();
    protected Faker faker = new Faker();

    protected void setWidgetId(String widgetName) {
        widgetId.set(ApiHelperWidgets.getWidgetId(widgetName));
    }

    protected void setApplicationId() {
        applicationID.set(ApiHelperIntegration
                .getIntegrationResponse(widgetId.get()).getBody()
                .jsonPath()
                .getList("", IntegrationResponse.class)
                .stream().findAny()
                .orElseThrow(() -> new AssertionError("No integrations found for widget" + widgetId))
                .getIntegrator()
                .getApplicationUuid());
    }

    protected void setActivationKey() {
        activationKey.set(ApiHelperWidgets.getActivationKey(widgetId.get()).getApiKey());
    }

    protected String getWidgetId(Map<String, String> dataMap) {
        return dataMap.get("i.widgetId");
    }

    protected int getExpectedCode(Map<String, String> dataMap) {
        return parseInt(dataMap.get("o.responseCode"));
    }

    protected int getResponseCode(Response response) {
        return response.statusCode();
    }

    protected static String getActivationKey(Map<String, String> valuesMap) {
        String authToken = valuesMap.get("i.activationKey");
        if (authToken.equals("token")) {
            authToken = ApiHelperChat2Pay.token.get();
        }
        return authToken;
    }

    @NotNull
    protected static List<String> getListOfElementsFromTruthTable(String number) {
        List<String> numbers;
        if (Objects.nonNull(number) && number.contains(",")) {
            numbers = Arrays.asList(number.split(","));
        } else {
            numbers = Collections.singletonList(number);
        }
        return numbers;
    }

    protected static void getPaymentSettingsId(String widgetId) {
        paymentGatewaySettingsId.set(ApiHelperPayments
                .getPaymentGatewaySettingsResponse(widgetId)
                .getPaymentGatewaySettingsId());
    }

    protected static String getFirstApplicationId(String widgetId) {
        return getIntegrationResponse(widgetId)
                .jsonPath().getList("", IntegrationResponse.class)
                .get(0).getIntegrator().getApplicationUuid();
    }

    protected void clearTestData() {
        paymentBody.remove();
        paymentGatewaySettingsId.remove();
        paymentLink.remove();
        paymentLinkRef.remove();
        widgetId.remove();
        applicationID.remove();
        activationKey.remove();
        createdWidgetId.remove();
        createdWidgetName.remove();
        deleteAllGeneratedWidgets();
    }
}