package steps.dot_control;

import api_helper.APIHelperDotControl;
import api_helper.ApiHelper;
import api_helper.ApiHelperTie;
import com.github.javafaker.Faker;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dataManager.Tenants;
import dataManager.dot_control.DotControlCreateIntegrationInfo;
import dataManager.jackson_schemas.ChatHistoryItem;
import dataManager.jackson_schemas.Integration;
import dataManager.jackson_schemas.dot_control.DotControlRequestMessage;
import dbManager.DBConnector;
import driverManager.ConfigManager;
import io.restassured.response.Response;
import java_server.Server;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class DotControlSteps {

    private static ThreadLocal<DotControlCreateIntegrationInfo> infoForCreatingIntegration = new ThreadLocal<>();
    private static ThreadLocal<DotControlRequestMessage> dotControlRequestMessage = new ThreadLocal<>();
    private static ThreadLocal<String> apiToken = new ThreadLocal<>();
    private static ThreadLocal<String> clientId = new ThreadLocal<>();
    private static ThreadLocal<String> initCallMessageId= new ThreadLocal<>();
    Faker faker = new Faker();

    @Given("Create .Control integration for (.*) tenant")
    public void createIntegration(String tenantOrgName){
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
        if(token==null){
            Assert.assertTrue(false, "apiToken is absent in create integration response " +
                    "Status code " + resp.statusCode()+
                    "\n Body: " + resp.getBody().asString());
        }
        apiToken.set(token);


    }

    @When("Send (.*) message for .Control")
    public void sendMessageToDotControl(String message){
        if (dotControlRequestMessage.get()==null){
            createRequestMessage(apiToken.get(), message);
        } else{
            dotControlRequestMessage.get().setMessage(message);
            dotControlRequestMessage.get().setMessageId(
                    dotControlRequestMessage.get().getMessageId() + faker.number().randomNumber(7, false));

        }
        APIHelperDotControl.sendMessage(dotControlRequestMessage.get());
        clientId.set(dotControlRequestMessage.get().getClientId());
    }

    @Then("Verify dot .Control returns response with correct text for initial (.*) user message")
    public void verifyDotControlResponse(String initialMessage){
        String intent = ApiHelperTie.getListOfIntentsOnUserMessage(initialMessage).get(0).getIntent();
        String expectedMessage = "Hi. " + ApiHelperTie.getExpectedMessageOnIntent(intent);
        waitFotResponseToComeToServer();
        try {
        Assert.assertEquals(Server.incomingRequests.get(dotControlRequestMessage.get().getClientId()).getMessage(), expectedMessage,
                "Message is not as expected");
        }catch (NullPointerException e){
            Assert.assertTrue(false, "Some nullpointer exception was faced\n" + Server.incomingRequests.toString() +
                    "\n"+ dotControlRequestMessage.get().toString());
        }
    }

    @Then("Verify dot .Control returns (.*) response")
    public void verifyDotControlReturnedCorrectResponse(String expectedResponse){
        waitFotResponseToComeToServer();
        if(expectedResponse.equalsIgnoreCase("agents_available")){
            Assert.assertEquals(Server.incomingRequests.get(clientId.get()).getMessageType(), "AGENT_AVAILABLE",
                    "Message is not as expected");
        }else {
            Assert.assertEquals(Server.incomingRequests.get(clientId.get()).getMessage(), expectedResponse,
                    "Message is not as expected");
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
        Response resp = APIHelperDotControl.sendInitCall(Tenants.getTenantUnderTestOrgName(), apiToken.get(), clientId.get(), initCallMessageId.get());
        soft.assertEquals(resp.getStatusCode(), 200,
                "\nResponse status code is not as expected after sending INIT message\n");
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
        Response resp = APIHelperDotControl.sendInitCall(Tenants.getTenantUnderTestOrgName(), apiToken.get(), clientId.get(), initCallMessageId.get());
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

    @Then("^MessageId is correctly saved$")
    public void checkMessageIdSavingInINITCall(){
        // skipping for demo1 because its db is located in another network
        if(!ConfigManager.getEnv().equalsIgnoreCase("demo1")) {
            String sessionId = DBConnector.getSessionIdByClientProfileID(ConfigManager.getEnv(), clientId.get());
            List<ChatHistoryItem> chatHistoryItemList = ApiHelper.getChatHistory(Tenants.getTenantUnderTestOrgName(), sessionId);
            String actualMessageId = chatHistoryItemList.stream()
                    .filter(e -> e.getClientId().equalsIgnoreCase(clientId.get()))
                    .findFirst().get().getMessageId();
            if (initCallMessageId.get() != null) {
                Assert.assertEquals(actualMessageId, initCallMessageId.get(),
                        "Message id is not as expected\n");
            } else {
                Assert.assertTrue(!actualMessageId.equalsIgnoreCase("null"),
                        "Message id is not auto generated");
            }
        }
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

    private void waitFotResponseToComeToServer() {
        for(int i = 0; i<35; i++) {
            if (!Server.incomingRequests.isEmpty() &
                    Server.incomingRequests.keySet().contains(clientId)) {
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(Server.incomingRequests.isEmpty()){
            Assert.assertTrue(false, ".Control is not responding after 35 seconds wait.");
        }
    }

    public void generateInitCallMessageId(String messageIdStrategy){
        switch(messageIdStrategy){
            case "provided":
                initCallMessageId.set("testing_" + faker.lorem().word() + "_" + faker.lorem().word() + faker.number().digits(3));
                break;
            default:
                initCallMessageId.set(null);
        }
    }

    public static void cleanUPMessagesInfo(){
        infoForCreatingIntegration.set(null);
        dotControlRequestMessage.set(null);
        apiToken.set(null);
        clientId.set(null);
    }
}
