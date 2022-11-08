package unitypages;

import abstractclasses.UnityAbstractPage;
import driverfactory.UnityURLs;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class UnityLoginPage extends UnityAbstractPage {

    @FindBy(css = "[id='email']")
    private WebElement emailAddressField;

    @FindBy(css = "[id='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//iframe[@title='reCAPTCHA']")
    private WebElement captchaFrame;

    @FindBy(xpath = "//div[@class='recaptcha-checkbox-border']")
    private WebElement captchaCheck;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement signInBtn;

// Todo : Fix constructor
    public UnityLoginPage() {
        super();
        getCurrentDriver().get(UnityURLs.getUnityLoginForm());
    }

    public UnityLoginPage openChatHubLoginPage() {
        getCurrentDriver().get(UnityURLs.getUnityLoginForm());
        return this;
    }

    public UnityLoginPage loginToUnity(String email, String password){
        inputText(this.getCurrentDriver(),emailAddressField,3,"Email Field",email);
        inputText(this.getCurrentDriver(),passwordField,3,"Password Field",password);
        bypassCaptcha();
        clickElem(this.getCurrentDriver(),signInBtn,10,"Sign In Button");
        return this;
    }

    private void bypassCaptcha(){
        String parentWindow = getCurrentDriver().getWindowHandle();

        switchToFrameByXpath(this.getCurrentDriver(),captchaFrame,25,"Captcha Frame");

        clickElem(this.getCurrentDriver(),captchaCheck,10,"Captcha Check Mark");

        getCurrentDriver().switchTo().window(parentWindow);
    }
}
