package twitter.uielements;

import interfaces.WebActions;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class DMToUserMessage implements WebActions {

    private String userMessage;

    public DMToUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    private String toUserTextMessagesXPATH = "//p[text()='%s']//following::li[contains(@class, 'DirectMessage--received')]//p[contains(@class, 'tweet-text')]";

    public String getMessageText() {
        try{
            return findElemsByXPATH(String.format(toUserTextMessagesXPATH, userMessage)).get(0).getText();
        } catch (IndexOutOfBoundsException e) {
            return "no text response found";
        }
    }

    public boolean isTextResponseShown(int wait) {
        try{
            waitForElementsToBeVisibleByXpath(String.format(toUserTextMessagesXPATH, userMessage), wait);
//            waitForElementsToBeVisible(toUserTextMessages, wait);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
