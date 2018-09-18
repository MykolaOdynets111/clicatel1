package facebook.uielements;

import abstract_classes.AbstractUIElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = "div#stream_pagelet")
public class YourPostPage extends AbstractUIElement {

    private String closeYourPostPopupButton = "(//div[contains(@class, 'titlebarLabel clearfix')])[2]";

    private String closeDMPopupButton = "//a[@aria-label='Close tab']";

    private String userInitialPostMessageCSS = "div.userContent p";

    private String commentsCSS = "div[aria-label='Comment']";

    private String inputField = "div.UFIAddCommentInput div";

    private String inputContainer = "div.UFIAddCommentInput";

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
        return userPostInCommentsLine.findElements(By.xpath("//following-sibling::div[contains(@class,'UFIReplyList')]/div"))
                .stream().map(CommentInYourPostWindow::new)
                .anyMatch(e1 -> e1.getCommentText().equals(expectedResponse));
    }
}