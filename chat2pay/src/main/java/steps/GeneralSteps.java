package steps;

import api.clients.ApiHelperChat2Pay;
import api.clients.ApiHelperWidgets;
import api.models.request.PaymentBody;
import com.github.javafaker.Faker;
import org.assertj.core.api.SoftAssertions;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GeneralSteps {

    protected static final ThreadLocal<PaymentBody> paymentBody = new ThreadLocal<>();
    protected static final ThreadLocal<String> paymentGatewaySettingsId = new ThreadLocal<>();
    protected static final ThreadLocal<String> paymentLink = new ThreadLocal<>();
    protected static final ThreadLocal<String> paymentLinkRef = new ThreadLocal<>();
    protected static final ThreadLocal<String> widgetId = new ThreadLocal<>();
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
        applicationID.set(ApiHelperWidgets
                .getIntegrationResponse(widgetId.get())
                .getIntegrator()
                .getApplicationUuid());
    }

    protected void setActivationKey() {
        activationKey.set(ApiHelperWidgets.getActivationKey(widgetId.get()).getApiKey());
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