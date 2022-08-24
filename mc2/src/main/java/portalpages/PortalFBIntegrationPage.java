package portalpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PortalFBIntegrationPage extends PortalAbstractPage {

    @FindBy(xpath = "//button[contains(text(), 'Delink account')]")
    private WebElement delinkAccountButton;

    @FindBy(css = "div.discard-modal.ng-scope")
    private WebElement delinkAccountConfirmationPopup;

    // == Constructors == //

    public PortalFBIntegrationPage() {
        super();
    }
    public PortalFBIntegrationPage(String agent) {
        super(agent);
    }
    public PortalFBIntegrationPage(WebDriver driver) {
        super(driver);
    }

    public void delinkFBAccount(){
        waitForElementToBeClickable(this.getCurrentDriver(), delinkAccountButton, 5);
        delinkAccountButton.click();
        waitForElementToBeVisible(this.getCurrentDriver(), delinkAccountConfirmationPopup, 5);
        delinkAccountButton.click();
        waitForElementsToBeVisibleByCss(this.getCurrentDriver(), PortalAbstractPage.getProcessingAlertLocator(), 5);
        waitForElementsToBeInvisibleByCss(this.getCurrentDriver(),PortalAbstractPage.getProcessingAlertLocator(), 5);
    }

}
