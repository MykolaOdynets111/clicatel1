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
import datamanager.jacksonschemas.dotcontrol.DotControlRequestMessage;
import dbmanager.DBConnector;
import drivermanager.ConfigManager;
import io.restassured.response.Response;
import javaserver.Server;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DotControlSteps {

    private static ThreadLocal<DotControlCreateIntegrationInfo> infoForCreatingIntegration = new ThreadLocal<>();
    private static ThreadLocal<DotControlRequestMessage> dotControlRequestMessage = new ThreadLocal<>();
    private static ThreadLocal<String> apiToken = new ThreadLocal<>();
    private static ThreadLocal<String> clientId = new ThreadLocal<>();
    private static ThreadLocal<String> initCallMessageId = new ThreadLocal<>();
    private static ThreadLocal<Response> responseOnSentRequest = new ThreadLocal<>();
    private static Map<String, String> adapterApiTokens= new HashMap();
    Faker faker = new Faker();

    @Given("Create .Control integration for (.*) tenant")
    public void    createIntegration(String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        APIHelperDotControl.deleteHTTPIntegrations(Tenants.getTenantUnderTestOrgName());

        Response resp = APIHelperDotControl.createIntegration(tenantOrgName,
                generateInfoForCreatingIntegration(Server.getServerURL()).get());

        if(!(resp.statusCode()==200)) {
            Assert.assertTrue(false, "Integration creating was not successful\n" +
                    "Status code " + resp.statusCode()+
                    "\n Body: " + resp.getBody().asString());
        }
        String token = resp.getBody().jsonPath().get("channels[0].config.apiToken");
        System.out.println("!! Api token from creating integration: " + token);
        if(token==null){
            Assert.assertTrue(false, "apiToken is absent in create integration response " +
                    "Status code " + resp.statusCode()+
                    "\n Body: " + resp.getBody().asString());
        }
        apiToken.remove();
        apiToken.set(token);
    }

    @Given("Create .Control '(.*)' adapters integration for (.*) tenant")
    public void createIntegrationAdapters(String adapters, String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        APIHelperDotControl.deleteHTTPIntegrations(Tenants.getTenantUnderTestOrgName());

        Response resp = APIHelperDotControl.createIntegrationForAdapters(adapters, tenantOrgName,
                generateInfoForCreatingIntegration(Server.getServerURL()).get());

        if(!(resp.statusCode()==200)) {
            Assert.assertTrue(false, "Integration creating was not successful\n" +
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
            Assert.assertTrue(false, "apiToken is absent in create integration response " +
                    "Status code " + resp.statusCode()+
                    "\n Body: " + resp.getBody().asString());
        }
    }

    @When("Send (.*) message for .Control")
    public void sendMessageToDotControl(String message){
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
    }

    @When("Send '(.*)' messages for .Control '(.*)' adapter")
    public void sendMessageToDotControlAdapter(String message,String adapter ){
        createRequestMessage(adapterApiTokens.get(adapter), message);
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
                Assert.assertTrue(false, "Sending message was not successful\n" +
                        "Sent data in the request: " + dotControlRequestMessage.get().toString() + "\n\n" +
                        "Status code " + responseOnSentRequest.get().statusCode()+
                        "\nResponse Body: " + responseOnSentRequest.get().getBody().asString() + "\n\n" +
                "HTTP Integration status: " + ApiHelper.getIntegration(Tenants.getTenantUnderTestOrgName(), "HTTP").toString());
            }
        String intent = ApiHelperTie.getListOfIntentsOnUserMessage(initialMessage).get(0).getIntent();
        String expectedMessage = "Hi. " + ApiHelperTie.getExpectedMessageOnIntent(intent);
        waitFotResponseToComeToServer(10);
        try {
            Assert.assertEquals(Server.incomingRequests.get(dotControlRequestMessage.get().getClientId()).getMessage(), expectedMessage,
                    "Message is not as expected");
        }catch(NullPointerException e){
            Assert.assertTrue(false, "Nullpointer exception was faced\n " +
                    "The request: " + dotControlRequestMessage.get().toString() + "\n" +
                    "clientId from request:" + dotControlRequestMessage.get().getClientId() + "\n" +
            "Received clientId from .Control response" + Server.incomingRequests.keySet());
        }
    }

    @Then("^Error about client  is returned$")
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

    @Then("Verify dot .Control returns (.*) response")
    public void verifyDotControlReturnedCorrectResponse(String expectedResponse){
        try {
            if (expectedResponse.equalsIgnoreCase("agents_available")) {
                waitFotResponseToComeToServer(40);
                Assert.assertEquals(Server.incomingRequests.get(clientId.get()).getMessageType(), "AGENT_AVAILABLE",
                        "Message is not as expected");
            } else {
                waitFotResponseToComeToServer(10);
                Assert.assertEquals(Server.incomingRequests.get(clientId.get()).getMessage(), expectedResponse,
                        "Message is not as expected");
            }
        }catch(NullPointerException e){
            Assert.assertTrue(false, "NullPointerException was faced\n" +
            "Keyset from server: " + Server.incomingRequests.keySet() + "\n" +
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
        clientId.set("aqa_" + faker.lorem().word() + "_" + faker.lorem().word() + faker.number().digits(3));
        generateInitCallMessageId(messageIdStrategy);
        SoftAssert soft = new SoftAssert();
        Response resp = APIHelperDotControl.sendInitCallWithWait(Tenants.getTenantUnderTestOrgName(), apiToken.get(), clientId.get(), initCallMessageId.get());
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

    @When("^Send init call with (.*) messageId and no active agents correct response is returned$")
    public void sendInitCallWithoutAgent(String messageIdStrategy){
        clientId.set("aqa_" + faker.lorem().word() + "_" + faker.lorem().word() + faker.number().digits(3));
        generateInitCallMessageId(messageIdStrategy);
        SoftAssert soft = new SoftAssert();
        Response resp = APIHelperDotControl.sendInitCallWithWait(Tenants.getTenantUnderTestOrgName(), apiToken.get(), clientId.get(), initCallMessageId.get());
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
        clientId.set("aqa_" + faker.lorem().word() + "_" + faker.lorem().word() + faker.number().digits(3));
        generateInitCallMessageId("provided");
        SoftAssert soft = new SoftAssert();
        Response resp = APIHelperDotControl.sendInitCallWithWait(Tenants.getTenantUnderTestOrgName(), "eQscd8r4_notRegistered", clientId.get(), initCallMessageId.get());
        soft.assertEquals(resp.getStatusCode(), 401,
                "\nResponse status code is not as expected after sending INIT with not registered ApiToken \n" +
                        resp.getBody().asString() + "\n\n");
        soft.assertEquals(resp.getBody().jsonPath().get("error"), "API token is not registered",
                "\nResponse on INIT call contains incorrect error message\n");
        soft.assertAll();
    }

    @When("^Send init call with provided messageId and empty clientId then correct response is returned$")
    public void sendInitCallWithEmptyClientId(){
        clientId.set("");
        generateInitCallMessageId("provided");
        SoftAssert soft = new SoftAssert();
        Response resp = APIHelperDotControl.sendInitCallWithWait(Tenants.getTenantUnderTestOrgName(), apiToken.get(), clientId.get(), initCallMessageId.get());
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
        clientId.set("aqa_" + faker.lorem().word() + "_" + faker.lorem().word() + faker.number().digits(3));
        generateInitCallMessageId(messageIdStrategy);
        SoftAssert soft = new SoftAssert();
        List<SupportHoursItem> expectedBusinessHours = ApiHelper.getAgentSupportDaysAndHours(Tenants.getTenantUnderTestOrgName());
        Response resp = APIHelperDotControl.sendInitCallWithWait(Tenants.getTenantUnderTestOrgName(), apiToken.get(), clientId.get(), initCallMessageId.get());
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

    private void waitFotResponseToComeToServer(int wait) {
        for(int i = 0; i<wait; i++) {
            if (!Server.incomingRequests.isEmpty() &
                    Server.incomingRequests.keySet().contains(clientId.get())) {
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(Server.incomingRequests.isEmpty()|!(Server.incomingRequests.keySet().contains(clientId.get()))){
            Assert.assertTrue(false,
                    ".Control is not responding after "+ wait +" seconds wait. to client with id '"+clientId.get()+"'");
        }
    }

    public void generateInitCallMessageId(String messageIdStrategy){
        switch(messageIdStrategy){
            case "provided":
                initCallMessageId.set("testing_" + faker.lorem().word() + "_" + faker.lorem().word() + faker.number().digits(3));
                break;
            default:
                initCallMessageId.set("");
        }
    }

    public static void cleanUPMessagesInfo(){
        Server.incomingRequests.clear();
        responseOnSentRequest.remove();
        infoForCreatingIntegration.remove();
        dotControlRequestMessage.remove();
        apiToken.remove();
        clientId.remove();
    }
}
