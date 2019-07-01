package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import drivermanager.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

@FindBy(css = "div.chat-form")
public class ChatForm extends AbstractUIElement {

    private String currentAgent;

    private String suggestionInputFieldCSS = "div.suggestion-wrapper";
    private String messageInputLocator = "//textarea[contains(@class, 'text-input--example')]";

    @FindBy(css = "div.suggestion-wrapper")
    private WebElement suggestionInputField;

    @FindBy(css = "div.text-field")
    private WebElement suggestionInputFieldContainer;

    @FindBy(css = "textarea.text-input--example")
    private WebElement messageInput;

    @FindBy(css = "button.btn.btn-primary.pull-right.btn.btn-default")
    private WebElement submitMessageButton;

    @FindBy(xpath = "//button[text()='Clear']")
    private WebElement clearButton;

    @FindBy(xpath = "//button[text()='Edit']")
    private WebElement editButton;

    @FindBy(xpath = "//div[contains(@class, 'overnight-chat-controls')]//a[text() = 'Send email']")
    public WebElement overnightTicketSendEmail;

    @FindBy(css = "div.overnight-chat-controls p")
    public WebElement overnightTicketLable;

    public void setCurrectAgent(String agent){
        this.currentAgent = agent;
    }

    public boolean isSuggestionFieldShown() {
        try {
            return isElementShownAgentByCSS(suggestionInputFieldCSS, 5, "main agent");
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getSuggestionFromInputFiled() {
        return suggestionInputField.getText();
    }

    public boolean isSuggestionContainerDisappears(){
        try {
            waitForElementToBeInvisibleAgent(suggestionInputField,10);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void deleteSuggestionAndAddAnother(String message) {
        suggestionInputFieldContainer.click();
        clearAndSendResponseToUser(message);
    }

    public String getTextFromMessageInputField(){
        waitForElementToBeVisibleByXpathAgent(messageInputLocator, 1, this.currentAgent);
        moveToElemAndClick(DriverFactory.getDriverForAgent(this.currentAgent), findElemByXPATHAgent(messageInputLocator, this.currentAgent));
        waitForElementToBeClickableAgent(messageInput, 4, this.currentAgent);
        return messageInput.getText();
    }

    public boolean isMessageInputFieldContainText(String textToSearch){
        String enteredText = getTextFromMessageInputField().replaceAll("\\s", "");
        return enteredText.contains(textToSearch);
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

    public void clearAndSendResponseToUser(String response){
        waitForElementToBeVisibleByXpathAgent(messageInputLocator, 5, this.currentAgent);
        moveToElemAndClick(DriverFactory.getDriverForAgent(this.currentAgent), findElemByXPATHAgent(messageInputLocator, this.currentAgent));
        waitForElementToBeClickableAgent(messageInput, 4, this.currentAgent);
        messageInput.clear();
        int symbolsNumber = messageInput.getText().length();
        if(symbolsNumber>0) {
            findElemByXPATHAgent(messageInputLocator, this.currentAgent).sendKeys(Keys.chord(Keys.CONTROL,"A", Keys.DELETE));
        }
        int symbolsNumber2 = messageInput.getText().length();
        while (symbolsNumber2>0 && symbolsNumber2<10) {
            findElemByXPATHAgent(messageInputLocator, this.currentAgent).sendKeys(Keys.BACK_SPACE);
            symbolsNumber2 = messageInput.getText().length();
        }
        sendResponseToUser(response);
    }

    public ChatForm sendResponseToUser(String responseToUser) {
        try {
            waitForElementToBeClickableAgent(messageInput, 5,  this.currentAgent);
            messageInput.sendKeys(responseToUser);
            clickSendButton();
            return this;
        } catch (InvalidElementStateException e){
            Assert.assertTrue(false, "There is a problem with agent desk page." +
                    " Check if there is no blinking connection error.");
            return this;
        }
    }

    public void clickSendButton() {
        clickElemAgent(submitMessageButton, 2, this.currentAgent, "Send Message");
    }

    public boolean isMessageInputFieldEmpty(){
        waitForElementToBeVisible(messageInput);
        boolean result = false;
        for(int i=0; i<3; i++){
            result = messageInput.getText().equals("");
            if (result) return true;
            waitFor(1000);
        }
        return result;
    }

    public String getSubmitMessageButton() {
        return Color.fromString(submitMessageButton.getCssValue("background-color")).asHex();
    }

    public boolean isClearButtonShown(){
        return isElementShownAgent(clearButton,10);
    }

    public void clickClearButton(){
        clickElemAgent(clearButton, 2, this.currentAgent, "Clear suggestion");
    }

    public boolean isEditButtonShown(){
        return isElementShownAgent(editButton,10);
    }

    public void clickEditButton(){
        clickElemAgent(editButton, 2, this.currentAgent, "Edit suggestion");
    }

    public boolean isSendEmailForOvernightTicketMessageShown(){ return isElementEnabledAgent(overnightTicketSendEmail, 3, this.currentAgent); }

    public boolean isOvernightTicketMessageShown(){ return isElementShownAgent(overnightTicketLable, 3, this.currentAgent); }

    public String getPlaceholderFromInputLocator(){
        waitForElementToBeVisibleByXpathAgent(messageInputLocator, 5, this.currentAgent);
        return findElemByXPATHAgent(messageInputLocator, this.currentAgent).getAttribute("placeholder");
    }
}
