package portaluielem;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

@FindBy(css = ".page-wrapper")
public class AutoRespondersWindow extends BasePortalWindow {

    @FindBy(css = ".auto-responder")
    private List<WebElement> autoResponderItems;

    public AutoResponderItem getTargetAutoResponderItem(String autoresponder){
        return autoResponderItems.stream().map(e -> new AutoResponderItem(e).setCurrentDriver(getCurrentDriver()))
                            .filter(e -> e.getTitle().trim().equalsIgnoreCase(autoresponder))
                            .findFirst().get();
    }

    public void waitToBeLoaded(){
        waitForElementToBeVisible(this.getCurrentDriver(), this.getWrappedElement(), 6);
    }

    public void clickExpandArrowForMessage(String autoresponder){
        getTargetAutoResponderItem(autoresponder).clickCollapseIcon();
    }

    public void waitForAutoRespondersToLoad(){
        try {
            waitForElementsToBeVisible(this.getCurrentDriver(), autoResponderItems, 16);
        } catch (TimeoutException e){
            Assert.fail("Auto responders are not loaded after 16 seconds wait");
        }
    }

    public void clickResetToDefaultForMessage(String autoresponder){
        waitForElementsToBeVisible(this.getCurrentDriver(), autoResponderItems, 5);
        getTargetAutoResponderItem(autoresponder).clickResetToDefaultButton();
    }
}
