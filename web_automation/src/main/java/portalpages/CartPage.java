package portalpages;

import org.openqa.selenium.WebDriver;
import portalpages.uielements.ConfirmPaymentDetailsWindow;

public class CartPage extends PortalAbstractPage {

   private String checkOutButtonCSS = "button.small-centered.button.button-primary";

   private String confirmDetailsPopUp = "div.cl-wizzard.create-integration-container";

   private ConfirmPaymentDetailsWindow confirmPaymentDetailsWindow;

    // == Constructors == //

    public CartPage() {
        super();
    }
    public CartPage(String agent) {
        super(agent);
    }
    public CartPage(WebDriver driver) {
        super(driver);
    }

    public ConfirmPaymentDetailsWindow getConfirmPaymentDetailsWindow() {
        waitForElementsToBeVisibleByCss(this.getCurrentDriver(), confirmDetailsPopUp, 5);
        confirmPaymentDetailsWindow.setCurrentDriver(this.getCurrentDriver());
        return confirmPaymentDetailsWindow;
    }

    public void clickCheckoutButton(){
        waitForElementToBeVisibleByCss(this.getCurrentDriver(), checkOutButtonCSS, 5);
        waitForElementToBeClickable(
                this.getCurrentDriver(), findElemByCSS(this.getCurrentDriver(), checkOutButtonCSS), 1)
                .click();
    }



}
