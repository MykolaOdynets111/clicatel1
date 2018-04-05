package twitter.uielements;

import abstract_classes.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

    public void sendUserMessage(String message){
        findElemByCSS(cssLocatorDMInputfield).sendKeys(message);
        sendButton.click();
        deleteConversation();
    }

    public void deleteConversation(){
        infoButton.click();
        waitForElementToBeVisible(deleteConversationButton);
        deleteConversationButton.click();
        waitForElementToBeVisible(findElemByCSS(deleteConversationConfirmButton));
        findElemByCSS(deleteConversationConfirmButton).click();
        waitForElementToBeInvisible(findElemByCSS(deleteConversationConfirmButton), 2);
    }

}
