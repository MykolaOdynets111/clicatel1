package steps;

import apihelper.ApiHelper;
import apihelper.ApiORCA;
import com.github.javafaker.Faker;
import com.google.common.io.Files;
import datamanager.Tenants;
import datamanager.jacksonschemas.departments.Department;
import datamanager.jacksonschemas.orca.OrcaEvent;
import datamanager.jacksonschemas.orca.event.Event;
import interfaces.WebWait;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;
import sqsreader.OrcaSQSHandler;
import sqsreader.SQSConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class ORCASteps implements WebWait {

    public static final ThreadLocal<OrcaEvent> orcaMessageCallBody = new ThreadLocal<>();
    private static final ThreadLocal<String> apiToken = new ThreadLocal<>();
    private static final ThreadLocal<String> clientId = new ThreadLocal<>();
    private static final ThreadLocal<String> smsSourceId = new ThreadLocal<>();
    public static ThreadLocal<String> mediaFileName = new ThreadLocal<>();
    private static final ThreadLocal<String> orcaChannelId = new ThreadLocal<>();

    public static String getClientId() {
        return clientId.get();
    }

    public static String getSmsSourceId() {
        return smsSourceId.get();
    }

    public static String getChannelId() {
        return orcaChannelId.get();
    }

    public static void cleanUPORCAData() {
        OrcaSQSHandler.orcaMessages.clear();
        OrcaSQSHandler.orcaMessagesMap.clear();
        orcaMessageCallBody.remove();
        apiToken.remove();
        clientId.remove();
        orcaChannelId.remove();
        System.out.println("Orca artifacts were removed");
    }

    @Given("^Send (.*) message by ORCA$")
    public void sendOrcaMessage(String message){

        if (orcaMessageCallBody.get() == null){
            System.out.println("creating new OrcaEvent for message: " + message);
            createRequestMessage(apiToken.get(), message);
            System.out.println("Message body is: " + orcaMessageCallBody.get().toString());
        }else{
            System.out.println("Updating new OrcaEvent for message: " + message);
            System.out.println("Message body before update is: " + orcaMessageCallBody.get().toString());
            orcaMessageCallBody.get().getContent().getEvent().setText(message);
            System.out.println("Message body is: " + orcaMessageCallBody.get().toString());
        }
        ApiORCA.sendMessageToAgent(orcaMessageCallBody.get());
    }

    @When("^User send Lviv location message to agent by ORCA$")
    public void sendLocationMessage(){
        Event locationEvent = Event.builder().address("Lviv, Lviv Oblast, Ukraine, 79000").
                name("Lviv").eventType("LOCATION").latitude("49.839683").longitude("24.029717").build();
        orcaMessageCallBody.get().getContent().setEvent(locationEvent);
        ApiORCA.sendMessageToAgent(orcaMessageCallBody.get());
    }

    @When("^Send (.*) message by ORCA to (.*) department$")
    public void sendChatToSpecificDepartment(String message, String departmentName){
        createRequestMessage(apiToken.get(), message);
        List<Department> departments = ApiHelper.getDepartments(Tenants.getTenantUnderTestOrgName());
        String departmentId = departments.stream().filter(e->e.getName().equalsIgnoreCase(departmentName)).findFirst().get().getId();
        orcaMessageCallBody.get().getContent().getExtraFields().setDepartment(departmentId);
        System.out.println("Message body is: " + orcaMessageCallBody.get().toString());
        ApiORCA.sendMessageToAgent(orcaMessageCallBody.get());
    }

    @Given("^(.*) generate closed ORCA whatsapp chat (.*) hours ago$")
    public void generateClosedChat(String agent, int hours){
        createRequestMessage(apiToken.get(), "message for closed chat");
        ApiORCA.createClosedChat(agent,hours,orcaMessageCallBody.get(),orcaChannelId.get());
    }

    @Given("^Setup ORCA (.*) integration for (.*) tenant$")
    public void createOrUpdateOrcaIntegration(String channel, String tenantName) {
        Tenants.setTenantUnderTestOrgName(tenantName);

        orcaChannelId.set(getIntegrationId(channel, "ORCA"));
        if (orcaChannelId.get() == null) {
            apiToken.set(ApiORCA.createIntegration(channel, SQSConfiguration.getCallbackUrl()));
            orcaChannelId.set(getIntegrationId(channel, "ORCA"));
        } else {
            apiToken.set(ApiORCA.updateIntegration(channel, SQSConfiguration.getCallbackUrl() ,orcaChannelId.get()));
            System.out.println("apiToken was set with: " + apiToken.get());
        }
    }

    private String getIntegrationId(String channel, String transportType) {
        Response response = ApiORCA.getORCAIntegrationsList();
        List<Map> channels = response.getBody().jsonPath().getList("");
        if (!(channels.size() == 0)) {
            for (Map channelMap : channels) {
                if ((Boolean)channelMap.get("enabled")
                        && channelMap.get("channelType").toString().equalsIgnoreCase(channel)
                        && channelMap.get("transportType").toString().equalsIgnoreCase(transportType)){

                    return channelMap.get("id").toString();
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
        verifyAutoresponder(expectedResponse, wait);
    }

    @Then("^Verify Orca returns survey (.*) response (.*) number of times during (.*) seconds")
    public void verifyOrcaReturnedResponseNumberOfTimes(String expectedResponse, int expectedSize, int wait) {
        verifyAutoResponderMessageListSize(expectedResponse, expectedSize, wait);
    }

    @Then("^Verify Orca returns (.*) autoresponder during (.*) seconds$")
    public void verifyOrcaReturnedCorrectAutoresponder(String expectedResponse, int wait) {

        expectedResponse = DefaultTouchUserSteps.formExpectedAutoresponder(expectedResponse);

        verifyAutoresponder(expectedResponse, wait);
    }

    private void verifyAutoresponder(String expectedResponse, int wait){
        Assert.assertTrue(isResponseComeToServerForClient(expectedResponse, wait),
                String.format("Autoresponder is not as expected\n" +
                        " Messages which came from server for clientId %s are: %s \n" +
                        "Expected: %s", clientId.get(), OrcaSQSHandler.orcaMessages, expectedResponse));
    }

    private void verifyAutoResponderMessageListSize(String expectedResponse, int size, int wait){
        Assert.assertTrue(isExpectedResponseListCheck(expectedResponse, size, wait),
                String.format("Autoresponder is not as expected\n" +
                        " Messages which came from server for clientId %s are: %s \n" +
                        "Expected: %s", clientId.get(), OrcaSQSHandler.orcaMessages, expectedResponse));
    }
    @Then("^Verify Orca returns (.*) Location sent by Agent during (.*) seconds$")
    public void verifyOrcaReturnedCorrectLocation(String locationName, int wait) {
        Assert.assertTrue(isLocationCameToUser(locationName, wait),
                String.format("Location '%s' didn't come to user",locationName ));
    }

    @Then("^Verify Orca returns (.*) HSM sent by Agent during (.*) seconds with parameters$")
    public void verifyOrcaReturnedCorrectLocation(String templateId, int wait, Map<String, String> parameters) {
        Assert.assertTrue(isHSMCameToUser(templateId, parameters, wait),
                String.format("HSM '%s' didn't come to user",templateId ) +"\n" +
                "Following ORCA events sent to user " +  OrcaSQSHandler.orcaEvents);
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

    private boolean isResponseComeToServerForClient(String message, int wait) {
        for (int i = 0; i < wait; i++) {
            if (isExpectedResponseArrives(message)) return true;
            waitFor(1000);
        }
        return false;
    }

    private boolean isLocationCameToUser(String location, int wait){
        for (int i = 0; i < wait; i++) {
            if (isCorrectLocationCameToUser(location)) return true;
            waitFor(1000);
        }
        return false;
    }

    private boolean isCorrectLocationCameToUser(String location){
        return OrcaSQSHandler.orcaEvents.stream().anyMatch(e->
                Optional.ofNullable(e.getContent().getEvent().getName()).equals(Optional.of(location)));
    }

    private boolean isHSMCameToUser(String templateId, Map<String, String> parameters, int wait){
        for (int i = 0; i < wait; i++) {
            if (isCorrectHSMCameToUser(templateId,  parameters)) return true;
            waitFor(1000);
        }
        return false;
    }

    private boolean isCorrectHSMCameToUser(String templateId, Map<String, String> parameters){
        return OrcaSQSHandler.orcaEvents.stream().anyMatch(e->
                Optional.ofNullable(e.getContent()).isPresent()
                && Optional.ofNullable(e.getContent().getEvent()).isPresent()
                && Optional.ofNullable(e.getContent().getEvent().getNestedEvent()).isPresent()
                && Optional.ofNullable(e.getContent().getEvent().getNestedEvent().getTemplateId()).equals(Optional.of(templateId))
                && Optional.ofNullable(e.getContent().getEvent().getNestedEvent().getParameters()).equals(Optional.of(parameters))
        );
    }


    private boolean isExpectedResponseArrives(String message) {
        if(Objects.isNull(OrcaSQSHandler.orcaMessages)) {
            return false;
        }
        return OrcaSQSHandler.orcaMessages.contains(message);
    }

    private boolean isExpectedResponseListCheck(String message, int expectedSize, int wait) {
        for (int i = 0; i < wait; i++) {
            if ((Collections.frequency(OrcaSQSHandler.orcaMessages, message)) >= expectedSize) {
                break;
            }
        }
        return (Collections.frequency(OrcaSQSHandler.orcaMessages, message)) >= expectedSize;
    }

    private void createRequestMessage(String apiKey, String message) {
        orcaMessageCallBody.set(new OrcaEvent(apiKey, message));
        clientId.set(orcaMessageCallBody.get().getUserInfo().getUserName());
        smsSourceId.set(orcaMessageCallBody.get().getSourceId());
    }
}
