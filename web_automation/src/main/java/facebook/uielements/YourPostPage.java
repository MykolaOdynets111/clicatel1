package facebook.uielements;

import abstract_classes.AbstractUIElement;
import interfaces.WebActions;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = "div#stream_pagelet")
public class YourPostPage extends AbstractUIElement {

    private String closeYourPostPopup = "(//div[@data-tooltip-content='Close tab'])[2]";

    private String userInitialPostMessageCSS = "div.userContent p";

    private String commentsCSS = "div[aria-label='Comment']";

    private String inputField = "div.UFIAddCommentInput";


    public boolean isYourPostWindowContainsInitialUserPostText(String initialUserPost){
        findElemByXPATH(closeYourPostPopup).click();
        waitForElementToBeVisible(findElemByCSS(userInitialPostMessageCSS), 25);
        return findElemByCSS(userInitialPostMessageCSS).getText().equals(initialUserPost);
    }

    public boolean isExpectedResponseShownInComments(String expectedResponse){
       return findElemsByCSS(commentsCSS).stream().map(CommentInYourPostWindow::new)
               .anyMatch(e1 -> e1.getCommentText().equals(expectedResponse));
    }
}
