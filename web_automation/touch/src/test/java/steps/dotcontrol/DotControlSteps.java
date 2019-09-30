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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DotControlSteps implements WebWait {

    private static ThreadLocal<DotControlCreateIntegrationInfo> infoForCreatingIntegration = new ThreadLocal<>();
    private static ThreadLocal<DotControlRequestMessage> dotControlRequestMessage = new ThreadLocal<>();
    private static ThreadLocal<String> apiToken = new ThreadLocal<>();
    private static ThreadLocal<String> clientId = new ThreadLocal<>();
//    private static ThreadLocal<String> initCallMessageId = new ThreadLocal<>();

    private static ThreadLocal<DotControlInitRequest> initCallBody = new ThreadLocal<>();
    private static ThreadLocal<Response> responseOnSentRequest = new ThreadLocal<>();
    private static Map<String, String> adapterApiTokens= new HashMap<>();
    Faker faker = new Faker();

    @Given("Create .Control integration for (.*) tenant")
    public void createIntegration(String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        APIHelperDotControl.deleteHTTPIntegrations(Tenants.getTenantUnderTestOrgName());

        Response resp = APIHelperDotControl.createIntegration(tenantOrgName,
                generateInfoForCreatingIntegration(Server.getServerURL()).get());

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
        Tenants.setTenantUnderTestNames(tenantOrgName);

        Response resp = APIHelperDotControl.createIntegration(tenantOrgName,
                generateInfoForCreatingIntegration(Server.getServerURL()).get());

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
        if(apiToken.get()!=null){
            if(token.equals(apiToken.get())){
                Assert.fail("apiToken was not changed. integration response: " +
                        "Status code " + resp.statusCode()+
                        "\n Body: " + resp.getBody().asString());
            }
        }
        apiToken.remove();
        apiToken.set(token);
    }

    @Given("Update .Control integration for (.*) tenant")
    public void updateIntegration(String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);

        Response resp = APIHelperDotControl.updateIntegration(tenantOrgName,
                generateInfoForCreatingIntegration(Server.getServerURL()).get(),apiToken.get());

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

    @Given("Create .Control '(.*)' adapters integration for (.*) tenant")
    public void createIntegrationAdapters(String adapters, String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        APIHelperDotControl.deleteHTTPIntegrations(Tenants.getTenantUnderTestOrgName());

        Response resp = APIHelperDotControl.createIntegrationForAdapters(adapters, tenantOrgName,
                generateInfoForCreatingIntegration(Server.getServerURL()).get());

        if(!(resp.statusCode()==200)) {
            Assert.fail("Integration creating was not successful\n" +
                    "Status code " + resp.statusCode()+
                    "\n Body: " + resp.getBody().asString());
        }
        String[] arrayAdapters = adapters.split(",");
        for (int i=0; i<arrayAdapters.length; i++){
            String adapter = resp.getBody().jsonPath().get("channels["+i+"].adapter");
            String token = resp.getBody().jsonPath().get("channels["+i+"].config.apiToken");
            adapterApiTokens.put(adapter,token);
        }
        if(adapterApiTokens.isEmpty()){
            Assert.fail("apiToken is absent in create integration response " +
                    "Status code " + resp.statusCode()+
                    "\n Body: " + resp.getBody().asString());
        }
    }

    @When("^Send (.*) message for .Control$")
    public DotControlRequestMessage sendMessageToDotControl(String message){
        if (dotControlRequestMessage.get()==null) createRequestMessage(apiToken.get(), message);
        else{
                dotControlRequestMessage.get().setMessage(message);
                dotControlRequestMessage.get().setMessageId(
                        dotControlRequestMessage.get().getMessageId() + faker.number().randomNumber(7, false));
        }
        if (message.contains("invalid apiToken")) dotControlRequestMessage.get().setApiToken("invalid_token");
        if (message.contains("empty")) dotControlRequestMessage.get().setMessage("");
        if (message.contains("empty clientID in")) dotControlRequestMessage.get().setClientId("");

        responseOnSentRequest.set(
                APIHelperDotControl.sendMessageWithWait(dotControlRequestMessage.get())
        );
        clientId.set(dotControlRequestMessage.get().getClientId());
        return dotControlRequestMessage.get();
    }

    @When("^Send (.*) message for .Control from existed client$")
    public void sendMessageToDotControlFromExistedClient(String message){
        createRequestMessage(apiToken.get(), message);
        dotControlRequestMessage.get().setClientId(initCallBody.get().getClientId());
        Response resp = APIHelperDotControl.sendMessageWithWait(dotControlRequestMessage.get());
        Assert.assertEquals(resp.statusCode(), 200,
                "Sending .Control message from " + initCallBody.get().getClientId() + " was not successful");
    }

    @When("Send '(.*)' messages for .Control '(.*)' adapter")
    public void sendMessageToDotControlAdapter(String message,String adapter ){
        createRequestMessage(adapterApiTokens.get(adapter), message);
        dotControlRequestMessage.get().setContext(new DotControlRequestMessageContext().setLastName(dotControlRequestMessage.get().getClientId()));
        responseOnSentRequest.set(
                APIHelperDotControl.sendMessageWithWait(dotControlRequestMessage.get())
        );
        clientId.set(dotControlRequestMessage.get().getClientId());
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
                        "Sent data in the request: " + dotControlRequestMessage.get().toString() + "\n\n" +
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
                    "The request: " + dotControlRequestMessage.get().toString() + "\n" +
                    "clientId from request:" + dotControlRequestMessage.get().getClientId() + "\n" +
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

    @Then("^(.*) status code for multiple integration creation$")
    public void verifyMultipleIntegrationsCreating(int statusCode){
        Response resp = APIHelperDotControl.createIntegration(Tenants.getTenantUnderTestOrgName(),
                generateInfoForCreatingIntegration(Server.getServerURL()).get());
        Assert.assertEquals(resp.getStatusCode(), statusCode,
                "Wrong status code after trying to create second integration");
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


    @When("^Send init call with (.*) messageId correct response is returned$")
    public void sendInitCall(String messageIdStrategy){
        SoftAssert soft = new SoftAssert();

        Response resp = APIHelperDotControl.sendInitCallWithWait(Tenants.getTenantUnderTestOrgName(),
                            formInitRequestBody(apiToken.get(), "generated", messageIdStrategy));
        soft.assertEquals(resp.getStatusCode(), 200,
                "\nResponse status code is not as expected after sending INIT message\n"+
                resp.getBody().asString() + "\n");
        soft.assertEquals(resp.getBody().jsonPath().get("clientId"), clientId.get(),
                "\nResponse on INIT call contains incorrect clientId\n");
        soft.assertTrue(resp.getBody().jsonPath().get("conversationId")!=null,
                "\nResponse on INIT call contains incorrect conversationId\n");
        soft.assertEquals(resp.getBody().jsonPath().get("agentStatus"), "OK",
                "\nResponse on INIT call contains incorrect agentStatus\n");
        soft.assertTrue(resp.getBody().jsonPath().get("businessHours") == null,
                "\nResponse on INIT call contains incorrect businessHours\n");
        soft.assertAll();
    }

    @When("^Send parameterized init call with context correct response is returned$")
    public void sendInitCalWithAdditionalParameters(){
        DotControlInitRequest initRequest = formInitRequestBody(apiToken.get(), "generated", "provided");
        initRequest.setInitContext(new InitContext());
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
        DotControlInitRequest initRequest = formInitRequestBody(apiToken.get(), "generated", "provided");
        initRequest.setHistory(history);
        Response resp = APIHelperDotControl.sendInitCallWithWait(Tenants.getTenantUnderTestOrgName(), initRequest);
        Assert.assertEquals(resp.getStatusCode(), 200,
                "\nResponse status code is not as expected after sending INIT message\n" +
                        resp.getBody().asString() + "\n\n");

    }


    @When("^Send init call with (.*) messageId and no active agents correct response is returned$")
    public void sendInitCallWithoutAgent(String messageIdStrategy){
        SoftAssert soft = new SoftAssert();

        Response resp = APIHelperDotControl.sendInitCallWithWait(Tenants.getTenantUnderTestOrgName(),
                formInitRequestBody(apiToken.get(), "generated", messageIdStrategy));
        soft.assertEquals(resp.getStatusCode(), 200,
                "\nResponse status code is not as expected after sending INIT message\n" +
                        resp.getBody().asString() + "\n\n");
        soft.assertEquals(resp.getBody().jsonPath().get("clientId"), clientId.get(),
                "\nResponse on INIT call contains incorrect clientId\n");
        soft.assertTrue(resp.getBody().jsonPath().get("conversationId")!=null,
                "\nResponse on INIT call contains 'null' conversationId\n");
        soft.assertEquals(resp.getBody().jsonPath().get("agentStatus"), "NO_AGENTS_AVAILABLE",
                "\nResponse on INIT call contains incorrect agentStatus\n");
        soft.assertTrue(resp.getBody().jsonPath().get("businessHours") == null,
                "\nResponse on INIT call contains not null businessHours\n");
        soft.assertAll();
    }

    @When("^Send init call with provided messageId and not registered apiToken then correct response is returned$")
    public void sendInitCallWithNotRegisteredApiToken(){
        SoftAssert soft = new SoftAssert();

        Response resp = APIHelperDotControl.sendInitCallWithWait(Tenants.getTenantUnderTestOrgName(),
                formInitRequestBody("eQscd8r4_notRegistered", "generated", "provided"));
        soft.assertEquals(resp.getStatusCode(), 401,
                "\nResponse status code is not as expected after sending INIT with not registered ApiToken \n" +
                        resp.getBody().asString() + "\n\n");
        soft.assertEquals(resp.getBody().jsonPath().get("error"), "API token is not registered",
                "\nResponse on INIT call contains incorrect error message\n");
        soft.assertAll();
    }

    @When("^Send init call with provided messageId and empty clientId then correct response is returned$")
    public void sendInitCallWithEmptyClientId(){
        SoftAssert soft = new SoftAssert();
        Response resp = APIHelperDotControl.sendInitCallWithWait(Tenants.getTenantUnderTestOrgName(),
                formInitRequestBody(apiToken.get(), "empty", "provided"));
        soft.assertEquals(resp.getStatusCode(), 400,
                "\nResponse status code is not as expected after sending INIT with not registered ApiToken \n" +
                        resp.getBody().asString() + "\n\n");
        soft.assertEquals(resp.getBody().jsonPath().get("error"), "Invalid client id",
                "\nResponse on INIT call contains incorrect error message\n");
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


    @When("^Send init call with (.*) messageId correct response without of support hours is returned$")
    public void sendInitCallOutOfSupport(String messageIdStrategy){
        SoftAssert soft = new SoftAssert();
        List<SupportHoursItem> expectedBusinessHours = ApiHelper.getAgentSupportDaysAndHours(Tenants.getTenantUnderTestOrgName());
        Response resp = APIHelperDotControl.sendInitCallWithWait(Tenants.getTenantUnderTestOrgName(),
                formInitRequestBody(apiToken.get(), "generated", messageIdStrategy));
        soft.assertEquals(resp.getStatusCode(), 200,
                "\nResponse status code is not as expected after sending INIT message\n"+
                        resp.getBody().asString() + "\n");
        soft.assertEquals(resp.getBody().jsonPath().get("clientId"), clientId.get(),
                "\nResponse on INIT call contains incorrect clientId\n");
        soft.assertTrue(resp.getBody().jsonPath().get("conversationId")!=null,
                "\nResponse on INIT call contains incorrect conversationId\n");
        soft.assertEquals(resp.getBody().jsonPath().get("agentStatus"), "OUT_OF_BUSINESS_HOURS",
                "\nResponse on INIT call contains incorrect agentStatus\n");
        soft.assertEquals(resp.getBody().jsonPath().getList("businessHours", SupportHoursItem.class), expectedBusinessHours,
                "\nResponse on INIT call contains incorrect businessHours\n");
        soft.assertAll();
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
            return dotControlRequestMessage.get().getClientId();
        }
        else return dotControlRequestMessage.get().getClientId();
    }


    public static DotControlRequestMessage getFromClientRequestMessage(){
        return dotControlRequestMessage.get();
    }

    private ThreadLocal<DotControlCreateIntegrationInfo> generateInfoForCreatingIntegration(String callBackURL){
        infoForCreatingIntegration.set(new DotControlCreateIntegrationInfo(true, callBackURL));
        return infoForCreatingIntegration;
    }

    private ThreadLocal<DotControlRequestMessage> createRequestMessage(String apiKey, String message){
        dotControlRequestMessage.set(new DotControlRequestMessage(apiKey, message));
        return dotControlRequestMessage;
    }

    private boolean isResponseComeToServer(String message, int wait) {
        for(int i = 0; i<wait; i++) {
            if (isExpectedResponseArrives(message)) return true;
            waitFor(1000);
        }
        Assert.fail(".Control is not responding after "+ wait +" seconds wait. to client with id '"+clientId.get()+"'");
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


    public static void cleanUPMessagesInfo(){
        Server.incomingRequests.clear();
        responseOnSentRequest.remove();
        infoForCreatingIntegration.remove();
        dotControlRequestMessage.remove();
        apiToken.remove();
        clientId.remove();
        initCallBody.remove();
    }

    public static void cleanUPDotControlRequestMessage(){
        dotControlRequestMessage.remove();
    }

    public DotControlInitRequest formInitRequestBody(String apiToken, String clientIdStrategy, String messageIdStrategy){

        DotControlInitRequest body = new DotControlInitRequest(apiToken,
                                                            generateInitCallClientId(clientIdStrategy),
                                                            generateInitCallMessageId(messageIdStrategy));
        initCallBody.set(body);
        return initCallBody.get();
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
        clientId.set(clientIdString);
        return clientIdString;
    }
}
