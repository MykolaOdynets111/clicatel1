package touch_pages.uielements;

import abstract_classes.AbstractUIElement;
import driverManager.DriverFactory;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

@FindBy(css = "div.ctl-chat-area-header-container")
public class WidgetHeader extends AbstractUIElement {

    @FindBy(css = "div.ctl-chat-agentflows-name")
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

    public boolean isStartChatButtonShown(int wait) {
        return isElementShown(startChatHeaderButton, wait);
    }

    public void clickEndChatButton() {
        click(endChatHeaderButton);
    }

    public void clickStartChatButton() {
        click(startChatHeaderButton);
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
