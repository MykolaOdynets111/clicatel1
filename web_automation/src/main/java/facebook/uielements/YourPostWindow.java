package facebook.uielements;

import abstract_classes.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(xpath = "//div[@class='fbNubFlyoutInner'][descendant::div[@data-tooltip-content='Minimize tab']]")
public class YourPostWindow extends AbstractUIElement {

    @FindBy(xpath = "./div[contains(@class, 'userContent')]//p")
    private WebElement userInitialPostMessage;

    @FindBy(xpath = "./div[@aria-label='Comment']")
    private List<WebElement> comments;

    @FindBy(xpath = "./div[contains(@class,'UFIAddCommentInput')]")
    private WebElement inputField;

    public boolean isYourPostWindowContainsInitialUserPostText(String initialUserPost){
        waitForElementToBeVisible(userInitialPostMessage, 10);
        return userInitialPostMessage.getText().equals(initialUserPost);
    }

    public boolean isExpectedResponseShownInComments(String expectedResponse){
       return comments.stream().map(CommentInYourPostWindow::new)
               .anyMatch(e1 -> e1.getCommentText().equals(expectedResponse));
    }
}
