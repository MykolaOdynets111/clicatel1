package steps;

import agent_side_pages.AgentHomePage;
import agent_side_pages.AgentLoginPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.testng.asserts.SoftAssert;
import touch_pages.pages.MainPage;
import touch_pages.pages.Widget;
import touch_pages.uielements.WidgetConversationArea;

public class DefaultTouchUserSteps {

    private MainPage mainPage = new MainPage();
    private Widget widget;
    WidgetConversationArea widgetConversationArea;


    @Given("^User select (.*) tenant$")
    public void selectTenant(String tenantName) {
        mainPage.selectTenant(tenantName);
    }

    @Given("^Click chat icon$")
    public void clickChatIcon() {
        widget = mainPage.openWidget();
    }

    @When("^User enter (.*) into widget input field$")
    public void enterText(String text) {
        widgetConversationArea = widget.getWidgetConversationArea();
        widgetConversationArea.waitForSalutation();
        widget.getWidgetFooter().enterMessage(text).sendMessage();
    }


    @Then("^User have to receive '(.*)' text response for his '(.*)' input$")
    public void verifyTextResponse(String expectedTextResponse, String userInput) {
        SoftAssert soft = new SoftAssert();
        widgetConversationArea = widget.getWidgetConversationArea();
        soft.assertTrue(widgetConversationArea.isTextResponseShownFor(userInput, 15),
                "No text response is shown on '"+userInput+"' user's input");
        soft.assertEquals(widgetConversationArea.getResponseTextOnUserInput(userInput), expectedTextResponse,
                "Incorrect text response is shown on '"+userInput+"' user's input");
        soft.assertAll();
    }

}
