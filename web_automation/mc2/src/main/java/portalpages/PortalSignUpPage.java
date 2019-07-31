package portalpages;

import driverfactory.MC2DriverFactory;
import io.qameta.allure.Step;
import mc2api.EndpointsPlatform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class PortalSignUpPage extends PortalAbstractPage {

    @FindBy(name = "fullName")
    private WebElement fullName;

    @FindBy(name = "accountName")
    private WebElement accountNameInput;

    @FindBy(name = "email")
    private WebElement emailInput;

    @FindBy(name = "password")
    private WebElement password;

    @FindBy(xpath = "//button[text()='Sign Up']")
    private WebElement signUpButton;

    @FindBy(xpath = "//div[text()='Your account has successfully been created!']")
    private WebElement successSignUpMessage;

    @FindBy(css = "form[name='signupForm'] input.ng-invalid")
    private List<WebElement> requiredInputErrors;


    // == Constructors == //

    public PortalSignUpPage() {
        super();
    }
    public PortalSignUpPage(String agent) {
        super(agent);
    }
    public PortalSignUpPage(WebDriver driver) {
        super(driver);
    }

    public static void openPortalSignUpPage(WebDriver driver) {
        driver.get(EndpointsPlatform.PORTAL_SIGN_UP_PAGE);
    }

    @Step("Navigate to sign up page")
    public static PortalSignUpPage openPortalSignUpPage() {
        MC2DriverFactory.getPortalDriver().get(EndpointsPlatform.PORTAL_SIGN_UP_PAGE);
        return new PortalSignUpPage();
    }

    @Step(value = "Sign up new account")
    public void signUp(String firstName, String accountName, String email, String pass){
        provideSignUpDetails(firstName, accountName, email, pass);
        clickSignUpButton();
    }

    @Step(value = "Fill in valid required information")
    public PortalSignUpPage provideSignUpDetails(String firstName, String accountName, String email, String pass){
        waitForElementToBeVisible(this.getCurrentDriver(), fullName, 5);
        fullName.clear();
        fullName.sendKeys(firstName);
        accountNameInput.clear();
        accountNameInput.sendKeys(accountName);
        emailInput.clear();
        emailInput.sendKeys(email);
        password.clear();
        password.sendKeys(pass);

        return this;
    }

    @Step(value = "Click Sign Up button")
    public PortalLoginPage clickSignUpButton(){
        signUpButton.click();
        return new PortalLoginPage(this.getCurrentDriver());
    }



    public boolean isSuccessSignUpMessageShown(){
        return isElementShown(this.getCurrentDriver(), successSignUpMessage, 20);
    }

    public boolean areRequiredErrorsShown(){
        return  areElementsShown(this.getCurrentDriver(), requiredInputErrors, 2);
    }



}
