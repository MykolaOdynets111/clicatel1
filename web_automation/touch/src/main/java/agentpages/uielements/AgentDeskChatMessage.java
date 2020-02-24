package agentpages.uielements;

import abstractclasses.AbstractWidget;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class AgentDeskChatMessage extends AbstractWidget {

    @FindBy(css = "[selenium-id=emojified-text]")
    private WebElement toUserTextResponse;

    @FindBy(css = "[selenium-id=emojified-text]")
    private List<WebElement> toUserTextResponses;

    @FindBy(xpath = "//li[contains(@class, 'to')]//span[@class='emoji-mart-emoji']")
    private WebElement sentEmoji;

    @FindAll({
            @FindBy(css = "[selenium-id=chat-message-content-PlainMessage]"),
            @FindBy(css = "[selenium-id=chat-message-content-InputCardMessage]")
    })
    private WebElement messageText;

    @FindBy(css = ".msg-time")
    private WebElement messageTime;

    @FindBy(css = "[selenium-id=channel-separator-title]")
    private WebElement channelSeparator;

    public AgentDeskChatMessage(WebElement element) {
        super(element);
    }

    public AgentDeskChatMessage setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
        return this;
    }


    public String getMessageText() {
        try {
            return messageText.getAttribute("innerText").trim();
        } catch (NoSuchElementException e) {
            return "no text response found";
        }
    }

    public boolean isTextResponseShown(int wait) {
        try {
            waitForElementToBeVisible(this.getCurrentDriver(), messageText, wait);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getMessageTime() {
        try{
            waitForElementToBeVisible(this.getCurrentDriver(), messageTime, 5);
            return messageTime.getAttribute("innerText").trim();
        }catch (NoSuchElementException|TimeoutException|NullPointerException e){
            waitFor(500);
            return messageTime.getAttribute("innerText").trim();
        }
    }

    public String getMessageInfo() {
        try{
            return channelSeparator.getAttribute("innerText");
        }catch(NoSuchElementException e) {
            return getMessageText() + " " + getMessageTime();
        }
    }

    public boolean isToUserTextResponseShown(int wait) {
        try {
            waitForElementToBeVisible(this.getCurrentDriver(), toUserTextResponse, wait);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isToUserTextResponseShownAmongOthers(String expectedMessage) {
        boolean result = false;
        for(int i = 0; i < 15; i++){
            result = toUserTextResponses.stream().anyMatch(e -> e.getText().equals(expectedMessage));
            if (result) return true;
            waitFor(500);
        }
        return result;
    }

    public String getAgentResponseEmoji() {
        return getAttributeFromElem(this.getCurrentDriver(), sentEmoji, 3, "Sent by Agent Emoji", "aria-label");
    }
}
