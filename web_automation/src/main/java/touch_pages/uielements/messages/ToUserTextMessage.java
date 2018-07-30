package touch_pages.uielements.messages;

import interfaces.WebActions;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ToUserTextMessage  extends Widget implements WebActions {

    @FindBy(xpath = "./following-sibling::li[@class='ctl-chat-message-container message-to']//span[@class=' text-break-mod']")
    private List<WebElement> toUserTextMessages;

    public ToUserTextMessage(WebElement element) {
        super(element);
        PageFactory.initElements(new AppiumFieldDecorator(element), this);
    }

    public String getMessageText() {
        try{
            if(toUserTextMessages.get(0).getText().isEmpty()){
                waitFor(1000);
            }
            return toUserTextMessages.get(0).getText();
        } catch (IndexOutOfBoundsException e) {
            return "no text response found";
        }
    }

    public String getSecondMessageText() {
        for(int i=0; i<4; i++){
            if (toUserTextMessages.size()>1) break;
            else waitFor(500);
        }
        try{
            return toUserTextMessages.get(1).getText();
        } catch (IndexOutOfBoundsException e) {
            return "no text response found";
        }
    }

    public boolean isTextResponseShown(int wait) {
        try{
            waitForElementsToBeVisible(toUserTextMessages, wait);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isTextResponseShownAmongOthers(String expectedMessage) {
        if(toUserTextMessages.size()==0){
            return false;
        } else{
            return toUserTextMessages.stream().anyMatch(e -> e.getText().equals(expectedMessage));
        }
    }

    public boolean isOnlyOneTextResponseShwon(){
        if (toUserTextMessages.size()>1)
            return false;
        else
            return true;
    }
}
