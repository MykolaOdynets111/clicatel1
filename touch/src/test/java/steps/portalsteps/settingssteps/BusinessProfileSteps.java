package steps.portalsteps.settingssteps;

import agentpages.AgentHomePage;
import datamanager.enums.Days;
import datamanager.jacksonschemas.AgentMapping;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.joda.time.LocalDate;
import org.openqa.selenium.NoSuchSessionException;
import portaluielem.BusinessProfileWindow;
import steps.portalsteps.AbstractPortalSteps;

import java.util.List;
import java.util.stream.Collectors;

import static apihelper.ApiHelper.getAgentSupportDaysAndHoursForMainAgent;
import static apihelper.ApiHelper.setAgentSupportDaysAndHours;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static steps.agentsteps.AbstractAgentSteps.getAgentHomePage;

public class BusinessProfileSteps extends AbstractPortalSteps {

    public static final String DEFAULT_START_WORK_TIME = "00:00";
    public static final String DEFAULT_END_WORK_TIME = "23:59";

    @When("^Upload: photo for tenant$")
    public void uploadPhotoForTenant() {
        businessProfileWindow()
                .uploadPhoto("src/test/resources/agentphoto/tenant.png");
        getPortalTouchPreferencesPage()
                .getEditCompanyLogoWindow()
                .clickSaveImageButton();
    }

    @When("^Delete 'Company Logo'$")
    public void deleteCompanyLogo() {
        businessProfileWindow().clickEditImageButton();
        getPortalTouchPreferencesPage()
                .getEditCompanyLogoWindow()
                .clickRemoveImageButton();
    }

    @Then("^Verify Logo is uploaded$")
    public void verifyLogoIsUploaded() {
        assertTrue(businessProfileWindow().isLogoIsVisible(), "Logo preview should be visible");
    }

    @Then("^Verify Logo is deleted$")
    public void verifyLogoIsDeleted() {
        assertFalse(businessProfileWindow().isLogoIsVisible(), "Logo preview should be deleted");
    }

    @Then("^Tenant logo is shown on Chat desk$")
    public void verifyTenantLogoIsShownOnChatDesk() {
        AgentHomePage homePage = getAgentHomePage("main");

        assertTrue(homePage.getPageHeader().isIconPresent(), "Tenant logo should be shown on chatdesk");
    }

    @When("^Select (.*) option in Agent Supported Hours section$")
    public void selectAgentSupportedHoursOption(String option) {
        businessProfileWindow().selectAgentSupportHoursOption(option);
    }

    @Then("^Verify 'Support hours' are default for (.*)$")
    public void verifySupportHoursAreDefault(String tenantOrgName) {
        verifyThatSupportHoursAreDefaultFor(tenantOrgName);
    }

    @Then("^Uncheck current day and verify that 'Today' is unselected for (.*)$")
    public void uncheckTodayAndVerifyIfItsUnselected(String tenantOrgName) {
        String uncheckedDay = LocalDate.now().dayOfWeek().getAsText();
        List<String> workingDays = Days.getAllDays().stream()
                .filter(value -> !value.equalsIgnoreCase(uncheckedDay))
                .collect(Collectors.toList());

        setAgentSupportDaysAndHours(tenantOrgName, workingDays);

        AgentMapping getAgentSupportDaysAndHours = getAgentSupportDaysAndHoursForMainAgent(tenantOrgName)
                .getAgentMapping().stream().findFirst().orElseThrow(NoSuchSessionException::new);

        assertThat(getAgentSupportDaysAndHours.getStartWorkTime())
                .as("'Start Work' hour should be default").isEqualTo(DEFAULT_START_WORK_TIME);
        assertThat(getAgentSupportDaysAndHours.getEndWorkTime())
                .as("'End Work' hour should be default").isEqualTo(DEFAULT_END_WORK_TIME);
        assertThat(uncheckedDay)
                .as("Current day shouldn't be selected")
                .isNotIn(getAgentSupportDaysAndHours.getDays());
    }

    @Then("^Set default 'Support Hours' value for (.*)$")
    public void setDefaultSupportHoursValueFor(String tenantOrgName) {
        setAgentSupportDaysAndHours(tenantOrgName, Days.getAllDays());

        verifyThatSupportHoursAreDefaultFor(tenantOrgName);
    }

    private static void verifyThatSupportHoursAreDefaultFor(String tenantOrgName) {
        AgentMapping getAgentSupportDaysAndHours = getAgentSupportDaysAndHoursForMainAgent(tenantOrgName)
                .getAgentMapping().stream().findFirst().orElseThrow(NoSuchSessionException::new);

        assertThat(getAgentSupportDaysAndHours.getStartWorkTime())
                .as("'Start Work' hour should be default").isEqualTo(DEFAULT_START_WORK_TIME);
        assertThat(getAgentSupportDaysAndHours.getEndWorkTime())
                .as("'End Work' hour should be default").isEqualTo(DEFAULT_END_WORK_TIME);
        assertThat(getAgentSupportDaysAndHours.getDays())
                .as("All days should be selected").isEqualTo(Days.getAllDays());
    }

    private static BusinessProfileWindow businessProfileWindow() {
        return getPortalTouchPreferencesPage().getBusinessProfileWindow();
    }
}
