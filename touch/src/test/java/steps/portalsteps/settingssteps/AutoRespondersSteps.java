package steps.portalsteps.settingssteps;

import apihelper.ApiHelper;
import datamanager.enums.AutoRespondersMessage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import portaluielem.AutoRespondersWindow;
import steps.portalsteps.AbstractPortalSteps;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AutoRespondersSteps extends AbstractPortalSteps {

    @When("^Agent click expand arrow for (.*) auto responder$")
    public void clickExpandArrowForAutoResponder(String autoresponder) {
        autoRespondersWindow().clickExpandArrowForMessage(autoresponder);
    }

    @When("^Click \"Reset to default\" button for (.*) auto responder$")
    public void clickResetToDefaultButton(String autoresponder) {
        autoRespondersWindow().clickResetToDefaultForMessage(autoresponder);
        getPortalTouchPreferencesPage().waitWhileProcessing(8, 20);
    }

    @When("^Admin edit the text for (.*) auto responder$")
    public void clearTextForAutoResponder(String autoresponder) {
        autoRespondersWindow().inputTextMessage(autoresponder);
    }

    @When("^Admin click save button for (.*) auto responder$")
    public void clickSaveButtonForAutoResponder(String autoresponder) {
        autoRespondersWindow().clickSaveButton(autoresponder);
        getPortalTouchPreferencesPage().waitWhileProcessing(14, 20);
    }

    @Then("^The (.*) Auto Responder message was reset again$")
    public void verifyAutoResponderMessageWasReset(String messageTitle) {
        String actualMessage = ApiHelper.getAutoResponderMessageText(messageTitle);
        String defaultMessage = AutoRespondersMessage.getMessageByTitle(messageTitle);
        assertThat(actualMessage)
                .as("Message should be default")
                .isEqualTo(defaultMessage);
    }

    private static AutoRespondersWindow autoRespondersWindow() {
        return getPortalTouchPreferencesPage().getAutoRespondersWindow();
    }
}
