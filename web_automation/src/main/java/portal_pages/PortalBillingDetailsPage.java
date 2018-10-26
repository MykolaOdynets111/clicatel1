package portal_pages;

import driverManager.DriverFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portal_pages.uielements.AddPaymentMethodWindow;
import portal_pages.uielements.LeftMenu;
import portal_pages.uielements.PageHeader;
import portal_pages.uielements.UpgradeYourPlanWindow;

import java.util.List;

public class PortalBillingDetailsPage extends PortalAbstractPage {

    @FindBy(css = "li[ui-sref='billingDetails']")
    private WebElement pageHeader;

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

    @FindBy(css = "div.cl-header--item>div>button")
    private WebElement removePaymentButton;

    @FindBy(xpath = "//button[text()='Accept']")
    private WebElement removePaymentConfirmationButton;

    private AddPaymentMethodWindow addPaymentMethodWindow;

    public AddPaymentMethodWindow getAddPaymentMethodWindow(){
        return addPaymentMethodWindow;
    }

    public boolean isPageOpened(int wait){
        return isElementShownAgent(pageHeader, wait);
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

    public boolean isNewPaymentAdded() { return isElementShownAgent(addedPayment);}

    public String getPaymentMethodDetails(){ return paymentMethodDetails.getText();}

    public void deletePaymentMethod(){
        managePaymentMethodButton.click();
        waitForElementToBeVisibleAgent(removePaymentButton, 9);
        JavascriptExecutor executor = (JavascriptExecutor) DriverFactory.getAgentDriverInstance();
        executor.executeScript("arguments[0].click();", removePaymentButton);
        removePaymentConfirmationButton.click();

    }
}
