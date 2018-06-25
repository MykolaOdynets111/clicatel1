package portal_pages;

import agent_side_pages.AgentLoginPage;
import driverManager.DriverFactory;
import driverManager.URLs;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PortalLoginPage extends PortalAbstractPage {

    @FindBy(css = "input[type='email']")
    private WebElement emailInput;

    @FindBy(css = "input[type='password']")
    private WebElement passInput;

    @FindBy(xpath = "//button[text()='Log In']")
    private WebElement loginButton;

    public static PortalLoginPage openPortalLoginPage() {
        DriverFactory.getSecondDriverInstance().get(URLs.getPortalURL());
        return new PortalLoginPage();
    }

    public void login(String email, String pass){
        waitForElementToBeVisibleAgent(emailInput, 10);
        emailInput.sendKeys(email);
        passInput.sendKeys(pass);
        loginButton.click();
    }
}
