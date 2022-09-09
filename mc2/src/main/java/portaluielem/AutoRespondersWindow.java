package portaluielem;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.String.format;

@FindBy(css = ".page-wrapper")
public class AutoRespondersWindow extends BasePortalWindow {

    @FindBy(xpath = ".//div[contains(@class, 'auto-responder ')]")
    private List<WebElement> autoResponderItems;

    public AutoResponderItem getTargetAutoResponderItem(String autoresponder) {
        return autoResponderItems.stream().map(e -> new AutoResponderItem(e).setCurrentDriver(getCurrentDriver()))
                .filter(e -> e.getTitle().trim().equalsIgnoreCase(autoresponder)).findFirst().orElseThrow(() ->
                        new NoSuchElementException(format("There is no auto responder with title: %s", autoresponder)));
    }

    public void waitToBeLoaded() {
        waitForElementToBeVisible(this.getCurrentDriver(), this.getWrappedElement(), 6);
    }

    public void clickExpandArrowForMessage(String autoresponder) {
        getTargetAutoResponderItem(autoresponder).clickCollapseIcon();
    }

    public void waitForAutoRespondersToLoad() {
        try {
            waitForElementsToBeVisible(this.getCurrentDriver(), autoResponderItems, 16);
        } catch (TimeoutException e) {
            Assert.fail("Auto responders are not loaded after 16 seconds wait");
        }
    }

    public void clickResetToDefaultForMessage(String autoresponder) {
        waitForElementsToBeVisible(this.getCurrentDriver(), autoResponderItems, 5);
        getTargetAutoResponderItem(autoresponder).clickResetToDefaultButton();
    }

    public void clickOnOffForMessage(String autoresponder) {
        waitForElementsToBeVisible(this.getCurrentDriver(), autoResponderItems, 5);
        getTargetAutoResponderItem(autoresponder).clickOnOff();
    }
}
