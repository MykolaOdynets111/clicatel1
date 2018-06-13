package facebook.uielements;

import abstract_classes.AbstractUIElement;
import driverManager.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(xpath = "//div[@id='PageComposerPagelet_']")
public class PostFeed extends AbstractUIElement {

    @FindBy(xpath = "//form[@rel='async']//textarea[@name='xhpc_message_text']")
    private WebElement postArea;

    @FindBy(xpath = "//span[text()='Post']")
    private WebElement postButton;

    private String postInputField = "//div[@role='presentation']//div[@role='textbox']";


    public void makeAPost(String userPostText){
        postArea.click();
        waitForElementToBeVisibleByXpath(postInputField,4);
        DriverFactory.getInstance().findElement(By.xpath(postInputField)).sendKeys(userPostText);
        postButton.click();
        waitForElementToBeInvisible(postButton,10);
    }
}