package facebook;

import abstract_classes.AbstractPage;
import dataManager.FacebookUsers;
import driverManager.DriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FBLoginPage extends AbstractPage {

    @FindBy(css = "div#bluebarRoot")
    private WebElement facebookHeader;

    @FindBy(css = "form#login_form")
    private WebElement loginForm;

    @FindBy(xpath = "//input[@id='email']")
    private WebElement emailInputField;

    @FindBy(xpath = "//input[@id='pass']")
    private WebElement passInputField;

    @FindBy(xpath = "//button[@name=login]")
    private WebElement logInButton;

    public static FBLoginPage openFacebookLoginPage() {
        DriverFactory.getTouchDriverInstance().get("https://www.facebook.com/");
        return new FBLoginPage();
    }

    public void loginUser() {
        waitForElementToBeVisible(emailInputField, 6);
        emailInputField.sendKeys(FacebookUsers.FIRST_USER.getFBUserEmail());
        passInputField.sendKeys(FacebookUsers.FIRST_USER.getFBUserPass());
        FacebookUsers.setLoggedInUser(FacebookUsers.FIRST_USER);
        if (isElementShown(loginForm, 2)) loginForm.submit();
        else logInButton.click();
        waitForElementToBeVisible(facebookHeader, 10);
    }
}
