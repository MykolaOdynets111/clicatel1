package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import driverfactory.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import touchpages.uielements.AttachmentWindow;

import java.security.SecureRandom;
import java.util.List;

@FindBy(css = ".cl-message-sender")
public class ChatForm extends AbstractUIElement {

    public static String inputMassage;

    private String messageInputLocator = "//textarea[@selenium-id='message-composer-textarea']";
    private SecureRandom random = new SecureRandom();
    private String suggestionCSSInput = "[selenium-id=suggestion-wrapper]";

    @FindAll({
            @FindBy(css = "[data-testid=chat-item-icons-holder]"),
            @FindBy(css = "[selenium-id=suggestion-wrapper]") //toDo old locator
    })
    private WebElement suggestionInputField;

    @FindBy(css = ".cl-message-composer__textarea")
    private WebElement messageInput;

    @FindAll({
    @FindBy(css = "[data-testid=message-composer-send-button]"),
    @FindBy(css = "[selenium-id=message-composer-send-button]") //toDo old locator
    })
    private WebElement submitMessageButton;

    @FindBy(css = ".cl-r-suggestion-wrapper__icon")
    private WebElement clearButton;

    @FindAll({
            @FindBy(css = "[data-testid=chat-form-send-email] button"),
            @FindBy(css = "[selenium-id=chat-form-send-email] button") //toDo old locator
    })
    public WebElement overnightTicketSendEmail;

    @FindBy(css = "div.overnight-chat-controls p")
    public WebElement overnightTicketLable;

    @FindBy(css = "[name='sentiment-happy']")
    public WebElement emoticonButton;

    @FindBy(xpath = "//div[@data-name='Recent']/following-sibling::ul[@class='emoji-mart-category-list']//button")
    public List<WebElement> frequetlyUsedEmojis;

    @FindBy(css ="svg[name='attachment']")
    private WebElement attachmentButton;

    @FindBy(css ="svg[name='map-pin']")
    private WebElement location;

    @FindBy(css="svg[name='puzzle']")
    private WebElement extensionsButton;

    @FindBy(xpath = "//button[text()='Start Chat']")
    private WebElement startChatButton;

    @FindBy(xpath = "//div[contains(@class, 'cl-time-picker-modal__body')]//div[contains(@class, 'cl-select__indicators')]")
    private WebElement datePickerDropdown;

    @FindBy(xpath = "//div[contains(@id, 'react-select')]")
    private List<WebElement> datePickerItems;

    @FindBy(css = ".cl-message-composer__count-text")
    private WebElement typingIndicator;

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
        clearAndTypeResponseToUser(message).clickSendButton();
    }

    public String getTextFromMessageInputField(){
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

    public ChatForm clearAndTypeResponseToUser(String response){
        if(isSuggestionFieldShown()){
            clickClearButton();
        } else {
            messageInput.clear();
        }
        messageInput.sendKeys(response);
        return this;
    }

    public ChatForm sendResponseInSuggestionWrapperToUser(String responseToUser) {
        suggestionInputField.click();
        suggestionInputField.sendKeys(Keys.chord(Keys.CONTROL,Keys.END));
//        suggestionInputField.sendKeys(Keys.ENTER);
        suggestionInputField.sendKeys(responseToUser);
        clickSendButton();
        return this;
    }

    public ChatForm clickSendButton() {
        clickElem(this.getCurrentDriver(), submitMessageButton, 2, "Send Message");
    return this;
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

    public String getTypingIndicatorText(){ return getTextFromElem(this.getCurrentDriver(), typingIndicator, 3, "Typing indicator text").trim(); }

    public String getPlaceholderFromInputLocator(){
        waitForElementToBeVisibleByXpath(this.getCurrentDriver(), messageInputLocator, 5);
        return findElemByXPATH(this.getCurrentDriver(), messageInputLocator).getAttribute("placeholder");
    }

    public void clickEmoticonButton(){
        clickElem(this.getCurrentDriver(), emoticonButton, 2, "Emoticon button");
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

    public void openLocationForm(){
        clickElem(this.getCurrentDriver(), location, 2, "Location button");
    }

    public void openExtensionsForm(){
        clickElem(this.getCurrentDriver(), extensionsButton, 5, "Extensions button");
    }

    public void setDevicePickerName(String name) {
        clickElem(this.getCurrentDriver(), datePickerDropdown, 20, "Rating number dropdown");

        waitForElementsToBeVisible(this.getCurrentDriver(), datePickerItems, 5);
        datePickerItems.stream().filter(e -> e.getText().trim().equals(name)).findFirst()
                .orElseThrow(() -> new AssertionError(name + " name was not found in dropdown.")).click();
    }

    public void openHSMForm(){
        clickElem(this.getCurrentDriver(), startChatButton, 2, "Start Chat button");
    }

    public boolean c2pExtensionIconIsVisible(){
        return isElementShown(this.getCurrentDriver(),extensionsButton,3);
    }
}
