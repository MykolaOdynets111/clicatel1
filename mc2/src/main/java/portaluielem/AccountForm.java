package portaluielem;

import io.qameta.allure.Step;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;


@FindBy(css = "div.cl-card.account-form")
public class AccountForm extends BasePortalWindow {

    @FindBy(css = "input[type='email']")
    private WebElement emailInput;

    @FindBy(css = "input[type='password']")
    private WebElement passInput;

    @FindBy(css = "input[type='password']")
    private List<WebElement> createPassInput;

    @FindAll({
            @FindBy(xpath = "//button[text()='Login']"),
            @FindBy(css = "div.account-form button.button.button-primary")
    })
    private WebElement loginButton;

    @FindBy(css = "a[ui-sref='forgotPasswordEmail']")
    private WebElement forgotPasswordLink;

    @FindBy(xpath = "//span[text()='Your Forgot Password email has been sent!']")
    private WebElement forgotPasswordSent;

    @FindBy(xpath = "//div[@ng-show='showCheckInboxMessageCard']")
    private WebElement forgotPasswordInstructions;

    @FindBy(css = "div.set-new-password")
    private WebElement setNewPasswordLabel;

    @FindBy(css = "div[ng-show='newAccountEmail']")
    private WebElement newAccConfirmationEmailMessage;

    @FindBy(css = "div.invitation-welcome.ng-binding")
    private WebElement invitationWelcomeMsg;

    // ======== Sign Up ====== //

    public boolean areCreatePasswordInputsShown(int wait){
        int numbers = createPassInput.size();
        if(numbers!=2){
            waitFor(wait);
            numbers = createPassInput.size();
        }
        return numbers==2;
    }

    public AccountForm createNewPass(String pass){
        for(WebElement elem : createPassInput){
            elem.sendKeys(pass);
        }
        return this;
    }

    public boolean isMessageAboutConfirmationMailSentShown(){
        return isElementShown(this.getCurrentDriver(), newAccConfirmationEmailMessage, 15);
    }

    public String getWelcomeMessage(int wait){
        return getTextFromElem(this.getCurrentDriver(), invitationWelcomeMsg, wait,
                "Welcome Text in Login screen");
    }

    @Step(value = "Get message about confirmation email sent")
    public String getMessageAboutSendingConfirmationEmail(){
        try {
            return newAccConfirmationEmailMessage.getText();
        }catch(NoSuchElementException e){
            return "no elemnt to get the text from";
        }
    }


    // ======== Login ======= //

    public AccountForm enterAdminCreds(String email, String pass){
        waitForElementToBeVisible(this.getCurrentDriver(), emailInput, 6);
        emailInput.clear();
        emailInput.sendKeys(email);
        passInput.clear();
        passInput.sendKeys(pass);
        return this;
    }

    @Step(value = "Enter existing account email and Submit")
    public void enterEmailAndSubmit(String email) {
        waitForElementToBeVisible(this.getCurrentDriver(), emailInput, 6);
        emailInput.sendKeys(email);
        clickLogin();
    }

    public void clickLogin(){
        clickElem(this.getCurrentDriver(), loginButton, 1, "Login Button" );
    }

    public boolean isEmailInputShown(int wait){
        return isElementShown(this.getCurrentDriver(), emailInput, wait);
    }

    // ==== Create / Forgot Password ===== //

    @Step(value = "Click 'Forgot password?' from the log in page")
    public AccountForm clickForgotPasswordLink(){
        clickElem(this.getCurrentDriver(), forgotPasswordLink, 2, "Forgot password");
        return this;
    }

    public String getNewPasswordLabel(){
        return getTextFromElem(this.getCurrentDriver(), setNewPasswordLabel, 4, "Set new password");
    }

    @Step(value = "Verify \"Set new password\" page opened")
    public Boolean isSetNewPasswordLabelShown(int wait){
        return isElementShown(this.getCurrentDriver(), setNewPasswordLabel, wait);
    }

    @Step(value = "Verify \"Your Forgot Password email has been sent!\" message is displayed")
    public boolean isForgotPasswordSentNotificationShown(){
        return isElementShown(this.getCurrentDriver(), forgotPasswordSent, 5);
    }

    @Step(value = "Verify Instructions are not empty")
    public boolean isInstructionsEmpty(){
        return forgotPasswordInstructions.getText()
                .replace("Your Forgot Password email has been sent!", "").trim().isEmpty();
    }


}
