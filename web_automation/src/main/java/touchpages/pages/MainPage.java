package touchpages.pages;

import abstractclasses.AbstractPage;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
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

    @FindBy(css = "div.info > div.name")
    private WebElement tenantName;

    @FindBy(css = "div.header")
    private WebElement header;


    public boolean isChatIconShown() {
        try{
        waitForElementToBeInvisible(chatIcon, 3);}
        catch (TimeoutException e){}
        return isElementShown(chatIcon, 3);
    }

    public Widget openWidget() {
    try {
        waitForElementToBeVisible(chatIcon, 5);
        moveToElemAndClick(chatIcon);
        return new Widget();
    } catch (TimeoutException e) {
        Assert.assertTrue(false, "Chat icon is not visible");
        return null;
    }
    }

    public String getTenantNameColor() {
        String hexColor = Color.fromString(tenantName.getCssValue("color")).asHex();
        return hexColor;
    }

    public String getchatIconColor() {
        String hexColor = Color.fromString(chatIcon.getCssValue("background-color")).asHex();
        return hexColor;
    }

    public String getHeaderColor() {
        String hexColor = Color.fromString(header.getCssValue("background-color")).asHex();
        return hexColor;
    }

}
