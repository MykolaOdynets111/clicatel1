package portal_pages;

import driverManager.DriverFactory;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portal_pages.uielements.AddPaymentMethodWindow;

import java.util.List;

public class PortalBillingDetailsPage extends PortalAbstractPage {

    @FindBy(css = "form[name=billingDetailsForm]")
    private WebElement billingDetailsForm;

    @FindBy(css = "div[cl-tabs='tabs'] ol.list-unstyled.list-inline>li")
    private List<WebElement> navItems;

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

    private AddPaymentMethodWindow addPaymentMethodWindow;

    private String removePaymentButton =  "//button[@ng-click='removeCard()']";

    public AddPaymentMethodWindow getAddPaymentMethodWindow(){
        return addPaymentMethodWindow;
    }

    public boolean isPageOpened(int wait){
        return isElementShownAgent(billingDetailsForm, wait);
    }

    public void clickNavItem(String navName){
        navItems.stream().filter(e -> e.getText().equalsIgnoreCase(navName)).findFirst().get().click();
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
//        waitForNotificationAlertToDisappear();
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
}
