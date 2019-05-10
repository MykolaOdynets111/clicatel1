package portalpages;

import apihelper.Endpoints;
import drivermanager.DriverFactory;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portalpages.uielements.PageHeader;

public class PortalLoginPage extends PortalAbstractPage {

    @FindBy(css = "input[type='email']")
    private WebElement emailInput;

    @FindBy(css = "input[type='password']")
    private WebElement passInput;

    @FindBy(xpath = "//button[text()='Log In']")
    private WebElement loginButton;

    @FindBy(css = "div[ng-show='newAccountEmail']")
    private WebElement confirmationEmailMessage;

    PageHeader pageHeader;

    public static PortalLoginPage openPortalLoginPage() {
        DriverFactory.getAgentDriverInstance().get(Endpoints.PORTAL_LOGIN_PAGE);
        return new PortalLoginPage();
    }

    public PortalMainPage login(String email, String pass){
        try{
            enterAdminCreds(email, pass);
        }catch(TimeoutException e){
            pageHeader.logoutAdmin();
            enterAdminCreds(email, pass);
        }
        waitWhileProcessing();
        return new PortalMainPage();
    }

    private void enterAdminCreds(String email, String pass){
        waitForElementToBeVisibleAgent(emailInput, 6);
        emailInput.sendKeys(email);
        passInput.sendKeys(pass);
        loginButton.click();
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

    public  boolean isLoginPageOpened(int wait){
        return isElementShownAgent(emailInput,wait);
    }
}
