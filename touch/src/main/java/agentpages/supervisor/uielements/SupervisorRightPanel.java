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
    public void clickOnNotesTab() {
        waitForElementToBeVisible(this.getCurrentDriver(), notesTab, 3);
        notesTab.click();
    }

    public void clickOnNewNoteButton() {
        waitForElementToBeVisible(this.getCurrentDriver(), newNoteButton, 3);
        newNoteButton.click();
    }

    public void addTextToNote(String note) {
        waitForElementToBeVisible(this.getCurrentDriver(), notesTextBox, 3);
        notesTextBox.sendKeys(note);
    }

    public void addJiraLinkToNote(String jiraLink) {
        waitForElementToBeVisible(this.getCurrentDriver(), jiraLinkBox, 3);
        jiraLinkBox.sendKeys(jiraLink);
    }

    public void addTicketNumberToNote(String ticketNumber) {
        waitForElementToBeVisible(this.getCurrentDriver(), ticketNumberBox, 3);
        ticketNumberBox.sendKeys(ticketNumber);
    }

    public void clickOnCreateNoteButton() {
        waitForElementToBeVisible(this.getCurrentDriver(), createNoteButton, 3);
        createNoteButton.click();
    }

    // <<================= GETTER METHODS ==========================>>
    public String getNoteCardText() {
        waitForElementToBeVisible(this.getCurrentDriver(), noteCardText, 3);
        return noteCardText.getText();
    }

    public String getNoteCardJiraLink() {
        waitForElementToBeVisible(this.getCurrentDriver(), noteCardTicketNumberJiraLink, 3);
        return noteCardTicketNumberJiraLink.getAttribute("href");
    }

    public String getNoteCardTicketNumber() {
        waitForElementToBeVisible(this.getCurrentDriver(), noteCardTicketNumberJiraLink, 3);
        return noteCardTicketNumberJiraLink.getAttribute("title");
    }

    // <<================= ASSERTION METHODS ==========================>>
    public void verifyNoteCardText(String noteCardText){
        Assert.assertEquals(getNoteCardText(), noteCardText, "Text inside note does not match");
    }

    public void verifyNoteCardJiraLink(String noteCardJiraLink){
        Assert.assertEquals(getNoteCardJiraLink(), noteCardJiraLink, "JIRA Link does not match");
    }

    public void verifyNoteCardTicketNumber(String noteCardTicketNumber){
        Assert.assertEquals(getNoteCardTicketNumber(), noteCardTicketNumber, "Ticket Number does not match");
    }
}
