package twitter;

import abstract_classes.AbstractPage;
import dataManager.TwitterUsers;
import driverManager.DriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import javax.print.DocFlavor;

public class TwitterLoginPage extends AbstractPage {

    @FindBy(css = "div.DashboardProfileCard")
    private WebElement profileDashboard;

    @FindBy(xpath = "//form[@data-element='form']")
    private WebElement loginForm;

    @FindBy(xpath = "//form[@data-element='form']//input[@type='text']")
    private WebElement emailInputField;

    private String emailInputOnSeparatePageXPATH = "//form//fieldset//input[@type='text']";

    @FindBy(xpath = "//form[@data-element='form']//input[@type='password']")
    private WebElement passInputField;

    private String passInputFieldOnSeparatePageXPATH = "//form//fieldset//input[@type='password']";

    @FindBy(xpath = "//form[@data-element='form']//input[@type='submit']")
    private WebElement loginButton;

    private String loginButtonOnSeparatePageXPATH = "//button[@type='submit']";

    private String filedForTelefonVerification = "input#challenge_response";

    @FindBy(css = "input#email_challenge_submit")
    private WebElement submitVerification;

    @FindAll({
            @FindBy(css = "a.StaticLoggedOutHomePage-buttonLogin"),
            @FindBy(css = "a[href='/login']")
    })
    private WebElement logInButton;

    @FindBy(css = "li.people.notifications")
    private WebElement notificationsButton;

    public static TwitterLoginPage openTwitterLoginPage() {
        //https://twitter.com/login?lang=en
        DriverFactory.getTouchDriverInstance().get("https://twitter.com/");
        return new TwitterLoginPage();
    }

    public TwitterLoginPage loginUser() {
        if(isElementShown(logInButton, 6)){
            logInButton.click();
            waitForElementsToBeVisibleByXpath(emailInputOnSeparatePageXPATH, 4);
            findElemByXPATH(emailInputOnSeparatePageXPATH).sendKeys(TwitterUsers.FIRST_USER.getTwitterUserEmail());
            findElemByXPATH(passInputFieldOnSeparatePageXPATH).sendKeys(TwitterUsers.FIRST_USER.getTwitterUserPass());
            findElemByXPATH(loginButtonOnSeparatePageXPATH).click();
        } else {
            emailInputField.sendKeys(TwitterUsers.FIRST_USER.getTwitterUserEmail());
            passInputField.sendKeys(TwitterUsers.FIRST_USER.getTwitterUserPass());
            loginButton.click();
        }
        TwitterUsers.setLoggedInUser(TwitterUsers.FIRST_USER);
        waitForElementToBeVisible(profileDashboard, 10);
        return this;
    }

    public void clickNotificationsButton(){
        notificationsButton.click();
    }
}
