package agentpages.uielements;

import interfaces.WebActions;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AgentDeskToUserMessage extends Widget implements WebActions {

    @FindBy(xpath = "./following-sibling::li[@class='to']//span[@class='text-parsed-by-emoji']")
    private WebElement toUserTextMessage;

    public AgentDeskToUserMessage(WebElement element) {
        super(element);
        PageFactory.initElements(new AppiumFieldDecorator(element), this);
    }

    public String getMessageText() {
        try {
            return toUserTextMessage.getText();
        } catch (NoSuchElementException e) {
            return "no text response found";
        }
    }

    public boolean isTextResponseShown(int wait) {
        try {
            waitForElementToBeVisible(toUserTextMessage, wait);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
