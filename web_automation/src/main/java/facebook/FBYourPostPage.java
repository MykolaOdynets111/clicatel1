package facebook;

import abstract_classes.AbstractPage;
import abstract_classes.AbstractUIElement;
import driverManager.DriverFactory;
import facebook.uielements.CommentInYourPostWindow;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;


public class FBYourPostPage extends AbstractPage {

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

    @FindBy(xpath = "//div[@class='uiContextualLayerPositioner uiLayer']//div[@class='uiContextualLayer uiContextualLayerBelowRight']//ul[@role='menu']//a[@role='menuitem']/span")
    private List<WebElement> contextLayoverButtons;

    @FindBy(xpath = "//form[descendant::h3]")
    private WebElement deletePostConfirmationPopup;

    private String deletePostConfirmationPopupXPATH = "//form[descendant::h3]";

    @FindBy(xpath = "//form[descendant::h3]//button")
    private WebElement confirmDeleteButton;

    public boolean isYourPostWindowContainsInitialUserPostText(String initialUserPost){
        if (isElementShownByXpath(closeYourPostPopupButton, 5)) findElemByXPATH(closeYourPostPopupButton).click();
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

    public void deletePost(){
        makeAPost("end");
        scrollPageToTheTop(DriverFactory.getTouchDriverInstance());
        treeDotsButton.click();
        waitForElementToBeVisible(contextLayover, 5);
        contextLayoverButtons.stream().filter(e -> e.getText().equalsIgnoreCase("Delete"))
                .findFirst().get().click();
        waitForElementToBeVisible(deletePostConfirmationPopup, 5);
        confirmDeleteButton.click();
        waitForElementToBeInVisibleByXpath(deletePostConfirmationPopupXPATH, 25);
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
        try {
            waitForElementToBeVisible(userPostInCommentsLine.findElement(By.xpath(firstCommentOnSecondLevel)), 18);
        }catch (StaleElementReferenceException e1){
            waitFor(500);
            waitForElementToBeVisible(userPostInCommentsLine.findElement(By.xpath(firstCommentOnSecondLevel)), 18);
        }
        WebElement firstComment =userPostInCommentsLine.findElement(By.xpath(firstCommentOnSecondLevel));
        return new CommentInYourPostWindow(firstComment).getCommentText().equals(expectedResponse);
    }
}
