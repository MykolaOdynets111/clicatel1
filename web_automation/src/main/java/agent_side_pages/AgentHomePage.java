package agent_side_pages;

import abstract_classes.AgentAbstractPage;
import agent_side_pages.UIElements.ChatBody;
import agent_side_pages.UIElements.Header;
import agent_side_pages.UIElements.LeftMenuWithChats;
import agent_side_pages.UIElements.SuggestedGroup;
import driverManager.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class AgentHomePage extends AgentAbstractPage {

    @FindBy(css = "div.suggestion-wrapper")
    private WebElement suggestionInputField;

    String messageInputLocator = "//textarea[contains(@class,'text-input')]";

    @FindBy(xpath = "//textarea[contains(@class,'text-input')]")
    private WebElement messageInput;

//    @FindBy(css = "button[type='submit']")
    @FindBy(css = "span.icon.svg-icon-send")
    private WebElement submitMessageButton;

    @FindBy(css = "div.dashboard div.chat")
    private WebElement conversationAreaContainer;

    private LeftMenuWithChats leftMenuWithChats;
    private ChatBody chatBody;
    private Header header;
    private SuggestedGroup suggestedGroup;

    public AgentHomePage() {
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

    public void clickSendButton() {
        click(submitMessageButton);
    }


    public boolean isAgentSuccessfullyLoggedIn() {
        try {
            return isElementShown(conversationAreaContainer);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getSuggestionFromInputFiled() {
        return suggestionInputField.getText();
    }

    public void deleteSuggestionAndAddAnother(String message) {
        try {
            suggestionInputField.click();
            messageInput.clear();
            messageInput.sendKeys(message);
        } catch (StaleElementReferenceException e) {
            DriverFactory.getSecondDriverInstance().findElement(By.xpath(messageInputLocator)).sendKeys(message);
        }
    }

    public void addMoreInfo(String additionalMessage){
            try {
                suggestionInputField.click();
                messageInput.sendKeys(additionalMessage);
            } catch (StaleElementReferenceException e) {
                DriverFactory.getSecondDriverInstance().findElement(By.xpath(messageInputLocator)).sendKeys(additionalMessage);
            }
    }
}
