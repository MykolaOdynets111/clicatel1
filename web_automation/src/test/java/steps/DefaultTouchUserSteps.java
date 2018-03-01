package steps;

import api_helper.ApiHelper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dataprovider.Tenants;
import interfaces.JSHelper;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import touch_pages.pages.MainPage;
import touch_pages.pages.Widget;
import touch_pages.uielements.WidgetConversationArea;
import touch_pages.uielements.WidgetHeader;

import java.util.Arrays;
import java.util.List;

public class DefaultTouchUserSteps implements JSHelper{

    private MainPage mainPage = new MainPage();
    private Widget widgetForDefaultStep;
    WidgetConversationArea widgetConversationAreaDefaultStep;
    private WidgetHeader widgetHeader;


    @Given("^User select (.*) tenant$")
    public void selectTenant(String tenantName) {
//        if(tenantName.equalsIgnoreCase("general bank demo") && ConfigManager.getEnv().equalsIgnoreCase("demo")){
//            tenantName="Standard Bank";
//        }
        if (tenantName.equalsIgnoreCase("General Bank Demo")){
            Tenants.setTenantUnderTest("generalbank");
        }
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
        SoftAssert softAssert = new SoftAssert();
        widgetConversationAreaDefaultStep = widgetForDefaultStep.getWidgetConversationArea();
        softAssert.assertTrue(widgetConversationAreaDefaultStep.isTextResponseShownFor(userInput, 10),
                "No text response is shown on '"+userInput+"' user's input (Client ID: "+getUserNameFromLocalStorage()+")");
        softAssert.assertTrue(widgetConversationAreaDefaultStep.isOnlyOneTextResponseShwonFor(userInput),
                "More than one text response is shown for user (Client ID: "+getUserNameFromLocalStorage()+")");
        softAssert.assertEquals(widgetConversationAreaDefaultStep.getResponseTextOnUserInput(userInput), expectedTextResponse,
                "Incorrect text response is shown on '"+userInput+"' user's input (Client ID: "+getUserNameFromLocalStorage()+")");
        softAssert.assertAll();
    }

    @Then("^Card with a (?:button|buttons) (.*) is shown on user (.*) message$")
    public void isCardWithButtonShown(String buttonNames, String userMessage){
        List<String> buttons = Arrays.asList(buttonNames.split(";"));
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(widgetConversationAreaDefaultStep.isCardShownFor(userMessage, 6),
                "Card is not show after '"+userMessage+"' user message (Client ID: "+getUserNameFromLocalStorage()+")");
        soft.assertTrue(widgetConversationAreaDefaultStep.isCardButtonsShownFor(userMessage, buttons),
                buttons + " buttons are not shown in card (Client ID: "+getUserNameFromLocalStorage()+")");
        soft.assertAll();
    }

    @When("^User click (.*) button in the card on user message (.*)$")
    public void clickButtonOnToUserCard(String buttonName, String userMessage) {
        widgetConversationAreaDefaultStep.clickOptionInTheCard(userMessage, buttonName);
    }

    @Then("^\"End chat\" button is shown in widget's header$")
    public void isEndChatButtonShown() {
        widgetHeader = widgetForDefaultStep.getWidgetHeader();
        Assert.assertTrue(widgetHeader.isEndChatButtonShown(5),
                "End chat button is not shown on widget header after 5 seconds wait");
    }

    @Given("^User profile for (.*) is created$")
    public void createUserProfile(String tenantName){
        String clientID = getUserNameFromLocalStorage();
        ApiHelper.createUserProfile(tenantName, clientID, "firstName", clientID);
        ApiHelper.createUserProfile(tenantName, clientID, "email", "aqa_test@gmail.com");
    }

    @Then("^Widget is connected$")
    public void verifyIfWidgetIsConnected() {
//        Tenants.RESULT=false;
//        Assert.assertTrue(widgetForDefaultStep.isWidgetConnected(25), "Widget is not connected after 25 seconds wait");
        Assert.assertTrue(false);
    }
}
