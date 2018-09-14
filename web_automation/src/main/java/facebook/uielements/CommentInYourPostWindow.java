package facebook.uielements;

import interfaces.WebActions;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CommentInYourPostWindow extends Widget implements WebActions {

    @FindBy(css = "span.UFICommentBody>span")
    private WebElement commentText;

    public CommentInYourPostWindow(WebElement element) {
        super(element);
        PageFactory.initElements(new AppiumFieldDecorator(element), this);
    }

    public String getCommentText() {
        return commentText.getAttribute("innerText");
//        return messageText.getText();
    }
}
