package portaluielem;

import abstractclasses.AbstractUIElement;
import io.qameta.allure.Step;
import mc2api.ApiHelperPlatform;
import org.apache.commons.math3.util.Precision;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portalpages.CartPage;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;


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

    @FindBy(xpath = "//a[text() = 'Profile settings']")
    private WebElement profileSettings;

    @FindBy(css = "div.balance-total.ng-binding")
    private WebElement topUpBalanceSum;

    @FindBy(xpath = ".//button[text() = 'Top up balance']")
    private WebElement topUpBalanceButton;

    @FindBy(css = "span.agent-seats-info")
    private WebElement agentSeatsInfo;

    public void setCurrentDriver(WebDriver driver){
        this.currentDriver = driver;
    }

    @Step("Click button for adding agents")
    public void clickUpgradeButton(){
        waitForElementToBeClickable(this.getCurrentDriver(), upgradeButton, 5);
        try {
            upgradeButton.click();
        }catch (org.openqa.selenium.WebDriverException e) {
            waitForElementToBeVisible(this.getCurrentDriver(), upgradeButton, 7);
            upgradeButton.click();
        }
    }

    @Step(value = "Open Cart")
    public CartPage openCart(){
        cartIcon.click();
        waitForElementToBeVisible(this.getCurrentDriver(), checkoutCartButton, 10);
        checkoutCartButton.click();
        return new CartPage(this.currentDriver);
    }

    @Step(value = "Get touch go plan name")
    public String getTouchGoPlanName(){
        if(isElementShown(this.getCurrentDriver(), touchGoPlanName,3)) return touchGoPlanName.getText();
        else return "no Touch Go plan name is shown";
    }

    @Step(value = "Get text from buying agents button")
    public String getTextFromBuyingAgentsButton(){
        return getTextFromElem(this.getCurrentDriver(), upgradeButton, 3, "Buying agents button");
    }

    @Step(value = "Get Agents seats info")
    public String getAgentSeatsInfo(){
        return getTextFromElem(this.getCurrentDriver(), agentSeatsInfo, 3, "Agent seats info");
    }

    @Step(value = "Log out admin")
    public void logoutAdmin(){
        adminIcon.click();
        waitForElementToBeVisible(this.getCurrentDriver(), adminDropdown, 5);
        logoutButton.click();
    }

    @Step()
    public void openProfileSettings(){
        adminIcon.click();
        waitForElementToBeVisible(this.getCurrentDriver(), adminDropdown, 5);
        clickElem(this.getCurrentDriver(), profileSettings, 3, "'Profile settings' button");
    }

    public boolean isAdminIconShown(int wait){
        return isElementShown(this.getCurrentDriver(), adminIcon, wait);
    }

    public String getTopUpBalanceSumm(){
        return topUpBalanceSum.getText().split(" ")[0];
    }

    @Step(value = "Verify 'Top up balance' button shown")
    public boolean isTopUpBalanceButtonShown(){
        return isElementShown(this.getCurrentDriver(), topUpBalanceButton, 5);
    }

    @Step(value = "Click 'Top up balance' button")
    public void clickTopUpBalanceButton(){
        clickElem(this.getCurrentDriver(), topUpBalanceButton, 5, "'Top up balance'");
    }

    @Step(value = "Verify Top Up updated in the page header")
    public Map isTopUpUpdated(String authToken, int waitMinutes){
        Map output = new HashMap();
        String valueFromPortal = getTopUpBalanceSumm();
        String actualInfo="";
        double valueFromBackend = ApiHelperPlatform.getAccountBalance(authToken).getBalance();
        boolean result = false;
        for(int i = 0; i<(waitMinutes*60)/25; i++){
            valueFromPortal = valueFromPortal.replace(",", "");
            actualInfo = String.format("%1.2f", valueFromBackend);
            if(valueFromPortal.equals(actualInfo)){
                result = true;
                break;
            } else{
                waitFor(25000);
                valueFromPortal = getTopUpBalanceSumm();
            }
        }
        output.put("result", result);
        output.put("valueFromBackend", valueFromBackend);
        output.put("actualInfo", actualInfo);
        output.put("valueFromPortal", valueFromPortal);
        return output;
    }


    @Step(value = "Verify top up updated on backend")
    public boolean isTopUpUpdatedOnBackend(double expectedAmount, int waitMinutes, String authToken){
        expectedAmount = Precision.round(expectedAmount, 2);
        DecimalFormat df = new DecimalFormat("####0.00");
        double valueFromBackend = Precision.round(ApiHelperPlatform.getAccountBalance(authToken).getBalance(),2);

        boolean result = false;
        for(int i = 0; i<(waitMinutes*60)/25; i++){

            if(valueFromBackend > (expectedAmount-0.5) & valueFromBackend <= expectedAmount){ //0.5 - possible withheld commission by tract
                result = true;
                break;
            } else{
                waitFor(25000);
                valueFromBackend = Precision.round(ApiHelperPlatform.getAccountBalance(authToken).getBalance(),2);
            }
        }
        return result;
    }
}
