package facebook.uielements;

import interfaces.WebActionsDeprecated;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CommentInYourPostWindow extends Widget implements WebActionsDeprecated {

    @FindBy(css = "span.UFICommentBody")
    private WebElement commentText;

    public CommentInYourPostWindow(WebElement element) {
        super(element);
        PageFactory.initElements(new AppiumFieldDecorator(element), this);
    }

    public String getCommentText() {
        try {
            return commentText.getAttribute("innerText");
        } catch (NoSuchElementException e){
            return "no text in comment element";
        }
    }
}
