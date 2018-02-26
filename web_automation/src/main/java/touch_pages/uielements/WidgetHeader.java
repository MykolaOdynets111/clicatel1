package touch_pages.uielements;

import abstract_classes.AbstractUIElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = "div.ctl-chat-area-header-container")
public class WidgetHeader extends AbstractUIElement {

    @FindBy(xpath = ".//button[text()='Start chat']")
    private WebElement startChatHeaderButton;

    @FindBy(xpath = ".//button[text()='End chat']")
    private WebElement endChatHeaderButton;

    public boolean isEndChatButtonShown(int wait) {
        try{
            waitForElementToBeVisible(endChatHeaderButton, wait);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void clickEndChatButton() {

    }
}
