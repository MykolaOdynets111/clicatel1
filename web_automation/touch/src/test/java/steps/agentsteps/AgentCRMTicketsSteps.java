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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;

public class AgentCRMTicketsSteps extends AbstractAgentSteps {

    private static ThreadLocal<CRMTicket> createdCrmTicket = new ThreadLocal<>();

    private static ThreadLocal<Map<String, String>> crmTicketInfoForCreatingViaAPI = new ThreadLocal<>();

    private static ThreadLocal<Map<String, String>> crmTicketInfoForUpdating = new ThreadLocal<>();

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
            getAgentHomeForMainAgent().waitForDeprecated(1100);
        }
        createdCrmTicketsList.set(createdCRMTicket);
        Assert.assertEquals(ApiHelper.getCRMTickets(getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()), "TOUCH").size(), ticketsNumber,
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
                put("note", "Note: " + ticket.getAgentNote());
                put("number", "Ticket Number: " + ticket.getTicketNumber());
            }});
        }
        Assert.assertEquals(actualTickets, expectedTickets,
                "Tickets order is not as Expected\n" +
                        "Created tickets: " + createdTickets.toString() + "\n\n" +
                        "Expected list" + expectedTickets        );
    }

    @Then("^CRM ticket is created on backend with correct information$")
    public void crmTicketIsCreatedOnBackendWithCorrectInformation() {
        SoftAssert soft = new SoftAssert();
        CRMTicket actualTicketInfoFromBackend = ApiHelper.getCRMTickets(getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()), "TOUCH").get(0);

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
        List<String> tags = ApiHelper.getTagsForCRMTicket(actualTicketInfoFromBackend.getSessionId());
        //Collections.reverse(tags);
        String crmTicketTags = String.join(", ", tags);
        soft.assertTrue((actualMili-expectedMili)<=3000,
                "Ticket created date does not match created on the backend \n");
        soft.assertEquals(actualTicketInfoFromBackend.getTicketNumber(), crmTicketInfoForUpdating.get().get("ticketNumber"),
                "Ticket Number does not match created on the backend  \n");
        soft.assertEquals(actualTicketInfoFromBackend.getAgentNote(),  crmTicketInfoForUpdating.get().get("agentNote"),
                "Ticket note does not match created on the backend \n");
        soft.assertEquals(actualTicketInfoFromBackend.getLink(), crmTicketInfoForUpdating.get().get("link"),
                "Ticket link does not match created on the backend \n");
        soft.assertTrue(crmTicketTags.equals(crmTicketInfoForUpdating.get().get("agentTags")),
                "CRM ticket 'Tags' does not match created on the backend \n");
        soft.assertAll();

    }

    @Then("Container with new CRM (?:ticket|tickets) is shown")
    public void verifyCRMTicketIsShown(){
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(getAgentHomeForMainAgent().getCrmTicketContainer().getContainerHeader(), "Notes",
                "CRM tickets section header is not 'Notes'");
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

    @Then("^(.*) is redirected to empty chatdesk page$")
    public void verifyUserRedirectedEmptyChatdeskPage(String agent){
        String pageUrl = DriverFactory.getDriverForAgent(agent).getCurrentUrl();
        String currentWindow = DriverFactory.getDriverForAgent("main").getWindowHandle();
        for (String winHandle : DriverFactory.getDriverForAgent("main").getWindowHandles()) {
            if (!winHandle.equals(currentWindow)) {
                DriverFactory.getDriverForAgent("main").switchTo().window(winHandle);
            }
        }
        Assert.assertTrue(pageUrl.contains("null")&pageUrl.contains("chatdesk"),
                "Agent is not redirected by CRM tickets' url");
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

    @When("^(.*) click 'Edit' button for CRM ticket$")
    public void clickEditCRMTicketButton(String agent){
        getAgentHomePage(agent).getCrmTicketContainer().getFirstTicket().clickEditButton();
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
        String expectedTicketCreated = "Created: " + formExpectedCRMTicketCreatedDate(createdCrmTicket.get().getCreatedDate());
        soft.assertEquals(actualInfo.get("createdDate").toLowerCase(), expectedTicketCreated.toLowerCase(),
                "Shown Ticket created date is not correct \n");
        soft.assertEquals(actualInfo.get("number"), "Ticket Number: " + crmTicketInfoForUpdating.get().get("ticketNumber"),
                "Shown Ticket Number is not correct \n");
        soft.assertEquals(actualInfo.get("note"), "Note: " + crmTicketInfoForUpdating.get().get("agentNote"),
                "Shown Ticket note is not correct \n");
        soft.assertAll();
    }

    @Then("CRM ticket is updated on back end")
    public void verifyCRMTicketUpdated() {
        SoftAssert soft = new SoftAssert();
        CRMTicket actualTicketInfoFromBackend = ApiHelper.getCRMTickets(getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()), "TOUCH").get(0);
        String createdDate = getAgentHomeForMainAgent().getCrmTicketContainer().getFirstTicket().getCreatedDate();
        String createdDateFromBackend = "Created: " + formExpectedCRMTicketCreatedDate(actualTicketInfoFromBackend.getCreatedDate());


        soft.assertEquals(createdDateFromBackend.toLowerCase(), createdDate.toLowerCase(),
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
        String expectedTicketCreated = "Created: " + formExpectedCRMTicketCreatedDate(createdCrmTicket.get().getCreatedDate());
        soft.assertEquals(actualInfo.get("createdDate").toLowerCase(), expectedTicketCreated.toLowerCase(),
                "Shown Ticket created date is not correct \n");
        soft.assertEquals(actualInfo.get("number"), "Ticket Number: " + createdCrmTicket.get().getTicketNumber(),
                "Shown Ticket Number is not correct \n");
        soft.assertEquals(actualInfo.get("note"), "Note: " + createdCrmTicket.get().getAgentNote(),
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
        CRMTicket actualTicketInfoFromBackend = ApiHelper.getCRMTickets(getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()), "TOUCH").get(0);

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
        Assert.assertEquals( ApiHelper.getCRMTickets(getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()), "TOUCH").size(), 0,
                "CRM ticket was created on back end");
    }

    @Then("(.*)type Note:(.*), Link:(.*), Number:(.*) for CRM ticket$")
    public void agentCreateCRMTicket(String agent,String note, String link, String number) {
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

    private String formExpectedCRMTicketCreatedDate(String createdTimeFromBackend){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        LocalDateTime dateTimeFromBackend =  LocalDateTime.parse(createdCrmTicket.get().getCreatedDate(), formatter).atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(TimeZone.getDefault().toZoneId()).toLocalDateTime();

        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        Map<Long, String> newDaysMap = new HashMap<>();
        newDaysMap.put(1L, "1st");
        newDaysMap.put(2L, "2nd");
        newDaysMap.put(3L, "3rd");
        newDaysMap.put(4L, "4th");
        newDaysMap.put(5L, "5th");
        newDaysMap.put(6L, "6th");
        newDaysMap.put(7L, "7th");
        newDaysMap.put(8L, "8th");
        newDaysMap.put(9L, "9th");
        newDaysMap.put(10L, "10th");
        newDaysMap.put(11L, "11th");
        newDaysMap.put(12L, "12th");
        newDaysMap.put(13L, "13th");
        newDaysMap.put(14L, "14th");
        newDaysMap.put(15L, "15th");
        newDaysMap.put(16L, "16th");
        newDaysMap.put(17L, "17th");
        newDaysMap.put(18L, "18th");
        newDaysMap.put(19L, "19th");
        newDaysMap.put(20L, "20th");
        newDaysMap.put(21L, "21st");
        newDaysMap.put(22L, "22nd");
        newDaysMap.put(23L, "23rd");
        newDaysMap.put(24L, "24th");
        newDaysMap.put(25L, "25th");
        newDaysMap.put(26L, "26th");
        newDaysMap.put(27L, "27th");
        newDaysMap.put(28L, "28th");
        newDaysMap.put(29L, "29th");
        newDaysMap.put(30L, "30th");
        newDaysMap.put(31L, "31st");

        builder.appendText(ChronoField.DAY_OF_MONTH, newDaysMap );
        builder.append(DateTimeFormatter.ofPattern(" yyyy, h:mm a"));
        DateTimeFormatter formatter1 = builder.toFormatter();


        return (dateTimeFromBackend.getMonth() + " " + dateTimeFromBackend.format(formatter1)).toLowerCase();
    }
}
