package portalpages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import portaluielem.*;


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

    @FindBy(css = "button[ng-click='openGetStarted()']")
    private WebElement getStartedButton;

    @FindBy(css = "button.launchpad-btn")
    private WebElement launchpadButton;

    @FindBy(css = "div.button-container button.button.button-primary.ng-binding")
    private WebElement closeLandingPageConfirmation;



    private LeftMenu leftMenu;
    private UpgradeYourPlanWindow upgradeYourPlanWindow;
    private CartPage cartPage;
    private ConfigureTouchWindow configureTouchWindow;
    private GDPRWindow gdprWindow;
    private GetStartedWindow getStartedWindow;
    private AddTestPhoneWindow addTestPhoneWindow;
    private TopUpBalanceWindow topUpBalanceWindow;
    private AddPaymentMethodWindow addPaymentMethodWindow;

    // == Constructors == //

    public PortalMainPage(WebDriver driver) {
        super(driver);
    }
    public PortalMainPage(String agent) {
        super(agent);
    }
    public PortalMainPage() {
        super();
    }

    public TopUpBalanceWindow getTopUpBalanceWindow(){
        topUpBalanceWindow.setCurrentDriver(this.getCurrentDriver());
        return topUpBalanceWindow;
    }

    public AddPaymentMethodWindow getAddPaymentMethodWindow(){
        addPaymentMethodWindow.setCurrentDriver(this.getCurrentDriver());
        return addPaymentMethodWindow;
    }

    public AddTestPhoneWindow getAddTestPhoneWindow(){
        addTestPhoneWindow.setCurrentDriver(this.getCurrentDriver());
        return addTestPhoneWindow;
    }

    public GDPRWindow getGdprWindow(){
        gdprWindow.setCurrentDriver(this.getCurrentDriver());
        return gdprWindow;
    }

    public GetStartedWindow getStartedWindow(){
        getStartedWindow.setCurrentDriver(this.getCurrentDriver());
        return getStartedWindow;
    }

    public ConfigureTouchWindow getConfigureTouchWindow() {
        configureTouchWindow.setCurrentDriver(this.getCurrentDriver());
        return configureTouchWindow;
    }

    public UpgradeYourPlanWindow getUpgradeYourPlanWindow() {
        try {
            waitForElementToBeVisible(this.getCurrentDriver(), upgradeYourPlanWindow.getWrappedElement(), 5);
        } catch (TimeoutException | NoSuchElementException e){
            Assert.fail("Upgrade window is not shown");
        }
        upgradeYourPlanWindow.setCurrentDriver(this.getCurrentDriver());
        return upgradeYourPlanWindow;
    }

    public PortalLaunchpadPage getLaunchpad(){
        return new PortalLaunchpadPage(this.getCurrentDriver());
    }

    public LeftMenu getLeftMenu() {
        isElementShown(this.getCurrentDriver(), leftMenu.getWrappedElement(), 5);
        leftMenu.setCurrentDriver(this.getCurrentDriver());
        return leftMenu;
    }

    public void upgradePlan(int agentSeats){
        addAgentSeatsIntoCart(agentSeats);
        openAgentsPurchasingConfirmationWindow();
        checkoutAndBuy(cartPage);
    }

    @Step(value = "Checkout and buy")
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
        waitWhileProcessing(3, 20);
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
        waitWhileProcessing(14, 20);
        confirmPaymentDetailsWindow.waitFotPaymentSummaryScreenToLoad()
                                    .clickPayNowButton();
    }

    public void addAgentSeatsIntoCart(int agentSeats){
        waitForAngularRequestsToFinish(this.getCurrentDriver());
        waitForAngularToBeReady(this.getCurrentDriver());
        getPageHeader().clickUpgradeButton();
        getUpgradeYourPlanWindow()
                .selectAgentSeats(agentSeats)
                .selectMonthly()
                .clickAddToCardButton();
        waitWhileProcessing(14, 20);
        try {
            waitForElementToBeVisibleByXpath(this.getCurrentDriver(), addedToCartAlertXPATH, 10);
            waitForElementToBeInvisibleByXpath(this.getCurrentDriver(),addedToCartAlertXPATH, 20);
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
            cartPage =  new CartPage(this.getCurrentDriver());
            return cartPage;
        } else{
            return cartPage;
        }
    }

    public boolean isBillingNotSetUpPopupShown(int wait){
        return isElementShown(this.getCurrentDriver(), billingNotSetUpPopupHeader, wait);
    }

    public PortalBillingDetailsPage clickSetupBillingButton(){
        setUpBillingButton.click();
        return new PortalBillingDetailsPage(this.getCurrentDriver());
    }

    public void closeSetupBillingPopUpModal(){
        closeSetUpBillingPopUp.click();
    }

    @Step(value = "Verify GDPR and Privacy modal window is displayed")
    public boolean isUpdatePolicyPopUpOpened(){
        return isElementShown(this.getCurrentDriver(), updatePolicyPopUp, 5);
    }

    @Step(value = "Verify Landing (Get Started) modal window is displayed")
    public boolean isGetStartedWindowShown(){
        return isElementShown(this.getCurrentDriver(), getStartedWindow.getWrappedElement(), 5);
    }

    @Step(value = "Verify GDPR and Privacy modal window is not displayed")
    public boolean isUpdatePolicyPopUpNotShown(){
        return isElementRemoved(this.getCurrentDriver(), updatePolicyPopUp, 5);
    }

    @Step(value = "Verify Landing (Get Started) modal window is not displayed")
    public boolean isGetStartedWindowNotShown(){
        return isElementRemoved(this.getCurrentDriver(), getStartedWindow.getWrappedElement(), 5);
    }


    @Step(value = "Close Landing (Get Started) modal window ")
    public void closeGetStartedWindow(){
        try {
            getStartedWindow().clickCloseGetStartedWindow();
            clickElem(this.getCurrentDriver(), closeLandingPageConfirmation, 5,"Close landing popup confirmation");
        }catch (WebDriverException e){
            waitFor(1000);
            getStartedWindow().clickCloseGetStartedWindow();
            clickElem(this.getCurrentDriver(), closeLandingPageConfirmation, 5,"Close landing popup confirmation");
        }

    }


    public boolean isPortalPageOpened(){
        return getPageHeader().isAdminIconShown(3);
    }

    @Step(value = "Make sure GDPR and Privacy modal window closed")
    public void closeUpdatePolicyPopup(){
        for (int i=0; i<2; i++) {
            if (isElementShown(this.getCurrentDriver(), gotItButton, 2)) {
                gotItButton.click();
                try {
                    waitUntilElementNotDisplayed(this.getCurrentDriver(), updatePolicyPopUp, 4);
                } catch (TimeoutException e) {
                }
            }
        }
        waitWhileProcessing(2, 3);
    }

    @Step(value = "Get user greeting from Portal Main page")
    public String getGreetingMessage(){
        return getTextFromElem(this.getCurrentDriver(), greetingMessage, 3, "Welcome message");
    }

    @Step(value = "Verify if 'Get started' button shown")
    public boolean isGetStartedButtonShown(){
        return isElementShown(this.getCurrentDriver(), getStartedButton, 2);
    }

    @Step(value = "Verify if 'Get started with touch' window opened")
    public boolean isConfigureTouchWindowOpened(){
        return isElementShown(this.getCurrentDriver(), getConfigureTouchWindow().getWrappedElement(), 2);
    }

    public void clickLaunchpadButton(){
        if(isElementShown(this.getCurrentDriver(), launchpadButton,2)) launchpadButton.click();
    }


   public void makeSureGetStartedWindowOpened(){
        if (!isElementShown(this.getCurrentDriver(), getStartedWindow.getWrappedElement(), 2)){
            clickGetStartedButton();
        }
   }

   @Step(value = "Click 'Get Started' button")
   public void clickGetStartedButton(){
       clickElem(this.getCurrentDriver(), getStartedButton, 2, "'Get Started' button");
   }


   public void launchChatDesk(){
       String currentWindow = this.getCurrentDriver().getWindowHandle();
       getLeftMenu().navigateINLeftMenuWithSubmenu("Touch", "Launch Chat Desk");

       for (int i = 0; i < 4; i++){
           if(this.getCurrentDriver().getWindowHandles().size() == 2) break;
           else getLeftMenu().navigateINLeftMenuWithSubmenu("Touch", "Launch Chat Desk");
       }
       if(this.getCurrentDriver().getWindowHandles().size()>1) {
           for (String winHandle : this.getCurrentDriver().getWindowHandles()) {
               if (!winHandle.equals(currentWindow)) {
                   this.getCurrentDriver().switchTo().window(winHandle);
               }
           }
       }
   }
}
