package touchpages.pages;

import abstractclasses.AbstractPage;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class MainPage extends AbstractPage {

    @FindBy(css = "form#tenants-container")
    private WebElement tenantContainer;

    @FindBy(css = "form#tenants-container>p")
    private List<WebElement> tenants;

    @FindBy(css = "input.ctl-chat-widget-btn-open")
    private WebElement chatIcon;


    public boolean isChatIconShown() {
        try{
        waitForElementToBeInvisible(chatIcon, 3);}
        catch (TimeoutException e){}
        return isElementShown(chatIcon, 3);
    }

    public Widget openWidget() {
    try {
        waitForElementToBeVisible(chatIcon, 10);
        moveToElemAndClick(chatIcon);
        return new Widget();
    } catch (TimeoutException e) {
        Assert.assertTrue(false, "Chat icon is not visible");
        return null;
    }
    }
}
