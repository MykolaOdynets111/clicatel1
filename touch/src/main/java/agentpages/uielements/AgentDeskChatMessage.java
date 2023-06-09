package agentpages.uielements;

import abstractclasses.AbstractWidget;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

public class AgentDeskChatMessage extends AbstractWidget {

    private final String messageTimeCssLocator = ".msg-time";

    @FindBy(css = "[data-testid=emojified-text]")
    private WebElement toUserTextResponse;

    @FindAll({
            @FindBy(css = "[data-testid=chat-message-content-PlainMessage]"),
            @FindBy(css = "[data-testid=chat-message-content-InputCardMessage]")
    })
    private WebElement messageText;

    @FindBy(css = messageTimeCssLocator)
    private WebElement messageTime;

    @FindBy(css = "[data-testid=channel-separator-title]")
    private WebElement channelSeparator;

    public AgentDeskChatMessage(WebElement element) {
        super(element);
    }

    public AgentDeskChatMessage setCurrentDriver(WebDriver currentDriver) {
        this.currentDriver = currentDriver;
        return this;
    }

    public String getMessageText() {
        try {
            return messageText.getText().trim();
        } catch (NoSuchElementException e) {
            return "no text response found";
        }
    }

    public String getMessageTime() {
        try {
            waitForElementToBeVisible(this.getCurrentDriver(), messageTime, 5);
            return messageTime.getAttribute("innerText").trim();
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            waitFor(500);
            //find element because when reuse messageTime there's StaleReferenceException
            return findElemByCSS(this.getCurrentDriver(), messageTimeCssLocator).getAttribute("innerText").trim();
        }
    }

    public String getMessageInfo() {
        try {
            return channelSeparator.getAttribute("innerText");
        } catch (NoSuchElementException e) {
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
}
