package portalpages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import portalpages.uielements.*;

public class PortalMainPage extends PortalAbstractPage {

    private String addedToCartAlertXPATH = "//div[@ng-bind-html='alert'][text()='Added to cart']";

    @FindBy(xpath = "//div[@ng-bind-html='alert'][text()='Added to cart']")
    private WebElement addedToCartAlert;

    @FindBy(xpath = "//span[text() = 'Billing Not Setup']")
    private WebElement billingNotSetUpPopupHeader;

    @FindBy(xpath = "//button[text() = 'Setup Billing']")
    private WebElement setUpBillingButton;

    @FindBy(xpath = "//button[contains(@class, 'close-discard-modal')]")
    private WebElement closeSetUpBillingPopUp;

    @FindBy(xpath = "//h3[text()='We’ve updated our privacy policy']")
    private WebElement updatePolicyPopUp;

    @FindBy(xpath = "//button[text()='Got it']")
    private WebElement gotItButton;

    @FindBy(css = "div.cl-greeting-text")
    private WebElement greetingMessage;

    @FindBy(xpath = "//button[contains(text(), ' Get started with Touch')]")
    private WebElement getStartedWithTouchButton;

    @FindBy(css = "button.launchpad-btn")
    private WebElement launchpadButton;

    @FindBy(xpath = "//span[text()='Thank you for singing up. Welcome!']")
    private WebElement landingPage;

    @FindBy(css = "span.svg-close.cl-clickable.push-right")
    private WebElement closeLandingPage;

    @FindBy(css = "div.button-container button.button.button-primary.ng-binding")
    private WebElement closeLandingPageConfirmation;

    private LeftMenu leftMenu;
    private UpgradeYourPlanWindow upgradeYourPlanWindow;
    private CartPage cartPage;
    private ConfigureTouchWindow configureTouchWindow;

    public ConfigureTouchWindow getConfigureTouchWindow() {
        return configureTouchWindow;
    }

    public UpgradeYourPlanWindow getUpgradeYourPlanWindow() {
        try {
            waitForElementToBeVisibleAgent(upgradeYourPlanWindow.getWrappedElement(), 5);
        } catch (TimeoutException | NoSuchElementException e){
            Assert.assertTrue(false, "Upgrade window is not shown");
        }
        return upgradeYourPlanWindow;
    }


    public LeftMenu getLeftMenu() {
        waitForElementToBeVisibleAgent(leftMenu.getWrappedElement(), 5);
        leftMenu.setCurrentAgent("admin");
        return leftMenu;
    }

    public void upgradePlan(int agentSeats){
        addAgentSeatsIntoCart(agentSeats);
        openAgentsPurchasingConfirmationWindow();
        checkoutAndBuy(cartPage);
    }

    public void checkoutAndBuy(CartPage localCartPage){
        if(localCartPage.getConfirmPaymentDetailsWindow().isBillingContactShown()){
            localCartPage.getConfirmPaymentDetailsWindow().clickNexButton();
        }
        localCartPage.getConfirmPaymentDetailsWindow()
                .clickSelectPaymentField()
                .selectPaymentMethod("VISA")
                .clickNexButton()
                .acceptTerms()
                .clickNexButton()
                .waitFotPaymentSummaryScreenToLoad()
                .acceptTerms()
                .clickPayNowButton();
        waitWhileProcessing();
    }

    public void upgradePlanWithoutTerms(int agentSeats){
        addAgentSeatsIntoCart(agentSeats);
        openAgentsPurchasingConfirmationWindow();
        ConfirmPaymentDetailsWindow confirmPaymentDetailsWindow = cartPage.getConfirmPaymentDetailsWindow();
        if (!confirmPaymentDetailsWindow.isSelectPaymentShown()){
            confirmPaymentDetailsWindow.clickNexButton();
        }
        confirmPaymentDetailsWindow
                .clickSelectPaymentField()
                .selectPaymentMethod("VISA")
                .clickNexButton()
                .acceptTerms()
                .clickNexButton();
        waitWhileProcessing();
        confirmPaymentDetailsWindow.waitFotPaymentSummaryScreenToLoad()
                                    .clickPayNowButton();
    }

    public void addAgentSeatsIntoCart(int agentSeats){
        getPageHeader().clickUpgradeButton();
        getUpgradeYourPlanWindow()
                .selectAgentSeats(agentSeats)
                .selectMonthly()
                .clickAddToCardButton();
        waitWhileProcessing();
        try {
            waitForElementToBeVisibleByXpathAgent(addedToCartAlertXPATH, 10, "admin");
            waitForElementToBeInVisibleByXpathAgent(addedToCartAlertXPATH, 20);
        } catch (TimeoutException e){
//            Assert.assertTrue(false, "Item is not added to the cart");
        }
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

    public void closeSetupBillingPopUpModal(){
        closeSetUpBillingPopUp.click();
    }

    public boolean isUpdatePolicyPopUpOpened(){
        return isElementShownAgent(updatePolicyPopUp, 10);
    }

    public boolean isLandingPopUpOpened(){
        return isElementShownAgent(landingPage, 10);
    }


    public void closeLandingPage(){
        clickElemAgent(closeLandingPage, 5, "main", "Close landing popup");
        clickElemAgent(closeLandingPageConfirmation, 5, "main", "Close landing popup confirmation");

    }


    public boolean isPortalPageOpened(){
        return isElementShownAgent(getPageHeader().getWrappedElement());
    }

    public void closeUpdatePolicyPopup(){
        gotItButton.click();
    }

    public String getGreetingMessage(){
        waitForElementToBeVisibleAgent(greetingMessage, 3);
        return getTextFrom(greetingMessage);
    }

    public boolean isGetStartedWithTouchButtonIsShown(){ return isElementShownAgent(getStartedWithTouchButton, 2);}

    public void clickGetStartedWithTouchButton(){ getStartedWithTouchButton.click();}

    public boolean isConfigureTouchWindowOpened(){
        return isElementShownAgent(getConfigureTouchWindow().getWrappedElement());
    }

    public void clickLaunchpadButton(){
        if(isElementShownAgent(launchpadButton,2)) launchpadButton.click();}
}
