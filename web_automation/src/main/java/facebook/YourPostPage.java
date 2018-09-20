package facebook;

import abstract_classes.AbstractPage;
import abstract_classes.AbstractUIElement;
import facebook.uielements.CommentInYourPostWindow;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class YourPostPage extends AbstractPage {

    private String closeYourPostPopupButton = "(//div[contains(@class, 'titlebarLabel clearfix')])[2]";

    private String closeDMPopupButton = "//a[@aria-label='Close tab']";

    private String userInitialPostMessageCSS = "div.userContent p";

    private String commentsCSS = "div[aria-label='Comment']";

    private String inputField = "div.UFIAddCommentInput div";

    private String inputContainer = "div.UFIAddCommentInput";

    private String firstCommentOnSecondLevel = "(//following-sibling::div[contains(@class,'UFIReplyList')]/div)[1]";

    @FindBy(css = "a[data-testid='post_chevron_button']")
    private WebElement treeDotsButton;

    @FindBy(xpath = "//div[@class='uiContextualLayerPositioner uiLayer']//div[@class='uiContextualLayer uiContextualLayerBelowRight']")
    private WebElement contextLayover;

    public boolean isYourPostWindowContainsInitialUserPostText(String initialUserPost){
        if (isElementShownByXpath(closeYourPostPopupButton, 2)) findElemByXPATH(closeYourPostPopupButton).click();
        if (isElementShownByXpath(closeDMPopupButton, 2)) findElemByXPATH(closeDMPopupButton).click();

        waitForElementToBeVisible(findElemByCSS(userInitialPostMessageCSS), 25);
        return findElemByCSS(userInitialPostMessageCSS).getText().equals(initialUserPost);
    }

    public boolean isExpectedResponseShownInComments(String expectedResponse){
       return findElemsByCSS(commentsCSS).stream().map(CommentInYourPostWindow::new)
               .anyMatch(e1 -> e1.getCommentText().equals(expectedResponse));
    }

    public void makeAPost(String message){
        if (isElementShownByXpath(closeYourPostPopupButton, 2)) findElemByXPATH(closeYourPostPopupButton).click();
        findElemByCSS(inputContainer).click();
        waitForElementToBeVisibleByCss(inputField,3);
        findElemByCSS(inputField).sendKeys(message);
        findElemByCSS(inputField).sendKeys(Keys.ENTER);
    }

    public boolean isExpectedResponseShownInSecondLevelComments(String userMessage, String expectedResponse){
        WebElement userPostInCommentsLine;
        try {
            userPostInCommentsLine = findElemsByCSS(commentsCSS).stream().map(CommentInYourPostWindow::new)
                    .filter(e -> e.getCommentText().equals(userMessage)).findFirst().get().getWrappedElement();
        } catch (StaleElementReferenceException ex){
            waitFor(500);
            userPostInCommentsLine = findElemsByCSS(commentsCSS).stream().map(CommentInYourPostWindow::new)
                    .filter(e -> e.getCommentText().equals(userMessage)).findFirst().get().getWrappedElement();
        }

        waitForElementToBeVisible(userPostInCommentsLine.findElement(By.xpath(firstCommentOnSecondLevel)));
        WebElement firstComment =userPostInCommentsLine.findElement(By.xpath(firstCommentOnSecondLevel));
        return new CommentInYourPostWindow(firstComment).getCommentText().equals(expectedResponse);
//        try {
//            return userPostInCommentsLine.findElements(By.xpath("//following-sibling::div[contains(@class,'UFIReplyList')]/div"))
//                    .stream().map(CommentInYourPostWindow::new)
//                    .anyMatch(e1 -> e1.getCommentText().equals(expectedResponse));
//        }catch (StaleElementReferenceException ex){
//            return userPostInCommentsLine.findElements(By.xpath("//following-sibling::div[contains(@class,'UFIReplyList')]/div"))
//                    .stream().map(CommentInYourPostWindow::new)
//                    .anyMatch(e1 -> e1.getCommentText().equals(expectedResponse));
//        }
    }
}
