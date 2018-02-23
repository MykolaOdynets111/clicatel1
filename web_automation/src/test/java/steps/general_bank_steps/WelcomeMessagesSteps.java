package steps.general_bank_steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.testng.asserts.SoftAssert;
import touch_pages.uielements.messages.WelcomeMessages;

public class WelcomeMessagesSteps {

    private WelcomeMessages welcomeMessages = new WelcomeMessages();

    @Given("^Welcome card with a button \"(.*)\" is shown$")
    public void verifyWelcomeCardWithCorrectTextIsShown(String buttonName) {
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(welcomeMessages.isWelcomeCardContainerShown(), "Welcome card is not shown");
        soft.assertTrue(welcomeMessages.getWelcomeCardButtonText().contains(buttonName),
                "Button is not shown in Welcome card");
        soft.assertAll();
    }

    @When("^User select (.*) option from Welcome card$")
    public void selectOptionInWelcomeCard(String butonName) {
        welcomeMessages.clickActionButton(butonName);
    }
}
