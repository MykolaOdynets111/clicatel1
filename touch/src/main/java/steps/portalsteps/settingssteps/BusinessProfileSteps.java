package steps.portalsteps.settingssteps;

import apihelper.ApiHelperSupportHours;
import datamanager.enums.Days;
import datamanager.jacksonschemas.supportHours.DepartmentSupportHoursMapping;
import datamanager.jacksonschemas.supportHours.GeneralSupportHoursItem;
import datamanager.jacksonschemas.supportHours.SupportHoursMapping;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.NoSuchSessionException;
import portaluielem.BusinessProfileWindow;
import steps.portalsteps.AbstractPortalSteps;

import java.time.LocalDate;
import java.util.List;

import static apihelper.ApiHelperSupportHours.getSupportDaysAndHoursForMainAgent;
import static java.util.Collections.singletonList;
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

    @When("^Select (.*) option in Agent Supported Hours section$")
    public void selectAgentSupportedHoursOption(String option) {
        businessProfileWindow().selectAgentSupportHoursOption(option);
    }

    @Then("^Verify agent 'Support hours' are default for (.*)$")
    public void verifySupportHoursAreDefault(String tenantOrgName) {
        verifyThatAgentSupportHoursAreDefaultFor(tenantOrgName);
    }

    @Then("^Select only current day and verify if other are unselected for (.*)$")
    public void uncheckAllDaysExceptToday(String tenantOrgName) {
        String uncheckedDay = LocalDate.now().getDayOfWeek().name();

        ApiHelperSupportHours.setSupportDaysAndHours(new GeneralSupportHoursItem(singletonList(uncheckedDay)));

        SupportHoursMapping getAgentSupportDaysAndHours = getSupportDaysAndHoursForMainAgent(tenantOrgName)
                .getAgentMapping().stream().findFirst().orElseThrow(NoSuchSessionException::new);

        assertThat(getAgentSupportDaysAndHours.getStartWorkTime())
                .as("'Start Work' hour should be default").isEqualTo(DEFAULT_START_WORK_TIME);
        assertThat(getAgentSupportDaysAndHours.getEndWorkTime())
                .as("'End Work' hour should be default").isEqualTo(DEFAULT_END_WORK_TIME);
        assertThat(getAgentSupportDaysAndHours.getDays().size())
                .as("Only one working day should be selected :" + uncheckedDay)
                .isEqualTo(1);
        assertThat(uncheckedDay)
                .as("All days should be unchecked except for " + uncheckedDay)
                .isIn(getAgentSupportDaysAndHours.getDays());
    }

    @Given("^Set for (.*) department 'Support Hours and days' (.*)$")
    public void setDepartmentSupportHoursWithShift(String department, String shiftStrategy) {
        ApiHelperSupportHours.setSupportDaysAndHours(new GeneralSupportHoursItem());
        String checkedDay = LocalDate.now().minusDays(1).getDayOfWeek().name();

        switch (shiftStrategy) {
            case "with day shift":
                List<DepartmentSupportHoursMapping> departments = DepartmentSupportHoursMapping
                        .setDaysForDepartment(department, singletonList(checkedDay));
                GeneralSupportHoursItem supportHoursModel =
                        new GeneralSupportHoursItem(singletonList(new SupportHoursMapping()), departments);
                ApiHelperSupportHours.setSupportDaysAndHours(supportHoursModel);
                break;
            case "for all week":
                ApiHelperSupportHours.setSupportDaysAndHours(new GeneralSupportHoursItem());
                getAgentHomePage("main").waitFor(1500);
                break;
        }
    }

    private static void verifyThatAgentSupportHoursAreDefaultFor(String tenantOrgName) {
        SupportHoursMapping getAgentSupportDaysAndHours = getSupportDaysAndHoursForMainAgent(tenantOrgName)
                .getAgentMapping().stream().findFirst().orElseThrow(NoSuchSessionException::new);

        assertThat(getAgentSupportDaysAndHours.getStartWorkTime())
                .as("'Start Work' hour should be default").isEqualTo(DEFAULT_START_WORK_TIME);
        assertThat(getAgentSupportDaysAndHours.getEndWorkTime())
                .as("'End Work' hour should be default").isEqualTo(DEFAULT_END_WORK_TIME);
        assertThat(getAgentSupportDaysAndHours.getDays())
                .as("All days should be selected")
                .isEqualTo(Days.getDaysValue());
    }

    private static BusinessProfileWindow businessProfileWindow() {
        return getPortalTouchPreferencesPage().getBusinessProfileWindow();
    }
}
