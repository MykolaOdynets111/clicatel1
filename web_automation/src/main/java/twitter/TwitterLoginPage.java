package twitter;

import abstract_classes.AbstractPage;
import dataprovider.TwitterUsers;
import driverManager.DriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TwitterLoginPage extends AbstractPage {

    @FindBy(css = "div.DashboardProfileCard")
    private WebElement profileDashboard;

    @FindBy(xpath = "//form[@data-element='form']")
    private WebElement loginForm;

    @FindBy(xpath = "//form[@data-element='form']//input[@type='text']")
    private WebElement emailInputField;

    @FindBy(xpath = "//form[@data-element='form']//input[@type='password']")
    private WebElement passInputField;

    @FindBy(xpath = "//form[@data-element='form']//input[@type='submit']")
    private WebElement loginButton;

    public static TwitterLoginPage openTwitterLoginPage() {
        //https://twitter.com/login?lang=en
        DriverFactory.getInstance().get("https://twitter.com/");
        return new TwitterLoginPage();
    }

    public void loginUser() {
        waitForElementToBeVisible(loginForm, 6);
        emailInputField.sendKeys(TwitterUsers.FIRST_USER.getFBUserEmail());
        passInputField.sendKeys(TwitterUsers.FIRST_USER.getFBUserPass());
        TwitterUsers.setLoggedInUser(TwitterUsers.FIRST_USER);
        loginButton.click();
        waitForElementToBeVisible(profileDashboard, 10);
    }
}
