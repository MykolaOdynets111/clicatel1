package steps.portalsteps.settingssteps;

import apihelper.ApiHelper;
import datamanager.Tenants;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import portaluielem.PreferencesWindow;
import steps.portalsteps.AbstractPortalSteps;
import steps.portalsteps.BasePortalSteps;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PreferencesSteps extends AbstractPortalSteps {

    private String autoSchedulerPreActionStatus;

    @When("^Change chats per agent:\"(.*)\"$")
    public void changeChatPerAgent(String chats) {
        getPreferencesWindow().setChatsAvailable(chats);
    }
    @When("^Change Pending Chats Auto-closure Time:\"(.*)\"$")
    public void changePendingAutoClosure(String pendingTime) {
        getPreferencesWindow().setPendingChatAutoClosure(pendingTime);
    }

    @When("^Agent set (\\d*) hours and (\\d*) minutes in Agent Chat Timeout section$")
    public void setInactivityTimeout(int hours, int minutes) {
        getPreferencesWindow().setAgentInactivityTimeout(hours, minutes);
    }

    @Then("^\"(.*)\" error message is shown in Agent Chat Timeout section$")
    public void verifyInactivityTimeoutError(String expectedError) {
        Assert.assertEquals(getPreferencesWindow().getAgentInactivityTimeoutLimitError(), expectedError,
                "Incorrect limits message was shown in Agent Chat Timeout section");
    }

    @Then("^Error message is not shown in Agent Chat Timeout section$")
    public void verifyInactivityTimeoutErrorNotShowing() {
        Assert.assertFalse(getPreferencesWindow().isAgentInactivityTimeoutLimitErrorShown(),
                "Error message for Agent Chat Timeout limit should not be shown");
    }

    @When("^Agent set (\\d*) days in Media Files Expiration section$")
    public void setMediaFilesExpiration(int days) {
        getPreferencesWindow().setAttachmentLifeTimeDays(days);
    }

    @Then("^\"(.*)\" error message is shown in Media Files Expiration section$")
    public void verifyAttachmentLifeTimeDaysLimitError(String expectedError) {
        Assert.assertEquals(getPreferencesWindow().getAttachmentLifeTimeDaysLimitError(), expectedError, "Incorrect limits message was shown in Media Files Expiration section");
    }

    @Then("^Error message is not shown in Media Files Expiration section$")
    public void verifyAttachmentLifeTimeDaysErrorNotShowing() {
        Assert.assertFalse(getPreferencesWindow().isAttachmentLifeTimeDaysLimitErrorShown(),
                "Error message for Agent Chat Timeout limit should not be shown");
    }

    @When("^Agent set (\\d*) hours in Ticket Expiration section$")
    public void setTicketsExpiration(int hours) {
        getPreferencesWindow().setTicketExpirationHours(hours);
    }

    @Then("^\"(.*)\" error message is shown in Ticket Expiration section$")
    public void verifyTicketsExpirationLimitError(String expectedError) {
        Assert.assertEquals(getPreferencesWindow().getTicketExpirationLimitError(),
                expectedError, "Incorrect limits message was shown in Tickets Expiration section");
    }

    @Then("^Error message is not shown in Ticket Expiration section$")
    public void verifyTicketsExpirationLimitErrorNotShowing() {
        Assert.assertFalse(getPreferencesWindow().isTicketExpirationLimitErrorShown(), "Error message for Tickets Expiration limit should not be shown");
    }

    @When("^Chats per agent became:\"(.*)\"$")
    public void changeChatPerAgentPlusMinus(String result) {
        Assert.assertEquals(getPreferencesWindow().getChatsAvailable(), result, "Chats per agent is not as expected");
    }

    @When("^(.*) Error message is shown$")
    public void decimalErrorIsShownInWindow(String errorMessage) {
        getPreferencesWindow().isErrorMessageShown(errorMessage);
    }
    @When("^(.*) Error message is displayed for Pending Auto Closure Time$")
    public void errorIsShownInWindow(String decimalErrorMessage) {
        Assert.assertEquals(getPreferencesWindow().errorMessageShown(),decimalErrorMessage, "Eror message is not displayed");
    }

    @When("^Click off/on Chat Conclusion$")
    public void clickOffOnChatConclusion() {
        getPreferencesWindow().clickOnOffChatConclusion();
        saveChanges();
    }

    @When("^click off/on 'Automatic Scheduler'$")
    public void clickOnOffAutoScheduler() {
        autoSchedulerPreActionStatus = ApiHelper.getInternalTenantConfig(Tenants.getTenantUnderTestName(), "autoSchedulingEnabled");
        getPortalTouchPreferencesPage().getPreferencesWindow().clickOnOffAutoScheduler();
        saveChanges();
    }

    @Then("^On backend AUTOMATIC_SCHEDULER status is updated for (.*)$")
    public void verifyAutoSchedulingStatusOnBackend(String tenant) {
        Assert.assertNotEquals(ApiHelper.getInternalTenantConfig(tenant, "autoSchedulingEnabled"),
                autoSchedulerPreActionStatus,
                "Auto scheduling status on backend is not as expected \n");
    }

    @When("^Select (.*) department By Default$")
    public void selectDefaultDepartment(String name) {
        getPreferencesWindow().selectDefaultDepartment(name);
    }

    @When("^Switch Last Agent routing$")
    public void activateLastAgent() {
        getPreferencesWindow().clickOnLiveChatRoating();
        saveChanges();
    }

    @Then("^All default values on Preferences page are correct$")
    public void verifyAllDefaultPreferences(Map<String, String> pref) {
        PreferencesWindow preferencesWindow = getPreferencesWindow();
        softAssert.assertEquals(preferencesWindow.getChatsAvailable(), pref.get("maximumChatsPerAgent"),
                "Default Max Chats per agent preferences are not correct");
        softAssert.assertEquals(preferencesWindow.getTicketExpirationHours(), pref.get("ticketExpiration"),
                "Default Ticket Expiration hours are not correct");
        softAssert.assertEquals(preferencesWindow.getAgentChatTimeout(), pref.get("agentChatTimeout"),
                "Default Agent Chat Timeout are not correct");
        softAssert.assertEquals(preferencesWindow.getAttachmentLifeTimeDays(), pref.get("mediaFilesExpiration"),
                "Default Media Files Expiration days are not correct");
        softAssert.assertEquals(preferencesWindow.getInactivityTimeoutHours(), pref.get("InactivityTimeoutHours"),
                "Default Inactivity Timeout Hours hours are not correct");
        softAssert.assertEquals(preferencesWindow.getPendingChatAutoClosureHours(), pref.get("pendingChatsAuto_closureTime"),
                "Default Pending Chats Auto-closure Time hours are not correct");
        softAssert.assertAll();
    }

    @Then("^Verify 'Pending Chats Auto-closure Time' is (.*) hours$")
    public void verifyPendingChatsAutoClosureTimeIs(String hours) {
        assertThat(getPreferencesWindow().getPendingChatAutoClosureHours())
                .as(String.format("'Pending Chats Auto-closure Time' should be %s hours", hours))
                .isEqualTo(hours);
    }

    private static void saveChanges() {
        new BasePortalSteps().agentClickSaveChangesButton();
    }

    private static PreferencesWindow getPreferencesWindow() {
        return getPortalTouchPreferencesPage().getPreferencesWindow();
    }
}
