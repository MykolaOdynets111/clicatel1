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
    private WidgetConversationArea widgetConversationArea;
    private WidgetHeader widgetHeader;
    private TouchActionsMenu touchActionsMenu;
    private WelcomeMessages welcomeMessages;

    @When("^User click close chat button$")
    public void closeWidget() {
        widget.clickCloseButton();
    }

    @Then("^Widget is collapsed$")
    public void verifyWidgetCollapsed() {
        Assert.assertTrue(widget.isWidgetCollapsed(), "Widget is not collapsed");
    }

    @Given("^User select (.*) tenant$")
    public void selectTenant(String tenantName) {
//        if(tenantName.equalsIgnoreCase("general bank demo") && ConfigManager.getEnv().equalsIgnoreCase("demo")){
//            tenantName="Standard Bank";
//        }
        if (tenantName.equalsIgnoreCase("General Bank Demo")){
            Tenants.setTenantUnderTest("generalbank");
            Tenants.setTenantUnderTestOrgName("General Bank Demo");
        }
        String clientID = getUserNameFromLocalStorage();
        ApiHelper.createUserProfile(Tenants.getTenantUnderTest(), clientID, "firstName", clientID);
        ApiHelper.createUserProfile(Tenants.getTenantUnderTest(), clientID, "email", "aqa"+clientID+"@gmail.com");
        getMainPage().selectTenant(tenantName);
    }

    @Given("^Click chat icon$")
    public void clickChatIcon() {
        widget = getMainPage().openWidget();
    }

    @Then("^Chat icon is not visible$")
    public void isChatIconIsNotVisible(){
        Assert.assertTrue(getMainPage().isChatIconHidden(), "Chat icon is visible");
    }

    @Then("^Chat icon is visible$")
    public void isChatIconIsVisible(){
        Assert.assertTrue(getMainPage().isChatIconShown(), "Chat icon is not visible");
    }

    @When("^User enter (.*) into widget input field$")
    public void enterText(String text) {
        widgetConversationArea = widget.getWidgetConversationArea();
        widgetConversationArea.waitForSalutation();
        widget.getWidgetFooter().enterMessage(text).sendMessage();
    }


    @Then("^User have to receive '(.*)' text response for his '(.*)' input$")
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
//        if (textResponse.contains("[FIRST_NAME]")) {
//            expectedTextResponse.replace("[FIRST_NAME]", getUserNameFromLocalStorage());
//        }
//        if (textResponse.contains("${firstName}")) {
//            expectedTextResponse.replace("${firstName}", getUserNameFromLocalStorage());
//        }
        SoftAssert softAssert = new SoftAssert();
        widgetConversationArea = widget.getWidgetConversationArea();
        softAssert.assertTrue(widgetConversationArea.isTextResponseShownFor(userInput, 10),
                "No text response is shown on '"+userInput+"' user's input (Client ID: "+getUserNameFromLocalStorage()+")");
        softAssert.assertTrue(widgetConversationArea.isOnlyOneTextResponseShownFor(userInput),
                "More than one text response is shown for user (Client ID: "+getUserNameFromLocalStorage()+")");
        softAssert.assertEquals(widgetConversationArea.getResponseTextOnUserInput(userInput), expectedTextResponse,
                "Incorrect text response is shown on '"+userInput+"' user's input (Client ID: "+getUserNameFromLocalStorage()+")");
        softAssert.assertAll();

    }

    @Then("^Card with a (?:button|buttons) (.*) is shown (?:on|after) user (.*) (?:message|input)$")
    public void isCardWithButtonShown(String buttonNames, String userMessage){
        List<String> buttons = Arrays.asList(buttonNames.split(";"));
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(widgetConversationArea.isCardShownFor(userMessage, 6),
                "Card is not show after '"+userMessage+"' user message (Client ID: "+getUserNameFromLocalStorage()+")");
        soft.assertTrue(widgetConversationArea.isCardButtonsShownFor(userMessage, buttons),
                buttons + " buttons are not shown in card (Client ID: "+getUserNameFromLocalStorage()+")");
        soft.assertAll();
    }

    @Then("^Card with a (.*) text is shown (?:on|after) user (.*) (?:message|input)$")
    public void isCardWithTextShown(String cardText, String userMessage){
        String expectedCardText = null;
        switch (cardText){
            case "welcome":
                expectedCardText = ApiHelper.getTenantMessageText("first_navigation_card_title");
                break;
            default:
                expectedCardText = cardText;

        }
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(widgetConversationArea.isCardShownFor(userMessage, 6),
                "Card is not show after '"+userMessage+"' user message (Client ID: "+getUserNameFromLocalStorage()+")");
        soft.assertEquals(widgetConversationArea.getCardTextForUserMessage(userMessage), expectedCardText,
                "Incorrect card text is shown. (Client ID: "+getUserNameFromLocalStorage()+")");
        soft.assertAll();
    }

    @When("^User click (.*) button in the card on user message (.*)$")
    public void clickButtonOnToUserCard(String buttonName, String userMessage) {
        widgetConversationArea.clickOptionInTheCard(userMessage, buttonName);
    }

    @Then("^\"End chat\" button is shown in widget's header$")
    public void isEndChatButtonShown() {
        widget.scrollABitToRevealHeaderButtons();
        Assert.assertTrue(getWidgetHeader().isEndChatButtonShown(5),
                "End chat button is not shown on widget header after 5 seconds wait");
    }

    @Then("^\"Start chat\" button is shown in widget's header$")
    public void isStartChatButtonShown() {
        widget.scrollABitToRevealHeaderButtons();
        Assert.assertTrue(getWidgetHeader().isStartChatButtonShown(5),
                "Start chat button is not shown on widget header after 5 seconds wait");
    }

    @When("^User click \"End chat\" button in widget's header$")
    public void clickEndChatButtonInHeader() {
        getWidgetHeader().clickEndChatButton();
    }

    @When("^User click \"Start chat\" button in widget's header$")
    public void clickStartChatButtonInHeader() {
        getWidgetHeader().clickStartChatButton();
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

    @Then("^Welcome message with correct text is shown$")
    public void verifyWelcomeTextMessage() {
        String welcomeMessage = ApiHelper.getTenantMessageText("welcome_message");
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(getWelcomeMessages().isWelcomeTextMessageShown(),
                "Welcome text message is not shown");
        soft.assertEquals(getWelcomeMessages().getWelcomeMessageText(), welcomeMessage,
                "Welcome message is not as expected");
        soft.assertAll();
    }


    @Then("^Welcome back message with correct text is shown after user's input '(.*)'$")
    public void verifyWelcomeBackTextMessage(String userMessage) {
        String welcomeBackMessage = ApiHelper.getTenantMessageText("welcome_back_message");
        Assert.assertTrue( widget.getWidgetConversationArea().isTextResponseShownAmongOtherForUserMessage(userMessage, welcomeBackMessage),
                "'"+ welcomeBackMessage + "' welcome back message is not shown");
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
