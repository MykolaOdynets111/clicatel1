package portal_pages;

import api_helper.Endpoints;
import driverManager.DriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

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



    public static PortalSignUpPage openPortalSignUpPage() {
        DriverFactory.getAgentDriverInstance().get(Endpoints.PORTAL_SIGN_UP_PAGE);
        return new PortalSignUpPage();
    }

    public void signUp(String firstName, String accountName, String email, String pass){
        waitForElementToBeVisibleAgent(fullName, 5);
        fullName.clear();
        fullName.sendKeys(firstName);
        accountNameInput.clear();
        accountNameInput.sendKeys(accountName);
        emailInput.clear();
        emailInput.sendKeys(email);
        password.clear();
        password.sendKeys(pass);
        signUpButton.click();
    }

    public boolean isSuccessSignUpMessageShown(){
        return isElementShownAgent(successSignUpMessage, 10);
    }

    public boolean areRequiredErrorsShown(){
        return  areElementsShownAgent(requiredInputErrors, 2, "admin");
    }



}
