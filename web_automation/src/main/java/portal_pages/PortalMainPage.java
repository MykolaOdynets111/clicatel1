package portal_pages;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
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
        waitForElementToBeVisibleAgent(upgradeYourPlanWindow.getWrappedElement(), 5);
        return upgradeYourPlanWindow;
    }

    public PageHeader getPageHeader() {
        return pageHeader;
    }

    public LeftMenu getLeftMenu() {
        waitForElementToBeVisibleAgent(leftMenu.getWrappedElement(), 5);
        return leftMenu;
    }

    public void upgradePlan(int agentSeats){
        addAgentSeatsIntoCart(agentSeats);
        openAgentsPurchasingConfirmationWindow();
        cartPage.getConfirmPaymentDetailsWindow()
                .clickSelectPaymentField()
                .selectPaymentMethod("VISA")
                .clickNexButton()
                .acceptTerms()
                .clickNexButton()
                .waitFotPaymentSummaryScreenToLoad()
                .acceptTerms()
                .clickNexButton();

    }

    public void addAgentSeatsIntoCart(int agentSeats){
        getPageHeader().clickUpgradeButton();
        getUpgradeYourPlanWindow().selectAgentSeats(agentSeats)
                .clickAddToCardButton();
        try {
            waitForElementToBeVisibleAgent(addedToCartAlert, 15);
        } catch (TimeoutException e){
            Assert.assertTrue(false, "Item is not added to the cart");
        }
        waitForElementToBeInVisibleByXpathAgent(addedToCartAlertXPATH, 10);
    }

    public void openAgentsPurchasingConfirmationWindow(){
        cartPage = getPageHeader().openCart();
        cartPage.clickCheckoutButton();
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
        return isElementShownAgent(billingNotSetUpPopupHeader, wait);
    }

    public PortalBillingDetailsPage clickSetupBillingButton(){
        setUpBillingButton.click();
        return new PortalBillingDetailsPage();
    }



}
