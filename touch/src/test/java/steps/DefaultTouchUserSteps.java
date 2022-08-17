package steps;

import agentpages.AgentHomePage;
import agentpages.uielements.ChatAttachment;
import apihelper.ApiHelper;
import apihelper.ApiHelperTie;
import com.github.javafaker.Faker;
import com.google.common.io.Files;
import datamanager.Tenants;
import datamanager.VMQuoteRequestUserData;
import datamanager.jacksonschemas.tie.TIEIntentPerCategory;
import driverfactory.DriverFactory;
import drivermanager.ConfigManager;
import interfaces.JSHelper;
import interfaces.VerificationHelper;
import interfaces.WebWait;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import steps.agentsteps.AgentConversationSteps;
import steps.portalsteps.BasePortalSteps;
import steps.portalsteps.SurveyManagementSteps;
import touchpages.pages.MainPage;
import touchpages.pages.Widget;
import touchpages.uielements.SurveyForm;
import touchpages.uielements.TouchActionsMenu;
import touchpages.uielements.WidgetConversationArea;
import touchpages.uielements.WidgetHeader;
import touchpages.uielements.messages.WelcomeMessages;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class DefaultTouchUserSteps implements JSHelper, VerificationHelper, WebWait {

    private MainPage mainPage;
    private Widget widget;
    private WidgetConversationArea widgetConversationArea;
    private SurveyForm surveyForm;
    private WidgetHeader widgetHeader;
    private TouchActionsMenu touchActionsMenu;
    private WelcomeMessages welcomeMessages;
    private static Map<Long, VMQuoteRequestUserData> userDataForQuoteRequest = new ConcurrentHashMap<>();
    private static ThreadLocal<String> enteredUserMessageInTouchWidget = new ThreadLocal<>();
    private static Map selectedClient;
    public static ThreadLocal<String> mediaFileName = new ThreadLocal<>();

    public static VMQuoteRequestUserData getUserDataForQuoteRequest(long threadID) {
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

    @Given("^Widget for (.*) is turned on$")
    public void turnOnWidget(String tenantOrgName) {
//        ApiHelper.setWidgetVisibilityDaysAndHours(tenantOrgName, "all week", "00:00", "23:59");
        ApiHelper.setAvailableForAllTerritories(tenantOrgName);
    }

    @Given("^User (?:select|opens) (.*) (?:tenant|tenant page)$")
    public void openTenantPage(String tenantOrgName) {
        if (ConfigManager.isWebWidget()) {
            Tenants.setTenantUnderTestNames(tenantOrgName);
            Tenants.checkWidgetConnectionStatus();
            DriverFactory.openUrl(tenantOrgName);
            String clientID = getClientIdFromLocalStorage();
            ApiHelper.createUserProfile(clientID);
        } else {
//            ORCASteps orca = new ORCASteps();
//            orca.createOrUpdateOrcaIntegration(tenantOrgName);
        }
    }

    @Given("(.*) survey configuration for (.*)")
    public static void switchSurveyConfiguration(String action, String tenantOrgName) {
        if (action.equalsIgnoreCase("On")) {
            ApiHelper.ratingEnabling(tenantOrgName, true, "webchat");
        } else if (action.equalsIgnoreCase("Off")) {
            ApiHelper.ratingEnabling(tenantOrgName, false, "webchat");
        }
    }

    @Given("^User with phone number (?:select|opens) (.*) (?:tenant|tenant page)$")
    public void openTenantPageAsUserWithPhone(String tenantOrgName) {
        Tenants.setTenantUnderTestNames(tenantOrgName);
        Tenants.checkWidgetConnectionStatus();
        DriverFactory.openUrl(tenantOrgName);
        String clientID = getClientIdFromLocalStorage();
        String phoneNumber = generateUSCellPhoneNumber();
        ApiHelper.createUserProfileWithPhone(clientID, phoneNumber);
    }

    private String getClientIdFromLocalStorage() {
        JavascriptExecutor jsExec = (JavascriptExecutor) DriverFactory.getTouchDriverInstance();
        String clientId = getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance());
        if (clientId == null || clientId.equals("")) {
            try {
                Thread.sleep(1100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clientId = (String) jsExec.executeScript("return window.localStorage.getItem('ctlUsername');");

            if (clientId == null || clientId.equals("")) {
                Assert.fail("Client id was not saved in local storage after 1.1 seconds wait \n");
            }
        }
        return clientId;
    }


    @Given("^User open new (.*) tenant$")
    public void openNewTenantPage(String tenantOrgName) {
        //     Tenants.setTenantUnderTestNames(tenantOrgName);
        //     Tenants.checkWidgetConnectionStatus();
        DriverFactory.openUrl(tenantOrgName);
        String clientID = getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance());
        ApiHelper.createUserProfile(clientID);
    }

    @Given("^User (?:select|opens) (.*) (?:tenant|tenant page) without creating profile$")
    public void openTenantPageWithoutCreatingUserProfile(String tenantOrgName) {
        Tenants.setTenantUnderTestNames(tenantOrgName);
        Tenants.checkWidgetConnectionStatus();
        DriverFactory.openUrl(tenantOrgName);
        String clientID = getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance());
//        getMainPage().openTenantPage(tenantOrgName);
    }

    @Given("^User opens page with desired tenant widget$")
    public DefaultTouchUserSteps openPageForDynamicallyPassedTenant() {
        DriverFactory.openUrlForDynamicTenant();
        return this;
    }

    @Given("^User opens (.*) tenant page for user (.*)$")
    public void openTenantPage(String tenantOrgName, String clientID) {
        if (clientID.equalsIgnoreCase("with history")) clientID = getClientWithHistory();
        DriverFactory.openTouchUrlWithPredifinedUserID(tenantOrgName, clientID);
        Tenants.setTenantUnderTestNames(tenantOrgName);
        ApiHelper.createUserProfile(clientID);
    }

    private String getClientWithHistory() {
        int page = 1;
        Response resp = ApiHelper.getFinishedChatsByLoggedInAgentAgent(Tenants.getTenantUnderTestOrgName(), page, 15);
        boolean lastPage = resp.jsonPath().getBoolean("last");
        if (resp.statusCode() != 200) {
            Assert.fail("Getting finished chats was not successful\n" +
                    "statusCode: " + resp.statusCode() + "\n" +
                    "errorMessage: " + resp.getBody().asString());
        }
        ZoneId zoneId = TimeZone.getDefault().toZoneId();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        LocalDateTime nowMinusTwoDays = LocalDateTime.now(zoneId).minusDays(2).minusHours(2);

        while (!lastPage) {
            try {
                selectedClient = resp.getBody().jsonPath().getList("content.sessions").stream()
                        .map(sessionContainer -> ((ArrayList) sessionContainer))
                        .filter(e -> e.size() == 1)
                        .map(session -> ((HashMap) session.get(0)))
                        .filter(session -> LocalDateTime.parse((String) session.get("endedDate"), formatter).isBefore(nowMinusTwoDays))
                        .findAny()
                        .get();
                break;
            } catch (NoSuchElementException e) {
                page += 1;
                resp = ApiHelper.getFinishedChatsByLoggedInAgentAgent(Tenants.getTenantUnderTestOrgName(), page, 15);
                lastPage = resp.jsonPath().getBoolean("last");

            }
        }

//    return "testing_2155727w7";
        return (String) selectedClient.get("clientId");
    }


    @Given("^Click chat icon$")
    public DefaultTouchUserSteps clickChatIcon() {
        widget = getMainPage().openWidget();
        widgetConversationArea = widget.getWidgetConversationArea();
        widgetConversationArea.waitForSalutation();
        return this;
    }

    @Then("^I check primary color for tenant in opened widget$")
    public void iCheckPrimaryColorForTenantInOpenedWidget() {
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(widget.getTenantNameWidgetColor(), BasePortalSteps.getTenantInfoMap().get("newColor"), "Color for tenant name in widget is not correct");
        soft.assertEquals(widget.getTenantCloseButtonColor(), BasePortalSteps.getTenantInfoMap().get("newColor"), "Color for tenant close widget button is not correct");
        soft.assertAll();
    }

    @Then("^Chat icon is not visible$")
    public void isChatIconIsNotVisible() {
        Assert.assertFalse(getMainPage().isChatIconShown(), "Chat icon is visible.");
    }

    @Then("^Chat icon is visible$")
    public void isChatIconIsVisible() {
        Assert.assertTrue(getMainPage().isChatIconShown(), "Chat icon is not visible.");
    }

    @When("^User enter (.*) into widget input field$")
    public DefaultTouchUserSteps enterText(String text) {
        if (ConfigManager.isWebWidget()) {
            widget.getWidgetFooter().enterMessage(text).sendMessage();
            widgetConversationArea.waitForMessageToAppearInWidget(text);
        } else {
//            ORCASteps orcaSteps = new ORCASteps();
//            orcaSteps.sendOrcaMessage(text);
        }
        return this;
    }

    @Then("^User attach (.*) file type$")
    public void attachFile(String fileName) {
        File pathToFile = new File(System.getProperty("user.dir") + "/touch/src/test/resources/mediasupport/" + fileName + "." + fileName);
        String newName = new Faker().letterify(fileName + "?????") + "." + fileName;
        File renamed = new File(System.getProperty("user.dir") + "/touch/src/test/resources/mediasupport/renamed/" + newName);
        try {
            Files.copy(pathToFile, renamed);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaFileName.set(newName);
        widget.getWidgetFooter().openAttachmentWindow().setPathToFile(renamed.getPath());
        Assert.assertTrue(widget.isFileUploaded(), "File was not uploaded to widget");
    }

    @When("^User send attached file$")
    public void sendAttachedFile() {
        widget.clickSendAttachmentButton();
    }

    @When("^User enters message regarding (.*) into widget input field$")
    public void enterUniqueText(String text) {
        widgetConversationArea = widget.getWidgetConversationArea();
        widgetConversationArea.waitForSalutation();
        widget.getWidgetFooter().enterMessage(FacebookSteps.createUniqueUserMessage(text)).sendMessage();
        widgetConversationArea.waitForMessageToAppearInWidget(FacebookSteps.getCurrentUserMessageText());
    }

    @Then("^User should not receive '(.*)' message after his '(.*)' message in widget$")
    public void verifyMessageIsNotShownAfterUserMessage(String messageShouldNotBeShown, String userInput) {
        widgetConversationArea = widget.getWidgetConversationArea();
        String expectedTextResponse = formExpectedAutoresponder(messageShouldNotBeShown);

        Assert.assertFalse(widgetConversationArea.isTextResponseNotShownAmongOther(userInput, expectedTextResponse, 3),
                "Unexpected text response is shown on '" + userInput + "' user's input " +
                        "(Client ID: " + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()) + ")");
    }

    @Then("^User have to receive '(.*)' text response for his '(.*)' input$")
    public void verifyResponse(String textResponse, String userInput) {
        int waitForResponse = 10;
        String expectedTextResponse = formExpectedAutoresponder(textResponse);
        if (!expectedTextResponse.equals("")) verifyTextResponse(userInput, expectedTextResponse, waitForResponse);
    }

    @Then("^Correct response is shown in the widget for selected category$")
    public void verifyCorrectResponseOnSelectedCategory() {
        String selectedCategory = enteredUserMessageInTouchWidget.get();
        List<TIEIntentPerCategory> intentsListForCategory = ApiHelperTie.getListOfIntentsForCategory(selectedCategory);
        if (intentsListForCategory.size() == 1) {
            verifyResponse(intentsListForCategory.get(0).getText(), selectedCategory);
        } else {
            List<String> intentsTitles = intentsListForCategory.stream().map(TIEIntentPerCategory::getTitle).collect(Collectors.toList());
            widgetConversationArea.checkIfCardButtonsShownFor(selectedCategory, intentsTitles);

            String selectedIntentTitle = getRandomIntent(intentsTitles);
//            String selectedIntentTitle = "Loans";
            TIEIntentPerCategory selectedIntentItem = intentsListForCategory.stream()
                    .filter(e -> e.getTitle().equals(selectedIntentTitle))
                    .findFirst().get();
            String expectedAnswerOnSelectedIntent = selectedIntentItem.getText();
            String selectedIntentName = selectedIntentItem.getIntent();
            if (expectedAnswerOnSelectedIntent == null) {
                Assert.fail("Answer is not provided by the tie for '" + selectedIntentName + "' intent");
            }
            widgetConversationArea.clickOptionInTheCard(selectedCategory, selectedIntentTitle);
            verifyTextResponseWithIntent(expectedAnswerOnSelectedIntent, selectedIntentName, selectedIntentTitle);
        }
    }

    /**
     * This method is designed in order to exclude some tenant specific intents from randomiser
     * For e.g., on message 'Cost or transactions fees' tie API returns one intent, but on the camunda level
     * there is choice card mapped on this message and test will fail because it expects 1 text response (because 1 intent is shown),
     * but in fact choice map is shown
     *
     * @param intentsTitles
     * @return
     */
    private String getRandomIntent(List<String> intentsTitles) {
        if (Tenants.getTenantUnderTestOrgName().equalsIgnoreCase("Virgin Money")) {
            intentsTitles.remove("Cost or transactions fees");
            intentsTitles.remove("Quote request");
            intentsTitles.remove("Policy queries");
            intentsTitles.remove("Claims and premiums");
        }
        return intentsTitles.get(new Random().nextInt(intentsTitles.size() - 1));
    }

    @Then("^User have to receive '(.*)' text response for his question regarding (.*)$")
    public void verifyResponseOnUniqueMessage(String textResponse, String userInput) {
        int waitForResponse = 10;
        String expectedTextResponse = formExpectedAutoresponder(textResponse);
        verifyTextResponse(FacebookSteps.getCurrentUserMessageText(), expectedTextResponse, waitForResponse);
    }

    /**
     * Method for verifying tie response in widget on user's message
     * Method is designed to respect tie mod for tenant under test
     * If tie is in semi autonomus mode we expect ONLY text response from tie
     * If tie is in autonomus mode, we take into account choice card
     *
     * @param textResponse
     * @param userInput
     * @param intent       - intent should be passed as it will be shown in the widget (with upper case letters where they should be)
     */
    @Then("^User have to receive '(.*)' text response with (.*) intent for his '(.*)' input$")
    public void verifyTextResponseWithIntent(String textResponse, String intent, String userInput) {
        int waitForResponse = 10;
        String expectedTextResponse = formExpectedAutoresponder(textResponse);
        boolean isTextResponseShown = widgetConversationArea.isTextResponseShownFor(userInput, waitForResponse);

//      ToDo: As soon as there is an API to check the tie mode implement the following logic
//        String tenantTIEMode = ApiHelperTie.getTIEModeForTenant(Tenants.getTenantUnderTestOrgName()).equals("automomus")
//        if(!isTextResponseShown & tenantTIEMode.equals("autonomus"))
        waitForResponse = 2;
        if (!isTextResponseShown & widgetConversationArea.isCardShownFor(userInput, waitForResponse)) {
            verifyTextResponseAfterInteractionWithChoiceCard(userInput, expectedTextResponse, intent, waitForResponse);
        } else {
            verifyTextResponse(userInput, expectedTextResponse, waitForResponse);
        }
    }

    @Then("^User should see emoji response for his '(.*)' input$")
    public void verifyEmoticonAnswer(String userInput) {
        String emoji = AgentConversationSteps.getSelectedEmoji();
        verifyTextResponseRegardlessPosition(emoji, userInput);
    }

    private void verifyTextResponseAfterInteractionWithChoiceCard(String userInput, String expectedTextResponse, String intent, int waitForResponse) {
        widgetConversationArea = widget.getWidgetConversationArea();
        int intentsCount = ApiHelperTie.getListOfIntentsOnUserMessage(userInput).size();

        //if tie returns more than 1 intent then choice card should be shown.
        // And we are verifying that expected intent is among the choice options
        if (intentsCount > 1) {
            if (!widgetConversationArea.isCardContainsButton(userInput, intent)) {
                Assert.fail("CreatedIntent '" + intent + "' is not shown in choice card on '" + userInput + "' user input");
            }
            expectedTextResponse = expectedTextResponse.replace("Hi " + getClientIdFromLocalStorage() + ". ", "");
            widgetConversationArea.clickOptionInTheCard(userInput, intent);
            verifyTextResponse(intent, expectedTextResponse, 10);
        }

        // if tie returns 1 intent and we have card shown then we are verifying that it is
        // confirmation card with correct intent
        else {
            double configConfidenceThreshold = Double.valueOf(ApiHelperTie.getTenantConfig(Tenants.getTenantUnderTestName(), "intent_confidence_threshold.high_confident"));
            double confidenceOnUserMessage = ApiHelperTie.getIntentConfidenceOnUserMessage(userInput);
            if (confidenceOnUserMessage < configConfidenceThreshold) {
                String textInCard = widgetConversationArea.getCardTextForUserMessage(userInput);
                String expectedText = "Do you mean \"" + intent + "\"?";
                if (!expectedText.equals(textInCard)) {
                    Assert.fail("Card text on user message '" + userInput + "' is not as expected.\n" +
                            "Expected: " + expectedText + "\n" +
                            "But found: " + textInCard + "\n");
                }
                widgetConversationArea.clickOptionInTheCard(userInput, "Yes");
                verifyTextResponse("Yes", expectedTextResponse, 10);


            } else {
                Assert.fail("Unexpected card is shown as a response on '" + userInput + "' user message.");
            }
        }

    }


    /**
     * Method that strictly verifies is there only one text response is shown
     * and it is correct (expected) one
     *
     * @param userInput
     * @param expectedTextResponse
     * @param waitForResponse
     */
    private void verifyTextResponse(String userInput, String expectedTextResponse, int waitForResponse) {
        SoftAssert softAssert = new SoftAssert();
        widgetConversationArea = widget.getWidgetConversationArea();
        softAssert.assertTrue(widgetConversationArea.isTextResponseShownFor(userInput, waitForResponse),
                "No text response is shown on '" + userInput + "' user's input (Client ID: " + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()) + ")");
        softAssert.assertTrue(widgetConversationArea.isOnlyOneTextResponseShownFor(userInput),
                "More than one text response is shown for user (Client ID: " + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()) + ")");
        String actualCardText = widgetConversationArea.getResponseTextOnUserInput(userInput);
        if (!expectedTextResponse.contains("\n")) {
            if (!expectedTextResponse.contains("\\n")) actualCardText = actualCardText.replace("\n", "");
            else expectedTextResponse = expectedTextResponse.replace("\\n", "\n");
        }
        softAssert.assertEquals(actualCardText, expectedTextResponse,
                "Incorrect text response is shown on '" + userInput + "' user's input (Client ID: " + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()) + ")");
        softAssert.assertAll();
    }


    /**
     * Method that strictly verifies is there expected response shown as an second response in the widget
     *
     * @param textResponse expected text response
     * @param place        expected place for text response
     * @param userInput    user input on which we are expecting response
     */
    @Then("^User have to receive '(.*)' (?:text response|url) as a (.*) response for his '(.*)' input$")
    public void verifyCertainTextResponse(String textResponse, int place, String userInput) {
        int waitForResponse = 10;
        String expectedTextResponse = formExpectedAutoresponder(textResponse);
        SoftAssert softAssert = new SoftAssert();
        widgetConversationArea = widget.getWidgetConversationArea();
        softAssert.assertTrue(widgetConversationArea.isResponseTextShownOnCorrectPlace(userInput, waitForResponse, place),
                "No second text response is shown on '" + userInput + "' user's input (Client ID: " + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()) + ")");
        softAssert.assertEquals(widgetConversationArea.getCertaiNumberResponseTextOnUserInput(userInput, place - 1).replace("\n", "")
                , expectedTextResponse,
                "Incorrect second text response is shown on '" + userInput + "' user's input (Client ID: " + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()) + ")");
        softAssert.assertAll();
    }

    /**
     * Method that verifies is there expected response among shown text responses,
     * regardless position and any other unexpected messages
     * Some analog of contains check
     *
     * @param textResponse expected text response
     * @param userInput    user input on which we are expecting response
     */
    @Then("^User should see '(.*)' (?:text response|url) for his '(.*)' input$")
    public void verifyTextResponseRegardlessPosition(String textResponse, String userInput) {
        if (userInput.contains("personal info")) {
            userInput = "Submitted data:\n" +
                    "" + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()) + "\n" +
                    "health@test.com";
        }
        int waitForResponse = 10;
        String expectedTextResponse = formExpectedAutoresponder(textResponse);
        SoftAssert softAssert = new SoftAssert();
        widgetConversationArea = widget.getWidgetConversationArea();
        softAssert.assertTrue(widgetConversationArea.isTextResponseShownFor(userInput, waitForResponse),
                "No text response is shown on '" + userInput + "' user's input (Client ID: " + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()) + ")");
        softAssert.assertTrue(widgetConversationArea.isTextResponseShownAmongOtherForUserMessage(userInput, expectedTextResponse), //errorWait act ~6/ exp 10(int waitForResponse=10;)
                "Expected '" + expectedTextResponse + "' text response on '" + userInput + "' user's input (Client ID: " + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()) + ") is missing.");
        softAssert.assertAll();
    }

    @Then("^Text response that contains \"(.*)\" is shown$")
    public void quickVerifyIsResponseShown(String text) {
        Assert.assertTrue(widget.getWidgetConversationArea().isTextShown(formExpectedAutoresponder(text), 10),
                "Response to user is not shown");
    }

    @Then("^User see correct Thanks message from Survey management$")
    public void verifyThankSurveyResponse() {
        quickVerifyIsResponseShown(SurveyManagementSteps.surveyConfiguration.get().getRatingThanksMessage());
    }

    @Then("^Card with a (?:button|buttons) (.*) is shown (?:on|after) user (.*) (?:message|input)$")
    public void isCardWithButtonShown(String buttonNames, String userMessage) {
        List<String> buttons = formListOfExpectedButtonNames(buttonNames);
        if (userMessage.equalsIgnoreCase("personal info")) {
            userMessage = userDataForQuoteRequest.get(Thread.currentThread().getId()).getWidgetPresentationOfPersonalInfoInput();
        }

        SoftAssert soft = new SoftAssert();
        soft.assertTrue(widgetConversationArea.isCardShownFor(userMessage, 10),
                "Card is not show after '" + userMessage + "' user message (Client ID: " + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()) + ")");
        soft.assertTrue(widgetConversationArea.checkIfCardButtonsShownFor(userMessage, buttons),
                buttons + " buttons are not shown in card (Client ID: " + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()) + ")");
        soft.assertAll();
    }

    private List<String> formListOfExpectedButtonNames(String buttonNames) {
        if (buttonNames.contains("FAQ categories")) {
            return ApiHelperTie.getLIstOfAllFAGCategories();
        }
        return Arrays.asList(buttonNames.split(";"));
    }


    @Then("^Card with a (.*) text is shown (?:on|after) user (.*) (?:message|input)$")
    public DefaultTouchUserSteps isCardWithTextShown(String cardText, String userMessage) {
        if (userMessage.equalsIgnoreCase("personal info")) {
            userMessage = userDataForQuoteRequest.get(Thread.currentThread().getId()).getWidgetPresentationOfPersonalInfoInput();
        }
        String expectedCardText;
        switch (cardText) {
            case "welcome":
                expectedCardText = ApiHelper.getAutoResponderMessageText("first_navigation_card_title");
                break;
            default:
                expectedCardText = cardText;

        }
        if (cardText.contains("${firstName}")) {
            expectedCardText = cardText.replace("${firstName}", getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()));
        }
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(widgetConversationArea.isCardShownFor(userMessage, 15),
                "Card is not show after '" + userMessage + "' user message (Client ID: " + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()) + ")");
        soft.assertEquals(widgetConversationArea.getCardTextForUserMessage(userMessage), expectedCardText,
                "Incorrect card text is shown. (Client ID: " + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()) + ")");
        soft.assertAll();
        return this;
    }

    @Then("^User is able to provide some info about himself in the card after his (.*) message$")
    public void verifyUserCanProvideInfoBeforeRedirectingToTheAgent(String userMessage) {
        widgetConversationArea.provideInfoBeforeGoingToAgent(userMessage, getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()), "health@test.com");
    }

    @Then("^Text '(.*)' is shown above the buttons in the card on user (.*) input above$")
    public void verifyTextShownInTheCardAboveButtons(String expectedText, String userMessage) {
        Assert.assertEquals(widgetConversationArea.getCardAboveButtonsTextForUserMessage(userMessage), expectedText,
                "Incorrect card text is shown. (Client ID: " + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()) + ")");
    }

    @Then("^No (?:additional card|card) should be shown (?:on|after) user (.*) (?:message|input)$")
    public void verifyNoCardIsShown(String userMessage) {
        Assert.assertTrue(widgetConversationArea.isCardNotShownFor(userMessage, 10),
                "Unexpected Card is show after '" + userMessage + "' user message (Client ID: " + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()) + ")");
    }

    @When("^User click (.*) button in the card on user message (.*)$")
    public void clickButtonOnToUserCard(String buttonName, String userMessage) {
        if (userMessage.equalsIgnoreCase("personal info")) {
            userMessage = userDataForQuoteRequest.get(Thread.currentThread().getId()).getWidgetPresentationOfPersonalInfoInput();
        }
        widgetConversationArea.clickOptionInTheCard(userMessage, buttonName);
    }

    @When("^User select random (.*) in the card on user message (.*)$")
    public void clickRandomButtonOnToUserCard(String faqEntity, String userMessage) {
        List<String> entities = ApiHelperTie.getLIstOfAllFAGCategories();
        List<String> toRemove = Arrays.asList("mobile banking 120 3279", "global one",
                "cellphone banking app", "general",
                "general bank masterpass app", "travel help",
                "milleage plus programme help", "travel policies", "credit card");
        entities.removeIf(e -> toRemove.contains(e));

        enteredUserMessageInTouchWidget.set(entities.get(new Random().nextInt(entities.size() - 1)));
//        enteredUserMessageInTouchWidget.set("generic");
        widgetConversationArea.clickOptionInTheCard(userMessage, enteredUserMessageInTouchWidget.get());
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

    @Then("^Widget is connected$")
    public DefaultTouchUserSteps verifyIfWidgetIsConnected() {
        Assert.assertTrue(widget.isWidgetConnected(15), "Widget is not connected after 15 seconds wait");//errorWait 15
        return this;
    }

    @Then("^User sees name of tenant: (.*) and its short description in the header$")
    public void verifyWidgetHeader(String tenantOrgName) {
        Map<String, String> tenantInfo = ApiHelper.getTenantInfoMap(tenantOrgName);
        String expectedDescription = tenantInfo.get("shortDescription");
        String dynamicTenantOrgName = tenantInfo.get("tenantOrgName");
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(getWidgetHeader().getDisplayedTenantName().toLowerCase(), dynamicTenantOrgName.toLowerCase(),
                dynamicTenantOrgName + " tenant name is not shown in the widget header\n");
        soft.assertEquals(getWidgetHeader().getDisplayedTenantDescription(), expectedDescription,
                expectedDescription + " description is not shown for " + dynamicTenantOrgName + " tenant\n");
        soft.assertAll();
    }

    @Then("^User session is (.*)$")
    public void verifyUserSessionStatus(String sessionStatus) {
        String expStatus = "terminated";
        if (sessionStatus.equals("created")) expStatus = "active";
        boolean result = false;
        for (int i = 0; i < 15; i++) {
            result = Tenants.getLastUserSessionStatus(getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()))
                    .equalsIgnoreCase(expStatus);
            if (result) {
                break;
            } else {
                waitFor(500);
            }
        }
        Assert.assertTrue(result, "Session with id " + ApiHelper.getLastUserSession(getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()), Tenants.getTenantUnderTestName()).getSessionId() +
                "is not " + expStatus + " for user: " + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()) +
                " after bot response.");
    }

    @When("^User submit card with personal information after user's message: (.*)$")
    public void fillInCardForVMQuoteRequestFlow(String userMessage) {
        createQuoteRequestUserDataAndAddToTheMap();
        widgetConversationArea.submitCardWithUserInfo(userMessage, getUserDataForQuoteRequest(Thread.currentThread().getId()));
    }

    @When("^User click 'Submit' button in the card after user message: (.*)$")
    public void clickSubmitButton(String userMessage) {
        widgetConversationArea.clickSubmitButton(userMessage);
    }

    @When("^(.*) field required (?:errors|error) is shown in personal info input card on user message: (.*)$")
    public void verifyFieldRequiredErrors(int numberOfErrors, String userMessage) {
        Assert.assertEquals(widgetConversationArea.getNumberOfFieldRequiredErrorsInCardOnUserMessage(userMessage), numberOfErrors,
                "Number of required filed errors is not as expected.");
    }

    @When("^User fill in field (.*) with '(.*)' in card on user message: (.*)$")
    public void clickSubmitButton(String fieldName, String fieldValue, String userMessage) {
        widgetConversationArea.fillInTheField(userMessage, fieldName, fieldValue);
    }

    @Then("^No required field errors are shown in card on user message (.*)$")
    public void verifyRequiredFieldErrorsNotShown(String userMessage) {
        Assert.assertFalse(widgetConversationArea.areFieldRequiredErrorsInCardOnUserMessageShown(userMessage),
                "Errors about required fields are still shown");
    }

    // ======================== Touch Actions Steps ======================== //

    @When("^User click Touch button$")
    public void clickTouchButton() {
        widget.clickTouchButton();
    }

    @Then("^\"(.*)\" is shown in touch menu$")
    public void isTouchMenuActionShown(String action) {
        Assert.assertTrue(getTouchActionsMenu().isTouchActionShown(action),
                "Touch action '" + action + "' is not shown in Touch menu");
    }

    @When("^User select \"(.*)\" from touch menu$")
    public void selectActionFromTouchMenu(String action) {
        getTouchActionsMenu().selectTouchAction(action);
    }

    // ======================== Welcome Card Steps ======================= //

    @Given("^Welcome card with a button \"(.*)\" is shown$")
    public void verifyWelcomeCardWithCorrectButtonIsShown(String buttonName) {
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(getWelcomeMessages().isWelcomeCardContainerShown(), "Welcome card is not shown. Client ID: " + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()) + "");
        soft.assertTrue(getWelcomeMessages().getWelcomeCardButtonText().contains(buttonName),
                "Button is not shown in Welcome card (Client ID: " + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()) + ")");
        soft.assertAll();
    }

    @When("^User select (.*) option from Welcome card$")
    public void selectOptionInWelcomeCard(String butonName) {
        getWelcomeMessages().clickActionButton(butonName);
    }

    @Then("^Welcome message with correct text is shown$")
    public void verifyWelcomeTextMessage() {
        String welcomeMessage = ApiHelper.getAutoResponderMessageText("welcome_message");
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(getWelcomeMessages().isWelcomeTextMessageShown(),
                "Welcome text message is not shown");
        soft.assertEquals(getWelcomeMessages().getWelcomeMessageText(), welcomeMessage,
                "Welcome message is not as expected");
        soft.assertAll();
    }

    @Then("^Welcome message is not shown$")
    public void verifyWelcomeMessageNotShown() {
        Assert.assertFalse(getWelcomeMessages().isWelcomeTextMessageShown(),
                "Welcome text message is still shown after disabling");
    }


    @Then("^Welcome back message with correct text is shown after user's input '(.*)'$")
    public void verifyWelcomeBackTextMessage(String userMessage) {
        String welcomeBackMessage = ApiHelper.getAutoResponderMessageText("welcome_back_message");
        Assert.assertTrue(widget.getWidgetConversationArea().isTextResponseShownAmongOtherForUserMessage(userMessage, welcomeBackMessage),
                "'" + welcomeBackMessage + "' welcome back message is not shown");
    }

    @Given("^Welcome card with correct (.*) text is shown$")
    public void verifyWelcomeCardWithCorrectText(String welcomeCardText) {
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(getWelcomeMessages().isWelcomeCardContainerShown(), "Welcome card is not shown. Client ID: " + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()) + "");
        soft.assertEquals(getWelcomeMessages().getWelcomeCardText(), welcomeCardText,
                "Text in Welcome card is not as expected");
        soft.assertAll();
    }

    @Given("^Welcome card with correct text and button \"(.*)\" is shown$")
    public void verifyWelcomeCardWithCorrectTextAndButtonIsShown(String buttonName) {
        String welcomeCardText = ApiHelper.getAutoResponderMessageText("first_navigation_card_title");
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(getWelcomeMessages().isWelcomeCardContainerShown(), "Welcome card is not shown. Client ID: " + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()) + "");
        soft.assertEquals(getWelcomeMessages().getWelcomeCardText(), welcomeCardText,
                "Text in Welcome card is not as expected");
        soft.assertTrue(getWelcomeMessages().getWelcomeCardButtonText().contains(buttonName),
                "Button is not shown in Welcome card (Client ID: " + getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()) + ")");
        soft.assertAll();
    }
    // ========================== Chat history Steps ========================= //

    @When("^User refreshes the widget page$")
    public void refreshThePage() {
        DriverFactory.getTouchDriverInstance().navigate().refresh();
    }

    @When("^I send window.lodash into browser console it returns function$")
    public void sendWindowLodashToBrowserConsole() {
        String windowLodashType = ((String) ((JavascriptExecutor) DriverFactory.getTouchDriverInstance())
                .executeScript("return typeof window.lodash;"));

        Assert.assertEquals(windowLodashType, "function",
                "'window.lodash' input in browser console does not return function.\n");
    }

    @When("^I send underscore: '_' into browser console it returns 'undefined' type$")
    public void sendUnderscoreToBrowserConsole() {
        String underscoreType = ((String) ((JavascriptExecutor) DriverFactory.getTouchDriverInstance())
                .executeScript("return typeof _;"));

        Assert.assertEquals(underscoreType, "undefined",
                "'_' input in browser console does not return 'undefined'.\n");
    }

    @Then("^Touch button is not shown$")
    public void verifyTButtonIsNotShown() {
        Assert.assertFalse(widget.isTouchButtonShown(2),
                "TButton is shown for tenant with Starter TouchGoPlan.");
    }

    @Then("^There is no (.*) response$")
    public void verifyTextResponseIsNotShownForUser(String expectedText) {
        if (expectedText.equalsIgnoreCase("otp code")) {
            AgentHomePage agentHomePage = new AgentHomePage("main");
            Assert.assertFalse(widget.getWidgetConversationArea()
                    .isTextShown(formExpectedAutoresponder(agentHomePage.getChatBody().getLastOTPCode()), 10), "Error: OTP code displayed in the widget");
        } else
            Assert.assertFalse(widget.getWidgetConversationArea()
                    .isTextShown(formExpectedAutoresponder(expectedText), 4), "Error: Response is shown in widget");
    }

    public static String formExpectedAutoresponder(String fromFeatureText) {
        return ApiHelper.getAutoResponderMessageText(fromFeatureText);
    }

    @Then("^Tenant photo is shown on widget$")
    public void verifyTenantImageIsShownOnChatdesk() {
        clickChatIcon();
        Assert.assertTrue(widget.isTenantImageShown(), "Tenant image is not shown on widget");
    }

    // ========================== Survey steps ========================= //

    @Then("^User see (.*) survey form$")
    public void verifySurveyFormIsDisplayed(String surveyType) {
        surveyForm = widgetConversationArea.getSurveyForm();
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(surveyForm.isSurveyDisplayed(), "Survey form is not shown");
        if (surveyType.equalsIgnoreCase("NPS")) {
            soft.assertTrue(surveyForm.isNPCRatingScaleDisplayed(), surveyType + " survey Type rating form should be displayed");
            soft.assertTrue(surveyForm.isNPSCorrectScaleSHown(), surveyType + " scale is not 0-10");
        } else if (surveyType.equalsIgnoreCase("CSAT")) {
            soft.assertTrue(surveyForm.isCSATRatingScaleDisplayed(), surveyType + " survey Type rating form should be displayed");
        }
        soft.assertAll();
    }

    @Then("^There is no survey form shown$")
    public void verifySurveyAbsent() {
        Assert.assertFalse(widgetConversationArea.getSurveyForm().isSurveyDisplayed(), "Survey form should not be shown");
    }

    @When("^Submit survey form with (.*) comment and (.*) rate$")
    public void submitSurveyForm(String comment, String rate) {
        if (comment.equalsIgnoreCase("no")) {
            surveyForm.selectRatingNumber(rate).clickSubmitReviewButton();
        } else {
            surveyForm.selectRatingNumber(rate).setComment(comment).clickSubmitReviewButton();
        }
    }

    @When("^Reject survey form submit$")
    public void rejectSurveyForm() {
        surveyForm.clickNoThanksButton();
    }

    @Then("^No comment field in Survey form is shown$")
    public void noCommentFieldVerification() {
        Assert.assertFalse(surveyForm.isCommentFieldShown(), "Unexpected comment field is shown");
    }

    // ========================== Attachments steps ========================= //

    @Then("^Widget contains attachment message$")
    public void verifyAttachmentMessagePresent() {
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(widgetConversationArea.isAttachmentMessageShown(), "No attachment message is shown from agent on widget");
        soft.assertEquals(widgetConversationArea.getAttachmentFile().getFileName(), DefaultTouchUserSteps.mediaFileName.get(),
                "File name is not the same as the file name which user sent");
        soft.assertAll();
    }


    @When("User is downloading the file")
    public void downloadAttachment() {
        widgetConversationArea.getAttachmentFile().clickDownloadLink();
    }

    @Then("^User can play (.*) file$")
    public void verifyIsFilePlaying(String fileType) {
        ChatAttachment agentDeskChatAttachment = widgetConversationArea.getAttachmentFile().clickPlayPauseButton(fileType);
        Assert.assertTrue(agentDeskChatAttachment.verifyIsFilePlaying(), "Media content is not playing");
    }

    // ======================= Private Getters ========================== //

    private WelcomeMessages getWelcomeMessages() {
        if (welcomeMessages == null) {
            welcomeMessages = new WelcomeMessages();
            return welcomeMessages;
        } else {
            return welcomeMessages;
        }
    }

    private MainPage getMainPage() {
        if (mainPage == null) {
            mainPage = new MainPage();
            return mainPage;
        } else {
            return mainPage;
        }
    }

    private TouchActionsMenu getTouchActionsMenu() {
        if (touchActionsMenu == null) {
            touchActionsMenu = widget.getTouchActionsMenu();
            return touchActionsMenu;
        } else {
            return touchActionsMenu;
        }
    }

    private WidgetHeader getWidgetHeader() {
        if (widgetHeader == null) {
            widgetHeader = widget.getWidgetHeader();
            return widgetHeader;
        } else {
            return widgetHeader;
        }
    }

    private void createQuoteRequestUserDataAndAddToTheMap() {
        Faker faker = new Faker();
        userDataForQuoteRequest.put(Thread.currentThread().getId(), new VMQuoteRequestUserData(
                getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()),                                              // first name
                "AQA",                                                             // last name
                String.valueOf(faker.number().randomNumber(6, false)),   // contact phone
                "aqa_quoterequest" + System.currentTimeMillis() + "@aqa.com"                          // email
        ));
    }

}
