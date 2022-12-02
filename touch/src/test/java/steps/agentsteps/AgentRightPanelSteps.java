package steps.agentsteps;

import interfaces.JSHelper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import steps.ORCASteps;

public class AgentRightPanelSteps extends AbstractAgentSteps implements JSHelper {

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

    @And("^(.*) invites (.*) to conversation via internal comments$")
    public void addNewCommentRightPanel(String agent, String commentText){
        getAgentHomePage(agent).getAgentRightPanel().clickOnCommentsTab()
                .clickOnNewCommentsButton()
                .addNewComment("@" +commentText)
                .selectMentionsListBoxItem(commentText)
                .clickOnPostCommentsButton();
    }

    @And("^(.*) edits User Profile with location (.*) and clicks Save$")
    public void editUserProfileInfo(String agent, String location){
        getAgentHomePage(agent).getAgentRightPanel().clickOnProfileTab()
                .clickOnEditProfileInfoButton()
                .addProfileInfoLocation(location)
                .clickOnSaveProfileInfoButton();
    }

    @Then("^(.*) views User profile with (.*) name (.*) location (.*) email without refreshing Agent Desk$")
    public void verifyProfileDetailsAgentRightPanel(String agent, String userName, String location, String email) {
        getAgentHomePage(agent).getAgentRightPanel().clickOnProfileTab();

        SoftAssert soft = new SoftAssert();
        soft.assertEquals(getAgentHomePage(agent).getAgentRightPanel().getProfileUserNameText(userName),userName,"Text inside user name does not match");
        soft.assertEquals(getAgentHomePage(agent).getAgentRightPanel().getProfileUserLocation(location),location,"Location does not match");
        soft.assertEquals(getAgentHomePage(agent).getAgentRightPanel().getProfileUserEmail(email),email,"User email does not match");
        soft.assertAll();
    }

    @Then("^(.*) can see the whatsapp profile name updated in the username section of the customer profile$")
    public void verifyUser(String agent) {
        Assert.assertEquals(getAgentHomePage(agent).getAgentRightPanel().getUserName(), ORCASteps.getClientId(), "User name does not match");
    }
}