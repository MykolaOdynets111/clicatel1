package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import apihelper.ApiHelper;
import datamanager.Tenants;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@FindBy(css = "div.modal-content")
public class AgentFeedbackWindow extends AbstractUIElement {

    private String windowCss = "div.modal-content";

    private String closeChatButtonXPATH = "//span[text()='Close Chat']";

    private String overlappedPage = "//div[@id='app'][@aria-hidden='true']";

    @FindBy(xpath = "//button[text()='Cancel']")
    private WebElement cancelButton;

    @FindBy(xpath = "//button[text()='Skip']")
    private WebElement skipButton;

    @FindBy(xpath = "//span[text()='Close Chat']")
    private WebElement closeChatButton;

    @FindBy(css = ".icon-negative")
    private WebElement sentimentUnsatisfied ;

    @FindBy(css = ".icon-neutral")
    private WebElement sentimentNeutral;

    @FindBy(css = ".icon-positive")
    private WebElement sentimentHappy;

    @FindBy(css = ".Select-arrow")
    private WebElement tagsDropdown;

    @FindBy(id = "CRMNote")
    private WebElement crmNoteTextField;

    @FindBy(id = "CRMLink")
    private WebElement crmLink;

    @FindBy(id = "CRMTicketNumber")
    private WebElement crmTicketNumber;

    public void clickCancel() {
        clickElemAgent(cancelButton, 5, "main agent", "Cancel button" );
        waitForElementsToBeInvisibleByXpathAgent(overlappedPage, 7, "main agent");
    }

    public void clickCloseСhat() {
        clickElemAgent(closeChatButton, 5, "main agent", "Close сhat button" );
        waitForElementsToBeInvisibleByXpathAgent(overlappedPage, 7, "main agent");
    }

    public void clickSkip() {
        clickElemAgent(skipButton, 5, "main agent", "Skip button" );
        waitForElementsToBeInvisibleByXpathAgent(overlappedPage, 7, "main agent");
    }

    public void clickCloseButtonInCloseChatPopup (){
        if( isElementShownAgentByXpath(closeChatButtonXPATH, 3, "main")){
            waitForElementToBeVisibleByXpathAgent(closeChatButtonXPATH, 10, "main agent");
            findElemByXPATHAgent(closeChatButtonXPATH).click();
        }
    }

    public boolean isEndChatPopupShown (){
        return isElementShownAgent(closeChatButton,12);
    }

    public void typeCRMNoteTextField(String msg) {
        inputTextAgent(crmNoteTextField, 5,"main agent", "Note", msg);
    }

    public void typeCRMLink(String msg) {
        inputTextAgent(crmLink,5 , "main agent", "Note link", msg);
    }

    public void typeCRMTicketNumber(String msg) {
        inputTextAgent(crmTicketNumber, 5, "main agent", "Note number", msg);
    }

    public void setSentimentUnsatisfied() {
        clickElemAgent(sentimentUnsatisfied, 5, "main agent", "Sentiment Unsatisfied" );
    }

    public void setSentimentNeutral() {
        clickElemAgent(sentimentNeutral, 5, "main agent", "Sentiment Neutral" );
    }

    public void setSentimentHappy() {
        clickElemAgent(sentimentHappy, 5, "main agent", "Sentiment Happy" );
    }

}
