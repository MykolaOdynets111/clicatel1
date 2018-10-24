package portal_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portal_pages.uielements.ConfirmPaymentDetailsWindow;
import portal_pages.uielements.LeftMenu;
import portal_pages.uielements.PageHeader;
import portal_pages.uielements.UpgradeYourPlanWindow;

public class PortalMainPage extends PortalAbstractPage {

    private String addedToCartAlertXPATH = "//div[@ng-bind-html='alert'][text()='Added to cart']";

    @FindBy(xpath = "//div[@ng-bind-html='alert'][text()='Added to cart']")
    private WebElement addedToCartAlert;

    @FindBy(xpath = "//span[text() = 'Billing Not Setup']")
    private WebElement billingNotSetUpPopupHeader;

    @FindBy(xpath = "//button[text() = 'Setup Billing']")
    private WebElement setUpBillingButton;

    private LeftMenu leftMenu;
    private PageHeader pageHeader;
    private UpgradeYourPlanWindow upgradeYourPlanWindow;
    private CartPage cartPage;

    public UpgradeYourPlanWindow getUpgradeYourPlanWindow() {
        waitForElementToBeVisible(upgradeYourPlanWindow.getWrappedElement());
        return upgradeYourPlanWindow;
    }

    public PageHeader getPageHeader() {
        return pageHeader;
    }

    public LeftMenu getLeftMenu() {
        waitForElementToBeVisible(leftMenu.getWrappedElement());
        return leftMenu;
    }

    public void upgradePlan(int agentSeats){
        getPageHeader().clickUpgradeButton();
        getUpgradeYourPlanWindow().selectAgentSeats(agentSeats)
                                    .clickAddToCardButton();
        waitForElementToBeVisibleAgent(addedToCartAlert, 15);
        waitForElementToBeInVisibleByXpathAgent(addedToCartAlertXPATH, 10);
        cartPage = getPageHeader().openCart();
        cartPage.clickCheckoutButton();
        cartPage.getConfirmPaymentDetailsWindow()
                .selectTestVisaCardToPay()
                .acceptTerms()
                .clickNexButton()
                .waitFotPaymentSummaryScreenToLoad()
                .acceptTerms()
                .clickNexButton();

    }

    public CartPage getCartPage() {
        if (cartPage==null) {
            cartPage =  new CartPage();
            return cartPage;
        } else{
            return cartPage;
        }
    }

    public boolean isBillingNotSetUpPopupShown(int wait){
        return isElementShown(billingNotSetUpPopupHeader, wait);
    }

    public PortalBillingDetailsPage clickSetupBillingButton(){
        setUpBillingButton.click();
        return new PortalBillingDetailsPage();
    }



}
