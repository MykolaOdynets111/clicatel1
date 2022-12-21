package api.clients;

import api.models.request.AdditionalData;
import api.models.request.AuthBody;
import api.models.request.Credentials;
import api.models.request.PaymentBody;
import api.models.response.AccountsResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.ResponseBody;

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
        ResponseBody responseBody = postQueryWithoutAuth(baseUrl + accountsEndpoint, createCredentialsBody(), 200);
        AccountsResponse accountsResponse = objectMapper.readValue(responseBody.asString(), AccountsResponse.class);
        testData.put("id", accountsResponse.getAccounts().get(0).getId());
        testData.put(TOKEN, accountsResponse.getToken());
    }

    public static void logInToUnity() throws JsonProcessingException {
        ResponseBody responseBody = postQueryWithoutAuth(baseUrl + signInEndpoint, createLogInBody(), 200);
        testData.put("auth", responseBody.jsonPath().getString(TOKEN));
    }

    public static void getWidgetId(String widgetName) {
        ResponseBody responseBody = getQuery(c2pUrl + widgetsEndpoint + "all?detailed=false&page=0&size=20", testData.get("auth"), 200);
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
                testData.get(WIDGET_ID) + "/payment-gateway-settings", testData.get("auth"), 200);
        String paymentGatewaySettingsId = responseBody.jsonPath().getString(PAYMENT_GATEWAY_SETTINGS_ID)
                .replace("[", "").replace("]", "");
        testData.put(PAYMENT_GATEWAY_SETTINGS_ID, paymentGatewaySettingsId);
    }

    public static void getApplicationId() {
        ResponseBody responseBody = getQuery(c2pUrl + widgetsEndpoint +
                testData.get(WIDGET_ID) + "/integration", testData.get("auth"), 200);

        String applicationID = responseBody.jsonPath().getString("integrator.applicationUuid")
                .replace("[", "").replace("]", "");
        testData.put("applicationID", applicationID);
    }

    public static void getActivationKey() {
        ResponseBody responseBody = getQuery(c2pUrl + "/v2/widget/" +
                testData.get(WIDGET_ID) + "/api-keys", testData.get("auth"), 200);
        String activationKey = responseBody.jsonPath().getString("apiKey").replace("[", "").replace("]", "");
        testData.put("activationKey", activationKey);
    }

    public static void userCanGetAPaymentLink() {
        ResponseBody responseBody = postQuery(c2pUrl + "/api/v2/chat-2-pay", testData.get("paymentBody"), testData.get("activationKey"), 201);
        testData.put(PAYMENT_LINK, responseBody.jsonPath().getString(PAYMENT_LINK));
    }

    public static void userCanNotGetAPaymentLink() {
        ResponseBody responseBody = postQuery(c2pUrl + "/api/v2/chat-2-pay", testData.get("paymentBody"), testData.get("activationKey"), 400);
        testData.put(PAYMENT_LINK, responseBody.jsonPath().getString(PAYMENT_LINK));
    }

    public static void checkWorkingPaymentLink(int statusCode) {
        getQuery(testData.get(PAYMENT_LINK), testData.get("auth"), statusCode);
    }

    public static void setPaymentBody(Map<String, String> dataMap) throws JsonProcessingException {
        AdditionalData additionalData = AdditionalData.builder()
                .departmentId(dataMap.get("departmentId"))
                .departmentName(dataMap.get("departmentName")).build();
        PaymentBody body = PaymentBody.builder()
                .channel(dataMap.get("channel"))
                .to(dataMap.get("to"))
                .currency(dataMap.get("currency"))
                .orderNumber(dataMap.get("orderNumber"))
                .subTotalAmount(dataMap.get("subTotalAmount"))
                .taxAmount(dataMap.get("taxAmount"))
                .totalAmount(dataMap.get("totalAmount"))
                .timestamp(dataMap.get("timestamp"))
                .additionalData(additionalData)
                .paymentGatewaySettingsId(testData.get(PAYMENT_GATEWAY_SETTINGS_ID))
                .returnPaymentLink(dataMap.get("returnPaymentLink"))
                .paymentReviewAutoReversal(dataMap.get("paymentReviewAutoReversal"))
                .applicationId(testData.get("applicationID"))
                .transactionType(dataMap.get("transactionType"))
                .build();
        testData.put("paymentBody", objectMapper.writeValueAsString(body));
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

}
