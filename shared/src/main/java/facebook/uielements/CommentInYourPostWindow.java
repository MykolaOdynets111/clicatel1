package facebook.uielements;

import abstractclasses.AbstractWidget;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CommentInYourPostWindow extends AbstractWidget {

    @FindBy(css = "span.UFICommentBody")
    private WebElement commentText;

    public CommentInYourPostWindow(WebElement element) {
        super(element);
    }

    public CommentInYourPostWindow setCurrentDriver(WebDriver driver){
        this.currentDriver = driver;
        return this;
    }

    public String getCommentText() {
        try {
            return commentText.getAttribute("innerText");
        } catch (NoSuchElementException e){
            return "no text in comment element";
        }
    }


}
