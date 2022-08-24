package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = ".cl-r-files-bar")
public class ChatAttachmentForm extends AbstractUIElement {

    @FindBy(xpath = ".//div[text()='File successfully uploaded']")
    private WebElement successUploadedStatus;

    @FindBy(css=".cl-r-send-button")
    private WebElement sendButton;

    public boolean isFileUploaded(){
        return isElementShown(this.getCurrentDriver(), successUploadedStatus, 15);
    }

    public void clickSendButton(){
        clickElem(this.getCurrentDriver(), sendButton, 1, "Send button" );
    }
}
