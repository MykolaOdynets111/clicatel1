package touchpages.pages;

import abstractclasses.AbstractSocialPage;
import driverfactory.DriverFactory;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class MainPage extends AbstractSocialPage {

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

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public MainPage() {
        super(DriverFactory.getTouchDriverInstance());
    }

    public boolean isChatIconShown() {
        waitUntilElementNotDisplayed(this.getCurrentDriver(), chatIcon, 3);
        return isElementShown(this.getCurrentDriver(), chatIcon, 3);
    }

    public Widget openWidget() {
    try {
        waitForElementToBeVisible(this.getCurrentDriver(), chatIcon, 5);
        moveToElemAndClick(this.getCurrentDriver(), chatIcon);
        return new Widget();
    } catch (TimeoutException e) {
        Assert.fail("Chat icon is not visible");
        return null;
    }
    }

    public String getTenantNameColor() {
        return Color.fromString(tenantName.getCssValue("color")).asHex();
    }

    public String getChatIconColor() {
        return Color.fromString(chatIcon.getCssValue("background-color")).asHex();
    }

    public String getHeaderColor() {
        return Color.fromString(header.getCssValue("background-color")).asHex();
    }

}
