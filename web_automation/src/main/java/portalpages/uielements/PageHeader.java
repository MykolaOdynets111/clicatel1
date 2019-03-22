package portalpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portalpages.CartPage;


@FindBy(css = "div.header.ng-scope")
public class PageHeader extends AbstractUIElement {

    @FindBy(css = "div[cl-tenant-feature='UPGRADE_PACKAGE||ADD_AGENT_SEATS'] button.upgrade-agentflows-plan-bttn")
    private WebElement upgradeButton;

    @FindBy(css = "span.mini-cart-icon")
    private WebElement cartIcon;

    @FindBy(css = "div.cl-cart-drop-footer--controls>button")
    private WebElement checkoutCartButton;

    @FindBy(css = "div.balance-text.ng-binding")
    private WebElement touchGoPlanName;

    @FindBy(css = "div.cl-card--details-avatar")
    private WebElement adminIcon;

    @FindBy(css = "ul.dropdown-menu.cl-user-dropdown")
    private WebElement adminDropdown;

    @FindBy(xpath = "//a[text() = 'Logout']")
    private WebElement logoutButton;

    public void clickUpgradeButton(){
        waitForElementToBeClickableAgent(upgradeButton, 5, "main");
        try {
            upgradeButton.click();
        }catch (org.openqa.selenium.WebDriverException e) {
            waitForElementToBeVisibleAgent(upgradeButton, 7, "admin");
            upgradeButton.click();
        }
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

    public void logoutAdmin(){
        adminIcon.click();
        waitForElementToBeVisibleAgent(adminDropdown, 5);
        logoutButton.click();
    }
}
