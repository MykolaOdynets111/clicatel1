package portal_pages;

import api_helper.Endpoints;
import driverManager.DriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PortalSignUpPage extends PortalAbstractPage {

    @FindBy(name = "fullName")
    private WebElement fullName;

    @FindBy(name = "accountName")
    private WebElement accountName;

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

    public void signUp(String email, String pass){
        waitForElementToBeVisibleAgent(fullName, 5);
        fullName.sendKeys("Taras AQA");
        accountName.sendKeys("testaccount");
        emailInput.sendKeys("aqa@test.com");
        password.sendKeys("p@$$w0rd4te$t");
        signUpButton.click();
    }
}
