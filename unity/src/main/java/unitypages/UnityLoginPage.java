package unitypages;

import abstractclasses.UnityAbstractPage;
import driverfactory.UnityURLs;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;


public class UnityLoginPage extends UnityAbstractPage {

    @FindBy(css = "[id='email']")
    private WebElement emailAddressField;

    @FindBy(css = "[id='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement signInBtn;


    public UnityLoginPage() {
        super();
        getCurrentDriver().get(UnityURLs.getChatHubLoginForm());
    }

    public UnityLoginPage openChatHubLoginPage() {
        getCurrentDriver().get(UnityURLs.getChatHubLoginForm());
        return this;
    }

    public UnityLoginPage loginToUnity(String email, String password){
        inputText(this.getCurrentDriver(),emailAddressField,3,"Email Field",email);
        inputText(this.getCurrentDriver(),passwordField,3,"Password Field",password);
        clickElem(this.getCurrentDriver(),signInBtn,3,"Sign In Button");
        return this;
    }
}
