package steps.portalsteps;

import agentpages.AgentHomePage;
import datamanager.enums.Days;
import datamanager.jacksonschemas.AgentMapping;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.NoSuchSessionException;
import portaluielem.BusinessProfileWindow;

import static apihelper.ApiHelper.getAgentSupportDaysAndHoursForMainAgent;
import static org.testng.Assert.*;
import static steps.agentsteps.AbstractAgentSteps.getAgentHomePage;

public class BusinessProfileSteps extends AbstractPortalSteps {

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

    @Then("^Verify 'Support hours' is default for (.*)$")
    public void supportHoursAreUpdatedInTenantConfigs(String tenantOrgName) {
        AgentMapping agentSupportDaysAndHours = getAgentSupportDaysAndHoursForMainAgent(tenantOrgName)
                .getAgentMapping().stream().findFirst().orElseThrow(NoSuchSessionException::new);

        assertEquals(agentSupportDaysAndHours.getStartWorkTime(), "00:00");
        assertEquals(agentSupportDaysAndHours.getEndWorkTime(), "23:59");
        assertEquals(agentSupportDaysAndHours.getDays(), Days.getAllDays());
    }

    private static BusinessProfileWindow businessProfileWindow() {
        return getPortalTouchPreferencesPage().getBusinessProfileWindow();
    }
}
