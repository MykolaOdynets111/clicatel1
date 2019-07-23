package facebook.uielements;

import abstractclasses.AbstractWidget;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class VisitorPost extends AbstractWidget {

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
    }

    public VisitorPost setCurrentDriver(WebDriver driver){
        this.currentDriver = driver;
        return this;
    }

    public String getUserName() {
        waitForElementToBeVisibleByXpath(this.getCurrentDriver(), userName, 5);
        return findElemByXPATH(this.getCurrentDriver(), userName).getText();
    }

    public String getPostTime() {
        return findElemByXPATH(this.getCurrentDriver(), postTime).getText();
    }

    public void deletePost() {
        threeDotsButton.click();
        clickElem(this.getCurrentDriver(), deletePostButton, 6, "Delete post button");
        clickElem(this.getCurrentDriver(), confirmDeletingConverstionButton, 6,
                "Confirm Delete post button");
        waitForElementToBeInvisible(this.getCurrentDriver(), confirmDeletingConverstionButton,7);
    }
}
