package facebook.uielements;
import abstract_classes.AbstractUIElement;
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

    private String closeDMPopupButton = "//a[@aria-label='Close tab']";

    private String closeDMPopupButtonConfirmation = "//a[@action='cancel'][text()='OK']";

    public void makeAPost(String userPostText){
        waitForElementToBeVisible(postInputField, 6);
        if (isElementShownByXpath(closeDMPopupButton, 10)) findElemByXPATH(closeDMPopupButton).click();
        postInputField.click();
        postInputField.sendKeys(userPostText);
        if(!postInputField.getText().equals(userPostText)){
            if (isElementShownByXpath(closeDMPopupButton, 8)) {
                findElemByXPATH(closeDMPopupButton).click();
                if(isElementShownByXpath(closeDMPopupButtonConfirmation, 5)) findElemByXPATH(closeDMPopupButtonConfirmation).click();
            }
            postInputField.click();
            postInputField.clear();
            postInputField.sendKeys(userPostText);
        }
        try{
        postButton.click();
        } catch (WebDriverException e){
            if (isElementShownByXpath(closeDMPopupButton, 5)) findElemByXPATH(closeDMPopupButton).click();
            try {
                postButton.click();
            }catch (WebDriverException e1){
                if (isElementShownByXpath(closeDMPopupButton, 5)) findElemByXPATH(closeDMPopupButton).click();
                postButton.click();
            }
        }

        waitForElementToBeInvisible(postButton,10);
    }

    public void endSessionIfPostFeedIsShown(){
        if (isElementShown(postInputField, 5)){
            postInputField.sendKeys("end");
            postButton.click();
        }
    }
}