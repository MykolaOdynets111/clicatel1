package touchpages.uielements.messages;

import abstractclasses.AbstractWidget;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ToUserTextMessage extends AbstractWidget {

    @FindBy(xpath = "./following-sibling::li[@class='ctl-chat-message-container message-to']//span[@class=' text-break-mod']")
    private List<WebElement> toUserTextMessages;

    public ToUserTextMessage(WebElement element) {
        super(element);
    }

    public ToUserTextMessage setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
        return this;
    }

    public String getMessageText() {
        try{
            if(toUserTextMessages.get(0).getText().isEmpty()){
                waitFor(1000);
            }
            return toUserTextMessages.get(0).getAttribute("innerText");
        } catch (IndexOutOfBoundsException e) {
            return "no text response found";
        }
    }

    public String getMessageTextFromCertainPlace(int place) {
        for(int i=0; i<4; i++){
            if (toUserTextMessages.size()>1) break;
            else waitFor(500);
        }
        try{
            if(toUserTextMessages.get(place).getText().isEmpty())  waitFor(1000);
            return toUserTextMessages.get(place).getText();
        } catch (IndexOutOfBoundsException e) {
            return "no text response found";
        }
    }

    public boolean isCorrectSizeOfRespondersShown(int wait, int place){
        for(int i = 0; i <= wait; i++ ){
            if(toUserTextMessages.size()==place){
                return true;
            }else {
                waitFor(1000);
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
            isShown= toUserTextMessages.size() == 2;
            waitFor(1000);
        }
        return isShown;
    }

    public boolean isTextResponseShown(int wait) {
        try{
            waitForLastElementToBeVisible(this.getCurrentDriver(), toUserTextMessages, wait);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isTextResponseShownAmongOthers(String expectedMessage) {
        boolean result = false;
        for(int i = 0; i < 15; i++){
            result = toUserTextMessages.stream().anyMatch(e -> e.getText().trim().equals(expectedMessage));
            if (result) return true;
            waitFor(500);
        }
        return result;
    }

    public boolean isTextResponseNotShownAmongOthers(String expectedMessage, int wait) {
        boolean result = false;
        for(int i = 0; i < wait; i++){
            result = toUserTextMessages.stream().anyMatch(e -> e.getText().equals(expectedMessage));
            if (result) return true;
            waitFor(1000);
        }
        return result;
    }

    public boolean isOnlyOneTextResponseShwon(){
        return toUserTextMessages.size() <= 1;
    }
}
