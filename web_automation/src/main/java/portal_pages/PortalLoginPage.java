package portal_pages;

import api_helper.Endpoints;
import driverManager.DriverFactory;
import driverManager.URLs;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PortalLoginPage extends PortalAbstractPage {

    @FindBy(css = "input[type='email']")
    private WebElement emailInput;

    @FindBy(css = "input[type='password']")
    private WebElement passInput;

    @FindBy(xpath = "//button[text()='Log In']")
    private WebElement loginButton;

    @FindBy(css = "div[ng-show='newAccountEmail']")
    private WebElement confirmationEmailMessage;

    public static PortalLoginPage openPortalLoginPage() {
        DriverFactory.getAgentDriverInstance().get(Endpoints.PORTAL_LOGIN_PAGE);
        return new PortalLoginPage();
    }

    public PortalMainPage login(String email, String pass){
        waitForElementToBeVisibleAgent(emailInput, 10);
        emailInput.sendKeys(email);
        passInput.sendKeys(pass);
//        executeJSclick(loginButton);
        loginButton.click();
        return new PortalMainPage();
    }

    public boolean isMessageAboutConfirmationMailSentShown(){
        return isElementShownAgent(confirmationEmailMessage, 15);
    }

    public String getMessageAboutSendingConfirmationEmail(){
        try {
            return confirmationEmailMessage.getText();
        }catch(NoSuchElementException e){
            return "no elemnt to get the text from";
        }
    }

    public boolean isLoginPageOpened(){
        return isElementShownAgent(emailInput,1);
    }
}
