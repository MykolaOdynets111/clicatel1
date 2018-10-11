package portal_pages;

import interfaces.ActionsHelper;
import interfaces.JSHelper;
import interfaces.WebActions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portal_pages.uielements.ConfirmPaymentDetailsWindow;

public class CartPage implements WebActions, ActionsHelper, JSHelper {

   private String checkOutButtonCSS = "button.small-centered.button.button-primary";

    private ConfirmPaymentDetailsWindow confirmPaymentDetailsWindow;

    public ConfirmPaymentDetailsWindow getConfirmPaymentDetailsWindow() {
        return confirmPaymentDetailsWindow;
    }

    public void clickCheckoutButton(){
        waitForElementsToBeVisibleByCssAgent(checkOutButtonCSS, 5);
        findElemByCSS(checkOutButtonCSS).click();
    }
}
