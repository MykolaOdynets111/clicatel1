package steps.agentsteps;

import interfaces.JSHelper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.asserts.SoftAssert;

public class AgentNotesSteps extends AbstractAgentSteps implements JSHelper {

    @And("^(.*) adds a note (.*) Jira link (.*) and Ticket Number (.*)$")
    public void addNewNoteAgentRightPanel(String agent, String note, String jiraLink, String ticketNumber){
        getAgentHomePage(agent).getAgentRightPanel().clickOnNotesTab()
                .clickOnNewNoteButton()
                .addTextToNote(note)
                .addJiraLinkToNote(jiraLink)
                .addTicketNumberToNote(ticketNumber)
                .clickOnCreateNoteButton();
    }
    @Then("^(.*) sees note (.*) Jira link (.*) and Ticket Number (.*)$")
    public void verifyNoteDetailsAgentRightPanel(String agent, String note, String jiraLink, String ticketNumber) {
        getAgentHomePage(agent).getAgentRightPanel().clickOnNotesTab();

        SoftAssert soft = new SoftAssert();
        soft.assertEquals(getAgentHomePage(agent).getAgentRightPanel().getNoteCardText(),note,"Text inside note does not match");
        soft.assertEquals(getAgentHomePage(agent).getAgentRightPanel().getNoteCardJiraLink(),jiraLink,"JIRA Link does not match");
        soft.assertEquals(getAgentHomePage(agent).getAgentRightPanel().getNoteCardTicketNumber(),ticketNumber,"Ticket Number does not match");
        soft.assertAll();
    }

}