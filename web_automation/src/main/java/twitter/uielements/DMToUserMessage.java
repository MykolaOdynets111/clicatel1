package twitter.uielements;

import interfaces.WebActions;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class DMToUserMessage extends Widget implements WebActions {

    public DMToUserMessage(WebElement element) {
        super(element);
    }

    private String toUserTextMessagesXPATH = "./following-sibling::li[contains(@class, 'DirectMessage--received')]//p[contains(@class, 'tweet-text')]";

    public String getMessageText() {
        try{
            return findElemsByXPATH(toUserTextMessagesXPATH).get(0).getText();
        } catch (IndexOutOfBoundsException e) {
            return "no text response found";
        }
    }

    public boolean isTextResponseShown(int wait) {
        try{
            waitForElementsToBeVisibleByXpath(toUserTextMessagesXPATH, wait);
//            waitForElementsToBeVisible(toUserTextMessages, wait);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
