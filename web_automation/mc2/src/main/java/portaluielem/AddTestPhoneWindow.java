package portaluielem;

import dbmanager.DBConnector;
import drivermanager.ConfigManager;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

@FindBy(css = "div.modal-content")
public class AddTestPhoneWindow extends BasePortalWindow {

    @FindBy(css = "input#intl-phone-number-id")
    private WebElement phoneInput;

    @FindBy(css = "input[name='otp']")
    private WebElement otpInput;

    @FindBy(css = "button#integration-next")
    private WebElement addPhoneButton;

    @FindBy(css = "button[ng-if='newPhone.otp']")
    private WebElement registerButton;

    @FindBy(xpath = "//button[text()='Add another']")
    private WebElement addAnotherButton;

    @FindBy(css = "div.text-muted.content-description")
    private WebElement successMessage;

    @FindBy(css = "button#integration-save")
    private WebElement finishButton;

    @Step(value = "Provide test phone number")
    public AddTestPhoneWindow enterTestPhone(String phone){
        waitForAngularRequestsToFinish(this.getCurrentDriver());
        waitForAngularToBeReady(this.getCurrentDriver());
        phoneInput.clear();
        phoneInput.sendKeys(phone);
        return this;
    }

    @Step(value = "Click 'Add phone' button")
    public AddTestPhoneWindow clickAddPhoneButton(){
        waitForAngularRequestsToFinish(this.getCurrentDriver());
        waitForElementToBeClickable(this.getCurrentDriver(), addPhoneButton, 1);
        executeAngularClick(this.getCurrentDriver(), addPhoneButton);
        return this;
    }

    @Step(value = "Provide verification code (OTP)")
    public AddTestPhoneWindow enterVerificationOTPCode(String accountId, String phone){
        String otp = DBConnector.getVerificationOTPCode(ConfigManager.getEnv(), accountId, phone);
        for(int i = 0; i<5; i++){
            if(otp==null){
                waitFor(1000);
                otp = DBConnector.getVerificationOTPCode(ConfigManager.getEnv(), accountId, phone);
            }else break;
        }
        if(otp==null){
            Assert.fail("OTP code was not found for account " + accountId + " and phone " + phone);
        }
        otpInput.sendKeys(otp);
        return this;
    }

    @Step(value = "Click 'Register' button")
    public AddTestPhoneWindow clickRegisterButton(){
        waitForAngularRequestsToFinish(this.getCurrentDriver());
        executeAngularClick(this.getCurrentDriver(), registerButton);
        return this;
    }

    @Step(value = "Click 'Add another' button")
    public AddTestPhoneWindow clickAddAnotherButton(){
        waitForAngularRequestsToFinish(this.getCurrentDriver());
        waitForElementToBeClickable(this.getCurrentDriver(), addAnotherButton, 2);
        executeAngularClick(this.getCurrentDriver(), addAnotherButton);
        return this;
    }

    @Step(value = "Verify success message")
    public String getSuccessMessage(){
        return getTextFromElem(this.getCurrentDriver(), successMessage, 5, "Message about test phone adding");
    }

    @Step(value = "Click 'Finish' button")
    public AddTestPhoneWindow clickFinishButton(){
        waitForAngularRequestsToFinish(this.getCurrentDriver());
        executeAngularClick(this.getCurrentDriver(), finishButton);
        return this;
    }

}
