package steps;

import api_helper.ApiHelper;
import api_helper.ApiHelperTie;
import com.github.javafaker.Faker;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dataManager.Tenants;
import dataManager.VMQuoteRequestUserData;
import driverManager.DriverFactory;
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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultTouchUserSteps implements JSHelper{

    private MainPage mainPage;
    private Widget widget;
    private WidgetConversationArea widgetConversationArea;
    private WidgetHeader widgetHeader;
    private TouchActionsMenu touchActionsMenu;
    private WelcomeMessages welcomeMessages;
    private static Map<Long, VMQuoteRequestUserData> userDataForQuoteRequest = new ConcurrentHashMap<>();

    public static VMQuoteRequestUserData getUserDataForQuoteRequest(long threadID){
        return userDataForQuoteRequest.get(threadID);
    }

    @When("^User click close chat button$")
    public void closeWidget() {
        widget.clickCloseButton();
    }

    @Then("^Widget is collapsed$")
    public void verifyWidgetCollapsed() {
        Assert.assertTrue(widget.isWidgetCollapsed(), "Widget is not collapsed");
    }

    @Given("^User select (.*) tenant$")
    public void selectTenant(String tenantOrgName) {
        Tenants.setTenantUnderTestNames(tenantOrgName);
        String clientID = getUserNameFromLocalStorage();
        ApiHelper.createUserProfile(Tenants.getTenantUnderTest(), clientID, "firstName", clientID);
        ApiHelper.createUserProfile(Tenants.getTenantUnderTest(), clientID, "email", "aqa"+clientID+"@gmail.com");
        getMainPage().selectTenant(tenantOrgName);
    }


    @Given("^Click chat icon$")
    public void clickChatIcon() {
        widget = getMainPage().openWidget();
    }

    @Then("^Chat icon is not visible$")
    public void isChatIconIsNotVisible(){
        Assert.assertFalse(getMainPage().isChatIconShown(), "Chat icon is visible.");
    }

    @Then("^Chat icon is visible$")
    public void isChatIconIsVisible(){
        Assert.assertTrue(getMainPage().isChatIconShown(), "Chat icon is not visible.");
    }

    @When("^User enter (.*) into widget input field$")
    public void enterText(String text) {
        widgetConversationArea = widget.getWidgetConversationArea();
        widgetConversationArea.waitForSalutation();
        widget.getWidgetFooter().enterMessage(text).sendMessage();
        widgetConversationArea.waitForMessageToAppearInWidget(text);
    }

    @When("^User enters message regarding (.*) into widget input field$")
    public void enterUniqueText(String text) {
        widgetConversationArea = widget.getWidgetConversationArea();
        widgetConversationArea.waitForSalutation();
        widget.getWidgetFooter().enterMessage(FacebookSteps.createUniqueUserMessage(text)).sendMessage();
        widgetConversationArea.waitForMessageToAppearInWidget(FacebookSteps.getCurrentUserMessageText());
    }

    @Then("^User have to receive '(.*)' text response for his '(.*)' input$")
    public void verifyResponse(String textResponse, String userInput) {
        int waitForResponse=45;
        String expectedTextResponse = formExpectedTextResponseForBotWidget(textResponse);
        verifyTextResponse(userInput, expectedTextResponse, waitForResponse);
    }

    @Then("^User have to receive '(.*)' text response for his question regarding (.*)$")
    public void verifyResponseOnUniqueMessage(String textResponse, String userInput) {
        int waitForResponse=15;
        String expectedTextResponse = formExpectedTextResponseForBotWidget(textResponse);
        verifyTextResponse(FacebookSteps.getCurrentUserMessageText(), expectedTextResponse, waitForResponse);
    }

    /**Method for verifying TIE response in widget on user's message
     * Method is designed to respect TIE mod for tenant under test
     * If TIE is in semi autonomus mode we expect ONLY text response from TIE
     * If TIE is in autonomus mode, we take into account choice card
     * @param textResponse
     * @param userInput
     * @param intent - intent should be passed as it will be shown in the widget (with upper case letters where they should be)
     */
    @Then("^User have to receive '(.*)' text response with (.*) intent for his '(.*)' input$")
    public void verifyTextResponseWithIntent(String textResponse, String intent, String userInput){
        int waitForResponse=15;
        String expectedTextResponse = formExpectedTextResponseForBotWidget(textResponse);
        boolean isTextResponseShown= widgetConversationArea.isTextResponseShownFor(userInput, waitForResponse);

//      ToDo: As soon as there is an API to check the TIE mode implement the following logic
//        String tenantTIEMode = ApiHelperTie.getTIEModeForTenant(Tenants.getTenantUnderTestOrgName()).equals("automomus")
//        if(!isTextResponseShown & tenantTIEMode.equals("autonomus"))
        if (!isTextResponseShown & Tenants.getTenantUnderTestOrgName().equalsIgnoreCase("Virgin Money")){
            verifyTextResponseAfterInteractionWithChoiceCard(userInput, expectedTextResponse, intent, waitForResponse);
        } else{
            verifyTextResponse(userInput, expectedTextResponse, waitForResponse);
        }
    }

    private void verifyTextResponseAfterInteractionWithChoiceCard(String userInput, String expectedTextResponse, String intent, int waitForResponse){
        widgetConversationArea = widget.getWidgetConversationArea();
        if(!widgetConversationArea.isCardShownFor(userInput, 15)){
            Assert.assertTrue(false, "Neither plain text, nor choice card is shown on user's input "+userInput);
        }
        int intentsCount=ApiHelperTie.getListOfIntentsOnUserMessage(userInput).size();

        //if tie returns more than 1 intent then choice card should be shown.
        // And we are verifying that expected intent is among the choice options
        if(intentsCount>1){
            if (!widgetConversationArea.isCardContainsButton(userInput, intent)){
                Assert.assertTrue(false, "Intent '"+intent+"' is not shown in choice card on '"+userInput+"' user input");

            }
            widgetConversationArea.clickOptionInTheCard(userInput, intent);
            verifyTextResponse(intent, expectedTextResponse, 15);
        }

        // if tie returns 1 intent and we have card shown then we are verifying that it is
        // confirmation card with correct intent
        else{
            double configConfidenceThreshold = Double.valueOf(ApiHelperTie.getTenantConfig(Tenants.getTenantUnderTest(), "intent_confidence_threshold.high_confident"));
            double confidenceOnUserMessage = ApiHelperTie.getIntentConfidenceOnUserMessage(userInput);
            if(confidenceOnUserMessage<configConfidenceThreshold){
                String textInCard = widgetConversationArea.getCardTextForUserMessage(userInput);
                String expectedText = "Do you mean \""+intent + "\"?";
                if(!expectedText.equals(textInCard)){
                    Assert.assertTrue(false, "Card text on user message '"+userInput+"' is not as expected.\n" +
                            "Expected: " + expectedText + "\n" +
                            "But found: " + textInCard + "\n");
                }
                widgetConversationArea.clickOptionInTheCard(userInput, "Yes");
                verifyTextResponse("Yes", expectedTextResponse, 15);


            } else{
                Assert.assertTrue(false, "Unexpected card is shown as a response on '"+userInput+"' user message.");
            }
        }

    }

    private void verifyTextResponse(String userInput, String expectedTextResponse, int waitForResponse){
        SoftAssert softAssert = new SoftAssert();
        widgetConversationArea = widget.getWidgetConversationArea();
        softAssert.assertTrue(widgetConversationArea.isTextResponseShownFor(userInput, waitForResponse),
                "No text response is shown on '"+userInput+"' user's input (Client ID: "+getUserNameFromLocalStorage()+")");
        softAssert.assertTrue(widgetConversationArea.isOnlyOneTextResponseShownFor(userInput),
                "More than one text response is shown for user (Client ID: "+getUserNameFromLocalStorage()+")");
        String actualCardText = widgetConversationArea.getResponseTextOnUserInput(userInput);
        System.out.println("actualCardText "+ actualCardText);
        if (!expectedTextResponse.contains("\\n")) actualCardText.replace("\n", "");
        else expectedTextResponse = expectedTextResponse.replace("\\n", "\n");
        softAssert.assertEquals(actualCardText, expectedTextResponse,
                "Incorrect text response is shown on '"+userInput+"' user's input (Client ID: "+getUserNameFromLocalStorage()+")");
        softAssert.assertAll();
    }

    private String formExpectedTextResponseForBotWidget(String fromFeatureText){
        String expectedTextResponse;
        switch (fromFeatureText) {
            case "start new conversation":
                expectedTextResponse = ApiHelper.getTenantMessageText("start_new_conversation");
                break;
            case "welcome back message":
                expectedTextResponse = ApiHelper.getTenantMessageText("welcome_back_message");
                break;
            case "dynamical branch address":
                expectedTextResponse = Tenants.getTenantBranchLocationAddress(Tenants.getTenantUnderTest());
                break;
            case "exit":
                expectedTextResponse = ApiHelper.getTenantMessageText("start_new_conversation");
                break;
            case "agents_away":
                expectedTextResponse = ApiHelper.getTenantMessageText("agents_away");
                break;
            default:
                expectedTextResponse = fromFeatureText;
                break;
        }
        if (fromFeatureText.contains("${firstName}")) {
            expectedTextResponse = expectedTextResponse.replace("${firstName}", getUserNameFromLocalStorage());
        }
        return expectedTextResponse;
    }

    @Then("^User have to receive '(.*)' text response as a second response for his '(.*)' input$")
    public void verifySecondTextResponse(String textResponse, String userInput) {
        int waitForResponse=10;
        String expectedTextResponse;
        switch (textResponse) {
            case "start new conversation":
                expectedTextResponse = ApiHelper.getTenantMessageText("start_new_conversation");
                break;
            case "welcome back message":
                expectedTextResponse = ApiHelper.getTenantMessageText("welcome_back_message");
                break;
            case "dynamical branch address":
                expectedTextResponse = Tenants.getTenantBranchLocationAddress(Tenants.getTenantUnderTest());
                break;
            case "exit":
                expectedTextResponse = ApiHelper.getTenantMessageText("start_new_conversation");
                break;
            case "agents_away":
                waitForResponse = 60;
                expectedTextResponse = ApiHelper.getTenantMessageText("agents_away");
                break;
            default:
                expectedTextResponse = textResponse;
                break;
        }
        if (textResponse.contains("${firstName}")) {
            expectedTextResponse = expectedTextResponse.replace("${firstName}", getUserNameFromLocalStorage());
        }
        SoftAssert softAssert = new SoftAssert();
        widgetConversationArea = widget.getWidgetConversationArea();
        softAssert.assertTrue(widgetConversationArea.isTextResponseShownFor(userInput, waitForResponse),
                "No text response is shown on '"+userInput+"' user's input (Client ID: "+getUserNameFromLocalStorage()+")");
        softAssert.assertEquals(widgetConversationArea.getSecondResponseTextOnUserInput(userInput).replace("\n", "")
                , expectedTextResponse,
                "Incorrect text response is shown on '"+userInput+"' user's input (Client ID: "+getUserNameFromLocalStorage()+")");
        softAssert.assertAll();

    }

    @Then("^Text response that contains \"(.*)\" is shown$")
    public void quickVerifyIsResponseShown(String text){
       Assert.assertTrue(widget.getWidgetConversationArea().isTextShown(text, 15),
               "Bot response is not shown");
    }

    @Then("^Card with a (?:button|buttons) (.*) is shown (?:on|after) user (.*) (?:message|input)$")
    public void isCardWithButtonShown(String buttonNames, String userMessage){
        if (userMessage.equalsIgnoreCase("personal info")){
            userMessage = userDataForQuoteRequest.get(Thread.currentThread().getId()).getWidgetPresentationOfPersonalInfoInput();
        }
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
        if (userMessage.equalsIgnoreCase("personal info")){
            userMessage = userDataForQuoteRequest.get(Thread.currentThread().getId()).getWidgetPresentationOfPersonalInfoInput();
        }
        String expectedCardText;
        switch (cardText){
            case "welcome":
                expectedCardText = ApiHelper.getTenantMessageText("first_navigation_card_title");
                break;
            default:
                expectedCardText = cardText;

        }
        if (cardText.contains("${firstName}")) {
            expectedCardText = cardText.replace("${firstName}", getUserNameFromLocalStorage());
        }
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(widgetConversationArea.isCardShownFor(userMessage, 15),
                "Card is not show after '"+userMessage+"' user message (Client ID: "+getUserNameFromLocalStorage()+")");
        soft.assertEquals(widgetConversationArea.getCardTextForUserMessage(userMessage), expectedCardText,
                "Incorrect card text is shown. (Client ID: "+getUserNameFromLocalStorage()+")");
        soft.assertAll();
    }

    @Then("^Text '(.*)' is shown above the buttons in the card on user (.*) input above$")
    public void verifyTextShownInTheCardAboveButtons(String expectedText, String userMessage){
        Assert.assertEquals(widgetConversationArea.getCardAboveButtonsTextForUserMessage(userMessage), expectedText,
                "Incorrect card text is shown. (Client ID: "+getUserNameFromLocalStorage()+")");
    }

    @Then("^No (?:additional card|card) should be shown (?:on|after) user (.*) (?:message|input)$")
    public void verifyNoCardIsShown(String userMessage){
        Assert.assertTrue(widgetConversationArea.isCardNotShownFor(userMessage, 6),
                "Unexpected Card is show after '"+userMessage+"' user message (Client ID: "+getUserNameFromLocalStorage()+")");
    }

    @When("^User click (.*) button in the card on user message (.*)$")
    public void clickButtonOnToUserCard(String buttonName, String userMessage) {
        if (userMessage.equalsIgnoreCase("personal info")){
            userMessage = userDataForQuoteRequest.get(Thread.currentThread().getId()).getWidgetPresentationOfPersonalInfoInput();
        }
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

    @Then("^User session is ended$")
    public void verifyUserSessionEnded(){
        boolean result = false;
        for(int i = 0; i<15; i++){
            result =  Tenants.getLastUserSessionStatus(getUserNameFromLocalStorage())
                    .equalsIgnoreCase("terminated");
            if(result){
                break;
            } else{
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
       Assert.assertTrue(result, "Session with id "+ApiHelper.getLastUserSession(getUserNameFromLocalStorage(), Tenants.getTenantUnderTest()).getSessionId() +
                                            "is not terminated for user: " +getUserNameFromLocalStorage() +
                                                " after bot response.");
    }

    @Then("^User session is created")
    public void verifyUserSessionCreated(){
        boolean result = false;
        for(int i = 0; i<6; i++){
            result =  Tenants.getLastUserSessionStatus(getUserNameFromLocalStorage())
                    .equalsIgnoreCase("active");
            if(result){
                break;
            } else{
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        Assert.assertTrue(result, "Session with id "+ApiHelper.getLastUserSession(getUserNameFromLocalStorage(), Tenants.getTenantUnderTest()).getSessionId() +
                "is not created for user: " +getUserNameFromLocalStorage() +
                " after bot response.");
    }

    @When("^User submit card with personal information after user's message: (.*)$")
    public void fillInCardForVMQuoteRequestFlow(String userMessage){
        createQuoteRequestUserDataAndAddToTheMap();
        widgetConversationArea.submitCardWithUserInfo(userMessage, getUserDataForQuoteRequest(Thread.currentThread().getId()));
    }

    @When("^User click 'Submit' button in the card after user message: (.*)$")
    public void clickSubmitButton(String userMessage){
        widgetConversationArea.clickSubmitButton(userMessage);
      }

    @When("^(.*) field required (?:errors|error) is shown in personal info input card on user message: (.*)$")
    public void verifyFieldRequiredErrors(int numberOfErrors, String userMessage){
       Assert.assertEquals(widgetConversationArea.getNumberOfFieldRequiredErrorsInCardOnUserMessage(userMessage), numberOfErrors,
               "Number of required filed errors is not as expected.");
    }

    @When("^User fill in field (.*) with '(.*)' in card on user message: (.*)$")
    public void clickSubmitButton(String fieldName, String fieldValue, String userMessage){
        widgetConversationArea.fillInTheField(userMessage, fieldName, fieldValue);
    }

    @Then("^No required field errors are shown in card on user message (.*)$")
    public void verifyRequiredFieldErrorsNotShown(String userMessage){
        Assert.assertFalse(widgetConversationArea.areFieldRequiredErrorsInCardOnUserMessageShown(userMessage),
                "Errors about required fields are still shown");
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

    // ========================== Chat history Steps ========================= //

    @When("^User refreshes the page$")
    public void refreshThePage() {
        DriverFactory.getTouchDriverInstance().navigate().refresh();
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

    private void createQuoteRequestUserDataAndAddToTheMap(){
        Faker faker = new Faker();
        userDataForQuoteRequest.put(Thread.currentThread().getId(), new VMQuoteRequestUserData(
                getUserNameFromLocalStorage(),                                              // first name
                "AQA",                                                             // last name
                String.valueOf(faker.number().randomNumber(6, false)),   // contact phone
                "aqa_"+System.currentTimeMillis()+"@aqa.com"                          // email
        ));
    }

}
