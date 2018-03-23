package facebook;

import abstract_classes.AbstractPage;
import facebook.uielements.MessengerWindow;
import facebook.uielements.PostFeed;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FBTenantPage extends AbstractPage {

    @FindBy(xpath = "//span[text()='Send Message']")
    private WebElement sendMessageButton;

    @FindBy(xpath = "//a[text()='View Post.']")
    private WebElement viewPostButton;

    private String postLocator = "//a[text()='Tom Smith']//ancestor::li";

    private MessengerWindow messengerWindow;
    private PostFeed postFeed;

    public MessengerWindow getMessengerWindow() {
        return messengerWindow;
    }
    public PostFeed getPostFeed() {
        return postFeed;
    }

    public MessengerWindow openMessanger(){
        waitForElementToBeVisible(sendMessageButton);
        sendMessageButton.click();
        return messengerWindow;
    }

    public void clickViewPostButton() {
        waitForElementToBeVisible(viewPostButton).click();
    }
}
