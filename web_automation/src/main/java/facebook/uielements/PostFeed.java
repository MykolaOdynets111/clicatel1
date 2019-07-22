package facebook.uielements;
import abstractclasses.AbstractUIElementDeprecated;
import drivermanager.DriverFactory;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
@FindBy(xpath = "//div[@id='PageComposerPagelet_']")
public class PostFeed extends AbstractUIElementDeprecated {

    @FindBy(xpath = "//form[@rel='async']//textarea[@name='xhpc_message_text']")
    private WebElement postArea;

    @FindBy(xpath = "//span[text()='Post']")
    private WebElement postButton;

    @FindBy(xpath = "//div[@role='presentation']//div[@role='textbox']")
    private WebElement postInputField;

    private String dmWindow = "//div[@class='fbNubFlyout fbDockChatTabFlyout uiContextualLayerParent']";

    private String closeDMPopupButton = "//div[contains(@class,'close')]";

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
        waitForDeprecated(1000); //A wait for fb, because it blocs when going to fast
        postInputField.sendKeys(Keys.CONTROL, Keys.ENTER);
    }

    public void endSessionIfPostFeedIsShown(){
        if (isElementShown(postInputField, 5)){
            postInputField.sendKeys("end");
            postInputField.sendKeys(Keys.ENTER);
        }
    }



    private void closeFBDMWindowIfOpened(){
        if (isElementShownByXpath(dmWindow, 10)){
            executeJSHover(findElemByXPATH(closeDMPopupButton), DriverFactory.getTouchDriverInstance());
            executeJSclick(findElemByXPATH(closeDMPopupButton));
        }
    }
}