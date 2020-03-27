package touchpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

@FindBy(css = "div.ctl-text-input-container")
public class WidgetFooter extends AbstractUIElement {

    @FindBy(css = "textarea.ctl-text-input")
    private WebElement textInput;

    @FindAll({
            @FindBy(css = "div.svg-bttn-click-area"),
            @FindBy(css = "span.svg-bttn-container")
    })
    private WebElement sendMesageButton;

    @FindBy(css =".ctl-stroke-path")
    private  WebElement attachButton;

    private AttachmentWindow attachmentWindow;

    public WidgetFooter enterMessage(String text) {
        inputText(this.getCurrentDriver(), textInput, 3, "Widget input", text);
        return this;
    }

    public WidgetFooter sendMessage() {
        sendMesageButton.click();
        return this;
    }

    public void tryToCloseSession(){
            if (textInput.isEnabled()) enterMessage("/cmd:end").sendMessage();
    }

    public AttachmentWindow openAttachmentWindow(){
        attachmentWindow.setCurrentDriver(this.getCurrentDriver());
        clickElem(this.getCurrentDriver(), attachButton, 3, "Attachment button");
        return attachmentWindow;
    }

    public void attachTheFile(String filePath){
        openAttachmentWindow().setPathToFile(filePath);
    }
}
