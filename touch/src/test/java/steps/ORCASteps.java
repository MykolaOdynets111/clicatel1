package steps;

import apihelper.ApiHelper;
import apihelper.ApiORCA;
import com.github.javafaker.Faker;
import com.google.common.io.Files;
import datamanager.jacksonschemas.departments.Department;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import datamanager.Tenants;
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
import java.util.Map;
import java.util.Objects;

public class ORCASteps implements WebWait {

    private static final ThreadLocal<OrcaEvent> orcaMessageCallBody = new ThreadLocal<>();
    private static final ThreadLocal<String> apiToken = new ThreadLocal<>();
    private static final ThreadLocal<String> clientId = new ThreadLocal<>();
    public static ThreadLocal<String> mediaFileName = new ThreadLocal<>();

    public static String getClientId() {
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
            clientId.set(orcaMessageCallBody.get().getUserInfo().getUserName());
            System.out.println("Message body is: " + orcaMessageCallBody.get().toString());
        } else {
            System.out.println("Updating new OrcaEvent for message: " + message);
            System.out.println("Message body before update is: " + orcaMessageCallBody.get().toString());
            orcaMessageCallBody.get().getContent().getEvent().setText(message);
            System.out.println("Message body is: " + orcaMessageCallBody.get().toString());
        }
        ApiORCA.sendMessageToAgent(orcaMessageCallBody.get());
    }

    @When("^Send (.*) message by ORCA to (.*) department$")
    public void sendChatToSpecificDepartment(String message, String departmentName){
        createRequestMessage(apiToken.get(), message);
        clientId.set(orcaMessageCallBody.get().getUserInfo().getUserName());
        List<Department> departments = ApiHelper.getDepartments(Tenants.getTenantUnderTestOrgName());
        String departmentId = departments.stream().filter(e->e.getName().equalsIgnoreCase(departmentName)).findFirst().get().getId();
        orcaMessageCallBody.get().getContent().getExtraFields().setDepartment(departmentId);
        System.out.println("Message body is: " + orcaMessageCallBody.get().toString());
        ApiORCA.sendMessageToAgent(orcaMessageCallBody.get());
    }

    @Given("^Setup ORCA (.*) integration for (.*) tenant$")
    public void createOrUpdateOrcaIntegration(String channel, String tenantName) {
        Tenants.setTenantUnderTestOrgName(tenantName);
        String id = getIntegrationId(channel);
        if (id == null) {
            apiToken.set(ApiORCA.createIntegration(channel, Server.getServerURL()));
        } else {
            apiToken.set(ApiORCA.updateIntegration(channel, Server.getServerURL(),id));
            System.out.println("apiToken was set with: " + apiToken.get());
        }
    }


    private String getIntegrationId(String channel) {
        Response response = ApiORCA.getORCAIntegrationsList(channel);
        List<Map> types = response.getBody().jsonPath().getList("");
        if (!(types.size() == 0)) {
            for (Map integrationType : types) {
                if (integrationType.get("channelType").equals(channel.toUpperCase())) {
                    return integrationType.get("id").toString();
                }
            }
        }
        return null;
    }

    @Then("^Verify Orca returns (.*) response during (.*) seconds$")
    public void verifyOrcaReturnedCorrectResponse(String expectedResponse, int wait) {

        if (expectedResponse.equalsIgnoreCase("start_new_conversation")) {
            expectedResponse = DefaultTouchUserSteps.formExpectedAutoresponder(expectedResponse);
        }
        Assert.assertTrue(isResponseComeToServerForClient(expectedResponse, getClientId(), wait),
                String.format("Message is not as expected\n" +
                        " Messages which came from server for clientId %s are: %s \n" +
                        "Expected: %s", clientId.get(), OrcaServer.orcaMessages, expectedResponse));
    }

    @Then("^Verify Orca returns (.*) autoresponder during (.*) seconds$")
    public void verifyOrcaReturnedCorrectAutoresponder(String expectedResponse, int wait) {

        expectedResponse = DefaultTouchUserSteps.formExpectedAutoresponder(expectedResponse);

        Assert.assertTrue(isResponseComeToServerForClient(expectedResponse, getClientId(), wait),
                String.format("Autoresponder is not as expected\n" +
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
            if (isExpectedResponseArrives(message)) return true;
            waitFor(1000);
        }
        return false;
    }

    private boolean isExpectedResponseArrives(String message) {
        if(Objects.isNull(OrcaServer.orcaMessages)) {
            return false;
        }
        return OrcaServer.orcaMessages.contains(message);
    }

    private void createRequestMessage(String apiKey, String message) {
        orcaMessageCallBody.set(new OrcaEvent(apiKey, message));
    }

}
