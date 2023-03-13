package steps;

import api.clients.ApiHelperChat2Pay;
import api.clients.ApiHelperIntegration;
import api.clients.ApiHelperPayments;
import api.clients.ApiHelperWidgets;
import api.models.request.PaymentBody;
import api.models.response.integration.IntegrationResponse;
import com.github.javafaker.Faker;
import org.assertj.core.api.SoftAssertions;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    protected int getResponseCode(Map<String, String> dataMap) {
        return parseInt(dataMap.get("o.responseCode"));
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
    }
}