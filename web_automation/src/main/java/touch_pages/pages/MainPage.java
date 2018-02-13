package touch_pages.pages;

import abstract_classes.AbstractPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import touch_pages.uielements.TenantRow;

import java.util.List;

public class MainPage extends AbstractPage {

    @FindBy(css = "form#tenants-container")
    private WebElement tenantContainer;

    @FindBy(css = "form#tenants-container>p")
    private List<WebElement> tenants;

    @FindBy(css = "input.ctl-chat-widget-btn-open")
    private WebElement chatIcon;


    public MainPage selectTenant(String tenantName) {
        waitForElementToBeVisible(tenantContainer, 10);
        new TenantRow(
                        tenants.stream().filter(e -> e.getText().equalsIgnoreCase(tenantName)).findFirst().get())
                .selectTenant();
        return this;
    }

    public Widget openWidget() {
        waitForElementToBeVisible(chatIcon, 25);
        moveToElemAndClick(chatIcon);
//        executeJSclick(chatIcon);
        return new Widget();
    }
}
