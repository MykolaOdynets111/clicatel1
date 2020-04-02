package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import driverfactory.DriverFactory;
import io.appium.java_client.pagefactory.WithTimeout;
import org.openqa.selenium.*;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.testng.Assert;
import touchpages.uielements.AttachmentWindow;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static java.time.temporal.ChronoUnit.SECONDS;

@FindBy(css = "div[selenium-id=chat-form]")
public class ChatForm extends AbstractUIElement {

    public static String inputMassage;

    private String messageInputLocator = "//textarea[@selenium-id='message-composer-textarea']";
    private SecureRandom random = new SecureRandom();
    private String suggestionCSSInput = "[selenium-id=suggestion-wrapper]";

    @FindBy(css = "[selenium-id=suggestion-wrapper]")
    private WebElement suggestionInputField;

    @FindBy(css = "textarea[selenium-id=message-composer-textarea]")
    private WebElement messageInput;

    @FindAll({
    @FindBy(css = "[selenium-id=message-composer-send-button]"),
    @FindBy(css = ".cl-r-message-composer__send-button") // old locator
    })
    private WebElement submitMessageButton;

    @FindBy(css = ".cl-r-suggestion-wrapper__icon")
    private WebElement clearButton;

    @FindBy(css = "[selenium-id=chat-form-send-email] button")
    public WebElement overnightTicketSendEmail;

    @FindBy(css = "div.overnight-chat-controls p")
    public WebElement overnightTicketLable;

    @FindBy(css = "[selenium-id=message-composer-emoji-icon]")
    public WebElement emoticonButton;

    @FindBy(xpath = "//div[@data-name='Recent']/following-sibling::ul[@class='emoji-mart-category-list']//button")
    public List<WebElement> frequetlyUsedEmojis;

    @FindBy(css ="[selenium-id=message-composer-attachment-icon]")
    private WebElement attachmentButton;

    private String emojiMartCss = "section.emoji-mart";

    private AttachmentWindow attachmentWindow;

    public boolean isSuggestionFieldShown() {
        return  isElementShownByCSS(this.getCurrentDriver(), suggestionCSSInput, 1);
         //   return isElementShown(this.getCurrentDriver(), suggestionInputField, 1);
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
            if (isElementShown(this.getCurrentDriver(), suggestionInputField, 1)) {
                clickElem(this.getCurrentDriver(), suggestionInputField, 1, "Suggestion cover");
            }
            messageInput.sendKeys(additionalMessage);
            inputMassage = messageInput.getText();
        } catch (StaleElementReferenceException e) {
            DriverFactory.getAgentDriverInstance().findElement(By.xpath(messageInputLocator)).sendKeys(additionalMessage);
        }
    }

    public void clearAndSendResponseToUser(String response){
        if(isSuggestionFieldShown()){
            clickClearButton();
        } else {
            messageInput.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
        }
        System.out.println("2 " + LocalDateTime.now());
        sendResponseToUser(response);
    }

    public ChatForm sendResponseToUser(String responseToUser) {
        try {
            if (isSuggestionFieldShown()) {
                clickElem(this.getCurrentDriver(), suggestionInputField, 1, "Suggestion cover");
            }
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

    public boolean isSendEmailForOvernightTicketMessageShown(){ return isElementEnabled(this.getCurrentDriver(), overnightTicketSendEmail, 3); }

    public String getOvernightTicketMessage(){ return getTextFromElem(this.getCurrentDriver(), overnightTicketLable, 3, "Overnight ticket message").trim(); }

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

    public void openAttachmentForm(){
        clickElem(this.getCurrentDriver(), attachmentButton, 2, "Attachment button");
    }

}
