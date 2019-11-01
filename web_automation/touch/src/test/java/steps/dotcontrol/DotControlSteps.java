package steps.dotcontrol;

import apihelper.APIHelperDotControl;
import apihelper.ApiHelper;
import apihelper.ApiHelperTie;
import com.github.javafaker.Faker;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import datamanager.Tenants;
import datamanager.dotcontrol.DotControlCreateIntegrationInfo;
import datamanager.jacksonschemas.ChatHistoryItem;
import datamanager.jacksonschemas.Integration;
import datamanager.jacksonschemas.SupportHoursItem;
import datamanager.jacksonschemas.dotcontrol.*;
import dbmanager.DBConnector;
import drivermanager.ConfigManager;
import interfaces.WebWait;
import io.restassured.response.Response;
import javaserver.Server;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DotControlSteps implements WebWait {

    private static ThreadLocal<DotControlCreateIntegrationInfo> createIntegrationCallBody = new ThreadLocal<>();
    private static ThreadLocal<MessageRequest> messageCallBody = new ThreadLocal<>();
    private static ThreadLocal<String> apiToken = new ThreadLocal<>();
    private static ThreadLocal<String> clientFullName = new ThreadLocal<>();
    private static ThreadLocal<String> clientId = new ThreadLocal<>();
    private static ThreadLocal<DotControlInitRequest> initCallBody = new ThreadLocal<>();
    private static ThreadLocal<Response> responseOnSentRequest = new ThreadLocal<>();
    private static ThreadLocal<String> chatIDTranscript = new ThreadLocal<>();
    Faker faker = new Faker();

    public static void setChatIDTranscript(String chatId){
        chatIDTranscript.set(chatId);
    }

    public static String getChatIDTranscript(){
        return chatIDTranscript.get();
    }

    public static DotControlInitRequest getInitCallBody(){
        return initCallBody.get();
    }

    @Given("Create .Control integration for (.*)(?: tenant| and adapter: )(.*)")
    public void createIntegration(String tenantOrgName, String adaptor){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        APIHelperDotControl.deleteHTTPIntegrations(Tenants.getTenantUnderTestOrgName());
        if (adaptor.isEmpty()) adaptor = "fbmsg";

        Response resp = APIHelperDotControl.createIntegrationForAdapters(adaptor, tenantOrgName,
                preparePayloadForCreateIntegrationEndpoint(Server.getServerURL()).get());

        if(!(resp.statusCode()==200)) {
            Assert.fail("Integration creating was not successful\n" + "Status code " + resp.statusCode()+
                    "\n Body: " + resp.getBody().asString());
        }
        String token = resp.getBody().jsonPath().get("channels[0].config.apiToken");
        System.out.println("!! Api token from creating integration: " + token);
        if(token==null){
            Assert.fail("apiToken is absent in create integration response " +
                    "Status code " + resp.statusCode()+
                    "\n Body: " + resp.getBody().asString());
        }
        apiToken.remove();
        apiToken.set(token);
    }

    @Given("Create second .Control integration for (.*) tenant")
    public void createSecondIntegration(String tenantOrgName){
            createIntegration(tenantOrgName, "fbmsg");
    }

    @Then("^(.*) status code for multiple integration creation$")
    public void verifyMultipleIntegrationsCreating(int statusCode){
        Response resp = APIHelperDotControl.createIntegrationForAdapters("fbmsg", Tenants.getTenantUnderTestOrgName(),
                preparePayloadForCreateIntegrationEndpoint(Server.getServerURL()).get());
        Assert.assertEquals(resp.getStatusCode(), statusCode,
                "Wrong status code after trying to create second integration");
    }

    @Given("Update .Control integration for (.*) tenant")
    public void updateIntegration(String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);

        Response resp = APIHelperDotControl.updateIntegration(tenantOrgName,
                preparePayloadForCreateIntegrationEndpoint(Server.getServerURL()).get(),apiToken.get());

        if(!(resp.statusCode()==200)) {
            Assert.fail("Integration creating was not successful\n" + "Status code " + resp.statusCode()+
                    "\n Body: " + resp.getBody().asString());
        }
        String token = resp.getBody().jsonPath().get("channels[0].config.apiToken");
        if(!token.equals(apiToken.get())){
            Assert.fail("apiToken was changed. integration response: " +
                    "Status code " + resp.statusCode()+
                    "\n Body: " + resp.getBody().asString());
        }
    }

    @Given("^Prepare payload for sending (.*) message for .Control$")
    public MessageRequest preparePayloadForMessageEndpoint(String message){
        if (messageCallBody.get()==null) createRequestMessage(apiToken.get(), message);
        else{
                messageCallBody.get().setMessage(message);
                messageCallBody.get().setMessageId(
                        messageCallBody.get().getMessageId() + faker.number().randomNumber(7, false));
        }
        if (message.contains("invalid apiToken")) messageCallBody.get().setApiToken("invalid_token");
        if (message.contains("empty")) messageCallBody.get().setMessage("");
        if (message.contains("empty clientID in")) messageCallBody.get().setClientId("");

        return messageCallBody.get();
    }

    private ThreadLocal<MessageRequest> createRequestMessage(String apiKey, String message){
        messageCallBody.set(new MessageRequest(apiKey, message));
        return messageCallBody;
    }

    private ThreadLocal<DotControlCreateIntegrationInfo> preparePayloadForCreateIntegrationEndpoint(String callBackURL){
        createIntegrationCallBody.set(new DotControlCreateIntegrationInfo(true, callBackURL));
        return createIntegrationCallBody;
    }

    public DotControlInitRequest preparePayloadForInitEndpoint(String apiToken, String clientIdStrategy, String messageIdStrategy){

        DotControlInitRequest body = new DotControlInitRequest(apiToken,
                generateInitCallClientId(clientIdStrategy),
                generateInitCallMessageId(messageIdStrategy));
        initCallBody.set(body);
        return initCallBody.get();
    }

    @When("^Send init call with (.*) messageId correct (.*)response is returned$")
    public void sendInitCall(String messageIdStrategy, String expMessage){
        List<SupportHoursItem> expectedBusinessHours = new ArrayList<>();
        if(expMessage.isEmpty()) expMessage = "OK";
        if(expMessage.trim().equals("OUT_OF_BUSINESS_HOURS")){
            expectedBusinessHours =
                    ApiHelper.getAgentSupportDaysAndHours(Tenants.getTenantUnderTestOrgName());
        }
        SoftAssert soft = new SoftAssert();

        Response resp = APIHelperDotControl.sendInitCallWithWait(Tenants.getTenantUnderTestOrgName(),
                preparePayloadForInitEndpoint(apiToken.get(), "generated", messageIdStrategy));
        clientId.set(initCallBody.get().getClientId());
        soft.assertEquals(resp.getStatusCode(), 200,
                "\nResponse status code is not as expected after sending INIT message\n"+
                        resp.getBody().asString() + "\n");
        soft.assertEquals(resp.getBody().jsonPath().get("clientId"), clientId.get(),
                "\nResponse on INIT call contains incorrect clientId\n");
        soft.assertTrue(resp.getBody().jsonPath().get("conversationId")!=null,
                "\nResponse on INIT call contains incorrect conversationId\n");
        soft.assertEquals(resp.getBody().jsonPath().get("agentStatus"), expMessage.trim(),
                "\nResponse on INIT call contains incorrect agentStatus\n");
        soft.assertEquals(resp.getBody().jsonPath().getList("businessHours", SupportHoursItem.class), expectedBusinessHours,
                "\nResponse on INIT call contains incorrect businessHours\n");
        soft.assertAll();
    }


    @When("^Send parameterized init call with (.*) correct response is returned$")
    public void sendInitCalWithAdditionalParameters(String contextStrategy){
        DotControlInitRequest initRequest = preparePayloadForInitEndpoint(apiToken.get(), "generated", "provided");
        InitContext initContext;
        if(contextStrategy.equals("context")) initContext = new InitContext();
        else {
            initRequest.setClientId(messageCallBody.get().getClientId());
            initContext = new InitContext().setFirstName("AQA").setLastName(
                    messageCallBody.get().getClientId()
            );
        }
        initRequest.setInitContext(initContext);
        Response resp = APIHelperDotControl.sendInitCallWithWait(Tenants.getTenantUnderTestOrgName(), initRequest);
        Assert.assertEquals(resp.getStatusCode(), 200,
                "\nResponse status code is not as expected after sending INIT message\n" +
                        resp.getBody().asString() + "\n\n");
    }

    @When("^Send parameterized init call with history$")
    public void sendInitCalWithHistory(){
        List<History> history = Arrays.asList(new History("hi", "CLIENT"),
                new History("Hello. How can I help you?", "AGENT"),
                new History("Do you offer business accounts? ", "CLIENT"),
                new History("Sorry, no. We only offer accounts for Personal Banking", "AGENT"));
        DotControlInitRequest initRequest = preparePayloadForInitEndpoint(apiToken.get(), "generated", "provided");
        initRequest.setHistory(history);
        Response resp = APIHelperDotControl.sendInitCallWithWait(Tenants.getTenantUnderTestOrgName(), initRequest);
        Assert.assertEquals(resp.getStatusCode(), 200,
                "\nResponse status code is not as expected after sending INIT message\n" +
                        resp.getBody().asString() + "\n\n");

    }

    @When("^Send init call with provided messageId and empty clientId then correct response is returned$")
    public void sendInitCallWithEmptyClientId(){
        SoftAssert soft = new SoftAssert();
        Response resp = APIHelperDotControl.sendInitCallWithWait(Tenants.getTenantUnderTestOrgName(),
                preparePayloadForInitEndpoint(apiToken.get(), "empty", "provided"));
        soft.assertEquals(resp.getStatusCode(), 400,
                "\nResponse status code is not as expected after sending INIT with not registered ApiToken \n" +
                        resp.getBody().asString() + "\n\n");
        soft.assertEquals(resp.getBody().jsonPath().get("error"), "Invalid client id",
                "\nResponse on INIT call contains incorrect error message\n");
        soft.assertAll();
    }

    @When("^Send init call with provided messageId and not registered apiToken then correct response is returned$")
    public void sendInitCallWithNotRegisteredApiToken(){
        SoftAssert soft = new SoftAssert();

        Response resp = APIHelperDotControl.sendInitCallWithWait(Tenants.getTenantUnderTestOrgName(),
                preparePayloadForInitEndpoint("eQscd8r4_notRegistered", "generated", "provided"));
        soft.assertEquals(resp.getStatusCode(), 401,
                "\nResponse status code is not as expected after sending INIT with not registered ApiToken \n" +
                        resp.getBody().asString() + "\n\n");
        soft.assertEquals(resp.getBody().jsonPath().get("error"), "API token is not registered",
                "\nResponse on INIT call contains incorrect error message\n");
        soft.assertAll();
    }

    @When("^Send (.*) message for .Control$")
    public void sendMessage(String message){
        preparePayloadForMessageEndpoint(message);
        sendMessageCall();
    }

    public DotControlInitRequest createOfferToDotControl(String message){
        preparePayloadForMessageEndpoint(message);
        sendInitCalWithAdditionalParameters("clientId");
        sendMessageCall();
        return initCallBody.get();
    }

    @When("^Send message call$")
    public void sendMessageCall(){
        responseOnSentRequest.set(
                APIHelperDotControl.sendMessageWithWait(messageCallBody.get())
        );
        clientFullName.set(DotControlSteps.getClient());
        clientId.set(messageCallBody.get().getClientId());
    }

    @When("^Send (.*) message for .Control from existed client$")
    public void sendMessageToDotControlFromExistedClient(String message){
        messageCallBody.get().setClientId(initCallBody.get().getClientId());
        Response resp = APIHelperDotControl.sendMessageWithWait(messageCallBody.get());
        Assert.assertEquals(resp.statusCode(), 200,
                "Sending .Control message from " + initCallBody.get().getClientId() + " was not successful");
    }

    @Then("^Message should not be sent$")
    public void verifyMessageIsNotSent(){
        Assert.assertEquals(responseOnSentRequest.get().statusCode(), 400,
        "Sending empty message does not return 400 status code\n" +
        responseOnSentRequest.get().getBody().asString() + "\n");
    }

    @Then("^Error with not defined tenant is returned$")
    public void verifyInvalidApiKeyForSendMessage(){
        SoftAssert soft = new SoftAssert();
        Response resp = responseOnSentRequest.get();
        soft.assertEquals(resp.getStatusCode(), 401,
                "\nResponse status code is not as expected after sending message with not registered ApiToken \n" +
                        resp.getBody().asString() + "\n\n");
        soft.assertEquals(resp.getBody().jsonPath().get("error"), "API token is not registered",
                "\nResponse on invalid apiToken contains incorrect error message\n");
        soft.assertAll();
    }

    @Then("Verify dot .Control returns response with correct text for initial (.*) user message")
    public void verifyDotControlResponse(String initialMessage){
        if(!(responseOnSentRequest.get().statusCode()==200)) {
                Assert.fail("Sending message was not successful\n" +
                        "Sent data in the request: " + messageCallBody.get().toString() + "\n\n" +
                        "Status code " + responseOnSentRequest.get().statusCode()+
                        "\nResponse Body: " + responseOnSentRequest.get().getBody().asString() + "\n\n" +
                "HTTP Integration status: " + ApiHelper.getIntegration(Tenants.getTenantUnderTestOrgName(), "HTTP").toString());
            }
        String intent = ApiHelperTie.getListOfIntentsOnUserMessage(initialMessage).get(0).getIntent();
        String expectedMessage = "Hi. " + ApiHelperTie.getExpectedMessageOnIntent(intent);
        try {
            Assert.assertTrue(isResponseComeToServer(expectedMessage, 10),
                    "Message is not as expected\n Found: "
                            + Server.incomingRequests.get(clientId.get()).getMessage()
                            + "\nExpected: " + expectedMessage);
        }catch(NullPointerException e){
            Assert.fail("Nullpointer exception was faced\n " +
                    "The request: " + messageCallBody.get().toString() + "\n" +
                    "clientId from request:" + messageCallBody.get().getClientId() + "\n" +
            "Received clientId from .Control response" + Server.incomingRequests.keySet());
        }
    }

    @Then("^Error about client is returned$")
    public void verifyErrorOnInvalidClientID(){
        SoftAssert soft = new SoftAssert();
        Response resp = responseOnSentRequest.get();
        soft.assertEquals(resp.getStatusCode(), 400,
                "\nResponse status code is not as expected after sending message with invalid clientID \n" +
                        resp.getBody().asString() + "\n\n");
        soft.assertEquals(resp.getBody().jsonPath().get("error"), "Invalid client id",
                "\nResponse on call with invalid clientID contains incorrect error message\n");
        soft.assertAll();
        responseOnSentRequest.get();
    }

    @Then("Verify dot .Control returns (.*) response during (.*) seconds")
    public void verifyDotControlReturnedCorrectResponse(String expectedResponse, int wait){
        try {
            Assert.assertTrue(isResponseComeToServer(expectedResponse, wait),
                    "Message is not as expected\n Found: "
                            + Server.incomingRequests.get(clientId.get()).getMessage()
                            + "\nExpected: " + expectedResponse);
        }catch(NullPointerException e){
            Assert.fail("NullPointerException was faced\n" +
            "Key set from server: " + Server.incomingRequests.keySet() + "\n" +
            "clientId from created integration" + clientId.get() + "\n" +
            "" + Server.incomingRequests.toString()
            );
        }
    }

    @When("^Created .Control integration is correctly returned with GET response$")
    public void verifyDCIntegrationCorrectlyReturnedWithGet(){
        SoftAssert soft = new SoftAssert();
        Integration httpIntegration = ApiHelper.getIntegration(Tenants.getTenantUnderTestOrgName(), "http");
        soft.assertFalse(httpIntegration.getId()==null,
                "HTTP integration ID is null after its creation");
        soft.assertEquals(httpIntegration.getStatus(), "ACTIVE",
                "HTTP integration hast wrong STATUS after its creation");
        soft.assertEquals(httpIntegration.getPurchased(), Boolean.TRUE,
                "HTTP integration hast wrong PURCHASED status after its creation");
        soft.assertAll();
    }


    @When("^I delete .Control integration$")
    public void deleteHTTPIntegration(){
        Response resp = APIHelperDotControl.deleteHTTPIntegrations(Tenants.getTenantUnderTestOrgName());
        Assert.assertEquals(resp.statusCode(), 200,
                "Status code is not 200 after trying to delete .Control integration.\n" +
                        "apiTocken: " + apiToken.get() +",\n" +
                        "response body: " + resp.getBody().asString());
    }

    @Then("^Http integration status is updated after deleting$")
    public void verifyHTTPintegrationUpdatedAfterDeleting(){
        SoftAssert soft = new SoftAssert();
        Integration httpIntegration = ApiHelper.getIntegration(Tenants.getTenantUnderTestOrgName(), "http");
        soft.assertTrue(httpIntegration.getId()==null,
                "HTTP integration ID is not null after its deleting");
        soft.assertEquals(httpIntegration.getStatus(), "NO_SETUP",
                "HTTP integration hast wrong STATUS after its deleting");
        soft.assertEquals(httpIntegration.getPurchased(), Boolean.FALSE,
                "HTTP integration hast wrong PURCHASED status after its deleting");
        soft.assertAll();
    }

    @Then("^MessageId is not null$")
    public void checkMessageIdSavingInINITCall(){
            String sessionId = DBConnector.getActiveSessionDetailsByClientProfileID(ConfigManager.getEnv(), clientId.get()).get("sessionId");
            List<ChatHistoryItem> chatHistoryItemList = ApiHelper.getChatHistory(Tenants.getTenantUnderTestOrgName(), sessionId);
            String actualMessageId = null;
            try {
                actualMessageId = chatHistoryItemList.stream()
                        .filter(e -> e.getClientId().equalsIgnoreCase(clientId.get()))
                        .findFirst().get().getMessageId();
            }catch(java.util.NoSuchElementException e){
                Assert.fail("Not found message by clientId\n"
                        + String.join(", ", chatHistoryItemList.toString()));
            }

            Assert.assertFalse(actualMessageId.equalsIgnoreCase("null"),
                "Message id is not auto generated");
    }

    @When("^Set session capacity to (.*) for (.*) tenant$")
    public void updateSessionCapacity(int chats, String tenantOrgName){
        Response resp = ApiHelper.updateSessionCapacity(tenantOrgName, chats);
        Assert.assertEquals(resp.statusCode(), 200,
                "Updating session capacity was not successful\n" +
                "resp body: " + resp.getBody().asString());
    }

    public static String getClient(){
        if(initCallBody.get()!=null) {
            if (initCallBody.get().getInitContext()!=null){
                return initCallBody.get().getInitContext().getFullName();
            }
            return messageCallBody.get().getClientId();
        }
        else return messageCallBody.get().getClientId();
    }

    public static MessageRequest getFromClientRequestMessage(){
        return messageCallBody.get();
    }

    private boolean isResponseComeToServer(String message, int wait) {
        for(int i = 0; i<wait; i++) {
            if (isExpectedResponseArrives(message)) return true;
            waitFor(1000);
        }
        Assert.fail(".Control is not responding after "+ wait +" seconds wait. to client with id '"+ clientId.get()+"'");
        return false;
    }

    private boolean isExpectedResponseArrives(String message){
        if (message.equalsIgnoreCase("agents_available")) {
            return (!Server.incomingRequests.isEmpty()) &&
                    Server.incomingRequests.keySet().contains(clientId.get()) &&
                   Server.incomingRequests.get(clientId.get()).getMessageType().equals("AGENT_AVAILABLE");
        } else {
            return (!Server.incomingRequests.isEmpty()) &&
                    Server.incomingRequests.keySet().contains(clientId.get()) &&
                    Server.incomingRequests.get(clientId.get()).getMessage().equals(message);
        }
    }

    public static void cleanUPDotControlRequestMessage(){
        messageCallBody.remove();
    }

    public static String getClientId(){
        return clientId.get();
    }

    public static InitContext getInitContext(){
        return initCallBody.get().getInitContext();
    }

    private String generateInitCallMessageId(String messageIdStrategy){
        String messageId = "";
        switch(messageIdStrategy){
            case "provided":
                messageId = "testing_" + faker.lorem().word() + "_" + faker.lorem().word() + faker.number().digits(3);
                break;
            case "empty":
                messageId = "";
                break;
        }
        return messageId;
    }

    private String generateInitCallClientId(String clientIdStrategy){
        String clientIdString = "";
        switch(clientIdStrategy){
            case "generated":
                clientIdString = "aqa_" + faker.lorem().word() + "_" + faker.lorem().word() + faker.number().digits(3);
                break;
            case "empty":
                clientIdString = "";
                break;
            default:
                clientIdString = clientIdStrategy;
                break;
        }
        return clientIdString;
    }

    public static void cleanUPMessagesInfo(){
        Server.incomingRequests.clear();
        responseOnSentRequest.remove();
        createIntegrationCallBody.remove();
        messageCallBody.remove();
        apiToken.remove();
        clientFullName.remove();
        initCallBody.remove();
        clientId.remove();
    }
}
