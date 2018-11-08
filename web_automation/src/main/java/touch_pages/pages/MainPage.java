package touch_pages.pages;

import abstract_classes.AbstractPage;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import touch_pages.uielements.TenantRow;

import java.util.List;

public class MainPage extends AbstractPage {

    @FindBy(css = "form#tenants-container")
    private WebElement tenantContainer;

    @FindBy(css = "form#tenants-container>p")
    private List<WebElement> tenants;

    @FindBy(css = "input.ctl-chat-widget-btn-open")
    private WebElement chatIcon;


//    public MainPage selectTenant(String tenantName) {
//        try {
//        waitForElementToBeVisible(tenantContainer, 25);
//        new TenantRow(
//                        tenants.stream().filter(e -> e.getText().equalsIgnoreCase(tenantName)).findFirst().get())
//                .selectTenant();
//        return this;
//        } catch(TimeoutException e) {
//            Assert.assertTrue(false, "Tenants list is not shown");
//            return this;
//        }
//
//    }


    public boolean isChatIconShown() {
        try{
        waitForElementToBeInvisible(chatIcon, 8);}
        catch (TimeoutException e){}
        return isElementShown(chatIcon, 8);
    }

    public Widget openWidget() {
    try {
        waitForElementToBeVisible(chatIcon, 25);
        moveToElemAndClick(chatIcon);
        return new Widget();
    } catch (TimeoutException e) {
        Assert.assertTrue(false, "Chat icon is not visible");
        return null;
    }
    }
}
