package portal_pages;

import interfaces.ActionsHelper;
import interfaces.JSHelper;
import interfaces.WebActions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portal_pages.uielements.ConfirmPaymentDetailsWindow;

public class CartPage implements WebActions, ActionsHelper, JSHelper {

   private String checkOutButtonCSS = "button.small-centered.button.button-primary";

   private String confirmDetailsPopUp = "div.cl-wizzard.create-integration-container";

   private ConfirmPaymentDetailsWindow confirmPaymentDetailsWindow;

    public ConfirmPaymentDetailsWindow getConfirmPaymentDetailsWindow() {
        if (confirmPaymentDetailsWindow==null) {
            waitForElementsToBeVisibleByCssAgent(confirmDetailsPopUp, 5);
            confirmPaymentDetailsWindow =  new ConfirmPaymentDetailsWindow(findElemByCSSAgent(confirmDetailsPopUp));
            return confirmPaymentDetailsWindow;
        } else{
            return confirmPaymentDetailsWindow;
        }
    }

    public void clickCheckoutButton(){
        waitForElementsToBeVisibleByCssAgent(checkOutButtonCSS, 5);
        findElemByCSSAgent(checkOutButtonCSS).click();
    }



}
