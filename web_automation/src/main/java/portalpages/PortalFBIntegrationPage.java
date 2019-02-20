package portalpages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PortalFBIntegrationPage extends PortalAbstractPage {

    @FindBy(xpath = "//button[contains(text(), 'Delink account')]")
    private WebElement delinkAccountButton;

    @FindBy(css = "div.discard-modal.ng-scope")
    private WebElement delinkAccountConfirmationPopup;

    public void delinkFBAccount(){
        waitForElementToBeClickableAgent(delinkAccountButton, 5, "admin");
        delinkAccountButton.click();
        waitForElementToBeVisibleAgent(delinkAccountConfirmationPopup, 5);
        delinkAccountButton.click();
        waitForElementsToBeVisibleByCssAgent(PortalAbstractPage.getProcessingAlertLocator(), 5);
        waitForElementsToBeInvisibleByCssAgent(PortalAbstractPage.getProcessingAlertLocator(), 5);
    }

}
