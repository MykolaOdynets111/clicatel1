package steps.dot_control;

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

    private ThreadLocal<DotControlCreateIntegrationInfo> infoForCreatingIntegration = new ThreadLocal<>();
    private ThreadLocal<DotControlRequestMessage> dotControlRequestMessage = new ThreadLocal<>();

    @Given("^Test server is ready to accept .Control responses$")
    public void startServerForDotControl(){
        new Server().startServer();
        DotControlAPI.waitForServerToBeReady();
    }

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
        for(int i = 0; i<10; i++) {
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
        String expectedMessage = "Our branches are conveniently located nationwide near main commuter routes and in shopping malls. Use our branch locator to find your nearest branch.";
        Assert.assertEquals(Server.incomingRequests.get(dotControlRequestMessage.get().getClientId()).getMessage(), expectedMessage,
                "Message is not as expected");
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
