package steps.agentsteps;

import apihelper.ApiHelper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import datamanager.jacksonschemas.CRMTicket;
import dbmanager.DBConnector;
import driverfactory.DriverFactory;
import drivermanager.ConfigManager;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import steps.portalsteps.BasePortalSteps;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;

public class AgentCRMTicketsSteps extends AbstractAgentSteps {

    private static ThreadLocal<CRMTicket> createdCrmTicket = new ThreadLocal<>();

    private static ThreadLocal<Map<String, String>> crmTicketInfoForCreatingViaAPI = new ThreadLocal<>();

    public static ThreadLocal<Map<String, String>> crmTicketInfoForUpdating = new ThreadLocal<>();

    private static ThreadLocal<List<CRMTicket>> createdCrmTicketsList = new ThreadLocal<>();

    public static CRMTicket getCreatedCRMTicket(){
        return createdCrmTicket.get();
    }

    public static List<CRMTicket> getCreatedCRMTicketsList(){
        return createdCrmTicketsList.get();
    }

    @Given("CRM ticket (.*) is created")
    public void createCRMTicketViaAPI(String urlStatus){
        Map<String, String> sessionDetails = DBConnector.getActiveSessionDetailsByClientProfileID
                (ConfigManager.getEnv(), getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()));
        Map<String, String> dataForNewCRMTicket = prepareDataForCrmTicket(sessionDetails, urlStatus);

        Response resp = ApiHelper.createCRMTicket(getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()), dataForNewCRMTicket);
        createdCrmTicket.set(resp.getBody().as(CRMTicket.class));
        Assert.assertEquals(resp.statusCode(), 200,
                "Creating CRM ticket via API was not successful\n"
                        + resp.statusCode() + "\n" + "rest body: " +resp.getBody().asString());
    }

    @Given("(.*) CRM tickets are created")
    public void createCRMTicketsViaAPI(int ticketsNumber){
        Map<String, String>  sessionDetails = DBConnector.getActiveSessionDetailsByClientProfileID
                (ConfigManager.getEnv(), getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()));
        List<CRMTicket> createdCRMTicket = new ArrayList<>();
        for(int i = 0; i < ticketsNumber; i++) {
            Map<String, String> dataForNewCRMTicket = prepareDataForCrmTicket(sessionDetails, "with url");

            Response resp = ApiHelper.createCRMTicket(getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()), dataForNewCRMTicket);
            createdCRMTicket.add(resp.getBody().as(CRMTicket.class));
            waitFor(1100);
        }
        createdCrmTicketsList.set(createdCRMTicket);
        Assert.assertEquals(ApiHelper.getCRMTickets().size(), ticketsNumber,
                "Not all tickets where successfully created");
    }

    @Then("Tickets are correctly sorted")
    public void verifyTicketsSorting(){
        List<Map<String, String>> actualTickets = getAgentHomeForMainAgent().getCrmTicketContainer()
                .getAllTicketsInfoExceptDate();
        List<CRMTicket> createdTickets = getCreatedCRMTicketsList();

        Collections.reverse(createdTickets);
        List<Map<String, String>> expectedTickets = new ArrayList<>();

        for (CRMTicket ticket : createdTickets){
            expectedTickets.add(new HashMap<String, String>(){{
                put("note", ticket.getAgentNote());
                put("number", "Ticket #: " + ticket.getTicketNumber());
            }});
        }
        Assert.assertEquals(actualTickets, expectedTickets,
                "Tickets order is not as Expected\n" +
                        "Created tickets: " + createdTickets.toString() + "\n\n" +
                        "Expected list" + expectedTickets        );
    }

    @Then("^CRM ticket is (?:created|updated) on backend with correct information$")
    public void crmTicketIsCreatedOnBackendWithCorrectInformation() {
        SoftAssert soft = new SoftAssert();
        CRMTicket actualTicketInfoFromBackend = ApiHelper.getCRMTickets().get(0);

        ZoneId zoneId = TimeZone.getDefault().toZoneId();
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        String date = crmTicketInfoForUpdating.get().get("date");
        if(date.length()<=19){
            formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        }
        LocalDateTime createdDate = LocalDateTime.parse(date, formatter1);
        long expectedMili = convertLocalDateTimeToMillis(createdDate, zoneId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        LocalDateTime dateTimeFromBackend =  LocalDateTime.parse(actualTicketInfoFromBackend.getCreatedDate(), formatter)
                .atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(TimeZone.getDefault().toZoneId()).toLocalDateTime();
        long actualMili = convertLocalDateTimeToMillis(dateTimeFromBackend, zoneId);
        List<String> tags = ApiHelper.getTagsForCRMTicket(actualTicketInfoFromBackend.getConversationId());
        Collections.sort(tags);
        String crmTicketTags = String.join(", ", tags);
        soft.assertTrue((actualMili-expectedMili)<=6000,
                "Ticket created date does not match created on the backend \n");
        soft.assertEquals(actualTicketInfoFromBackend.getTicketNumber(), crmTicketInfoForUpdating.get().get("ticketNumber"),
                "Ticket Number does not match created on the backend  \n");
        soft.assertEquals(actualTicketInfoFromBackend.getAgentNote(),  crmTicketInfoForUpdating.get().get("agentNote"),
                "Ticket note does not match created on the backend \n");
        soft.assertEquals(actualTicketInfoFromBackend.getLink(), crmTicketInfoForUpdating.get().get("link"),
                "Ticket link does not match created on the backend \n");
        soft.assertEquals(crmTicketTags, crmTicketInfoForUpdating.get().get("agentTags"),
                "CRM ticket 'Tags' does not match created on the backend \n");
        soft.assertAll();

    }

    @Then("Container with new CRM (?:ticket|tickets) is shown")
    public void verifyCRMTicketIsShown(){
        SoftAssert soft = new SoftAssert();
//        soft.assertEquals(getAgentHomeForMainAgent().getCrmTicketContainer().getContainerHeader(), "Notes",
//                "CRM tickets section header is not 'Notes'");
        soft.assertTrue(getAgentHomeForMainAgent().getCrmTicketContainer().isTicketContainerShown(),
                "CRM ticket is not shown");
        soft.assertAll();
    }

    @When("^I click CRM ticket number URL$")
    public void clickTicketNumber(){
        getAgentHomeForMainAgent().getCrmTicketContainer().getFirstTicket().clickTicketNumber();

        String currentWindow = DriverFactory.getDriverForAgent("main").getWindowHandle();

        for (String winHandle : DriverFactory.getDriverForAgent("main").getWindowHandles()) {
            if (!winHandle.equals(currentWindow)) {
                DriverFactory.getDriverForAgent("main").switchTo().window(winHandle);
            }
        }
    }

    @Then("^(.*) is redirected by CRM ticket URL$")
    public void verifyUserRedirectedBlankPage(String agent){
        String pageUrl = DriverFactory.getDriverForAgent(agent).getCurrentUrl();
        String currentWindow = DriverFactory.getDriverForAgent("main").getWindowHandle();
        for (String winHandle : DriverFactory.getDriverForAgent("main").getWindowHandles()) {
            if (!winHandle.equals(currentWindow)) {
                DriverFactory.getDriverForAgent("main").switchTo().window(winHandle);
            }
        }
        Assert.assertEquals(pageUrl, "about:blank",
                "Agent is not redirected by CRM tickets' url");
    }

    @Then("^(.*) is redirected to (.*) chatdesk page$")
    public void verifyUserRedirectedEmptyChatdeskPage(String agent, String chatDeskPage) {
        String pageUrl = DriverFactory.getDriverForAgent(agent).getCurrentUrl();
        SoftAssert softAssert = new SoftAssert();
        if(chatDeskPage.equalsIgnoreCase("empty")) {
            softAssert.assertTrue(pageUrl.contains("null"),
                    "Agent is not redirected by CRM tickets' url");
        } else if(chatDeskPage.equalsIgnoreCase("supervisor")) {
            softAssert.assertTrue(pageUrl.contains("supervisor"),
                    "Agent is not redirected to supervisor chatdesk page");
        }
        softAssert.assertTrue(pageUrl.contains("chatdesk"),
                "Agent is not redirected to chatdesk page");
    }

    @When("^(.*) click 'Delete' button for CRM ticket$")
    public void clickDeleteCRMTicketButton(String agent){
        getAgentHomePage(agent).getCrmTicketContainer().getFirstTicket().clickDeleteButton();
    }

    @Then("^Confirmation deleting popup is shown$")
    public void verifyDeletingCRMTicketPopupShown(){
        Assert.assertTrue(getAgentHomeForMainAgent().getDeleteCRMConfirmationPopup().isOpened(),
                "CRM ticket deleting confirmation popup is not shown");
    }

    @When("^(.*) click 'Cancel' button' in CRM deleting popup$")
    public void cancelCRMDeleting(String agent){
        getAgentHomePage(agent).getDeleteCRMConfirmationPopup().clickCancel();
    }

    @When("^(.*) click 'Delete' button' in CRM deleting popup$")
    public void deleteCRMTicket(String agent){
        getAgentHomePage(agent).getDeleteCRMConfirmationPopup().clickDelete();
    }

    @Then("New CRM ticket is not shown")
    public void verifyCRMTicketIsNotShown(){
        Assert.assertTrue(getAgentHomeForMainAgent().getCrmTicketContainer().isTicketContainerRemoved(),
                "CRM ticket is still shown");
    }

    @When("^(.*) click on CRM ticket note$")
    public void clickOnCRMTicket(String agent){
        getAgentHomePage(agent).getCrmTicketContainer().getFirstTicket().clickTicketNote();
    }

    @Then("^'Edit ticket' window is opened$")
    public void verifyEditWindowOpened(){
        Assert.assertTrue(getAgentHomeForMainAgent().getEditCRMTicketWindow().isOpened(),
                "'Edit ticket' window is not opened after clicking 'Edit' button for CRM ticket");
    }

    @When("^(.*) fill in the form with new CRM ticket info$")
    public void fillCRMEditingFormWithNewData(String agent){
        formDataForCRMUpdating();
        getAgentHomePage(agent).getEditCRMTicketWindow().provideCRMNewTicketInfo(crmTicketInfoForUpdating.get());
    }

    @When("^Save CRM ticket changings$")
    public void saveCRMEditing(){
        getAgentHomeForMainAgent().getEditCRMTicketWindow().saveChanges();
    }

    @Then("Ticket info is updated on chatdesk")
    public void verifyTicketInfoUpdatedInActiveChat(){
        SoftAssert soft = new SoftAssert();
        Map<String, String> actualInfo = getAgentHomeForMainAgent().getCrmTicketContainer().getFirstTicket().getTicketInfo();
        soft.assertEquals(actualInfo.get("createdDate").toLowerCase(), formExpectedCRMTicketCreatedDate(createdCrmTicket.get().getCreatedDate()),
                "Shown Ticket created date is not correct \n");
        soft.assertEquals(actualInfo.get("number"), "Ticket #: " + crmTicketInfoForUpdating.get().get("ticketNumber"),
                "Shown Ticket Number is not correct \n");
        soft.assertEquals(actualInfo.get("note"), crmTicketInfoForUpdating.get().get("agentNote"),
                "Shown Ticket note is not correct \n");
        soft.assertAll();
    }

    @Then("CRM ticket is updated on back end")
    public void verifyCRMTicketUpdated() {
        SoftAssert soft = new SoftAssert();
        CRMTicket actualTicketInfoFromBackend = ApiHelper.getCRMTickets().get(0);
        String createdDate = getAgentHomeForMainAgent().getCrmTicketContainer().getFirstTicket().getCreatedDate();

        soft.assertEquals(formExpectedCRMTicketCreatedDate(actualTicketInfoFromBackend.getCreatedDate()), createdDate.toLowerCase(),
                "Ticket created date is changed after ticket editing \n");
        soft.assertEquals(actualTicketInfoFromBackend.getTicketNumber(), crmTicketInfoForUpdating.get().get("ticketNumber"),
                "Ticket Number is not changed after canceling ticket editing \n");
        soft.assertEquals(actualTicketInfoFromBackend.getAgentNote(),  crmTicketInfoForUpdating.get().get("agentNote"),
                " Ticket note is changed after canceling ticket editing \n");
        soft.assertEquals(actualTicketInfoFromBackend.getLink(), crmTicketInfoForUpdating.get().get("link"),
                " Ticket link is changed after canceling ticket editing \n");
        soft.assertAll();
    }

    @Then("Correct ticket info is shown")
    public void verifyTicketInfoInActiveChat(){
        SoftAssert soft = new SoftAssert();
        Map<String, String> actualInfo = getAgentHomeForMainAgent().getCrmTicketContainer().getFirstTicket().getTicketInfo();
        soft.assertEquals(actualInfo.get("createdDate").toLowerCase(), formExpectedCRMTicketCreatedDate(createdCrmTicket.get().getCreatedDate()),
                "Shown Ticket created date is not correct \n");
        soft.assertEquals(actualInfo.get("number"), "Ticket #: " + createdCrmTicket.get().getTicketNumber(),
                "Shown Ticket Number is not correct \n");
        soft.assertEquals(actualInfo.get("note"), createdCrmTicket.get().getAgentNote(),
                "Shown Ticket note is not correct \n");
        soft.assertAll();
    }

    @When("^Cancel CRM editing$")
    public void cancelCRMEditing(){
        getAgentHomeForMainAgent().getEditCRMTicketWindow().clickCancel();
    }

    @Then("CRM ticket is not updated on back end")
    public void verifyCRMTicketNotUpdated() {
        SoftAssert soft = new SoftAssert();
        CRMTicket actualTicketInfoFromBackend = ApiHelper.getCRMTickets().get(0);

        soft.assertEquals(actualTicketInfoFromBackend.getCreatedDate().split(":")[0],
                createdCrmTicket.get().getCreatedDate().split(":")[0],
                "Ticket created date is changed after canceling ticket editing \n");
        soft.assertEquals(actualTicketInfoFromBackend.getTicketNumber(), createdCrmTicket.get().getTicketNumber(),
                "Ticket Number is changed after canceling ticket editing \n");
        soft.assertEquals(actualTicketInfoFromBackend.getAgentNote(), createdCrmTicket.get().getAgentNote(),
                " Ticket note is changed after canceling ticket editing \n");
        soft.assertEquals(actualTicketInfoFromBackend.getLink(), createdCrmTicket.get().getLink(),
                " Ticket link is changed after canceling ticket editing \n");
        soft.assertAll();
    }

    @Then("^CRM ticket is not created$")
    public void crmTicketDidNotCreated() {
        Assert.assertEquals( ApiHelper.getCRMTickets().size(), 0,
                "CRM ticket was created on back end");
    }

    @Then("(.*) type Note:(.*), Link:(.*), Number:(.*) for CRM ticket$")
    public void agentCreateCRMTicket(String agent,String note, String link, String number) {
        // TODO: 9/4/2020 remove wait after spinner would be added
        waitFor(2000);
        getAgentHomePage(agent).getAgentFeedbackWindow().fillForm(note, link, number);
        List <String> tags = getAgentHomePage(agent).getAgentFeedbackWindow().getChosenTags();
        prepareDataForCrmTicketChatdesk(note, link, number, tags);
    }

    private Map<String, String> prepareDataForCrmTicket( Map<String, String>  sessionDetails, String urlStatus){
        Map<String, String> dataForNewCRMTicket = new HashMap<>();
        dataForNewCRMTicket.put("clientProfileId", sessionDetails.get("clientProfileId"));
        dataForNewCRMTicket.put("conversationId", sessionDetails.get("conversationId"));
        dataForNewCRMTicket.put("sessionId", sessionDetails.get("sessionId"));
        dataForNewCRMTicket.put("link", "about:blank");
        dataForNewCRMTicket.put("ticketNumber", faker.number().digits(5));
        dataForNewCRMTicket.put("agentNote", "AQA_note " + faker.book().title());
        crmTicketInfoForCreatingViaAPI.set(dataForNewCRMTicket);
        if(urlStatus.toLowerCase().contains("without url")) dataForNewCRMTicket.remove("link");
        return dataForNewCRMTicket;
    }

    @Then("^Correct (.*) sentiment selected$")
    public void verifyCorrectSentimentSelected(String sentiment){
        Assert.assertTrue(getAgentHomePage("main").getAgentFeedbackWindow().getSelectedSentiment()
                .contains(sentiment), "Sentiment '" + sentiment + "' is not selected in Conclusion window");
    }

    private void formDataForCRMUpdating(){
        Map<String, String> info = new HashMap<>();
        info.put("agentNote", "Note for updating ticket");
        info.put("link", "http://updateurl.com");
        info.put("ticketNumber", "11111");
        crmTicketInfoForUpdating.set(info);
    }

    private Map<String, String> prepareDataForCrmTicketChatdesk( String agentNote, String link, String ticketNumber, List<String> tags){
        Map<String, String>  sessionDetails = DBConnector.getActiveSessionDetailsByClientProfileID
                (ConfigManager.getEnv(), getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()));
        Map<String, String> dataForNewCRMTicket = new HashMap<>();
        Collections.sort(tags);
        dataForNewCRMTicket.put("clientProfileId", sessionDetails.get("clientProfileId"));
        dataForNewCRMTicket.put("conversationId", sessionDetails.get("conversationId"));
        dataForNewCRMTicket.put("sessionId", sessionDetails.get("sessionId"));
        dataForNewCRMTicket.put("agentNote", agentNote);
        dataForNewCRMTicket.put("agentTags", String.join(", ", tags));
        dataForNewCRMTicket.put("link", link);
        dataForNewCRMTicket.put("ticketNumber", ticketNumber);
        dataForNewCRMTicket.put("date", LocalDateTime.now().toString());
        crmTicketInfoForUpdating.set(dataForNewCRMTicket);
        return dataForNewCRMTicket;
    }

    @Then("^Agent sees (.*) CRM tickets$")
    public void verifyTicketsNumber(int expectedNumber){
        Assert.assertEquals(getAgentHomeForMainAgent().getCrmTicketContainer().getNumberOfTickets(), expectedNumber,
                "Shown tickets number is not as expected");
    }

    @Then("^Tickets number is reduced to (.*)$")
    public void verifyTicketsNumberReduced(int expectedNumber){
        boolean isReduced = false;
        for(int i =0; i < 6; i++){
            if(getAgentHomeForMainAgent().getCrmTicketContainer().getNumberOfTickets() == expectedNumber) {
                isReduced = true;
                break;
            }
            else getAgentHomeForMainAgent().waitFor(200);
        }
        Assert.assertTrue(isReduced, "Shown tickets number is not as expected");
    }


    @When("^(.*) deletes first ticket$")
    public void deleteFirstTicket(String agent){
        getAgentHomePage(agent).getCrmTicketContainer().getFirstTicket().clickDeleteButton();
        getAgentHomePage(agent).getDeleteCRMConfirmationPopup().clickDelete();
    }

    @Then("^Agent add (.*) tag$")
    public void agentAddSelectedTag(int iter) {
        getAgentHomeForMainAgent().getAgentFeedbackWindow().selectTags(iter);
        Assert.assertEquals(getAgentHomeForMainAgent().getAgentFeedbackWindow().getChosenTags().size(), iter,
                "Not all tags was added \n");
    }

    @Then("^(.*) select precreated tag$")
    public void agentAddSelectedTag(String agent) {
        getAgentHomePage(agent).getAgentFeedbackWindow().typeTags(BasePortalSteps.tagname);
        getAgentHomePage(agent).getAgentFeedbackWindow().selectTagInSearch().closeDropdown();
    }


    @Then("^Agent delete all tags$")
    public void agentDeleteAllTags() {
        getAgentHomeForMainAgent().getAgentFeedbackWindow().deleteTags();
    }

    @Then("^All tags for tenant is available in the dropdown$")
    public void allTagsForTenantIsAvailableInTheDropdown() {
        List<String> tags= ApiHelper.getAllTags();
        List<String> tagsInCRM = getAgentHomeForMainAgent().getAgentFeedbackWindow().getTags();
        Assert.assertEquals(tagsInCRM, tags, " CRM ticket 'Tags' does not match created on the backend \n");
    }

    @Then("^Agent can search tag and select tag, selected tag added in tags field$")
    public void agentCanSearchTagAndSelectTag() {
        SoftAssert soft = new SoftAssert();
        List<String> tags= ApiHelper.getAllTags();
        String randomTag= tags.get((int)(Math.random() * tags.size()));
        getAgentHomeForMainAgent().getAgentFeedbackWindow().typeTags(randomTag);
        List<String> tagsInCRM = getAgentHomeForMainAgent().getAgentFeedbackWindow().getTags();
        getAgentHomeForMainAgent().getAgentFeedbackWindow().selectTagInSearch();
        List<String> chosenTags = getAgentHomeForMainAgent().getAgentFeedbackWindow().getChosenTags();
        soft.assertTrue(tagsInCRM.contains(randomTag),
                "CRM ticket 'Tags' does not match in search \n");
        soft.assertTrue(chosenTags.contains(randomTag),
                "CRM ticket: selected 'Tag' does not match (or was not added) into the input field for Tags \n");
        soft.assertAll();
    }

    private String formExpectedCRMTicketCreatedDate(String createdTimeFromBackend){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        LocalDateTime dateTimeFromBackend =  LocalDateTime.parse(createdCrmTicket.get().getCreatedDate(), formatter).atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(TimeZone.getDefault().toZoneId()).toLocalDateTime();

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd MMM yyyy");


        return dateTimeFromBackend.format(formatter1).toLowerCase();
    }
}
