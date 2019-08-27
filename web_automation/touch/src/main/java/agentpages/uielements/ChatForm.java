package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import driverfactory.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

@FindBy(css = "div.chat-form")
public class ChatForm extends AbstractUIElement {

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

    @FindBy(css = "svg#emoticon")
    public WebElement emoticonButton;

    @FindBy(css = "section.emoji-mart")
    public WebElement emojiMart;

    @FindBy(xpath = "//div[@data-name='Recent']/following-sibling::ul[@class='emoji-mart-category-list']//button")
    public List<WebElement> frequetlyUsedEmojis;

    public boolean isSuggestionFieldShown() {
        try {
            return isElementShownByCSS(this.getCurrentDriver(), suggestionInputFieldCSS, 5);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getSuggestionFromInputFiled() {
        return suggestionInputField.getText();
    }

    public boolean isSuggestionContainerDisappears(){
        try {
            waitForElementToBeInvisible(this.getCurrentDriver(), suggestionInputField,10);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void deleteSuggestionAndAddAnother(String message) {
//        suggestionInputFieldContainer.click();
        clearAndSendResponseToUser(message);
    }

    public String getTextFromMessageInputField(){
        waitForElementToBeVisibleByXpath(this.getCurrentDriver(), messageInputLocator, 1);
        moveToElemAndClick(this.getCurrentDriver(), findElemByXPATH(this.getCurrentDriver(), messageInputLocator));
        waitForElementToBeClickable(this.getCurrentDriver(), messageInput, 4);
        return messageInput.getText();
    }

    public void addMoreInfo(String additionalMessage){
        try {
            waitForElementToBeVisible(this.getCurrentDriver(), suggestionInputFieldContainer,6);
            suggestionInputFieldContainer.click();
            messageInput.sendKeys(additionalMessage);
        } catch (StaleElementReferenceException e) {
            DriverFactory.getAgentDriverInstance().findElement(By.xpath(messageInputLocator)).sendKeys(additionalMessage);
        }
    }

    public void clearAndSendResponseToUser(String response){
        waitForElementToBeVisibleByXpath(this.getCurrentDriver(), messageInputLocator, 5);
        if(isElementShown(this.getCurrentDriver(), suggestionInputField, 2)){
            suggestionInputField.click();
            waitForElementToBeClickable(this.getCurrentDriver(), messageInput, 4);
            messageInput.click();
            messageInput.clear();
        }
        int symbolsNumber = messageInput.getText().length();
        if(symbolsNumber>0) {
            findElemByXPATH(this.getCurrentDriver(), messageInputLocator).sendKeys(Keys.chord(Keys.CONTROL,"A", Keys.DELETE));
        }
        int symbolsNumber2 = messageInput.getText().length();
        while (symbolsNumber2>0 && symbolsNumber2<10) {
            findElemByXPATH(this.getCurrentDriver(), messageInputLocator).sendKeys(Keys.BACK_SPACE);
            symbolsNumber2 = messageInput.getText().length();
        }
        sendResponseToUser(response);
    }

    public ChatForm sendResponseToUser(String responseToUser) {
        try {
            waitForElementToBeClickable(this.getCurrentDriver(), messageInput, 5);
            messageInput.sendKeys(responseToUser);
            clickSendButton();
            return this;
        } catch (InvalidElementStateException e){
            Assert.fail("There is a problem with agent desk page." +
                    " Check if there is no blinking connection error.");
            return this;
        }
    }

    public void clickSendButton() {
        clickElem(this.getCurrentDriver(), submitMessageButton, 2, "Send Message");
    }

    public boolean isMessageInputFieldEmpty(){
        waitForElementToBeVisible(this.getCurrentDriver(), messageInput, 3);
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
        return isElementShown(this.getCurrentDriver(),clearButton,10);
    }

    public void clickClearButton(){
        clickElem(this.getCurrentDriver(), clearButton, 2, "Clear suggestion");
    }

    public boolean isEditButtonShown(){
        return isElementShown(this.getCurrentDriver(), editButton,10);
    }

    public void clickEditButton(){
        clickElem(this.getCurrentDriver(), editButton, 2,"Edit suggestion");
    }

    public boolean isSendEmailForOvernightTicketMessageShown(){ return isElementEnabled(this.getCurrentDriver(), overnightTicketSendEmail, 3); }

    public boolean isOvernightTicketMessageShown(){ return isElementShown(this.getCurrentDriver(), overnightTicketLable, 3); }

    public String getPlaceholderFromInputLocator(){
        waitForElementToBeVisibleByXpath(this.getCurrentDriver(), messageInputLocator, 5);
        return findElemByXPATH(this.getCurrentDriver(), messageInputLocator).getAttribute("placeholder");
    }

    public void clickEmoticonButton(){
        clickElem(this.getCurrentDriver(), emoticonButton, 2,"Emoticon button in chatdesk");
        waitForElementToBeVisible(this.getCurrentDriver(), emojiMart, 2);
    }

    public String selectRandomFrequentlyUsedEmoji(){
        Random generator = new Random();
        WebElement emoji = frequetlyUsedEmojis.get(generator.nextInt(frequetlyUsedEmojis.size()-1));
        String emojiText = emoji.getAttribute("aria-label").split(",")[0].trim();
        clickElem(this.getCurrentDriver(), emoji, 2, emojiText + " emoji");
        return emojiText;
    }
}
