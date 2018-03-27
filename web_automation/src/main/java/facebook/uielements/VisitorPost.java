package facebook.uielements;

import interfaces.WebActions;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class VisitorPost extends Widget implements WebActions {

    private String userName = ".//a[contains(@href, 'pageid')][not(@class)]";

    private String postTime = ".//span[@class='timestampContent']";

    @FindBy(xpath = ".//a[text()='Comment']")
    private WebElement commentButton;

    @FindBy(xpath = ".//div[@class='clearfix']/following-sibling::span")
    private WebElement postText;

    @FindBy(xpath = "//a[@aria-label='Story options']")
    private WebElement threeDotsButton;

    @FindBy(xpath = "//*[text()='Delete']")
    private WebElement deletePostButton;

    @FindBy(xpath = "//button[text()='Delete Post']")
    private WebElement confirmDeletingConverstionButton;

    public VisitorPost(WebElement element) {
        super(element);
        PageFactory.initElements(new AppiumFieldDecorator(element), this);
    }

    public String getUserName() {
        waitForElementToBeVisibleByXpath(userName, 5);
        return findElemByXPATH(userName).getText();
    }

    public String getPostTime() {
        return findElemByXPATH(postTime).getText();
    }

    public void deletePost() {
        threeDotsButton.click();
        waitForElementToBeVisible(deletePostButton, 6);
        deletePostButton.click();
        waitForElementToBeVisible(confirmDeletingConverstionButton, 5);
        confirmDeletingConverstionButton.click();
        waitForElementToBeInvisible(confirmDeletingConverstionButton,7);
    }
}
