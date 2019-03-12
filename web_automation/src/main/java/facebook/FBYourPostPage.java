package facebook;

import abstractclasses.AbstractPage;
import drivermanager.DriverFactory;
import facebook.uielements.CommentInYourPostWindow;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

import java.util.List;


public class FBYourPostPage extends AbstractPage {

    private String closeYourPostPopupButton = "(//div[contains(@class, 'titlebarLabel clearfix')])[2]";

    private String closeDMPopupButton = "//a[@aria-label='Close tab']";

    private String userInitialPostMessageCSS = "div.userContent p";

    private String commentsCSS = "div[aria-label='Comment']";

    private String inputField = "div.UFIAddCommentInput div";

    private String inputContainer = "div.UFIAddCommentInput";

    private String firstCommentOnSecondLevel = "(//following-sibling::div[@class=' UFIReplyList']/div)[1]";

    private String repliesContainer = "//following-sibling::div[@class=' UFIReplyList']";

    private String secondLevelInputContainer = "//div[contains(@class, 'UFIAddCommentWithPhotoAttacher')][not(ancestor::div[@class=' UFIReplyList'])]";

    private String secondLevelInputField = "//div[@data-testid='ufi_comment_composer']";

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

        try {
            waitForElementToBeVisible(findElemByCSS(userInitialPostMessageCSS), 25);
        }catch (TimeoutException|NoSuchElementException e){
            scrollPageToTheTop(DriverFactory.getTouchDriverInstance());
            waitForElementToBeVisible(findElemByCSS(userInitialPostMessageCSS), 5);
        }
        return findElemByCSS(userInitialPostMessageCSS).getText().equals(initialUserPost);
    }

    public boolean isExpectedResponseShownInComments(String expectedResponse){
       return findElemsByCSS(commentsCSS).stream().map(CommentInYourPostWindow::new)
               .anyMatch(e1 -> e1.getCommentText().contains(expectedResponse));
    }

    public void makeAPost(String message){
        if (isElementShownByXpath(closeYourPostPopupButton, 2)) findElemByXPATH(closeYourPostPopupButton).click();
        findElemByCSS(inputContainer).click();
        waitForElementToBeVisibleByCss(inputField,3);
        findElemByCSS(inputField).sendKeys(message);
        findElemByCSS(inputField).sendKeys(Keys.ENTER);
    }

    public void makeASecondPostInBranch(String message){
        if (isElementShownByXpath(closeYourPostPopupButton, 2)) findElemByXPATH(closeYourPostPopupButton).click();
        findElemByXPATH(secondLevelInputContainer).click();
        waitForElementToBeVisibleByXpath(secondLevelInputField,3);
        findElemByXPATH(secondLevelInputField).sendKeys(message);
        findElemByXPATH(secondLevelInputField).sendKeys(Keys.ENTER);
    }

    public void deletePost(){
//        makeAPost("end");
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
        boolean isStaleReferenceErrorThrown=true;
        boolean isVerificationPassed=false;
        int loopsCounter=0;
        while(isStaleReferenceErrorThrown&loopsCounter<10){
            try {
                waitFor(1000);
                isVerificationPassed =  makeSecondLevelResponseVerification(userMessage, expectedResponse);
                isStaleReferenceErrorThrown=false;
            } catch(StaleElementReferenceException ex){
                loopsCounter++;
                waitFor(200);
            }
        }
        return isVerificationPassed;
    }

    private boolean makeSecondLevelResponseVerification(String userMessage, String expectedResponse){
        WebElement userPostInCommentsLine;
        userPostInCommentsLine = findElemsByCSS(commentsCSS).stream().map(CommentInYourPostWindow::new)
                .filter(e -> e.getCommentText().equals(userMessage)).findFirst().get().getWrappedElement();
        waitForElementToBeVisible(userPostInCommentsLine.findElement(By.xpath(repliesContainer)), 25);
        WebElement firstComment = userPostInCommentsLine.findElement(By.xpath(firstCommentOnSecondLevel));
        return new CommentInYourPostWindow(firstComment).getCommentText().equals(expectedResponse);
    }
}
