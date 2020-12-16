package steps;

import apihelper.ApiORCA;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import datamanager.jacksonschemas.orca.OrcaEvent;
import interfaces.WebWait;
import io.restassured.response.Response;
import javaserver.OrcaServer;
import javaserver.Server;
import org.testng.Assert;

import java.util.List;
import java.util.Objects;

public class ORCASteps implements WebWait {

    private static final ThreadLocal<OrcaEvent> orcaMessageCallBody = new ThreadLocal<>();
    private static final ThreadLocal<String> apiToken = new ThreadLocal<>();
    private static final ThreadLocal<String> clientId = new ThreadLocal<>();

    public static String getClient() {
        return clientId.get();
    }

    public static void cleanUPORCAData() {
        OrcaServer.orcaMessages.clear();
        OrcaServer.orcaMessagesMap.clear();
        orcaMessageCallBody.remove();
        apiToken.remove();
        clientId.remove();
    }

    @Given("^Send (.*) message by ORCA$")
    public void sendOrcaMessage(String message) {
        if (orcaMessageCallBody.get() == null) {
            createRequestMessage(apiToken.get(), message);
            clientId.set(orcaMessageCallBody.get().getSourceId());
        } else {
            orcaMessageCallBody.get().getContent().getEvent().setText(message);
        }
        ApiORCA.sendMessageToAgent(orcaMessageCallBody.get());

    }

    @Given("^Setup ORCA integration for (.*) tenant$")
    public void createOrUpdateOrcaIntegration(String tenantName) {
        String action = getIntegrationCreationMethod(tenantName);
        if (action.equalsIgnoreCase("create")) {
            apiToken.set(ApiORCA.createIntegration(tenantName, Server.getServerURL()));
        } else if (action.equalsIgnoreCase("update")) {
            apiToken.set(ApiORCA.updateIntegration(tenantName, Server.getServerURL()));
        }
    }

    public String getIntegrationCreationMethod(String tenantName) {
        Response response = ApiORCA.getORCAIntegrationsList(tenantName);
        if (!(response.getBody().jsonPath().getList("").size() == 0)) {
            List<String> types = response.getBody().jsonPath().getList("transport.type");
            for (String integrationType : types) {
                if (integrationType.equalsIgnoreCase("orca")) {
                    return "update";
                }
            }
        }
        return "create";
    }

    @Then("Verify Orca returns (.*) response during (.*) seconds")
    public void verifyOrcaReturnedCorrectResponse(String expectedResponse, int wait) {

        if (expectedResponse.equalsIgnoreCase("start_new_conversation")) {
            expectedResponse = DefaultTouchUserSteps.formExpectedTextResponseFromBotWidget(expectedResponse);
        }
        Assert.assertTrue(isResponseComeToServerForClient(expectedResponse, clientId.get(), wait),
                String.format("Message is not as expected\n" +
                        " Messages which came from server for clientId %s are: %s \n" +
                        "Expected: %s", clientId.get(), OrcaServer.orcaMessages, expectedResponse));

    }

    private boolean isResponseComeToServerForClient(String message, String clientId, int wait) {
        for (int i = 0; i < wait; i++) {
            if (isExpectedResponseArrives(message, clientId)) return true;
            waitFor(1000);
        }
        return false;
    }

    private boolean isExpectedResponseArrives(String message, String clientId) {
        if(Objects.isNull(OrcaServer.orcaMessagesMap.get(clientId))) {
            return false;
        }
        return OrcaServer.orcaMessagesMap.get(clientId).contains(message);
    }

    private ThreadLocal<OrcaEvent> createRequestMessage(String apiKey, String message) {
        orcaMessageCallBody.set(new OrcaEvent(apiKey, message));
        return orcaMessageCallBody;
    }

}
