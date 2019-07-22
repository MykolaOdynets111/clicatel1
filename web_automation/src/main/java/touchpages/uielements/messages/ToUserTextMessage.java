package touchpages.uielements.messages;

import interfaces.WebActionsDeprecated;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ToUserTextMessage  extends Widget implements WebActionsDeprecated {

    @FindBy(xpath = "./following-sibling::li[@class='ctl-chat-message-container message-to']//span[@class=' text-break-mod']")
    private List<WebElement> toUserTextMessages;

    public ToUserTextMessage(WebElement element) {
        super(element);
        PageFactory.initElements(new AppiumFieldDecorator(element), this);
    }

    public String getMessageText() {
        try{
            if(toUserTextMessages.get(0).getText().isEmpty()){
                waitForDeprecated(1000);
            }
            return toUserTextMessages.get(0).getAttribute("innerText");
        } catch (IndexOutOfBoundsException e) {
            return "no text response found";
        }
    }

    public String getSecondMessageText() {
        for(int i=0; i<4; i++){
            if (toUserTextMessages.size()>1) break;
            else waitForDeprecated(500);
        }
        try{
            if(toUserTextMessages.get(1).getText().isEmpty())  waitForDeprecated(1000);
            return toUserTextMessages.get(1).getText();
        } catch (IndexOutOfBoundsException e) {
            return "no text response found";
        }
    }

    public boolean isSecondResponseShown(int wait){
        for(int i = 0; i <= wait; i++ ){
            if(toUserTextMessages.size()==2){
                return true;
            }else {
                waitForDeprecated(1000);
            }
        }
        return false;
    }

    /**
     * Method designed to make sure that second response is not shown after specifyed wait
     * @param wait
     * @return
     */
    public boolean isSecondResponseNotShown(int wait){
        boolean isShown = true;
        for(int i = 0; i <= wait/1000; i++ ){
            if(toUserTextMessages.size()==2){
                isShown=true;
            }else {
                isShown=false;
            }
            waitForDeprecated(1000);
        }
        return isShown;
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
        boolean result = false;
        for(int i = 0; i < 15; i++){
            result = toUserTextMessages.stream().anyMatch(e -> e.getText().equals(expectedMessage));
            if (result) return true;
            waitForDeprecated(500);
        }
        return result;
    }

    public boolean isOnlyOneTextResponseShwon(){
        if (toUserTextMessages.size()>1)
            return false;
        else
            return true;
    }
}
