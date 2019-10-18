package twitter;

import abstractclasses.AbstractSocialPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import socialaccounts.TwitterUsers;

public class TwitterLoginPage extends AbstractSocialPage {

    @FindBy(css = "div.DashboardProfileCard")
    private WebElement profileDashboard;

    @FindBy(xpath = "//a[@aria-label='Profile']")
    private WebElement profileButton;

    @FindBy(xpath = "//form[@data-element='form']")
    private WebElement loginForm;

    @FindBy(xpath = "//form[@data-element='form']//input[@type='text']")
    private WebElement emailInputField;

    @FindBy(xpath = "//div[@class='row user']/input")
    private WebElement emailInputFieldIntegration;

    private String emailInputOnSeparatePageXPATH = "//form//fieldset//input[@type='text']";

    @FindBy(xpath = "//form[@data-element='form']//input[@type='password']")
    private WebElement passInputField;


    @FindBy(xpath = "//div[@class='row password']/input")
    private WebElement passInputFieldIntegration;

    private String passInputFieldOnSeparatePageXPATH = "//form//fieldset//input[@type='password']";

    @FindBy(xpath = "//form[@data-element='form']//input[@type='submit']")
    private WebElement loginButton;

    private String loginButtonOnSeparatePageXPATH = "//button[@type='submit']";

    private String filedForTelefonVerification = "input#challenge_response";

    @FindBy(css = "input#email_challenge_submit")
    private WebElement submitVerification;

    @FindBy(css = "form#login-challenge-form")
    private WebElement loginChallengeForm;

    @FindAll({
            @FindBy(css = "a.StaticLoggedOutHomePage-buttonLogin"),
            @FindBy(css = "a[href='/login']"),
            @FindBy(xpath = "//input[@id='allow']")
    })
    private WebElement logInButton;

    @FindBy(css = "li.people.notifications")
    private WebElement notificationsButton;


    public TwitterLoginPage(WebDriver driver){
        super(driver);
    }

    public static TwitterLoginPage openTwitterLoginPage(WebDriver driver) {
        //https://twitter.com/login?lang=en
        driver.get("https://twitter.com/");
        return new TwitterLoginPage(driver);
    }

    public TwitterLoginPage loginUser() {
        if(isElementShown(this.getCurrentDriver(), logInButton, 6)){
            logInButton.click();
            waitForElementsToBeVisibleByXpath(this.getCurrentDriver(), emailInputOnSeparatePageXPATH, 4);
            findElemByXPATH(this.getCurrentDriver(),emailInputOnSeparatePageXPATH)
                    .sendKeys(TwitterUsers.THIRD_USER.getTwitterUserEmail());
            findElemByXPATH(this.getCurrentDriver(), passInputFieldOnSeparatePageXPATH)
                    .sendKeys(TwitterUsers.THIRD_USER.getTwitterUserPass());
            findElemByXPATH(this.getCurrentDriver(), loginButtonOnSeparatePageXPATH).click();
        } else {
            emailInputField.sendKeys(TwitterUsers.THIRD_USER.getTwitterUserEmail());
            passInputField.sendKeys(TwitterUsers.THIRD_USER.getTwitterUserPass());
            loginButton.click();
        }
        if(isElementShown(this.getCurrentDriver(), loginChallengeForm, 2)){
            findElemByCSS(this.getCurrentDriver(), filedForTelefonVerification)
                    .sendKeys(TwitterUsers.THIRD_USER.getUserPhone());
            loginChallengeForm.submit();
        }
        TwitterUsers.setLoggedInUser(TwitterUsers.THIRD_USER);
        waitForElementToBeVisible(this.getCurrentDriver(), profileButton, 10);
        return this;
    }

    public void loginUserForTwitterIntegration(){
        String twitterWindowHandle = this.getCurrentDriver().getWindowHandle();
        waitForElementToBeVisible(this.getCurrentDriver(), emailInputFieldIntegration, 6);
        emailInputFieldIntegration.sendKeys(TwitterUsers.TOUCHGO_USER.getTwitterUserEmail());
        passInputFieldIntegration.sendKeys(TwitterUsers.TOUCHGO_USER.getTwitterUserPass());
        logInButton.click();
        for(int i = 0; i<10; i++){
            if(this.getCurrentDriver().getWindowHandles().size()==1){
                break;
            }else{
                waitFor(200);
            }
        }
        if(this.getCurrentDriver().getWindowHandles().size()>1){
            Assert.fail("Twitter login page was not closed ");
        }
        for(String handle : this.getCurrentDriver().getWindowHandles()){
            if(!handle.equals(twitterWindowHandle)) this.getCurrentDriver().switchTo().window(handle);
        }
    }

    public void clickNotificationsButton(){
       notificationsButton.click();
    }
}
