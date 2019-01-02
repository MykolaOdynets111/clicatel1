package steps.dot_control;

import api_helper.ApiHelperTie;
import api_helper.DotControlAPI;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dataManager.Tenants;
import dataManager.dot_control.DotControlCreateIntegrationInfo;
import dataManager.jackson_schemas.dot_control.DotControlRequestMessage;
import java_server.Server;
import org.testng.Assert;

public class DotControlSteps {

    private static ThreadLocal<DotControlCreateIntegrationInfo> infoForCreatingIntegration = new ThreadLocal<>();
    private static ThreadLocal<DotControlRequestMessage> dotControlRequestMessage = new ThreadLocal<>();


    @Given("Create .Control integration for (.*) tenant")
    public void createIntegration(String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        DotControlAPI.createIntegration(tenantOrgName,
                generateInfoForCreatingIntegration(Server.getServerURL()).get());
    }

    @When("Send (.*) message for .Control bot")
    public void sendMessageToDotControl(String message){
        DotControlAPI.sendMessage(
                createRequestMessage(infoForCreatingIntegration.get().getApiToken(), message).get()
        );
    }

    @Then("Verify dot .Control returns response with correct text for initial (.*) user message")
    public void verifyDotControlResponse(String initialMessage){
        String intent = ApiHelperTie.getListOfIntentsOnUserMessage(initialMessage).get(0).getIntent();
        String expectedMessage = ApiHelperTie.getExpectedMessageOnIntent(intent);
        for(int i = 0; i<35; i++) {
            if (!Server.incomingRequests.isEmpty() ||
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
        Assert.assertEquals(Server.incomingRequests.get(dotControlRequestMessage.get().getClientId()).getMessage(), expectedMessage,
                "Message is not as expected");
    }


    @Given("^Test step$")
    public void testStep(){
        int aa = 1;
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
}
