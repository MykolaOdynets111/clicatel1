package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import driverfactory.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import touchpages.uielements.AttachmentWindow;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@FindBy(css = ".cl-message-sender")
public class ChatForm extends AbstractUIElement {

    public static String inputMassage;

    private String messageInputLocator = "//textarea[@data-testid='message-composer-textarea']";
    private SecureRandom random = new SecureRandom();
    private String suggestionCSSInput = "[data-testid=suggestion-wrapper]";

    @FindBy(css = "[data-testid=suggestion-wrapper]")
    private WebElement suggestionInputField;

    @FindBy(css = ".cl-message-composer__textarea")
    private WebElement messageInput;


    @FindBy(css = "[data-testid=message-composer-send-button]")
    private WebElement submitMessageButton;

    @FindBy(css = ".cl-r-suggestion-wrapper__icon")
    private WebElement clearButton;

    @FindBy(css = "[data-testid=chat-form-send-email] button")
    public WebElement overnightTicketSendEmail;

    @FindBy(css = "div.overnight-chat-controls p")
    public WebElement overnightTicketLable;

    @FindBy(css = "[id='Sentiment / Happy']")
    public WebElement emoticonButton;

    @FindBy(xpath = "//div[@data-name='Recent']/following-sibling::ul[@class='emoji-mart-category-list']//button")
    public List<WebElement> frequetlyUsedEmojis;

    @FindBy(css ="[id=Attachment]")
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
            suggestionInputField.sendKeys(additionalMessage);
            inputMassage = suggestionInputField.getText();
        } catch (StaleElementReferenceException e) {
            DriverFactory.getAgentDriverInstance().findElement(By.xpath(messageInputLocator)).sendKeys(additionalMessage);
        }
    }

    public void clearAndSendResponseToUser(String response){
        if(isSuggestionFieldShown()){
            clickClearButton();
        } else {
            messageInput.clear();
        }
        System.out.println("2 " + LocalDateTime.now());
        messageInput.sendKeys(response);
        clickSendButton();
    }

    public ChatForm sendResponseInSuggestionWrapperToUser(String responseToUser) {
        suggestionInputField.click();
        suggestionInputField.sendKeys(Keys.chord(Keys.CONTROL,Keys.END));
//        suggestionInputField.sendKeys(Keys.ENTER);
        suggestionInputField.sendKeys(responseToUser);
        clickSendButton();
        return this;
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
