package api.clients;

import api.models.request.AdditionalData;
import api.models.request.AuthBody;
import api.models.request.Credentials;
import api.models.request.PaymentBody;
import api.models.response.AccountsResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TransactionsHelper extends BasedAPIHelper {

    private static final String PAYMENT_LINK = "paymentLink";
    private static final String WIDGET_ID = "widgetId";
    private static final String TOKEN = "token";
    private static final String PAYMENT_GATEWAY_SETTINGS_ID = "paymentGatewaySettingsId";


    private static ObjectMapper objectMapper = new ObjectMapper();

    private static Map<String, String> testData = new HashMap<>();

    public static void fetchTokenAndAccountIDPOST() throws JsonProcessingException {
        ResponseBody responseBody = postQueryWithoutAuth(baseUrl + accountsEndpoint, createCredentialsBody());
        AccountsResponse accountsResponse = objectMapper.readValue(responseBody.asString(), AccountsResponse.class);
        testData.put("id", accountsResponse.getAccounts().get(0).getId());
        testData.put(TOKEN, accountsResponse.getToken());
    }

    public static void logInToUnity() throws JsonProcessingException {
        ResponseBody responseBody = postQueryWithoutAuth(baseUrl + signInEndpoint, createLogInBody());
        testData.put("auth", responseBody.jsonPath().getString(TOKEN));
    }

    public static void getWidgetId(String widgetName) {
        ResponseBody responseBody = getQuery(c2pUrl + widgetsEndpoint + "all?detailed=false&page=0&size=20", testData.get("auth"));
        List<Map<String, String>> widgets = responseBody.jsonPath().getList("content");
        String newWidgetId = null;
        Optional<Map<String, String>> widget = widgets.stream().filter(w -> w.get("name").equals(widgetName)).findFirst();
        if (widget.isPresent()) {
            newWidgetId = widget.get().get("id");
        }
        testData.put(WIDGET_ID, newWidgetId);
    }

    public static void getPaymentGatewaySettingsId() {
        ResponseBody responseBody = getQuery(c2pUrl + widgetsEndpoint +
                testData.get(WIDGET_ID) + "/payment-gateway-settings", testData.get("auth"));
        String paymentGatewaySettingsId = responseBody.jsonPath().getString(PAYMENT_GATEWAY_SETTINGS_ID)
                .replace("[", "").replace("]", "");
        testData.put(PAYMENT_GATEWAY_SETTINGS_ID, paymentGatewaySettingsId);
    }

    public static void getApplicationId() {
        ResponseBody responseBody = getQuery(c2pUrl + widgetsEndpoint +
                testData.get(WIDGET_ID) + "/integration", testData.get("auth"));

        String applicationID = responseBody.jsonPath().getString("integrator.applicationUuid")
                .replace("[", "").replace("]", "");
        testData.put("applicationID", applicationID);
    }

    public static void getActivationKey() {
        ResponseBody responseBody = getQuery(c2pUrl + "/v2/widget/" +
                testData.get(WIDGET_ID) + "/api-keys", testData.get("auth"));
        String activationKey = responseBody.jsonPath().getString("apiKey").replace("[", "").replace("]", "");
        testData.put("activationKey", activationKey);
    }

    public static void userCanGetAPaymentLink() throws JsonProcessingException {
        ResponseBody responseBody = postQuery(c2pUrl + "/api/v2/chat-2-pay", createPaymentBody(), testData.get("activationKey"));
        testData.put(PAYMENT_LINK, responseBody.jsonPath().getString(PAYMENT_LINK));
    }

    public static void checkWorkingPaymentLink() {
        Response response = RestAssured.given().get(testData.get(PAYMENT_LINK));
        Assert.assertEquals(response.statusCode(), 200,
                "Creating payment link via API was not successful\n"
                        + response.statusCode() + "\n" + "rest body: " + response.getBody().asString());
    }

    private static String createLogInBody() throws JsonProcessingException {
        AuthBody body = AuthBody.builder()
                .token(testData.get(TOKEN))
                .accountId(testData.get("id"))
                .build();
        return objectMapper.writeValueAsString(body);
    }

    private static String createCredentialsBody() throws JsonProcessingException {
        Credentials body = Credentials.builder()
                .email(eMail)
                .password(password)
                .build();
        return objectMapper.writeValueAsString(body);
    }

    private static String createPaymentBody() throws JsonProcessingException {
        AdditionalData additionalData = AdditionalData.builder()
                .departmentId("567")
                .departmentName("Sales").build();
        PaymentBody body = PaymentBody.builder()
                .channel("sms")
                .to("447938556403")
                .currency("ZAR")
                .orderNumber("001")
                .subTotalAmount(100)
                .taxAmount(0.0)
                .totalAmount(100.0)
                .timestamp("2021-04-27T17:35:58.000+0000")
                .additionalData(additionalData)
                .paymentGatewaySettingsId(testData.get(PAYMENT_GATEWAY_SETTINGS_ID))
                .returnPaymentLink(true)
                .paymentReviewAutoReversal(false)
                .applicationId(testData.get("applicationID"))
                .transactionType("authorization")
                .build();
        return objectMapper.writeValueAsString(body);
    }
}
