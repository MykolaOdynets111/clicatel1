package twitter;

import abstract_classes.AbstractPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import twitter.uielements.DMWindow;

public class TwitterTenantPage extends AbstractPage {

    @FindBy(css = "button.DMButton")
    private WebElement messageButton;

    @FindBy(css = "div.DMDock-conversations")
    private WebElement directConversationArea;

    private DMWindow dmWindow;

    public DMWindow getDmWindow() {
        return dmWindow;
    }

    public void openDMWindow() {
        waitForElementToBeVisible(messageButton, 5);
        messageButton.click();
        waitForElementToBeVisible(directConversationArea, 5);
    }

}
