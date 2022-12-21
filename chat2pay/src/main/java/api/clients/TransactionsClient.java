package api.clients;

import api.models.request.AdditionalData;
import api.models.request.AuthBody;
import api.models.request.Credentials;
import api.models.request.PaymentBody;
import api.models.response.AccountsResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

public class TransactionsClient extends BasedAPIClient {

    private static final String PAYMENT_LINK = "paymentLink";
    private static final String WIDGET_ID = "widgetId";
    private static final String TOKEN = "token";
    private static final String PAYMENT_GATEWAY_SETTINGS_ID = "paymentGatewaySettingsId";


    private static ObjectMapper objectMapper = new ObjectMapper();

    private static Map<String, String> testData = new HashMap<>();

    public static void fetchTokenAndAccountIDPOST() throws JsonProcessingException {
        String body = createCredentialsBody();
        RequestSpecification requestSpecification = createPOSTRequestSpecification(body);
        Response response = RestAssured.given().spec(requestSpecification).post(baseUrl + accountsEndpoint);
        AccountsResponse accountsResponse = objectMapper.readValue(response.getBody().asString(), AccountsResponse.class);
        testData.put("id", accountsResponse.getAccounts().get(0).getId());
        testData.put(TOKEN, accountsResponse.getToken());
    }

    public static void logInToUnity() throws JsonProcessingException {
        String body = createLogInBody();
        RequestSpecification requestSpecification = createPOSTRequestSpecification(body);
        Response response = RestAssured.given().spec(requestSpecification).post(baseUrl + signInEndpoint);
        testData.put("auth", response.jsonPath().getString(TOKEN));
    }

    public static void getWidgetId(String widgetName) {
        RequestSpecification requestSpecification = createGETRequestSpecification(testData.get("auth"));
        Response response = RestAssured.given().spec(requestSpecification).get(c2pUrl + widgetsEndpoint + "all?detailed=false&page=0&size=20");
        List<Map<String, String>> widgets = response.getBody().jsonPath().getList("content");
        String newWidgetId = null;
        Optional<Map<String, String>> widget = widgets.stream().filter(w -> w.get("name").equals(widgetName)).findFirst();
        if (widget.isPresent()) {
            newWidgetId = widget.get().get("id");
        }
        testData.put(WIDGET_ID, newWidgetId);
    }

    public static void getPaymentGatewaySettingsId() {
        RequestSpecification requestSpecification = createGETRequestSpecification(testData.get("auth"));
        Response response = RestAssured.given().spec(requestSpecification).get(c2pUrl + widgetsEndpoint +
                testData.get(WIDGET_ID) + "/payment-gateway-settings");
        String paymentGatewaySettingsId = response.jsonPath().getString(PAYMENT_GATEWAY_SETTINGS_ID)
                .replace("[", "").replace("]", "");
        testData.put(PAYMENT_GATEWAY_SETTINGS_ID, paymentGatewaySettingsId);
    }

    public static void getApplicationId() {
        RequestSpecification requestSpecification = createGETRequestSpecification(testData.get("auth"));
        Response response = RestAssured.given().spec(requestSpecification).get(c2pUrl + widgetsEndpoint +
                testData.get(WIDGET_ID) + "/integration");
        String applicationID = response.jsonPath().getString("integrator.applicationUuid")
                .replace("[", "").replace("]", "");
        testData.put("applicationID", applicationID);
    }

    public static void getActivationKey() {
        RequestSpecification requestSpecification = createGETRequestSpecification(testData.get("auth"));
        Response response = RestAssured.given().spec(requestSpecification).get(c2pUrl + "/v2/widget/" +
                testData.get(WIDGET_ID) + "/api-keys");
        String activationKey = response.jsonPath().getString("apiKey").replace("[", "").replace("]", "");
        testData.put("activationKey", activationKey);
    }

    public static void userCanGetAPaymentLink() throws JsonProcessingException {
        String body = createPaymentBody();
        RequestSpecification requestSpecification = createPOSTRequestSpecification(body);
        requestSpecification.headers("Authorization", testData.get("activationKey"));
        Response response = RestAssured.given().spec(requestSpecification).post(c2pUrl + "/api/v2/chat-2-pay");
        testData.put(PAYMENT_LINK, response.jsonPath().getString(PAYMENT_LINK));
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

    private static RequestSpecification createPOSTRequestSpecification(String body) {
        return new RequestSpecBuilder()
                .setUrlEncodingEnabled(false)
                .setBody(body)
                .setContentType(JSON)
                .log(ALL)
                .build();
    }

    private static RequestSpecification createGETRequestSpecification(String authKey) {
        return new RequestSpecBuilder()
                .addHeader("Authorization", authKey)
                .build();
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
