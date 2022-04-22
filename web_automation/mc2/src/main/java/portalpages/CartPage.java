package portalpages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portaluielem.ConfirmPaymentDetailsWindow;
import portaluielem.cart.CartTable;

public class CartPage extends PortalAbstractPage {

    @FindBy(xpath = "//li[text()='Cart']")
    private WebElement cartPageTitle;

    private String checkOutButtonCSS = "button.small-centered.button.button-primary";

    private String confirmDetailsPopUp = "div.cl-wizzard.create-integration-container";

    private ConfirmPaymentDetailsWindow confirmPaymentDetailsWindow;

    private CartTable cartTable;

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

    public CartTable getCartTable(){
        cartTable.setCurrentDriver(this.getCurrentDriver());
        return cartTable;
    }

    public ConfirmPaymentDetailsWindow getConfirmPaymentDetailsWindow() {
        waitForElementsToBeVisibleByCss(this.getCurrentDriver(), confirmDetailsPopUp, 5);
        confirmPaymentDetailsWindow.setCurrentDriver(this.getCurrentDriver());
        return confirmPaymentDetailsWindow;
    }

    @Step(value = "Click 'Checkout' button'")
    public CartPage clickCheckoutButton(){
        waitForElementToBeVisibleByCss(this.getCurrentDriver(), checkOutButtonCSS, 5);
        waitForElementToBeClickable(
                this.getCurrentDriver(), findElemByCSS(this.getCurrentDriver(), checkOutButtonCSS), 1)
                .click();
        return this;
    }

    @Step(value = "Verify Cart page opened")
    public boolean isCartPageOpened(){
        return isElementShown(this.getCurrentDriver(), cartPageTitle, 4);
    }



}
