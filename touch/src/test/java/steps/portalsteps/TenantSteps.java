package steps.portalsteps;

import apihelper.APIHelperTenant;
import datamanager.Tenants;
import io.cucumber.java.en.Then;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static steps.agentsteps.AbstractAgentSteps.getAgentHomePage;

public class TenantSteps extends AbstractPortalSteps {

    @Then("^Tenant logo is shown on Chat Desk$")
    public void verifyTenantLogoIsShownOnChatDesk() {
        assertThat(getAgentHomePage("main").getPageHeader().isTenantLogoShown())
                .as("Tenant logo should be shown on Chat Desk")
                .isTrue();
    }

    @Then("^Delete logo$")
    public void deleteLogo() {
        deleteLogoForTenant(Tenants.getTenantUnderTestOrgName());
    }

    private static void deleteLogoForTenant(String tenant) {
        APIHelperTenant.deleteTenantLogo(tenant);
        getAgentHomePage("main").getCurrentDriver().navigate().refresh();

        assertThat(getAgentHomePage("main").getPageHeader().isTenantLogoShown())
                .as("Tenant logo should not be shown on Chat Desk")
                .isFalse();
    }
}
