package steps.agentsteps;

import apihelper.ApiHelper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import datamanager.MC2Account;
import datamanager.Tenants;
import datamanager.jacksonschemas.ChatHistoryItem;
import datamanager.jacksonschemas.usersessioninfo.ClientProfile;
import dbmanager.DBConnector;
import drivermanager.ConfigManager;
import emailhelper.CheckEmail;
import emailhelper.GmailConnector;
import emailhelper.GmailParser;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import javax.mail.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AgentChatTranscriptSteps extends AbstractAgentSteps{

    private Message chatTranscriptEmail;

    @Given("Clear Chat Transcript email inbox")
    public void clearInbox(){
        // using standarttouchgoplan@gmail.com for all chat transcripts
        GmailConnector.loginAndGetInboxFolder(MC2Account.QA_STANDARD_ACCOUNT.getEmail(), MC2Account.QA_STANDARD_ACCOUNT.getPass());
        CheckEmail.clearEmailInbox();
    }

    @Given("Set Chat Transcript attribute to (.*) for (.*) tenant")
    public void setConfigAttributeValueTo(String value, String tenantOrgName){
        if (Tenants.getTenantUnderTestOrgName() == null)  // should it be applied?
            Tenants.setTenantUnderTestOrgName(tenantOrgName);
        // Adding "standarttouchgoplan@gmail.com" email from MC2 accounts for chat transcript emails
        ApiHelper.updateTenantConfig(tenantOrgName, "supportEmail", "\"" + MC2Account.QA_STANDARD_ACCOUNT.getEmail() + "\"");
        ApiHelper.updateTenantConfig(tenantOrgName, "chatTranscript", "\""+value+"\"");

    }

    @Then("Chat Transcript email arrives")
    public void verifyChatTranscriptEmail(){
        // Checking added "standarttouchgoplan@gmail.com" email for chat transcript emails
        GmailConnector.loginAndGetInboxFolder(MC2Account.QA_STANDARD_ACCOUNT.getEmail(), MC2Account.QA_STANDARD_ACCOUNT.getPass());
        chatTranscriptEmail = CheckEmail
                .getLastMessageBySender("Clickatell <transcripts@clickatell.com>", 15);
        for (int i = 0; i < 5; i ++){
            if(chatTranscriptEmail==null){
                GmailConnector.reopenFolder();
                chatTranscriptEmail = CheckEmail
                        .getLastMessageBySender("Clickatell <transcripts@clickatell.com>", 15);
                 continue;
            }
            if(GmailParser.getUserId(chatTranscriptEmail).equals(getClientIDGlobal())) {
                break;
            }
            else{
                getAgentHomePage("main").waitFor(15000);
                GmailConnector.reopenFolder();
                chatTranscriptEmail = CheckEmail
                        .getLastMessageBySender("Clickatell <transcripts@clickatell.com>", 1);
            }
        }

        Assert.assertNotNull(chatTranscriptEmail, "There is no chat transcript email");
        Assert.assertFalse(CheckEmail.getEmailSubject(chatTranscriptEmail).isEmpty(), "No Chat Transcript message received");
    }


    @Then("Email title contains (.*) adapter, client ID/Name/Email, chat ID, session number values")
    public void verifyChatTranscriptTitle(String adapter){
//        String chatID = (String) ApiHelper.getActiveSessionByClientId(clientIDGlobal).get("conversationId");
        int sessionsNumber = DBConnector.getNumberOfSessionsInConversationForLast3Days(ConfigManager.getEnv(), getChatIDTranscript());

        String chatTranscriptEmailTitle = CheckEmail.getEmailSubject(chatTranscriptEmail);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(getAdapterFromEmailSubject(chatTranscriptEmailTitle), getAdapter(adapter),
                "Adapters does not match");
        softAssert.assertEquals(getClientIDFromEmailSubject(chatTranscriptEmailTitle), getSecondParameterPerAdapter(adapter),
                "Client email does not match");
        softAssert.assertEquals(getChatIDFromEmailSubject(chatTranscriptEmailTitle), getChatIDTranscript(),
                "chatIDs does not match");
        softAssert.assertEquals(sessionsNumber, getLastSessionNumberFromEmailSubject(chatTranscriptEmailTitle), "Different session number");
        softAssert.assertAll();
    }

    @Then("Email content contains chat history from the terminated conversation")
    public void compareHistoriesInChatTranscript(){
        List<String> historyFromEmail = CheckEmail.getChatTranscriptEmailContent(chatTranscriptEmail);

        String lastSessionID = DBConnector.getLastSessioinID(ConfigManager.getEnv(), Tenants.getTenantUnderTestName(), getClientIDGlobal());
        List<ChatHistoryItem> chatItems = ApiHelper.getChatHistory(Tenants.getTenantUnderTestOrgName(), lastSessionID);
        List<String> historyFromDB = getBareChatHistory(chatItems);

        Assert.assertEquals(historyFromEmail, historyFromDB, "Histories in Email and in DB are different");
    }


    public String getClientIDFromEmailSubject(String chatTranscriptEmailTitle) {
        String [] dataInString = chatTranscriptEmailTitle.split(",");
        return dataInString[1].trim();
    }

    private String getChatIDAndSessionNumberFromEmailSubject(String chatTranscriptEmailTitle) {
        String [] dataInString = chatTranscriptEmailTitle.split(",");
        return dataInString[2].trim();
    }

    public String getChatIDFromEmailSubject(String chatTranscriptEmailTitle) {
        String chatAndSessionID = getChatIDAndSessionNumberFromEmailSubject(chatTranscriptEmailTitle);
        return chatAndSessionID.substring(0, chatAndSessionID.indexOf("(")).trim();
    }

    public int getLastSessionNumberFromEmailSubject(String chatTranscriptEmailTitle) {
        String chatAndSessionID = getChatIDAndSessionNumberFromEmailSubject(chatTranscriptEmailTitle);
        return Integer.parseInt(chatAndSessionID.substring(chatAndSessionID.indexOf("(")+1, chatAndSessionID.indexOf(")")));
    }

    private List<String> getBareChatHistory(List<ChatHistoryItem> items){
        List<String> expectedMessagesList = new ArrayList<>();

        for (ChatHistoryItem historyItem : items) {
            String expectedChatItem = historyItem.getDisplayMessage().replace("\n", "");
            if(expectedChatItem.contains("Input card")) expectedChatItem =
                    expectedChatItem.replace("Input card", "Let me connect you with a live agent to assist you further. Before I transfer you, please give us some basic info:");
            expectedMessagesList.add(expectedChatItem);
        }
        return expectedMessagesList;
    }

    public String getAdapterFromEmailSubject(String chatTranscriptEmailTitle) {
        String [] dataInString = chatTranscriptEmailTitle.split(",");
        return dataInString[0].trim();
    }

    public String getSecondParameterPerAdapter(String adapter){
        ClientProfile clientAttributes = ApiHelper.getClientAttributes(ApiHelper.getClientProfileId(getClientIDGlobal()));
        switch(adapter.toLowerCase()){
            case "dotcontrol":
            case "whatsapp":
                return getClientIDGlobal();
            case "fbmsg":
                return clientAttributes.getAttributes().getFirstName();
            case "twdm":
                return clientAttributes.getAttributes().getFirstName();
            case "webchat":
                return clientAttributes.getAttributes().getEmail();
            default:
                return null;
        }
    }

    public String getAdapter(String adapter){
        switch(adapter.toLowerCase()) {
            //Because of we are creating .Control integration for "fbmsg" adapter, this method will return this adapter name
            case "dotcontrol":
                return "fbmsg";

            default:
                return adapter;
        }
    }
}
