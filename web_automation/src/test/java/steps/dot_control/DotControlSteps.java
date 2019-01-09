package steps.dot_control;

import api_helper.ApiHelperTie;
import api_helper.DotControlAPIHelper;
import com.github.javafaker.Faker;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dataManager.Tenants;
import dataManager.dot_control.DotControlCreateIntegrationInfo;
import dataManager.jackson_schemas.dot_control.DotControlRequestMessage;
import io.restassured.RestAssured;
import java_server.Server;
import org.testng.Assert;

public class DotControlSteps {

    private static ThreadLocal<DotControlCreateIntegrationInfo> infoForCreatingIntegration = new ThreadLocal<>();
    private static ThreadLocal<DotControlRequestMessage> dotControlRequestMessage = new ThreadLocal<>();
    Faker faker = new Faker();

    @Given("Create .Control integration for (.*) tenant")
    public void createIntegration(String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        DotControlAPIHelper.createIntegration(tenantOrgName,
                generateInfoForCreatingIntegration(Server.getServerURL()).get());
    }

    @When("Send (.*) message for .Control")
    public void sendMessageToDotControl(String message){
        if (dotControlRequestMessage.get()==null){
            createRequestMessage(infoForCreatingIntegration.get().getApiToken(), message);
        } else{
            dotControlRequestMessage.get().setMessage(message);
            dotControlRequestMessage.get().setMessageId(
                    dotControlRequestMessage.get().getMessageId() + faker.number().randomNumber(7, false));

        }
        DotControlAPIHelper.sendMessage(dotControlRequestMessage.get());
    }

    @Then("Verify dot .Control returns response with correct text for initial (.*) user message")
    public void verifyDotControlResponse(String initialMessage){
        String intent = ApiHelperTie.getListOfIntentsOnUserMessage(initialMessage).get(0).getIntent();
        String expectedMessage = ApiHelperTie.getExpectedMessageOnIntent(intent);
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
        try {
            Assert.assertEquals(Server.incomingRequests.get(dotControlRequestMessage.get().getClientId()).getMessage(), expectedResponse,
                    "Message is not as expected");
        }catch (NullPointerException e){
            Assert.assertTrue(false, "Some nullpointer exception was faced\n" + Server.incomingRequests.toString() + "\n" +
                            Server.incomingRequests.get(Server.incomingRequests.keySet().iterator().next()).toString() + "\n But Expected \n" +
            "\n"+ dotControlRequestMessage.get().toString());
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
                    Server.incomingRequests.keySet().contains(dotControlRequestMessage.get().getClientId())) {
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

    @Given("^Test step$")
    public void testStep(){
        Assert.assertTrue(RestAssured.get(Server.getServerURL()).statusCode()==200, "Server check from another feature");
    }

    public static void cleanUPMessagesInfo(){
        infoForCreatingIntegration.set(null);
        dotControlRequestMessage.set(null);
    }
}
