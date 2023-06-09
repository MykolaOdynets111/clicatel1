package steps.dotcontrol;

import agentpages.uielements.ChatForm;
import apihelper.APIHelperDotControl;
import apihelper.ApiHelper;
import apihelper.ApiHelperSupportHours;
import apihelper.ApiHelperTie;
import com.github.javafaker.Faker;
import com.google.common.io.Files;
import datamanager.Tenants;
import datamanager.dotcontrol.DotControlCreateIntegrationInfo;
import datamanager.jacksonschemas.ChatHistoryItem;
import datamanager.jacksonschemas.Integration;
import datamanager.jacksonschemas.dotcontrol.*;
import datamanager.jacksonschemas.supportHours.GeneralSupportHoursItem;
import dbmanager.DBConnector;
import drivermanager.ConfigManager;
import interfaces.WebWait;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import javaserver.DotControlServer;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import sqsreader.SQSConfiguration;
import steps.DefaultTouchUserSteps;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DotControlSteps implements WebWait {

    private static ThreadLocal<DotControlCreateIntegrationInfo> createIntegrationCallBody = new ThreadLocal<>();
    private static ThreadLocal<MessageRequest> messageCallBody = new ThreadLocal<>();
    private static ThreadLocal<String> apiToken = new ThreadLocal<>();
    private static ThreadLocal<String> clientId = new ThreadLocal<>();
    private static ThreadLocal<DotControlInitRequest> initCallBody = new ThreadLocal<>();
    private static ThreadLocal<Response> responseOnSentRequest = new ThreadLocal<>();
    private static ThreadLocal<String> chatIDTranscript = new ThreadLocal<>();
    public static ThreadLocal<String> mediaFileName = new ThreadLocal<>();
    Faker faker = new Faker();

    public static void setChatIDTranscript(String chatId){
        chatIDTranscript.set(chatId);
    }

    public static String getChatIDTranscript(){
        return chatIDTranscript.get();
    }

    @Given("^Create .Control integration for (.*)(?: tenant| and adapter: )(.*)$")
    public void createIntegration(String tenantOrgName, String adaptor){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        APIHelperDotControl.deleteHTTPIntegrations(Tenants.getTenantUnderTestOrgName());
        if (adaptor.isEmpty()) adaptor = "fbmsg";

        Response resp = APIHelperDotControl.createIntegrationForAdapters(adaptor, tenantOrgName,
                preparePayloadForCreateIntegrationEndpoint(SQSConfiguration.getCallbackUrl()).get());

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


    @Given ("^User send (.*) attachment with .Control$")
    public void sendAttachment(String fileName){
        File pathToFile = new File(System.getProperty("user.dir")+"/touch/src/test/resources/mediasupport/" + fileName + "." + fileName);
        String newName = new Faker().letterify(fileName + "?????") + "." + fileName;
        File renamed =  new File(System.getProperty("user.dir")+"/touch/src/test/resources/mediasupport/renamed/" +  newName);
        try {
            Files.copy(pathToFile, renamed);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaFileName.set(newName);
        String fileID = APIHelperDotControl.uploadTheFile(renamed, apiToken.get());

        updateMCBForAttachmentMessage(fileID);

        sendMessageCall();
    }

    private void updateMCBForAttachmentMessage(String fileId){
        messageCallBody.get().setMessageType("ATTACHMENT");
        messageCallBody.get().setContext(new DotControlRequestAttachmentContext(fileId));
        messageCallBody.get().setMessage(null);
        messageCallBody.get().setMessageId(""+faker.number().randomNumber(7, false));
    }

    @Given("^Create second .Control integration for (.*) tenant$")
    public void createSecondIntegration(String tenantOrgName){
        createIntegration(tenantOrgName, "fbmsg");
    }

    @Then("^(.*) status code for multiple integration creation$")
    public void verifyMultipleIntegrationsCreating(int statusCode){
        Response resp = APIHelperDotControl.createIntegrationForAdapters("fbmsg", Tenants.getTenantUnderTestOrgName(),
                preparePayloadForCreateIntegrationEndpoint(SQSConfiguration.getCallbackUrl()).get());
        Assert.assertEquals(resp.getStatusCode(), statusCode,
                "Wrong status code after trying to create second integration");
    }

    @Given("^Update .Control integration for (.*) tenant$")
    public void updateIntegration(String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);

        Response resp = APIHelperDotControl.updateIntegration(tenantOrgName,
                preparePayloadForCreateIntegrationEndpoint(SQSConfiguration.getCallbackUrl()).get(),apiToken.get());

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

    @When("Set (.*) as customer location with api")
    public void updateLocation(String location){
        ApiHelper.updateClientProfileAttribute("location", location, messageCallBody.get().getClientId());
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
        GeneralSupportHoursItem expectedBusinessHours = null;
        if(expMessage.isEmpty()) expMessage = "OK";
        if(expMessage.trim().equals("OUT_OF_BUSINESS_HOURS")){
            expectedBusinessHours =
                    ApiHelperSupportHours.getSupportDaysAndHoursForMainAgent(Tenants.getTenantUnderTestOrgName());
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
        soft.assertEquals(resp.getBody().as(GeneralSupportHoursItem.class), expectedBusinessHours,
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
        String expectedMessage = ApiHelperTie.getExpectedMessageOnIntent(intent);
        try {
            Assert.assertTrue(isResponseComeToServer(expectedMessage, 10),
                    "Message is not as expected\n Found: "
                            + DotControlServer.dotControlIncomingRequests.get(clientId.get()).getMessage()
                            + "\nExpected: " + expectedMessage);
        }catch(NullPointerException e){
            Assert.fail("Nullpointer exception was faced\n " +
                    "The request: " + messageCallBody.get().toString() + "\n" +
                    "clientId from request:" + messageCallBody.get().getClientId() + "\n" +
                    "Received clientId from .Control response" + DotControlServer.dotControlIncomingRequests.keySet());
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

    @Then("Verify dot .Control returns edited response in (.*) seconds")
    public void verifyDotControlReturnEditedResponse(int wait){
        verifyDotControlReturnedCorrectResponse(ChatForm.inputMassage, wait);
    }

    @Then("Verify dot .Control returns (.*) response during (.*) seconds")
    public void verifyDotControlReturnedCorrectResponse(String expectedResponse, int wait){
        if (expectedResponse.equalsIgnoreCase("agents_available")) {
            try {
                Assert.assertTrue(isResponseComeToServer(expectedResponse, wait),
                        "Message is not as expected\n Found: "
                                + DotControlServer.dotControlIncomingRequests.get(clientId.get()).getMessage()
                                + "\nExpected: " + expectedResponse);
            }catch(NullPointerException e){
                Assert.fail("NullPointerException was faced\n" +
                        "Key set from server: " + DotControlServer.dotControlIncomingRequests.keySet() + "\n" +
                        "clientId from created integration" + clientId.get() + "\n" +
                        "" + DotControlServer.dotControlIncomingRequests.toString()
                );
            }
        }else {
            if (expectedResponse.equalsIgnoreCase("start_new_conversation")){
                expectedResponse = DefaultTouchUserSteps.formExpectedAutoresponder(expectedResponse);
            }
            Assert.assertTrue(isResponseComeToServer(expectedResponse, wait),
                    "Message is not as expected\n Messages which came from server are: "
                            + DotControlServer.dotControlMessages
                            + "\nExpected: " + expectedResponse +"\"");
        }
    }

    @Then("Verify .Control does not returns (.*) response during (.*) seconds")
    public void verifyIfResponseIsNotCome(String expectedResponse, int wait){
        Assert.assertFalse(isResponseComeToServer(expectedResponse, wait),
                "Message should not come to .Control\n Messages which came from server are: "
                        + DotControlServer.dotControlMessages
                        + "\nExpected: " + expectedResponse);
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
                    +  chatHistoryItemList.stream().map(s -> s.toString()).collect(Collectors.joining("\n")));
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
//        Assert.fail(".Control is not responding after "+ wait +" seconds wait. to client with id '"+ clientId.get()+"'");
        return false;
    }


    private boolean isExpectedResponseArrives(String message){
        boolean result = false;
        if (message.equalsIgnoreCase("agents_available")) {
            result =  (!DotControlServer.dotControlIncomingRequests.isEmpty()) &&
                    DotControlServer.dotControlIncomingRequests.keySet().contains(clientId.get()) &&
                    DotControlServer.dotControlIncomingRequests.get(clientId.get()).getMessageType().equals("AGENT_AVAILABLE");
        } else {
            result = DotControlServer.dotControlMessages.contains(message);
            if (result){ DotControlServer.dotControlMessages.clear();}
        }
        return result;
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
        DotControlServer.dotControlIncomingRequests.clear();
        DotControlServer.dotControlMessages.clear();
        responseOnSentRequest.remove();
        createIntegrationCallBody.remove();
        messageCallBody.remove();
        apiToken.remove();
        initCallBody.remove();
        clientId.remove();
    }
}
