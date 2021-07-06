package steps;

import apihelper.ApiORCA;
import com.github.javafaker.Faker;
import com.google.common.io.Files;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import datamanager.jacksonschemas.orca.OrcaEvent;
import datamanager.jacksonschemas.orca.event.Event;
import interfaces.WebWait;
import io.restassured.response.Response;
import javaserver.OrcaServer;
import javaserver.Server;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class ORCASteps implements WebWait {

    private static final ThreadLocal<OrcaEvent> orcaMessageCallBody = new ThreadLocal<>();
    private static final ThreadLocal<String> apiToken = new ThreadLocal<>();
    private static final ThreadLocal<String> clientId = new ThreadLocal<>();
    public static ThreadLocal<String> mediaFileName = new ThreadLocal<>();

    public static String getClient() {
        return clientId.get();
    }

    public static void cleanUPORCAData() {
        OrcaServer.orcaMessages.clear();
        OrcaServer.orcaMessagesMap.clear();
        orcaMessageCallBody.remove();
        apiToken.remove();
        clientId.remove();
        System.out.println("Orca artifacts were removed");
    }

    @Given("^Send (.*) message by ORCA$")
    public void sendOrcaMessage(String message) {
        if (orcaMessageCallBody.get() == null) {
            System.out.println("creating new OrcaEvent for message: " + message);
            createRequestMessage(apiToken.get(), message);
            clientId.set(orcaMessageCallBody.get().getSourceId());
            System.out.println("Message body is: " + orcaMessageCallBody.get().toString());
        } else {
            System.out.println("Updating new OrcaEvent for message: " + message);
            System.out.println("Message body before update is: " + orcaMessageCallBody.get().toString());
            orcaMessageCallBody.get().getContent().getEvent().setText(message);
            System.out.println("Message body is: " + orcaMessageCallBody.get().toString());
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
            System.out.println("apiToken was set with: " + apiToken.get());
        }
    }

    private String getIntegrationCreationMethod(String tenantName) {
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

    @Then("^Verify Orca returns (.*) response during (.*) seconds$")
    public void verifyOrcaReturnedCorrectResponse(String expectedResponse, int wait) {

        if (expectedResponse.equalsIgnoreCase("start_new_conversation")) {
            expectedResponse = DefaultTouchUserSteps.formExpectedTextResponseFromBotWidget(expectedResponse);
        }
        Assert.assertTrue(isResponseComeToServerForClient(expectedResponse, clientId.get(), wait),
                String.format("Message is not as expected\n" +
                        " Messages which came from server for clientId %s are: %s \n" +
                        "Expected: %s", clientId.get(), OrcaServer.orcaMessages, expectedResponse));
    }

    @When("^User send (.*) attachment with orca$")
    public void sendAttachment(String fileName){
        File pathToFile = new File(System.getProperty("user.dir")+"/src/test/resources/mediasupport/" + fileName + "." + fileName);
        String newName = new Faker().letterify(fileName + "?????") + "." + fileName;
        File renamed =  new File(System.getProperty("user.dir")+"/src/test/resources/mediasupport/renamed/" +  newName);
        try {
            Files.copy(pathToFile, renamed);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaFileName.set(newName);
        orcaMessageCallBody.get().getContent().setEvent(createMediaEvent(renamed));

        ApiORCA.sendMessageToAgent(orcaMessageCallBody.get());
    }

    private Event createMediaEvent(File file){
        Path path= file.toPath();
        String mimeType = "";
        try {
            mimeType = java.nio.file.Files.probeContentType(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Event(file);
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
