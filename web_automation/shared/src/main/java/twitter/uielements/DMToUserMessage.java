package twitter.uielements;

import abstractclasses.AbstractSocialPage;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.stream.Collectors;


public class DMToUserMessage extends AbstractSocialPage {

    private String toUserTextMessagesXPATH = "//section[@aria-labelledby='detail-header']//span[contains(text(),'%s')]//ancestor::div[not(@class) and not(@style)]/following::div//span";


    public DMToUserMessage(WebDriver driver) {
        super(driver);
    }

    public String getMessageText(String userMessage) {
        try{
            waitForElementToBeVisibleByXpath(this.getCurrentDriver(), String.format(toUserTextMessagesXPATH, userMessage), 5);
            return findElemByXPATH(this.getCurrentDriver(), String.format(toUserTextMessagesXPATH, userMessage)).getText();
        } catch (TimeoutException e) {
            return "Exeption:no text response found";
        }
    }

    public List<String> getAllToUserMessages(String userMessage, int wait){
        String locator = String.format(toUserTextMessagesXPATH, userMessage);
        areElementsShownByXpath(this.getCurrentDriver(), locator, wait);
        return findElemsByXPATH(this.getCurrentDriver(), locator)
                .stream().map(e -> e.getText()).collect(Collectors.toList());
    }

    public boolean isTextResponseShown(String message, int wait) {
        try{
            waitForElementToBeVisibleByXpath(this.getCurrentDriver(), String.format(toUserTextMessagesXPATH, message), wait);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
