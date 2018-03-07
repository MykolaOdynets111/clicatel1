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
import touch_pages.uielements.TouchActionsMenu;
import touch_pages.uielements.WidgetConversationArea;
import touch_pages.uielements.WidgetHeader;
import touch_pages.uielements.messages.WelcomeMessages;

import java.util.Arrays;
import java.util.List;

public class DefaultTouchUserSteps implements JSHelper{

    private MainPage mainPage;
    private Widget widget;
    private WidgetConversationArea widgetConversationAreaDefaultStep;
    private WidgetHeader widgetHeader;
    private TouchActionsMenu touchActionsMenu;
    private WelcomeMessages welcomeMessages;


    @Given("^User select (.*) tenant$")
    public void selectTenant(String tenantName) {
//        if(tenantName.equalsIgnoreCase("general bank demo") && ConfigManager.getEnv().equalsIgnoreCase("demo")){
//            tenantName="Standard Bank";
//        }
        if (tenantName.equalsIgnoreCase("General Bank Demo")){
            Tenants.setTenantUnderTest("generalbank");
            Tenants.setTenantUnderTestOrgName("General Bank Demo");
        }
        getMainPage().selectTenant(tenantName);
    }

    @Given("^Click chat icon$")
    public void clickChatIcon() {
        widget = getMainPage().openWidget();
    }

    @When("^User enter (.*) into widget input field$")
    public void enterText(String text) {
        widgetConversationAreaDefaultStep = widget.getWidgetConversationArea();
        widgetConversationAreaDefaultStep.waitForSalutation();
        widget.getWidgetFooter().enterMessage(text).sendMessage();
    }


    @Then("^User have to receive ('(.*)'|(.*)) text response for his '(.*)' input$")
    public void verifyTextResponse(String textResponse, String userInput) {
        String expectedTextResponse = null;
        switch (textResponse) {
            case "start new conversation":
                expectedTextResponse = ApiHelper.getTenantMessageText("start_new_conversation");
                break;
            case "welcome back message":
                expectedTextResponse = ApiHelper.getTenantMessageText("welcome_back_message");
                break;
            default:
                expectedTextResponse = textResponse;
                break;
        }
        SoftAssert softAssert = new SoftAssert();
        widgetConversationAreaDefaultStep = widget.getWidgetConversationArea();
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
        widget.scrollABitToRevealHeaderButtons();
        Assert.assertTrue(getWidgetHeader().isEndChatButtonShown(5),
                "End chat button is not shown on widget header after 5 seconds wait");
    }

    @When("^User click \"End chat\" button in widget's header$")
    public void clickEndChatButtonInHeader() {
        getWidgetHeader().clickEndChatButton();
    }

    @Given("^User profile for (.*) is created$")
    public void createUserProfile(String tenantName){
        String clientID = getUserNameFromLocalStorage();
        ApiHelper.createUserProfile(tenantName, clientID, "firstName", clientID);
        ApiHelper.createUserProfile(tenantName, clientID, "email", "aqa_test@gmail.com");
    }

    @Then("^Widget is connected$")
    public void verifyIfWidgetIsConnected() {
        Assert.assertTrue(widget.isWidgetConnected(25), "Widget is not connected after 25 seconds wait");

    }

    @Then("^User sees name of tenant: (.*) and its short description in the header$")
    public void verifyWidgetHeader(String tenantName){
        String expectedDescription = Tenants.getTenantInfo(tenantName, "shortDescription");
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(getWidgetHeader().getDisplayedTenantName(), tenantName,
                tenantName + " tenant name is not shown in the widget header");
        soft.assertEquals(getWidgetHeader().getDisplayedTenantDescription(), expectedDescription,
                expectedDescription + " description is not shown for " +tenantName+ " tenant");
        soft.assertAll();
    }

    // ======================== Touch Actions Steps ======================== //

    @When("^User click Touch button$")
    public  void clickTouchButton() {
        widget.clickTouchButton();
    }

    @Then("^\"(.*)\" is shown in touch menu$")
    public void isTouchMenuActionShown(String action) {
        Assert.assertTrue(getTouchActionsMenu().isTouchActionShown(action),
                "Touch action '"+action+"' is not shown in Touch menu");
    }

    @When("^User select \"(.*)\" from touch menu$")
    public void selectActionFromTouchMenu(String action) {
        getTouchActionsMenu().selectTouchAction(action);
    }

    // ======================== Welcome Card Steps ======================= //

    @Given("^Welcome card with a button \"(.*)\" is shown$")
    public void verifyWelcomeCardWithCorrectButtonIsShown(String buttonName) {
        SoftAssert soft = new SoftAssert();
        soft.assertTrue( getWelcomeMessages().isWelcomeCardContainerShown(), "Welcome card is not shown. Client ID: "+getUserNameFromLocalStorage()+"");
        soft.assertTrue( getWelcomeMessages().getWelcomeCardButtonText().contains(buttonName),
                "Button is not shown in Welcome card (Client ID: "+getUserNameFromLocalStorage()+")");
        soft.assertAll();
    }

    @When("^User select (.*) option from Welcome card$")
    public void selectOptionInWelcomeCard(String butonName) {
        getWelcomeMessages().clickActionButton(butonName);
    }

    @Then("^Welcome message with correct text is shown is shown$")
    public void verifyWelcomeTextMessage() {
        String welcomeMessage = ApiHelper.getTenantMessageText("welcome_message");
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(getWelcomeMessages().isWelcomeTextMessageShown(),
                "Welcome text message is not shown");
        soft.assertEquals(getWelcomeMessages().getWelcomeMessageText(), welcomeMessage,
                "Welcome message is not as expected");
        soft.assertAll();
    }

    @Given("^Welcome card with correct text and button \"(.*)\" is shown$")
    public void verifyWelcomeCardWithCorrectTextAndButtonIsShown(String buttonName) {
        String welcomeCardText = ApiHelper.getTenantMessageText("first_navigation_card_title");
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(getWelcomeMessages().isWelcomeCardContainerShown(), "Welcome card is not shown. Client ID: "+getUserNameFromLocalStorage()+"");
        soft.assertEquals(getWelcomeMessages().getWelcomeCardText(), welcomeCardText,
                "Text in Welcome card is not as expected");
        soft.assertTrue( getWelcomeMessages().getWelcomeCardButtonText().contains(buttonName),
                "Button is not shown in Welcome card (Client ID: "+getUserNameFromLocalStorage()+")");
        soft.assertAll();
    }

    // ======================= Private Getters ========================== //

    private WelcomeMessages getWelcomeMessages() {
        if (welcomeMessages==null) {
            welcomeMessages = new WelcomeMessages();
            return  welcomeMessages;
        } else{
            return welcomeMessages;
        }
    }

    private MainPage getMainPage() {
        if (mainPage==null) {
            mainPage = new MainPage();
            return mainPage;
        } else{
            return mainPage;
        }
    }

    private TouchActionsMenu getTouchActionsMenu() {
        if (touchActionsMenu==null) {
            touchActionsMenu = widget.getTouchActionsMenu();
            return touchActionsMenu;
        } else{
            return touchActionsMenu;
        }
    }

    private WidgetHeader getWidgetHeader() {
        if (widgetHeader==null) {
            widgetHeader = widget.getWidgetHeader();
            return widgetHeader;
        } else{
            return widgetHeader;
        }
    }
}
