package portalpages;

import apihelper.Endpoints;
import drivermanager.DriverFactory;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import portalpages.uielements.PageHeader;

import java.util.List;

public class PortalLoginPage extends PortalAbstractPage {

    @FindBy(css = "input[type='email']")
    private WebElement emailInput;

    @FindBy(css = "input[type='password']")
    private WebElement passInput;

    @FindAll({
        @FindBy(xpath = "//button[text()='Log In']"),
        @FindBy(xpath = "//button[text()='Login']")
    })
    private WebElement loginButton;

    @FindBy(css = "div[ng-show='newAccountEmail']")
    private WebElement confirmationEmailMessage;

    @FindBy(css = "input[type='password']")
    private List<WebElement> createPassInput;

    @FindBy(css = "div.invitation-welcome.ng-binding")
    private WebElement invitationWelcomeMsg;

    PageHeader pageHeader;

    public PortalLoginPage(String agent) {
        super(agent);
    }
    public PortalLoginPage() {
        super();
    }

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

    public void enterAdminCreds(String email, String pass){
        waitForElementToBeVisibleAgent(emailInput, 6);
        emailInput.sendKeys(email);
        passInput.sendKeys(pass);
        clickLogin();
    }

    public void clickLogin(){
        clickElemAgent(loginButton, 1, this.currentAgent, "Login Button" );
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

    public boolean areCreatePasswordInputsShown(int wait){
        int numbers = createPassInput.size();
        if(numbers!=2){
            waitFor(wait);
            numbers = createPassInput.size();
        }
        return numbers==2;
    }

    public String getWelcomeMessage(int wait){
        return getTextFromElemAgent(invitationWelcomeMsg, wait, getCurrentAgent(),
                "Welcome Text in Login screen");
    }

    public PortalLoginPage createNewPass(String pass){
        for(WebElement elem : createPassInput){
            elem.sendKeys(pass);
        }
        return this;
    }
}
