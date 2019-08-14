package portalpages;

import io.qameta.allure.Step;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portaluielem.AddPaymentMethodWindow;
import portaluielem.BillingContactsDetails;
import portaluielem.TopUpBalanceWindow;


public class PortalBillingDetailsPage extends PortalAbstractPage {

    @FindBy(css = "form[name=billingDetailsForm]")
    private WebElement billingDetailsForm;

    @FindBy(css = "a[ng-click='addPaymentMethod()']")
    private WebElement addPaymentMethodButton;

    @FindBy(css = "div.payment-method")
    private WebElement addedPayment;

    @FindBy(css = "div.dashboard-step-content")
    private WebElement paymentMethodDetails;

    @FindBy(css = "div.payment-method button")
    private WebElement managePaymentMethodButton;

    @FindBy(xpath = "//button[text()='Accept']")
    private WebElement removePaymentConfirmationButton;

    @FindBy(xpath = "//header//button[@ng-click='topUpBalance()']")
    private WebElement topUpBalanceButton;

    private BillingContactsDetails billingContactsDetails;

    private AddPaymentMethodWindow addPaymentMethodWindow;

    private TopUpBalanceWindow topUpBalanceWindow;

    private String removePaymentButton =  "//button[@ng-click='removeCard()']";

    // == Constructors == //

    public PortalBillingDetailsPage() {
        super();
    }
    public PortalBillingDetailsPage(String agent) {
        super(agent);
    }
    public PortalBillingDetailsPage(WebDriver driver) {
        super(driver);
    }

    public AddPaymentMethodWindow getAddPaymentMethodWindow(){
        addPaymentMethodWindow.setCurrentDriver(this.getCurrentDriver());
        return addPaymentMethodWindow;
    }

    public BillingContactsDetails getBillingContactsDetails() {
        billingContactsDetails.setCurrentDriver(this.getCurrentDriver());
        return billingContactsDetails;
    }

    public TopUpBalanceWindow getTopUpBalanceWindow(){
        topUpBalanceWindow.setCurrentDriver(this.getCurrentDriver());
        return topUpBalanceWindow;
    }

    public boolean isPageOpened(int wait){
        return isElementShown(this.getCurrentDriver(), billingDetailsForm, wait);
    }

    public boolean isAddPaymentMethodButtonShown(int wait){
        return isElementShown(this.getCurrentDriver(), addPaymentMethodButton, wait);
    }

    public boolean isAddPaymentMethodWindowShown(int wait){
        return isElementShown(this.getCurrentDriver(), getAddPaymentMethodWindow().getWrappedElement(), wait);
    }

    @Step(value = "Click 'Add Payment Method' button")
    public void clickAddPaymentButton(){
        addPaymentMethodButton.click();
    }

    public boolean isNewPaymentAdded() {
        return isElementShown(this.getCurrentDriver(), addedPayment, 20);
    }

    public String getPaymentMethodDetails(){ return paymentMethodDetails.getText();}

    public void deletePaymentMethod(){
        managePaymentMethodButton.click();
        waitForElementToBeVisibleByXpath(this.getCurrentDriver(), removePaymentButton, 12);
        try{
            findElemByXPATH(this.getCurrentDriver(), removePaymentButton).sendKeys(Keys.ENTER);
        } catch(InvalidElementStateException e){
            waitFor(200);
            findElemByXPATH(this.getCurrentDriver(), removePaymentButton).sendKeys(Keys.ENTER);

        }
        if(isElementShown(this.getCurrentDriver(), removePaymentConfirmationButton, 3))
            removePaymentConfirmationButton.click();

    }

    public void clickTopUPBalance(){
        clickElem(this.getCurrentDriver(), topUpBalanceButton, 5,  "'Top up balance' button" );
    }


}
