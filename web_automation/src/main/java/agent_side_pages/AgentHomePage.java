package agent_side_pages;

import abstract_classes.AgentAbstractPage;
import agent_side_pages.UIElements.*;
import api_helper.ApiHelper;
import dataManager.Tenants;
import driverManager.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class AgentHomePage extends AgentAbstractPage {

    private String suggestionInputFieldCSS = "div.suggestion-wrapper";

    @FindBy(css = "div.suggestion-wrapper")
    private WebElement suggestionInputField;

    @FindBy(css = "div.text-field")
    private WebElement suggestionInputFieldContainer;

    @FindBy(xpath = "//span[text()='Close Chat']")
    private WebElement closeChatButton;

    private String closeChatButtonXPATH = "//span[text()='Close Chat']";
    private String messageInputLocator = "//textarea[contains(@class,'text-input')]";
    private String loadingSpinner = "//*[text()='Connecting...']";

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

    @FindBy(xpath = "//div[text()='Profanity not allowed']")
    private WebElement profanityPopup;

    @FindBy(xpath = "//button[text()='Accept']")
    private WebElement acceptProfanityPopupButton;

    @FindBy(css = "span.connection-error-img")
    private WebElement connectionErrorImage;

    @FindBy(xpath = "//div[@class='modal-pageHeader'][text()='Agent limit reached']")
    private WebElement agentLimitReachedPopup;

    @FindBy(css = "div.overnight-chat-controls p")
    public WebElement overnightTicketLable;

    @FindBy(xpath = "//div[contains(@class, 'overnight-chat-controls')]//a[text() = 'Send email']")
    public WebElement overnightTicketSendEmail;

    private String openedProfileWindow = "//div[@class='profile-modal-pageHeader modal-pageHeader']/parent::div";

    private LeftMenuWithChats leftMenuWithChats;
    private ChatBody chatBody;
    private PageHeader pageHeader;
    private SuggestedGroup suggestedGroup;
    private ProfileWindow profileWindow;
    private TransferChatWindow transferChatWindow;
    private IncomingTransferWindow incomingTransferWindow;
    private Customer360Container customer360Container;
    private ChatHeader chatHeader;

    public AgentHomePage(String agent) {
        super(agent);
    }

    public ChatHeader getChatHeader() {
        return chatHeader;
    }

    public Customer360Container getCustomer360Container() {
        return customer360Container;
    }

    public IncomingTransferWindow getIncomingTransferWindow() {
        return incomingTransferWindow;
    }

    public TransferChatWindow getTransferChatWindow() {return transferChatWindow;}

    public ProfileWindow getProfileWindow() {
        return profileWindow;
    }

    public SuggestedGroup getSuggestedGroup() {
        return suggestedGroup;
    }

    public PageHeader getPageHeader() {
        return pageHeader;
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
        waitForElementToBeVisibleByXpathAgent(messageInputLocator, 5, this.getCurrentAgent());
        moveToElemAndClick(DriverFactory.getDriverForAgent(this.getCurrentAgent()), findElemByXPATHAgent(messageInputLocator, this.getCurrentAgent()));
        waitForElementToBeClickableAgent(messageInput, 4, this.getCurrentAgent());
        int symbolsNumber = messageInput.getText().split("").length;
        if(symbolsNumber>0) {
                messageInput.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
        }

        sendResponseToUser(response);
    }

    public void clickSendButton() {
        submitMessageButton.click();
    }


    public boolean isAgentSuccessfullyLoggedIn(String ordinalAgentNumber) {
        if (isElementShownAgent(conversationAreaContainer,15, ordinalAgentNumber)) {
            try{
                try {
                    waitForElementToBeVisibleByXpathAgent(loadingSpinner, 10, ordinalAgentNumber);
                }catch (TimeoutException e){ }
                waitForElementsToBeInvisibleByXpathAgent(loadingSpinner, 15, ordinalAgentNumber);
                return true;
            }
            catch (TimeoutException e){
                return false;
            }
        } else { return false;}
    }

    public boolean isConnectionErrorShown(String ordinalAgentNumber){
        try{
            waitForElementToBeVisibleAgent(connectionErrorImage, 10, ordinalAgentNumber);
            return true;
        }
        catch (TimeoutException e){
            return false;
        }
    }

    public String getSuggestionFromInputFiled() {
        return suggestionInputField.getText();
    }

    public void deleteSuggestionAndAddAnother(String message) {
        suggestionInputFieldContainer.click();
            clearAndSendResponseToUser(message);
    }

    public void addMoreInfo(String additionalMessage){
            try {
                waitForElementToBeVisible(suggestionInputFieldContainer,6);
                suggestionInputFieldContainer.click();
                messageInput.sendKeys(additionalMessage);
            } catch (StaleElementReferenceException e) {
                DriverFactory.getAgentDriverInstance().findElement(By.xpath(messageInputLocator)).sendKeys(additionalMessage);
            }
    }

    public boolean isSuggestionFieldShown() {
        try {
            return isElementShownAgentByCSS(suggestionInputFieldCSS, 5, "main agent");
        } catch (NoSuchElementException e) {
            return false;
        }
    }


    public void endChat(){
        if(getChatHeader().isEndChatShown(getCurrentAgent())){
            getChatHeader().clickEndChatButton();
            clickCloseButtonInCloseChatPopup();
        }
    }

    public boolean isOvernightTicketMessageShown(){ return isElementShownAgent(overnightTicketLable, 3, getCurrentAgent()); }

    public boolean isSendEmailForOvernightTicketMessageShown(){ return isElementEnabledAgent(overnightTicketSendEmail, 3, getCurrentAgent()); }

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
        for(int i = 0; i<10;i++){
            if (!isElementShownAgent(profanityPopup, 1, "main agent")){
                break;
            }
        }
    }

    public boolean isEndChatPopupShown (){
        return isElementShownAgent(closeChatButton,12);
    }

    public void clickCloseButtonInCloseChatPopup (){
        if(ApiHelper.getFeatureStatus(Tenants.getTenantUnderTestOrgName(), "AGENT_FEEDBACK")){
            waitForElementToBeVisibleByXpathAgent(closeChatButtonXPATH, 10, "main agent");
            findElemByXPATHAgent(closeChatButtonXPATH).click();
            waitForElementToBeInVisibleByXpathAgent(closeChatButtonXPATH, 5);
        }
    }

    public boolean isAgentLimitReachedPopupShown(int wait){
        return isElementShownAgent(agentLimitReachedPopup,wait);
    }

}
