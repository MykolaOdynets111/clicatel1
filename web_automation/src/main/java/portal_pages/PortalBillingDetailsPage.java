package portal_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portal_pages.uielements.LeftMenu;
import portal_pages.uielements.PageHeader;
import portal_pages.uielements.UpgradeYourPlanWindow;

public class PortalBillingDetailsPage extends PortalAbstractPage {


    @FindBy(css = "li[ui-sref='billingDetails']")
    private WebElement pageHeader;

    public boolean isPageOpened(int wait){
        return isElementShown(pageHeader, wait);
    }
}
