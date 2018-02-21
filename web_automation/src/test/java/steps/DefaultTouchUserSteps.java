package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import touch_pages.pages.MainPage;
import touch_pages.pages.Widget;
import touch_pages.uielements.WidgetConversationArea;

import java.util.Arrays;
import java.util.List;

public class DefaultTouchUserSteps {

    private MainPage mainPage = new MainPage();
    private Widget widgetForDefaultStep;
    WidgetConversationArea widgetConversationAreaDefaultStep;


    @Given("^User select (.*) tenant$")
    public void selectTenant(String tenantName) {
        mainPage.selectTenant(tenantName);
    }

    @Given("^Click chat icon$")
    public void clickChatIcon() {
        widgetForDefaultStep = mainPage.openWidget();
    }

    @When("^User enter (.*) into widget input field$")
    public void enterText(String text) {
        widgetConversationAreaDefaultStep = widgetForDefaultStep.getWidgetConversationArea();
        widgetConversationAreaDefaultStep.waitForSalutation();
        widgetForDefaultStep.getWidgetFooter().enterMessage(text).sendMessage();
    }


    @Then("^User have to receive '(.*)' text response for his '(.*)' input$")
    public void verifyTextResponse(String expectedTextResponse, String userInput) {
        SoftAssert soft = new SoftAssert();
        widgetConversationAreaDefaultStep = widgetForDefaultStep.getWidgetConversationArea();
        soft.assertTrue(widgetConversationAreaDefaultStep.isTextResponseShownFor(userInput, 15),
                "No text response is shown on '"+userInput+"' user's input");
        soft.assertEquals(widgetConversationAreaDefaultStep.getResponseTextOnUserInput(userInput), expectedTextResponse,
                "Incorrect text response is shown on '"+userInput+"' user's input");
        soft.assertAll();
    }

    @Then("^Card with a (?:button|buttons) (.*) is shown on user (.*) message$")
    public void isCardWithButtonShown(String buttonNames, String userMessage){
        List<String> buttons = Arrays.asList(buttonNames.split(";"));
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(widgetConversationAreaDefaultStep.isCardShownFor(userMessage, 6),
                "Card is not show after '"+userMessage+"' user message");
        soft.assertTrue(widgetConversationAreaDefaultStep.isCardButtonsShownFor(userMessage, buttons),
                buttons + " buttons are not shown in card.");
        soft.assertAll();
    }

}
