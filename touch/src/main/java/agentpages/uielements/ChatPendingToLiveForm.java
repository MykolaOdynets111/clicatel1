package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = ".cl-PendingChatBecomesLiveByUserReplyModal-modal")
public class ChatPendingToLiveForm extends AbstractUIElement {

    @FindBy(css=".cl-chat-pending-prevent-to-live-modal__text")
    private WebElement formText;

    @FindBy(css = ".cl-chat-pending-prevent-to-live-modal__button")
    private WebElement goToChatButton;

    @FindBy(css=".cl-modal-default-header-title")
    private WebElement headerTitle;

    public String getFormText() {
        return getTextFromElem(this.getCurrentDriver(), formText, 3, "Form Text element");
    }

    public String getFormHeaderTitle() {
        return getTextFromElem(this.getCurrentDriver(), headerTitle, 3, "Form Header");
    }

    public void clickGoToChatButton(){
        clickElem(this.getCurrentDriver(), goToChatButton, 10, "Go to chat button");
    }

}