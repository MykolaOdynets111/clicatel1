package portalpages.uielements;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

@FindBy(css = "div.automated-messages")
public class AutoRespondersWindow extends BasePortalWindow {

    @FindBy(name = "clMessageForm")
    private List<WebElement> autoresponderItems;

    public AutoResponderItem getTargetAutoResponderItem(String autoresponder){
        return autoresponderItems.stream().map(e -> new AutoResponderItem(e))
                            .filter(e -> e.getTitle().contains(autoresponder))
                            .findFirst().get();
    }

    public void waitToBeLoaded(){
        waitForElementToBeVisibleAgent(this.getWrappedElement(), 6);
    }

    public void clickExpandArrowForMessage(String autoresponder){
        try {
            waitForElementsToBeVisibleAgent(autoresponderItems, 8, "admin");
        } catch (TimeoutException e){
            Assert.assertTrue(false, "Auto responders are not loaded after 8 seconds wait");
        }
        getTargetAutoResponderItem(autoresponder).clickCollapseIcon();
    }

    public void clickResetToDefaultForMessage(String autoresponder){
        waitForElementsToBeVisibleAgent(autoresponderItems, 5, "admin");
        getTargetAutoResponderItem(autoresponder).clickResetToDefaultButton();
    }

    public void clickOnOffForMessage(String autoresponder){
        waitForElementsToBeVisibleAgent(autoresponderItems, 5, "admin");
        getTargetAutoResponderItem(autoresponder).clickOnOff();
    }
}
