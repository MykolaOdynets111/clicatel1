package twitter.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = "div.DMDock-conversations")
public class DMWindow extends AbstractUIElement {

    private String cssLocatorDMInputfield = "div#tweet-box-dm-conversation";
    private String deleteConversationConfirmButton = "button#confirm_dialog_submit_button";

    @FindBy(xpath = "//span[text()='Send']")
    private WebElement sendButton;

    @FindBy(css = "span.Icon--info")
    private WebElement infoButton;

    @FindBy(xpath = "//button[text()='Delete conversation']")
    private WebElement deleteConversationButton;

    @FindBy(xpath = "//li[contains(@class, 'DirectMessage--sent')]//p[contains(@class, 'tweet-text')]")
    private List<WebElement> userMessages;

    @FindBy(css = "div.DMActivity--open div.DMActivity-toolbar span.Icon--close")
    private WebElement closeDMButton;


    public void sendUserMessage(String message){
        findElemByCSS(this.getCurrentDriver(), cssLocatorDMInputfield).sendKeys(message);
        sendButton.click();
    }

    public void deleteConversation(){
        infoButton.click();
        waitForElementToBeVisible(this.getCurrentDriver(), deleteConversationButton, 5);
        deleteConversationButton.click();
        waitForElementToBeVisible(this.getCurrentDriver(), findElemByCSS(this.getCurrentDriver(), deleteConversationConfirmButton), 5);
        findElemByCSS(this.getCurrentDriver(), deleteConversationConfirmButton).click();
        waitForElementToBeInvisible(this.getCurrentDriver(), findElemByCSS(this.getCurrentDriver(), deleteConversationConfirmButton), 2);
    }

    public void closeDMWindow(){
        closeDMButton.click();
    }

    public boolean isTextResponseForUserMessageShown(String userMessage){
        // ToDo: update timeout after it is provided in System timeouts confluence page
        return new DMToUserMessage(this.getCurrentDriver()).isTextResponseShown(userMessage,40);// clarify_timeout
    }


    public String getToUserResponse(String userMessage){
        return new DMToUserMessage(this.getCurrentDriver()).getMessageText(userMessage);

    }
}
