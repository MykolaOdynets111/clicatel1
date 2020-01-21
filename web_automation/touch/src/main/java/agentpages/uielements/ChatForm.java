package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import driverfactory.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.testng.Assert;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

@FindBy(css = "div[selenium-id=message-composer]")
public class ChatForm extends AbstractUIElement {

    public static String inputMassage;

    private String suggestionInputFieldCSS = "[selenium-id=suggestion-wrapper]";
    private String messageInputLocator = "//textarea[@selenium-id='message-composer-textarea']";
    private SecureRandom random = new SecureRandom();

    @FindBy(css = "[selenium-id=suggestion-wrapper]")
    private WebElement suggestionInputField;

    @FindBy(css = "[selenium-id=message-composer-main]")
    private WebElement suggestionInputFieldContainer;

    @FindBy(css = "textarea[selenium-id=message-composer-textarea]")
    private WebElement messageInput;

    @FindAll({
    @FindBy(css = "[selenium-id=message-composer-send-button]"),
    @FindBy(css = ".cl-r-message-composer__send-button") // old locator
    })
    private WebElement submitMessageButton;

    @FindBy(css = "[selenium-id=suggestion-clear]")
    private WebElement clearButton;

    @FindBy(css = "[selenium-id=suggestion-edit]")
    private WebElement editButton;

    @FindBy(xpath = "//div[contains(@class, 'overnight-chat-controls')]//a[text() = 'Send email']")
    public WebElement overnightTicketSendEmail;

    @FindBy(css = "div.overnight-chat-controls p")
    public WebElement overnightTicketLable;

    @FindBy(css = "[selenium-id=message-composer-emoji-icon]")
    public WebElement emoticonButton;

    @FindBy(xpath = "//div[@data-name='Recent']/following-sibling::ul[@class='emoji-mart-category-list']//button")
    public List<WebElement> frequetlyUsedEmojis;

    private String emojiMartCss = "section.emoji-mart";

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
            inputMassage = messageInput.getText();
        } catch (StaleElementReferenceException e) {
            DriverFactory.getAgentDriverInstance().findElement(By.xpath(messageInputLocator)).sendKeys(additionalMessage);
        }
    }

    public void clearAndSendResponseToUser(String response){
        waitForElementToBeVisibleByXpath(this.getCurrentDriver(), messageInputLocator, 5);
        if(isElementShown(this.getCurrentDriver(), suggestionInputField, 2)){
            waitForElementToBeVisible(this.getCurrentDriver(), suggestionInputField, 4);
            moveAndClickByOffset(this.getCurrentDriver(), suggestionInputField, 10, 10);
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
        waitForElementToBeVisibleByCss(this.getCurrentDriver(), emojiMartCss,  5);
    }

    public String selectRandomFrequentlyUsedEmoji(){
//        Random generator = new Random();
        WebElement emoji = frequetlyUsedEmojis.get(random.nextInt(frequetlyUsedEmojis.size()-1));
        String emojiText = emoji.getAttribute("aria-label").split(",")[0].trim();
        clickElem(this.getCurrentDriver(), emoji, 2, emojiText + " emoji");
        return emojiText;
    }
}
