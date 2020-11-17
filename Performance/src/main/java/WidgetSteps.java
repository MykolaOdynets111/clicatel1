import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class WidgetSteps implements Waits {

    private WebDriver driver;

    @FindBy(css = "input.ctl-chat-widget-btn-open")
    private WebElement chatIcon;

    @FindBy(css = ".ctl-chat-icon.ctl-chat-icon-dots")
    private WebElement chatDots;

    @FindBy(css = "li.ctl-chat-message-container.message-to")
    private WebElement salutationElement;

    @FindBy(css = "textarea.ctl-text-input")
    private WebElement textInput;

    @FindBy(css = "span.svg-bttn-container")
    private WebElement sendMessageButton;

    @FindBy(name = "firstName")
    private WebElement nameField;

    @FindBy(name = "email")
    private WebElement emailField;

    @FindBy(xpath = "//span[text() = 'Submit']")
    private WebElement submitButton;

    String toUserMessage = "//li[@class = 'ctl-chat-message-container message-to']//span[text()='%s']";

    public WidgetSteps(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void loadWidgetPage(String URL){
        driver.get(URL);
        chatIcon.isEnabled();
        chatIcon.isDisplayed();
    }

    public WidgetSteps openWidget() {
        chatDots.click();
        return this;
    }

    public WidgetSteps waitForSalutation(){
        salutationElement.isDisplayed();
        salutationElement.isEnabled();
        return this;
    }

    public WidgetSteps setText(String text){
        waitForElementToBeClickable(driver, textInput, 60);
        textInput.clear();
        textInput.sendKeys(text);
        return this;
    }

    public WidgetSteps clickSendButton(){
        waitForElementToBeClickable(driver, textInput, 5);
        sendMessageButton.click();
        return this;
    }

    public void waitForWidgetResponse(String text){
        waitForElementToBeVisibleByXpath(driver, (String.format(toUserMessage, text)), 100);
    }

    public void waitForUserInfoFormIsAppear(){
        waitForElementToBeClickable(driver, nameField, 100);
    }

    public void fillUserFormAndSubmit(String name, String mail){
        nameField.sendKeys(name);
        emailField.sendKeys(mail);
        submitButton.click();
    }

}
