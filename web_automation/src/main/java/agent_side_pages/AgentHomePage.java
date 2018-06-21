package agent_side_pages;

import abstract_classes.AgentAbstractPage;
import agent_side_pages.UIElements.*;
import driverManager.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.security.cert.X509Certificate;

public class AgentHomePage extends AgentAbstractPage {

    @FindBy(css = "div.suggestion-wrapper")
    private WebElement suggestionInputField;

    @FindBy(xpath = "//button[text()='End chat']")
    private WebElement endChatButton;

    @FindBy(xpath = "//button[text()='Close chat']")
    private WebElement closeChatButton;

    String closeChatButtonXPATH = "//button[text()='Close chat']";
    String messageInputLocator = "//textarea[contains(@class,'text-input')]";

    @FindBy(xpath = "//textarea[contains(@class,'text-input')]")
    private WebElement messageInput;

    @FindBy(css = "span.icon.svg-icon-send")
    private WebElement submitMessageButton;

    @FindBy(css = "div.dashboard div.chat")
    private WebElement conversationAreaContainer;

    @FindBy(xpath = "//button[text()='Clear']")
    private WebElement clearButton;

    @FindBy(xpath = "//button[text()='Edit']")
    private WebElement editButton;

    @FindBy(xpath = "//div[text()='Agent Assistant']")
    private WebElement agentAssistantButton;

    @FindBy(xpath = "//div[@class='modal-header' and text()='Profanity not allowed']")
    private WebElement profanityPopup;

    @FindBy(xpath = "//button[text()='Accept']")
    private WebElement acceptProfanityPopupButton;

    private String openedProfileWindow = "//div[@class='profile-modal-header modal-header']/parent::div";

    private LeftMenuWithChats leftMenuWithChats;
    private ChatBody chatBody;
    private Header header;
    private SuggestedGroup suggestedGroup;
    private ProfileWindow profileWindow;

    public AgentHomePage() {
    }

    public ProfileWindow getProfileWindow() {
        return profileWindow;
    }

    public SuggestedGroup getSuggestedGroup() {
        return suggestedGroup;
    }

    public Header getHeader() {
        return header;
    }

    public LeftMenuWithChats getLeftMenuWithChats() {
        return leftMenuWithChats;
    }

    public ChatBody getChatBody() {
        return chatBody;
    }

    public AgentHomePage sendResponseToUser(String responseToUser) {
        try {
            messageInput.sendKeys(responseToUser);
            clickSendButton();
            return this;
        } catch (InvalidElementStateException e){
            Assert.assertTrue(false, "There is a problem with agent desk page." +
                    " Check if there is no blinking connection error.");
            return this;
        }
    }

    public void clearAndSendResponseToUser(String response){
        moveToElemAndClick(findElemByXPATH(messageInputLocator));
        waitForElementToBeClickable(messageInput);
        messageInput.clear();
        sendResponseToUser(response);
    }

    public void clickSendButton() {
        click(submitMessageButton);
    }


    public boolean isAgentSuccessfullyLoggedIn() {
            return isElementShownAgent(conversationAreaContainer,10);
    }

    public String getSuggestionFromInputFiled() {
        return suggestionInputField.getText();
    }

    public void deleteSuggestionAndAddAnother(String message) {
            suggestionInputField.click();
            clearAndSendResponseToUser(message);
    }

    public void addMoreInfo(String additionalMessage){
            try {
                waitForElementToBeVisible(suggestionInputField,6);
                suggestionInputField.click();
                messageInput.sendKeys(additionalMessage);
            } catch (StaleElementReferenceException e) {
                DriverFactory.getSecondDriverInstance().findElement(By.xpath(messageInputLocator)).sendKeys(additionalMessage);
            }
    }

    public boolean isSuggestionFieldShown() {
        try {
            return isElementShown(suggestionInputField);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void endChat(){
        if(isEndChatShown()){
            endChatButton.click();
            waitForElementToBeVisible(closeChatButton);
            closeChatButton.click();
        }
    }

    private boolean isEndChatShown(){
       return isElementShownAgent(endChatButton,1);
    }

    public boolean isClearButtonShown(){
        return isElementShownAgent(clearButton,10);
    }

    public boolean isEditButtonShown(){
        return isElementShownAgent(editButton,10);
    }

    public void clickEditButton(){
        editButton.click();
    }

    public void clickClearButton(){
        clearButton.click();
    }

    public boolean isMessageInputFieldEmpty(){
        waitForElementToBeVisible(messageInput);
        boolean result = false;
        for(int i=0; i<3; i++){
            result = messageInput.getText().equals("");
            if (result) return true;
            getSuggestedGroup().waitFor(1000);
        }
        return result;
    }

    public boolean isSuggestionContainerDisappears(){
        try {
            waitForElementToBeInvisibleAgent(suggestionInputField,10);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void clickAgentAssistantButton(){
        agentAssistantButton.click();
    }

    public boolean isProfanityPopupShown(){
        return isElementShownAgent(profanityPopup,10);
    }

    public void clickAcceptProfanityPopupButton(){
        acceptProfanityPopupButton.click();
    }

    public void clickEndChat(){
        if (!isElementShownAgent(endChatButton)){
            Assert.assertTrue(false, "'End chat' button is not shown.");
        } else {endChatButton.click();}
    }

    public boolean isEndChatPopupShown (){
        return isElementShownAgent(closeChatButton,12);
    }

    public void clickCloseButtonInCloseChatPopup (){
        waitForElementsToBeVisibleByXpathAgent(closeChatButtonXPATH, 6);
        findElemByXPATHAgent(closeChatButtonXPATH).click();
        //        closeChatButton.click();
    }

    public boolean isProfileWindowOpened(){
        try{
            findElemByXPATHAgent(openedProfileWindow);
            return true;
        } catch (WebDriverException e){
            return false;
        }
    }

}
