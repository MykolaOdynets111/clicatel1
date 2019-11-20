package touchpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = "div.ctl-text-input-container ")
public class WidgetFooter extends AbstractUIElement {

    @FindBy(css = "textarea.ctl-text-input")
    private WebElement textInput;

    @FindBy(css = "span.svg-bttn-container")
    private WebElement sendMesageButton;

    public WidgetFooter enterMessage(String text) {
        inputText(this.getCurrentDriver(), textInput, 3, "Widget input", text);
        return this;
    }

    public WidgetFooter sendMessage() {
        clickElem(this.getCurrentDriver(), sendMesageButton, 2, "Send Message button");
        return this;
    }

    public void tryToCloseSession(){
            if (textInput.isEnabled()) enterMessage("/cmd:end").sendMessage();
    }
}
