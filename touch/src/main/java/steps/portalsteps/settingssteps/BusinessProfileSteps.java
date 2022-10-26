package steps.portalsteps.settingssteps;

import agentpages.AgentHomePage;
import apihelper.ApiHelperSupportHours;
import datamanager.enums.Days;
import datamanager.jacksonschemas.supportHours.DepartmentSupportHoursMapping;
import datamanager.jacksonschemas.supportHours.GeneralSupportHoursItem;
import datamanager.jacksonschemas.supportHours.SupportHoursMapping;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.NoSuchSessionException;
import portaluielem.BusinessProfileWindow;
import steps.portalsteps.AbstractPortalSteps;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static apihelper.ApiHelperSupportHours.getSupportDaysAndHoursForMainAgent;
import static java.util.Collections.singletonList;
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

    }

    @NotNull
    private static List<String> getAllDaysExcept(String uncheckedDay) {
        return Days.getDaysValue().stream()
                .filter(value -> !value.equalsIgnoreCase(uncheckedDay))
                .collect(Collectors.toList());
    }

    private static BusinessProfileWindow businessProfileWindow() {
        return getPortalTouchPreferencesPage().getBusinessProfileWindow();
    }
}
