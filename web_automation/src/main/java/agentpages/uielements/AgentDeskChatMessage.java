package agentpages.uielements;

import interfaces.WebActions;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AgentDeskChatMessage extends Widget implements WebActions {

    @FindBy(xpath = "./following-sibling::li[@class='to']//span[@class='text-parsed-by-emoji']")
    private WebElement toUserTextResponse;

    @FindBy(css = "span.text-parsed-by-emoji")
    private WebElement messageText;

    @FindBy(xpath = ".//div/following-sibling::span[not(@class='icons msg-status')]")
    private WebElement messageTime;

    @FindBy(css = "div.channel-separator-title")
    private WebElement channelSeparator;

    public AgentDeskChatMessage(WebElement element) {
        super(element);
        PageFactory.initElements(new AppiumFieldDecorator(element), this);
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
            waitForElementToBeVisible(messageText, wait);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getMessageTime() {
        return messageTime.getAttribute("innerText").trim();
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
            waitForElementToBeVisible(toUserTextResponse, wait);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

}
