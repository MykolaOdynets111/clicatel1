package agentpages.supervisor.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

@FindBy(css = "[data-testid=right-panel]")
public class SupervisorRightPanel extends AbstractUIElement{

    // <<================= LOCATORS ==========================>>
    @FindBy(css = "[data-testid='tab-right-panel-1']")
    private WebElement notesTab;

    @FindBy(xpath = "//button[text()='New note']")
    private WebElement newNoteButton;

    @FindBy(css = "[data-testid='crm-note']")
    private WebElement notesTextBox;

    @FindBy(css = "[data-testid='crm-link']")
    private WebElement jiraLinkBox;

    @FindBy(css = "[data-testid='crm-ticket-number']")
    private WebElement ticketNumberBox;

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



    // <<================= ACTION METHODS ==========================>>
    public SupervisorRightPanel clickOnNotesTab() {
        clickElem(this.getCurrentDriver(),notesTab,3,"Notes");
        return this;
    }

    public SupervisorRightPanel clickOnNewNoteButton() {
        clickElem(this.getCurrentDriver(),newNoteButton,3,"New Note");
        return this;
    }

    public SupervisorRightPanel addTextToNote(String note) {
        inputText(this.getCurrentDriver(),notesTextBox,3,"Notes Text Box",note);
        return this;
    }

    public SupervisorRightPanel addJiraLinkToNote(String jiraLink) {
        inputText(this.getCurrentDriver(),jiraLinkBox,3,"JIRA link",jiraLink);
        return this;
    }

    public SupervisorRightPanel addTicketNumberToNote(String ticketNumber) {
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
}
