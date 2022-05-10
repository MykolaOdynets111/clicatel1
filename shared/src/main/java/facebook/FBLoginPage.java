package facebook;

import abstractclasses.AbstractSocialPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import socialaccounts.FacebookUsers;

public class FBLoginPage extends AbstractSocialPage {

    @FindBy(xpath = "//span[text()='Will']")
    private WebElement facebookUserName;

    @FindBy(css = "form#login_form")
    private WebElement loginForm;

    @FindAll({
            @FindBy(xpath = "//input[@id='email']"),
            @FindBy(xpath = "//input[@name='email']")
    })
    private WebElement emailInputField;

    @FindAll({
            @FindBy(xpath = "//input[@id='pass']"),
            @FindBy(xpath = "//input[@name='pass']")
    })
    private WebElement passInputField;

    @FindBy(name = "login")
    private WebElement logInButton;

    private String continueWithFBIntegrationXPATH = "//button[contains(@name, 'CONFIRM')]";

    public FBLoginPage(WebDriver driver){
        super(driver);
    }

    public static FBLoginPage openFacebookLoginPage(WebDriver driver) {
        driver.get("https://www.facebook.com/");
        return new FBLoginPage(driver);
    }

    public void loginUser() {
        try {
            waitForElementToBeVisible(this.getCurrentDriver(), emailInputField, 6);
        }catch (TimeoutException e){
            Assert.fail("Login to fb unsuccessful\n page source: \n" + this.getCurrentDriver().getPageSource());
        }
        FacebookUsers fbUser = FacebookUsers.getFBTestUserFromCurrentEnv();
        emailInputField.sendKeys(fbUser.getFBUserEmail());
        passInputField.sendKeys(fbUser.getFBUserPass());
        FacebookUsers.setLoggedInUser(fbUser);
        if (isElementShown(this.getCurrentDriver(), loginForm, 2)) loginForm.submit();
        else logInButton.click();
        waitForElementToBeVisible(this.getCurrentDriver(), facebookUserName, 10);
    }

    public void loginUserForFBIntegration(){
        String fbWindowHandle = this.getCurrentDriver().getWindowHandle();
        waitForElementToBeVisible(this.getCurrentDriver(), emailInputField, 6);
        emailInputField.sendKeys(FacebookUsers.USER_FOR_INTEGRATION.getFBUserEmail());
        passInputField.sendKeys(FacebookUsers.USER_FOR_INTEGRATION.getFBUserPass());
        logInButton.click();
        for(int i = 0; i < 3; i++){
            try {
                if (isElementShownByXpath(this.getCurrentDriver(), continueWithFBIntegrationXPATH, 3)) {
                    findElemByXPATH(this.getCurrentDriver(), continueWithFBIntegrationXPATH).click();
                }
            }catch(NoSuchWindowException|StaleElementReferenceException e){

            }
        }
        for(String handle : this.getCurrentDriver().getWindowHandles()){
            if(!handle.equals(fbWindowHandle)) this.getCurrentDriver().switchTo().window(handle);
        }
    }
}
