package portalpages;

import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portalpages.uielements.AddPaymentMethodWindow;
import portalpages.uielements.BillingContactsDetails;
import portalpages.uielements.TopUpBalanceWindow;


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

    public AddPaymentMethodWindow getAddPaymentMethodWindow(){
        return addPaymentMethodWindow;
    }

    public BillingContactsDetails getBillingContactsDetails() {
        return billingContactsDetails;
    }

    public TopUpBalanceWindow getTopUpBalanceWindow(){ return topUpBalanceWindow;}

    public boolean isPageOpened(int wait){
        return isElementShownAgent(billingDetailsForm, wait);
    }

    public boolean isAddPaymentMethodButtonShown(int wait){
        return isElementShownAgent(addPaymentMethodButton, wait);
    }

    public boolean isAddPaymentMethodWindowShown(int wait){
        return isElementShownAgent(getAddPaymentMethodWindow().getWrappedElement(), wait);
    }

    public void clickAddPaymentButton(){
        addPaymentMethodButton.click();
    }

    public boolean isNewPaymentAdded() {
        waitWhileProcessing();
        waitForNotificationAlertToDisappear();
        return isElementShownAgent(addedPayment, 20);
    }

    public String getPaymentMethodDetails(){ return paymentMethodDetails.getText();}

    public void deletePaymentMethod(){
        managePaymentMethodButton.click();
        waitForElementsToBeVisibleByXpathAgent(removePaymentButton, 12);
        try{
            findElemByXPATHAgent(removePaymentButton).sendKeys(Keys.ENTER);
        } catch(InvalidElementStateException e){
            waitFor(200);
            findElemByXPATHAgent(removePaymentButton).sendKeys(Keys.ENTER);

        }

        //        executeJSclick(findElemByXPATHAgent(removePaymentButton), DriverFactory.getAgentDriverInstance());
//        if(isElementShownAgent(removePaymentButton, 2)) executeJSclick(removePaymentButton, DriverFactory.getAgentDriverInstance());
//        moveToElemAndClick(DriverFactory.getAgentDriverInstance(), removePaymentButton);
//        JavascriptExecutor executor = (JavascriptExecutor) DriverFactory.getAgentDriverInstance();
//        executor.executeScript("arguments[0].click();", removePaymentButton);
        if(isElementShownAgent(removePaymentConfirmationButton, 3))removePaymentConfirmationButton.click();

    }

    public void clickTopUPBalance(){
        clickElemAgent(topUpBalanceButton, 5, "admin", "'Top up balance' button" );
    }

}
