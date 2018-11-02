package portal_pages;

import api_helper.Endpoints;
import driverManager.DriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

    public static PortalSignUpPage openPortalSignUpPage() {
        DriverFactory.getAgentDriverInstance().get(Endpoints.PORTAL_SIGN_UP_PAGE);
        return new PortalSignUpPage();
    }

    public void signUp(String accountName, String email, String pass){
        waitForElementToBeVisibleAgent(fullName, 5);
        fullName.sendKeys("Taras AQA");
        accountNameInput.sendKeys(accountName);
        emailInput.sendKeys(email);
        password.sendKeys(pass);
        signUpButton.click();
    }
}
