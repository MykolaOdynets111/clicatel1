package steps.agentsteps;


import agentpages.uielements.PageHeader;
import agentpages.uielements.ProfileWindow;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.SoftAssertions;

import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.testng.Assert.assertEquals;

public class AgentInfoSteps extends AbstractAgentSteps {

    @When("^I click icon with (.*) initials$")
    public void clickIconWithInitials(String agent) {
        getHeader(agent).clickIcon();
    }

    @When("^(.*) clicks \"Profile Settings\" button$")
    public void clickProfileSettingsButton(String agent) {
        getHeader(agent).clickProfileSettingsButton();
    }

    @When("^(.*) clicks \"Reset Password\" button$")
    public void clickResetPasswordButton(String agent) {
        getProfileWindow(agent).clickResetPasswordButton();
    }

    @Then("^(.*) views (.*) message in Profile Settings$")
    public void verifyResetPasswordSuccessMessage(String agent, String message) {
        assertEquals(getProfileWindow(agent).getResetPasswordSuccessMessage(),
                message, "Password reset success message does not match");
    }

    @Then("^(.*) views (.*) agent first name, (.*) last name and (.*) email in Profile Settings$")
    public void verifyAgentInfoProfileSettings(String agent, String firstName, String lastName, String email) {
        softAssert.assertEquals(getProfileWindow(agent).getFirstName(), firstName, "First name is incorrect");
        softAssert.assertEquals(getProfileWindow(agent).getLastName(), lastName, "Last name is incorrect");
        softAssert.assertEquals(getProfileWindow(agent).getMail(), email, "Email is incorrect");
        softAssert.assertAll();
    }

    @And("^(.*) should see (.*) icon in active chat header$")
    public void agentShouldSeeAppleChatIconInHeader(String agent, String adapter) {
        assertThat(getAgentHomePage(agent).getChatHeader().getChatIconName())
                .as(format("Image should have name %s", adapter))
                .isEqualTo(adapter);
    }

    @And("^(.*) verifies that personal data is not editable$")
    public void verifyPersonalDataIsNotEditable(String agent) {
        ProfileWindow profileWindow = getProfileWindow(agent);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(profileWindow.isFirstNameEditable()).as("First name should not be editable").isFalse();
        softly.assertThat(profileWindow.isLastNameEditable()).as("Last name should not be editable").isFalse();
        softly.assertThat(profileWindow.isMaleEditable()).as("Male should not be editable").isFalse();
        softly.assertAll();
    }

    private static PageHeader getHeader(String agent) {
        return getAgentHomePage(agent).getPageHeader();
    }

    private static ProfileWindow getProfileWindow(String agent) {
        return getAgentHomePage(agent).getProfileWindow();
    }
}
