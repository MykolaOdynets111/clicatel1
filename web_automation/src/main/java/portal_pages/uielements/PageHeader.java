package portal_pages.uielements;

import abstract_classes.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portal_pages.CartPage;


@FindBy(css = "div.header.ng-scope")
public class PageHeader extends AbstractUIElement {

    @FindBy(css = "div[cl-tenant-feature='UPGRADE_PACKAGE||ADD_AGENT_SEATS'] button.upgrade-touch-plan-bttn")
    private WebElement upgradeButton;

    @FindBy(css = "span.mini-cart-icon")
    private WebElement cartIcon;

    @FindBy(css = "div.cl-cart-drop-footer--controls>button")
    private WebElement checkoutCartButton;

    @FindBy(css = "div.balance-text.ng-binding")
    private WebElement touchGoPlanName;

    public void clickUpgradeButton(){
        waitForElementToBeClickableAgent(upgradeButton, 5, "main");
        upgradeButton.click();
    }

    public CartPage openCart(){
        cartIcon.click();
        waitForElementToBeVisibleAgent(checkoutCartButton, 10);
        checkoutCartButton.click();
        return new CartPage();
    }

    public String getTouchGoPlanName(){
        if(isElementShownAgent(touchGoPlanName,3)) return touchGoPlanName.getText();
        else return "no Touch Go plan name is shown";
    }

    public String getTextOfUpgradeButton(){
        return upgradeButton.getText();
    }
}
