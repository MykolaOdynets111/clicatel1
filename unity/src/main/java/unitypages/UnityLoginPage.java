package unitypages;

import abstractclasses.AgentAbstractPage;
import driverfactory.UnityURLs;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class UnityLoginPage extends AgentAbstractPage {

    @FindBy(css = "[id='email']")
    private WebElement emailAddressField;

    @FindBy(css = "[id='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//iframe[starts-with(@name,'a-')]")
    private WebElement captchaFrame;

    @FindBy(xpath = "//div[@class='recaptcha-checkbox-border']")
    private WebElement captchaCheck;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement signInBtn;

    public UnityLoginPage() {
        getCurrentDriver().get(UnityURLs.getUnityLoginForm());
    }

    public void loginToUnity(String email, String password){

        WebDriver driver = this.getCurrentDriver();

        inputText(driver,emailAddressField,10,"Email Field",email);
        inputText(driver,passwordField,3,"Password Field",password);
        clickElem(driver,signInBtn,15,"Sign In Button");
    }

    private void bypassCaptcha(){
        switchToFrameByXpath(this.getCurrentDriver(),captchaFrame,25,"Captcha Frame");

        clickElem(this.getCurrentDriver(),captchaCheck,10,"Captcha Check Mark");

        getCurrentDriver().switchTo().defaultContent();
    }
}
