package facebook.uielements;
import abstract_classes.AbstractUIElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
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

    private String closeDMPopupButton = "//div[@class='fbNubFlyoutOuter']//span[text()='General Bank QA']/ancestor::div[@role='presentation']";

    private String closeDMPopupButtonConfirmation = "//a[@action='cancel'][text()='OK']";

    public void makeAPost(String userPostText){
        waitForElementToBeVisible(postInputField, 6);
        closeFBDMWindowIfOpened();
        postInputField.click();
        postInputField.sendKeys(userPostText);
        if(!postInputField.getText().equals(userPostText)){
            closeFBDMWindowIfOpened();
            postInputField.click();
            postInputField.clear();
            postInputField.sendKeys(userPostText);
        }
        postInputField.sendKeys(Keys.CONTROL, Keys.ENTER);
    }

    public void endSessionIfPostFeedIsShown(){
        if (isElementShown(postInputField, 5)){
            postInputField.sendKeys("end");
            postInputField.sendKeys(Keys.ENTER);
        }
    }


    private void closeFBDMWindowIfOpened(){
        if (isElementShownByXpath(closeDMPopupButton, 10)){
            findElemByXPATH(closeDMPopupButton).click();
        }
    }
}