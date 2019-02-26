package touchpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = "div.ctl-text-input-container ")
public class WidgetFooter extends AbstractUIElement {

    @FindBy(css = "textarea.ctl-text-input")
    private WebElement textInput;

    @FindBy(css = "div.svg-bttn-click-area")
    private WebElement sendMesageButton;

    public WidgetFooter enterMessage(String text) {
        inputText(textInput, text);
        return this;
    }

    public WidgetFooter sendMessage() {
        sendMesageButton.click();
        return this;
    }

    public void tryToCloseSession(){
        if(textInput.isEnabled()) enterMessage("end").sendMessage();
    }
}
