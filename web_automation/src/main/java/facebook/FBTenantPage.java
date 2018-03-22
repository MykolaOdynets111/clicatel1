package facebook;

import abstract_classes.AbstractPage;
import facebook.uielements.MessengerWindow;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FBTenantPage extends AbstractPage {

    @FindBy(xpath = "//span[text()='Send Message']")
    private WebElement sendMessageButton;

    private MessengerWindow messengerWindow;

    public MessengerWindow getMessengerWindow() {
        return messengerWindow;
    }

    public MessengerWindow openMessanger(){
        waitForElementToBeVisible(sendMessageButton);
        sendMessageButton.click();
        return messengerWindow;
    }


}
