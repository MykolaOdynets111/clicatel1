package twitter.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import twitter.TwitterHomePage;

@FindBy(css = "div.container")
public class TwitterHeader extends AbstractUIElement {

    @FindBy(css = "a[data-component-context='home_nav']")
    private WebElement homeHeaderButton;

    @FindBy(xpath = "//span[text()='Notifications']")
    private WebElement notificationsIcon;

    private String newNotificationIcon="span.count.new-count";


    public TwitterHomePage openHomePage(){
        waitForElementToBeVisible(this.getCurrentDriver(), homeHeaderButton, 5);
        homeHeaderButton.click();
        return new TwitterHomePage(this.getCurrentDriver());
    }

    public void clickNotificationsIcon(){
        notificationsIcon.click();
    }

    public boolean waitForNewNotificationIconToBeShown(int wait) {
        try {
            checkIfNewNotificationShown(wait);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void checkIfNewNotificationShown(int wait){
        waitForElementToBeVisibleByCss(this.getCurrentDriver(), newNotificationIcon, wait);
    }
}
