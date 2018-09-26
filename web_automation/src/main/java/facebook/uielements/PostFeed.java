package facebook.uielements;
import abstract_classes.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
@FindBy(xpath = "//div[@id='PageComposerPagelet_']")
public class PostFeed extends AbstractUIElement {

    @FindBy(xpath = "//form[@rel='async']//textarea[@name='xhpc_message_text']")
    private WebElement postArea;

    @FindBy(xpath = "//span[text()='Post']")
    private WebElement postButton;

    @FindBy(xpath = "//div[@role='presentation']//div[@role='textbox']")
    private WebElement postInputField;

    private String closeDMPopupButton = "//a[@aria-label='Close tab']";

    public void makeAPost(String userPostText){
        waitForElementToBeVisible(postInputField, 6);
        if (isElementShownByXpath(closeDMPopupButton, 10)) findElemByXPATH(closeDMPopupButton).click();
        postInputField.click();
        postInputField.sendKeys(userPostText);
        if (isElementShownByXpath(closeDMPopupButton, 5)) findElemByXPATH(closeDMPopupButton).click();
        postButton.click();
        waitForElementToBeInvisible(postButton,10);
    }
}