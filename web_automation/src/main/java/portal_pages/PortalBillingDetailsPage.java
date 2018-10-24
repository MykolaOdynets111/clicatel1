package portal_pages;

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

    private AddPaymentMethodWindow addPaymentMethodWindow;

    public AddPaymentMethodWindow getAddPaymentMethodWindow(){
        return addPaymentMethodWindow;
    }

    public boolean isPageOpened(int wait){
        return isElementShown(pageHeader, wait);
    }

    public void clickNavItem(String navName){
        navItems.stream().filter(e -> e.getText().equalsIgnoreCase(navName)).findFirst().get().click();
    }

    public boolean isAddPaymentMethodButtonShwon(int wait){
        return isElementShown(addPaymentMethodButton, wait);
    }

    public boolean isAddPaymentMethodWindowShwon(int wait){
        return isElementShown(getAddPaymentMethodWindow().getWrappedElement(), wait);
    }

    public void clickAddPaymentButton(){
        addPaymentMethodButton.click();
    }
}
