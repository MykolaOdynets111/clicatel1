package twitter.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(xpath = "//section[@aria-labelledby='detail-header']")
public class DMWindow extends AbstractUIElement {

    private String cssLocatorDMInputfield = "div#tweet-box-dm-conversation";
    private String xpathLocatorDMInputfield = "//div[@class='DraftEditor-editorContainer']/div";
    private String leaveConversationConfirmButton = "//span[text()='Leave']";
    private String sendButtonXpath = "//div[@aria-label='Send']";///div[@dir='auto']/svg


    @FindBy(xpath = "//span[text()='Tweet']")
    private WebElement sendButton;

    @FindBy(xpath = "//a[@aria-label='Conversation info']//div[@dir='auto']")
    private WebElement infoButton;

    @FindBy(xpath = "//span[text()='Leave conversation']")
    private WebElement leaveConversationButton;

    @FindBy(xpath = "//li[contains(@class, 'DirectMessage--sent')]//p[contains(@class, 'tweet-text')]")
    private List<WebElement> userMessages;

    @FindBy(css = "div.DMActivity--open div.DMActivity-toolbar span.Icon--close")
    private WebElement closeDMButton;


    public void sendUserMessage(String message){
        waitForElementToBeVisibleByXpath(this.getCurrentDriver(),xpathLocatorDMInputfield,5);
        findElemByXPATH(this.getCurrentDriver(), xpathLocatorDMInputfield).sendKeys(message);
        findElemByXPATH(this.getCurrentDriver(), sendButtonXpath).click();
    }

    public void deleteConversation(){
        infoButton.click();
        waitForElementToBeVisible(this.getCurrentDriver(), leaveConversationButton, 5);
        leaveConversationButton.click();
        waitForElementToBeVisible(this.getCurrentDriver(), findElemByXPATH(this.getCurrentDriver(), leaveConversationConfirmButton), 5);
        findElemByXPATH(this.getCurrentDriver(), leaveConversationConfirmButton).click();
        waitForElementToBeInvisible(this.getCurrentDriver(), findElemByXPATH(this.getCurrentDriver(), leaveConversationConfirmButton), 2);
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

    public List<String> getToUserResponses(String userMessage){
        return new DMToUserMessage(this.getCurrentDriver()).getAllToUserMessages(userMessage, 40);

    }
}
