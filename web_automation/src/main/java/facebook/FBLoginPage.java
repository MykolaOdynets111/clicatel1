package facebook;

import abstract_classes.AbstractPage;
import dataManager.FacebookUsers;
import driverManager.DriverFactory;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
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

    @FindBy(name = "login")
    private WebElement logInButton;

    @FindBy(xpath = "//button[contains(@name, 'CONFIRM')]")
    private WebElement continueWithFBIntegration;

    public static FBLoginPage openFacebookLoginPage() {
        DriverFactory.getTouchDriverInstance().get("https://www.facebook.com/");
        return new FBLoginPage(DriverFactory.getTouchDriverInstance());
    }

    public FBLoginPage(WebDriver driver){
        super(driver);
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

    public void loginUserForFBIntegration(){
        String fbWindowHandle = DriverFactory.getDriverForAgent("admin").getWindowHandle();
        waitForElementToBeVisibleAgent(emailInputField, 6);
        emailInputField.sendKeys(FacebookUsers.USER_FOR_INTEGRATION.getFBUserEmail());
        passInputField.sendKeys(FacebookUsers.USER_FOR_INTEGRATION.getFBUserPass());
        logInButton.click();
        try {
            while(isElementShownAgent(continueWithFBIntegration, 2)) continueWithFBIntegration.click();
        }catch (NullPointerException|NoSuchWindowException e){
            // Nothing to do here. The exception means there is mo fb window and we may proceed
        }
        for(String handle : DriverFactory.getDriverForAgent("admin").getWindowHandles()){
            if(!handle.equals(fbWindowHandle)) DriverFactory.getDriverForAgent("admin").switchTo().window(handle);
        }
    }
}
