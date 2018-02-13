package touch_pages.uielements;

import abstract_classes.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = "div.ctl-text-input-container ")
public class WidgetFooter extends AbstractUIElement {

    @FindBy(css = "textarea.ctl-text-input")
    private WebElement textInput;

    @FindBy(css = "div.svg-bttn-click-area")
    private WebElement sendMesageButton;

    public WidgetFooter enterMessage(String text) {
//        waitForElementToBeVisible(textInput);
        inputText(textInput, text);
//        textInput.sendKeys(text);
        return this;
    }

    public WidgetFooter sendMessage() {
        sendMesageButton.click();
        return this;
    }
}
