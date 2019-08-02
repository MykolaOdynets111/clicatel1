package portaluielem;

import abstractclasses.AbstractUIElement;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portalpages.CartPage;


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

    @FindBy(css = "div.cl-card--details-avatar")
    private WebElement adminIcon;

    @FindBy(css = "ul.dropdown-menu.cl-user-dropdown")
    private WebElement adminDropdown;

    @FindBy(xpath = "//a[text() = 'Logout']")
    private WebElement logoutButton;

    @FindBy(css = "div.balance-total.ng-binding")
    private WebElement topUpBalanceSum;

    public void setCurrentDriver(WebDriver driver){
        this.currentDriver = driver;
    }

    public void clickUpgradeButton(){
        waitForElementToBeClickable(this.getCurrentDriver(), upgradeButton, 5);
        try {
            upgradeButton.click();
        }catch (org.openqa.selenium.WebDriverException e) {
            waitForElementToBeVisible(this.getCurrentDriver(), upgradeButton, 7);
            upgradeButton.click();
        }
    }

    public CartPage openCart(){
        cartIcon.click();
        waitForElementToBeVisible(this.getCurrentDriver(), checkoutCartButton, 10);
        checkoutCartButton.click();
        return new CartPage(this.currentDriver);
    }

    public String getTouchGoPlanName(){
        if(isElementShown(this.getCurrentDriver(), touchGoPlanName,3)) return touchGoPlanName.getText();
        else return "no Touch Go plan name is shown";
    }

    public String getTextOfUpgradeButton(){
        return upgradeButton.getText();
    }

    @Step(value = "Log out admin")
    public void logoutAdmin(){
        adminIcon.click();
        waitForElementToBeVisible(this.getCurrentDriver(), adminDropdown, 5);
        logoutButton.click();
    }

    public String getTopUpBalanceSumm(){
        return topUpBalanceSum.getText().split(" ")[0];
    }
}
