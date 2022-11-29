package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = "[data-testid=right-panel]")
public class RightPanelWindow extends AbstractUIElement{

    // <<================= LOCATORS ==========================>>

    @FindBy(css = "[data-testid='tab-right-panel-0']")
    private WebElement profileTab;

    @FindBy(css = "[data-testid='tab-right-panel-1']")
    private WebElement notesTab;

    @FindBy(css = "[data-testid='tab-right-panel-3']")
    private WebElement commentsTab;

    @FindBy(css = ".cl-comment-composer__new-comment-button")
    private WebElement newCommentsButton;

    @FindBy(css = ".cl-comment-composer__post-comment-button")
    private WebElement postCommentsButton;

    @FindBy(css = ".cl-mentions__input")
    private WebElement newCommentsTextBox;

    @FindBy(css = ".cl-mentions__suggestions__item")
    private List<WebElement> mentionsListBox;

    @FindBy(xpath = "//button[text()='New note']")
    private WebElement newNoteButton;

    @FindBy(css = "[data-testid='crm-note']")
    private WebElement notesTextBox;

    @FindBy(css = "[data-testid='crm-link']")
    private WebElement jiraLinkBox;

    @FindBy(css = "[data-testid='crm-ticket-number']")
    private WebElement ticketNumberBox;

    @FindBy(css = "[data-testid='user-profile-name']")
    private WebElement profileUserName;

    @FindBy(css = "[data-testid='user-profile-email']")
    private WebElement profileUserEmail;

    @FindBy(css = "[data-testid='edit-crm-ticket-save']")
    private WebElement createNoteButton;

    @FindBy(css = ".cl-tab-notes-list")
    private List<WebElement> noteCardList;

    @FindBy(css = "[data-testid='note-card']")
    private WebElement noteCard;

    @FindBy(css = "[data-testid='note-card-text']")
    private WebElement noteCardText;

    @FindBy(css = "[data-testid='note-card-ticket-number']")
    private WebElement noteCardTicketNumberJiraLink;

    @FindBy(css = "[data-testid='user-profile-edit']")
    private WebElement editProfileInfo;

    @FindBy(css = "[data-testid='user-profile-location']")
    private WebElement profileInfoLocation;

    @FindBy(css = "[data-testid='user-profile-save']")
    private WebElement saveProfileInfo;

    @FindBy(css = ".cl-details-value")
    private WebElement userName;

    private final String listBoxElementText = ".cl-suggestions-item__name";

    // <<================= ACTION METHODS ==========================>>
    public RightPanelWindow clickOnProfileTab() {
        clickElem(this.getCurrentDriver(),profileTab,3,"Profile");
        return this;
    }

    public RightPanelWindow clickOnNotesTab() {
        scrollToElem(this.getCurrentDriver(),notesTab,"Notes");
        clickElem(this.getCurrentDriver(),notesTab,3,"Notes");
        return this;
    }

    public RightPanelWindow clickOnCommentsTab() {
        scrollToElem(this.getCurrentDriver(),commentsTab,"Comments");
        clickElem(this.getCurrentDriver(),commentsTab,3,"Comments");
        return this;
    }

    public RightPanelWindow clickOnNewCommentsButton() {
        waitForElementToBeVisible(this.getCurrentDriver(),newCommentsButton,5);
        executeJSclick(this.getCurrentDriver(),newCommentsButton);
        return this;
    }

    public RightPanelWindow clickOnEditProfileInfoButton() {
        waitForElementToBeVisible(this.getCurrentDriver(),editProfileInfo,5);
        executeJSclick(this.getCurrentDriver(),editProfileInfo);
        return this;
    }

    public RightPanelWindow addProfileInfoLocation(String location) {
        inputText(this.getCurrentDriver(),profileInfoLocation,3,"Profile info Location", location);
        return this;
    }

    public RightPanelWindow clickOnSaveProfileInfoButton() {
        waitForElementToBeVisible(this.getCurrentDriver(),saveProfileInfo,5);
        executeJSclick(this.getCurrentDriver(),saveProfileInfo);
        return this;
    }

    public RightPanelWindow clickOnPostCommentsButton() {
        waitForElementToBeVisible(this.getCurrentDriver(),postCommentsButton,5);
        executeJSclick(this.getCurrentDriver(),postCommentsButton);
        return this;
    }

    public RightPanelWindow addNewComment(String newComment) {
        inputText(this.getCurrentDriver(),newCommentsTextBox,3,"New Comment Text box", newComment);
        return this;
    }

    public RightPanelWindow selectMentionsListBoxItem(String listItem) {
        selectListBoxItem(this.getCurrentDriver(), mentionsListBox, listBoxElementText, 5, listItem);
        return this;
    }

    public RightPanelWindow clickOnNewNoteButton() {
        scrollToElem(this.getCurrentDriver(),newNoteButton,"New Note");
        clickElem(this.getCurrentDriver(),newNoteButton,3,"New Note");
        return this;
    }

    public RightPanelWindow addTextToNote(String note) {
        inputText(this.getCurrentDriver(),notesTextBox,3,"Notes Text Box",note);
        return this;
    }

    public RightPanelWindow addJiraLinkToNote(String jiraLink) {
        inputText(this.getCurrentDriver(),jiraLinkBox,3,"JIRA link",jiraLink);
        return this;
    }

    public RightPanelWindow addTicketNumberToNote(String ticketNumber) {
        inputText(this.getCurrentDriver(),ticketNumberBox,3,"Ticket Number",ticketNumber);
        return this;
    }

    public void clickOnCreateNoteButton() {
        clickElem(this.getCurrentDriver(),createNoteButton,3,"Create Note");
    }

    // <<================= GETTER METHODS ==========================>>
    public String getNoteCardText() {
        return getTextFromElem(this.getCurrentDriver(), noteCardText, 3, "Note Card Text");
    }

    public String getNoteCardJiraLink() {
        return getAttributeFromElem(this.getCurrentDriver(), noteCardTicketNumberJiraLink, 3,"JIRA Ticket Number Link", "href");
    }

    public String getNoteCardTicketNumber() {
        return getAttributeFromElem(this.getCurrentDriver(), noteCardTicketNumberJiraLink, 3,"JIRA Ticket Number", "title");
    }

    public String getProfileUserNameText(String userName) {
        if(userName.equalsIgnoreCase("no")) {
            return userName;
        }
        else {
            return getAttributeFromElem(this.getCurrentDriver(), profileUserName, 5,"User name", "value");
        }
    }

    public String getProfileUserLocation(String location) {
        if(location.equalsIgnoreCase("no")) {
            return location;
        }
        else {
            return getAttributeFromElem(this.getCurrentDriver(), profileInfoLocation, 5,"User Location", "value");
        }
    }

    public String getProfileUserEmail(String email) {
        if(email.equalsIgnoreCase("no")) {
            return email;
        }
        else {
            return getAttributeFromElem(this.getCurrentDriver(), profileUserEmail, 5,"User email", "value");
        }
    }

    public String getUserName() {
        return getTextFromElem(this.getCurrentDriver(), userName, 5,"User name");
    }
}
