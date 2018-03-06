package touch_pages.uielements;

import abstract_classes.AbstractUIElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = "div.ctl-chat-area-header-container")
public class WidgetHeader extends AbstractUIElement {

    @FindBy(css = "div.ctl-chat-agent-name")
    private WebElement tenantName;

    @FindBy(css = "div.ctl-chat-description")
    private WebElement tenantDescription;

    @FindBy(xpath = ".//button[text()='Start chat']")
    private WebElement startChatHeaderButton;

    @FindBy(xpath = ".//button[text()='End chat']")
    private WebElement endChatHeaderButton;

    public boolean isEndChatButtonShown(int wait) {
       return isElementShown(endChatHeaderButton, wait);
    }

    public void clickEndChatButton() {

    }

    public String getDisplayedTenantName() {
        if(!isElementShown(tenantName)) {
            return "no tenant name in the header";
        }
        return tenantName.getText();
    }

    public String getDisplayedTenantDescription() {
        if(!isElementShown(tenantDescription)) {
            return "no tenant description in the header";
        }
        return tenantDescription.getText();
    }
}
