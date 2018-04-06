package twitter.uielements;

import abstract_classes.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

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

    @FindBy(css = "li.DirectMessage--sent")
    private List<WebElement> userMessages;

    private DMUserMessage getTargetUserMessageElem(String userInput) {
//        waitForElementsToBeVisible(userMessages, 6);
        return userMessages.stream().map(DMUserMessage::new).collect(Collectors.toList())
                .stream().filter(e -> e.getMessageText().equals(userInput))
                .findFirst().get();
    }

    public void sendUserMessage(String message){
        findElemByCSS(cssLocatorDMInputfield).sendKeys(message);
        sendButton.click();
    }

    public void deleteConversation(){
        infoButton.click();
        waitForElementToBeVisible(deleteConversationButton);
        deleteConversationButton.click();
        waitForElementToBeVisible(findElemByCSS(deleteConversationConfirmButton));
        findElemByCSS(deleteConversationConfirmButton).click();
        waitForElementToBeInvisible(findElemByCSS(deleteConversationConfirmButton), 2);
    }

    public boolean isTextResponseForUserMessageShown(String userMessage){
       return new DMToUserMessage(getTargetUserMessageElem(userMessage).getWrappedElement()).isTextResponseShown(30);
    }


    public String getToUserResponse(String userMessage){
        return new DMToUserMessage(getTargetUserMessageElem(userMessage).getWrappedElement()).getMessageText();

    }
}
