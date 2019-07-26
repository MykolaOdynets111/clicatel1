package portalpages;

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

    @Step(value = "Sign up new account")
    public void signUp(String firstName, String accountName, String email, String pass){
        waitForElementToBeVisible(this.getCurrentDriver(), fullName, 5);
        setFirstName(firstName);
        setAccountName(accountName);
        emailInput.clear();
        emailInput.sendKeys(email);
        password.clear();
        password.sendKeys(pass);
        signUpButton.click();
    }

    @Step(value = "Set first name")
    public void setFirstName(String firstName){
        fullName.clear();
        fullName.sendKeys(firstName);
    }

    @Step(value = "Set account name")
    public void setAccountName(String accountName){
        accountNameInput.clear();
        accountNameInput.sendKeys(accountName);
    }

    public boolean isSuccessSignUpMessageShown(){
        return isElementShown(this.getCurrentDriver(), successSignUpMessage, 20);
    }

    public boolean areRequiredErrorsShown(){
        return  areElementsShown(this.getCurrentDriver(), requiredInputErrors, 2);
    }



}
