package portalpages;

import driverfactory.DriverFactory;
import mc2api.EndpointsPlatform;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import portaluielem.PageHeader;

import java.util.List;

public class PortalLoginPage extends PortalAbstractPage {

    @FindBy(css = "input[type='email']")
    private WebElement emailInput;

    @FindBy(css = "input[type='password']")
    private WebElement passInput;

    @FindAll({
        @FindBy(xpath = "//button[text()='Login']"),
        @FindBy(css = "div.account-form button.button.button-primary")
    })
    private WebElement loginButton;

    @FindBy(css = "div[ng-show='newAccountEmail']")
    private WebElement confirmationEmailMessage;

    @FindBy(css = "input[type='password']")
    private List<WebElement> createPassInput;

    @FindBy(css = "div.invitation-welcome.ng-binding")
    private WebElement invitationWelcomeMsg;

    @FindBy(css = "div.set-new-password")
    private WebElement setNewPasswordLabel;

    PageHeader pageHeader;

    // == Constructors == //

    public PortalLoginPage() {
        super();
    }
    public PortalLoginPage(String agent) {
        super(agent);
    }
    public PortalLoginPage(WebDriver driver) {
        super(driver);
    }

    public static PortalLoginPage openPortalLoginPage() {
        DriverFactory.getAgentDriverInstance().get(EndpointsPlatform.PORTAL_LOGIN_PAGE);
        return new PortalLoginPage(DriverFactory.getAgentDriverInstance());
    }

    public PortalMainPage login(String email, String pass){
        try{
            enterAdminCreds(email, pass);
        }catch(TimeoutException e){
            getPageHeader().logoutAdmin();
            enterAdminCreds(email, pass);
        }
        waitWhileProcessing(2, 14);
        return new PortalMainPage(this.getCurrentDriver());
    }

    public void enterAdminCreds(String email, String pass){
        waitForElementToBeVisible(this.getCurrentDriver(), emailInput, 6);
        emailInput.sendKeys(email);
        passInput.sendKeys(pass);
        clickLogin();
    }

    public void clickLogin(){
        clickElem(this.getCurrentDriver(), loginButton, 1, "Login Button" );
    }

    public boolean isMessageAboutConfirmationMailSentShown(){
        return isElementShown(this.getCurrentDriver(), confirmationEmailMessage, 15);
    }

    public String getMessageAboutSendingConfirmationEmail(){
        try {
            return confirmationEmailMessage.getText();
        }catch(NoSuchElementException e){
            return "no elemnt to get the text from";
        }
    }

    public  boolean isLoginPageOpened(int wait){
        return isElementShown(this.getCurrentDriver(), emailInput, wait);
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
        return getTextFromElem(this.getCurrentDriver(), invitationWelcomeMsg, wait,
                "Welcome Text in Login screen");
    }

    public PortalLoginPage createNewPass(String pass){
        for(WebElement elem : createPassInput){
            elem.sendKeys(pass);
        }
        return this;
    }

    public String getNewPasswordLabel(){
        return getTextFromElem(this.getCurrentDriver(), setNewPasswordLabel, 4, "Set new password");
    }
}
