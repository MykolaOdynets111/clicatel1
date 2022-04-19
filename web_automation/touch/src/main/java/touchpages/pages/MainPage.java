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

    @FindBy(css = "input.ctl-chat-widget-btn-open")
    private WebElement chatIcon;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public MainPage() {
        super(DriverFactory.getTouchDriverInstance());
    }

    public boolean isChatIconShown() {
        try{
            waitForElementToBeInvisible(this.getCurrentDriver(), chatIcon, 3);}
        catch (TimeoutException e){}
        return isElementShown(this.getCurrentDriver(), chatIcon, 3);
    }

    public Widget openWidget() {
    try {
        waitForElementToBeVisible(this.getCurrentDriver(), chatIcon, 7);
        moveToElemAndClick(this.getCurrentDriver(), chatIcon);
        return new Widget();
    } catch (TimeoutException e) {
        Assert.fail("Chat icon is not visible");
        return null;
    }
    }

}
