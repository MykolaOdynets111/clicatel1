package facebook;

import abstract_classes.AbstractPage;
import dataprovider.FacebookUsers;
import driverManager.DriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FBLoginPage extends AbstractPage {

    @FindBy(css = "div#bluebarRoot")
    private WebElement facebookHeader;

    @FindBy(css = "form#login_form")
    private WebElement loginForm;

    @FindBy(css = "input[type='email']")
    private WebElement emailInputField;

    @FindBy(css = "input#pass")
    private WebElement passInputField;

    @FindBy(css = "label#loginbutton")
    private WebElement loginButton;

    public static FBLoginPage openFacebookLoginPage() {
        DriverFactory.getTouchDriverInstance().get("https://www.facebook.com/");
        return new FBLoginPage();
    }

    public void loginUser() {
        waitForElementToBeVisible(loginForm, 6);
        emailInputField.sendKeys(FacebookUsers.FIRST_USER.getFBUserEmail());
        passInputField.sendKeys(FacebookUsers.FIRST_USER.getFBUserPass());
        FacebookUsers.setLoggedInUser(FacebookUsers.FIRST_USER);
        loginButton.click();
        waitForElementToBeVisible(facebookHeader, 10);
    }
}
