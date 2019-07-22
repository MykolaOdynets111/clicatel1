package twitter;

import abstractclasses.AbstractPage;
import datamanager.TwitterUsers;
import drivermanager.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class TwitterLoginPage extends AbstractPage {

    @FindBy(css = "div.DashboardProfileCard")
    private WebElement profileDashboard;

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

    public static TwitterLoginPage openTwitterLoginPage() {
        //https://twitter.com/login?lang=en
        DriverFactory.getTouchDriverInstance().get("https://twitter.com/");
        return new TwitterLoginPage(DriverFactory.getTouchDriverInstance());
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

    public void loginUserForTwitterIntegration(){
        String twitterWindowHandle = DriverFactory.getDriverForAgent("admin").getWindowHandle();
        waitForElementToBeVisibleAgent(emailInputFieldIntegration, 6);
        emailInputFieldIntegration.sendKeys(TwitterUsers.TOUCHGO_USER.getTwitterUserEmail());
        passInputFieldIntegration.sendKeys(TwitterUsers.TOUCHGO_USER.getTwitterUserPass());
        logInButton.click();
        for(int i = 0; i<10; i++){
            if(DriverFactory.getDriverForAgent("admin").getWindowHandles().size()==1){
                break;
            }else{
                waitForDeprecated(200);
            }
        }
        if(DriverFactory.getDriverForAgent("admin").getWindowHandles().size()>1){
            Assert.fail("Twitter login page was not closed ");
        }
        for(String handle : DriverFactory.getDriverForAgent("admin").getWindowHandles()){
            if(!handle.equals(twitterWindowHandle)) DriverFactory.getDriverForAgent("admin").switchTo().window(handle);
        }
    }

    public void clickNotificationsButton(){
        notificationsButton.click();
    }
}
