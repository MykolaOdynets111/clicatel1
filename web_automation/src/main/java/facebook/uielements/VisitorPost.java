package facebook.uielements;

import interfaces.WebActions;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class VisitorPost extends Widget implements WebActions {

    @FindBy(xpath = ".//a[text()='Comment']")
    private WebElement commentButton;

    @FindBy(xpath = ".//a[contains(@href, 'pageid')][not(@class)]")
    private WebElement userName;

    @FindBy(xpath = ".//span[@class='timestampContent']")
    private WebElement postTime;

    @FindBy(xpath = ".//div[@class='clearfix']/following-sibling::span")
    private WebElement postText;

    public VisitorPost(WebElement element) {
        super(element);
        PageFactory.initElements(new AppiumFieldDecorator(element), this);
    }

    public String getUserName() {
        return userName.getText();
    }

    public String getPostTime() {
        return postTime.getText();
    }
}
