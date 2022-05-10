package touchpages.uielements;

import abstractclasses.AbstractUIElement;
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
       return isElementShown(this.getCurrentDriver(), endChatHeaderButton, wait);
    }

    public boolean isStartChatButtonShown(int wait) {
        return isElementShown(this.getCurrentDriver(), startChatHeaderButton, wait);
    }

    public void clickEndChatButton() {
        clickElem(this.getCurrentDriver(), endChatHeaderButton, 2, "End chat button");
    }

    public void clickStartChatButton() {
        clickElem(this.getCurrentDriver(), startChatHeaderButton, 2, "Start chat button");
    }

    public String getDisplayedTenantName() {
        if(!isElementShown(this.getCurrentDriver(), tenantName, 3)) {
            return "no tenant name in the header";
        }
        return tenantName.getText();
    }

    public String getDisplayedTenantDescription() {
        if(!isElementShown(this.getCurrentDriver(), tenantDescription, 4)) {
            return "no tenant description in the header";
        }
        return tenantDescription.getText();
    }


}
