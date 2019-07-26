package twitter.uielements;

import abstractclasses.AbstractSocialPage;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

public class DMToUserMessage extends AbstractSocialPage {

    private String toUserTextMessagesXPATH = "//p[text()='%s']//following::li[contains(@class, 'DirectMessage--received')]//p[contains(@class, 'tweet-text')]";

    public DMToUserMessage(WebDriver driver) {
        super(driver);
    }

    public String getMessageText(String userMessage) {
        try{
            return findElemsByXPATH(this.getCurrentDriver(),String.format(toUserTextMessagesXPATH, userMessage)).get(0).getText();
        } catch (IndexOutOfBoundsException e) {
            return "no text response found";
        }
    }

    public boolean isTextResponseShown(String message, int wait) {
        try{
            waitForElementsToBeVisibleByXpath(this.getCurrentDriver(), String.format(toUserTextMessagesXPATH, message), wait);
//            waitForElementsToBeVisible(toUserTextMessages, wait);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
