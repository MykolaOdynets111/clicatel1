package twitter.uielements;

import abstract_classes.AbstractUIElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = "div.container")
public class TwitterHeader extends AbstractUIElement {

    @FindBy(css = "a[data-component-context='home_nav']")
    private WebElement homeHeaderButton;

    @FindBy(xpath = "//span[text()='Notifications']")
    private WebElement notificationsIcon;

    private String newNotificationIcon="span.count.new-count";


    public void openHomePage(){
        waitForElementToBeVisible(homeHeaderButton);
        homeHeaderButton.click();
    }

    public void clickNotificationsIcon(){
        notificationsIcon.click();
    }

    public boolean waitForNewNotificationIconToBeShown(int wait) {
        try {
            waitForElementToBeVisibleByXpath(newNotificationIcon, wait);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
