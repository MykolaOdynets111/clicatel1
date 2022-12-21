package steps.agentsteps;


import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.testng.Assert.assertEquals;

public class AgentInfoSteps extends AbstractAgentSteps {

    @When("^I click icon with (.*) initials$")
    public void clickIconWithInitials(String agent) {
        getAgentHomePage(agent).getPageHeader().clickIcon();
    }

    @When("^(.*) clicks \"Profile Settings\" button$")
    public void clickProfileSettingsButton(String agent) {
        getAgentHomePage(agent).getPageHeader().clickProfileSettingsButton();
    }

    @When("^(.*) clicks \"Reset Password\" button$")
    public void clickResetPasswordButton(String agent) {
        getAgentHomePage(agent).getProfileWindow().clickResetPasswordButton();
    }

    @Then("^(.*) views (.*) message in Profile Settings$")
    public void verifyResetPasswordSuccessMessage(String agent, String message) {
        assertEquals(getAgentHomePage(agent).getProfileWindow().getResetPasswordSuccessMessage(),
                message, "Password reset success message does not match");
    }

    @Then("^(.*) views (.*) agent first name, (.*) last name and (.*) email in Profile Settings$")
    public void verifyAgentInfoProfileSettings(String agent, String firstName, String lastName, String email) {
        softAssert.assertEquals(getAgentHomePage(agent).getProfileWindow().getFirstName(),
                firstName, "First name is incorrect");

        softAssert.assertEquals(getAgentHomePage(agent).getProfileWindow().getLastName(),
                lastName, "Last name is incorrect");

        softAssert.assertEquals(getAgentHomePage(agent).getProfileWindow().getMail(),
                email, "Email is incorrect");

        softAssert.assertAll();
    }

    @And("^(.*) should see (.*) icon in active chat header$")
    public void agentShouldSeeAppleChatIconInHeader(String agent, String adapter) {
        assertThat(getAgentHomePage(agent).getChatHeader().getChatIconName())
                .as(format("Image should have name %s", adapter))
                .isEqualTo(adapter);
    }
}
