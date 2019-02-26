package portalpages;

import portalpages.uielements.ConfirmPaymentDetailsWindow;

public class CartPage extends PortalAbstractPage {

   private String checkOutButtonCSS = "button.small-centered.button.button-primary";

   private String confirmDetailsPopUp = "div.cl-wizzard.create-integration-container";

   private ConfirmPaymentDetailsWindow confirmPaymentDetailsWindow;

    public ConfirmPaymentDetailsWindow getConfirmPaymentDetailsWindow() {
            waitForElementsToBeVisibleByCssAgent(confirmDetailsPopUp, 5);
            return confirmPaymentDetailsWindow;
    }

    public void clickCheckoutButton(){
        waitForElementsToBeVisibleByCssAgent(checkOutButtonCSS, 5);
        findElemByCSSAgent(checkOutButtonCSS).click();
    }



}
