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
        waitForElementToBeVisible(homeHeaderButton);
        homeHeaderButton.click();
        return new TwitterHomePage();
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
        waitForElementToBeVisibleByCss(newNotificationIcon, wait);
    }
}
