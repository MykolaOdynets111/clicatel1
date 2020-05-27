package steps;

import apihelper.ApiHelper;
import com.github.javafaker.Faker;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import datamanager.Tenants;
import datamanager.jacksonschemas.AutoResponderMessage;
import driverfactory.DriverFactory;
import drivermanager.ConfigManager;
import interfaces.JSHelper;
import interfaces.WebActions;
import socialaccounts.TwitterUsers;
import dbmanager.DBConnector;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Random;

public class CamundaFlowsSteps implements JSHelper, WebActions {

    private Faker faker = new Faker();

    @Given("^Taf (.*) is set to (.*) for (.*) tenant$")
    public void updateTafMessageStatus(String autoResponderId, boolean status, String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        AutoResponderMessage autoResponderMessageUpdates = getTafMessageToUpdate(autoResponderId);
        autoResponderMessageUpdates.setEnabled(status);
        ApiHelper.updateAutoresponderMessage(autoResponderMessageUpdates, autoResponderId);

    }

    @Given("^Taf (.*) message text is updated for (.*) tenant$")
    public void updateTafMessageText(String tafMessageId, String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        AutoResponderMessage autoResponderMessageUpdates = getTafMessageToUpdate(tafMessageId);
        String updatedMessage = generateNewMessageText(tafMessageId);
        autoResponderMessageUpdates.setText(updatedMessage);
        ApiHelper.updateAutoresponderMessage(autoResponderMessageUpdates, tafMessageId);
        AutoResponderMessage tafMessageBackend = getTafMessageToUpdate(tafMessageId);
        Assert.assertEquals(tafMessageBackend.getText(), autoResponderMessageUpdates.getText(),
                "Message text is not updated for tenant");

    }

    @When("^Changes user id$")
    public void changeUserId(){
        Random r = new Random( System.currentTimeMillis() );
        JavascriptExecutor jsExec = (JavascriptExecutor)  DriverFactory.getTouchDriverInstance();
        jsExec.executeScript("window.localStorage.setItem('ctlUsername', 'testing_"+
                ((1 + r.nextInt(2)) * 1000000 + r.nextInt(1000000))+"');");
    }

    @When("Update conversation and session dates to (.*) hours$")
    public void updateDatesForConversationAndSession(int hoursShift){

        Map<String, String> conversation =  null;
        //Wait 5 seconds if some date is not updated in table yet
        for (int i=0; i<5; i++ ) {
            conversation = DBConnector.getDatesOfUserConversationOrSession(ConfigManager.getEnv(), getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()), "conversation");
            boolean hasNull = false;
            for (String key: conversation.keySet()){
                String date = conversation.get(key);
                if (date == null || date.isEmpty()){
                    waitFor(1000);
                    hasNull = true;
                    break;
                }
            }
            if (!hasNull) break ;
        }

        conversation = updateDates(conversation, hoursShift);
        DBConnector.updateDatesOfUserConversationOrSession(ConfigManager.getEnv(), getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()), "conversation", conversation);

        Map<String, String> session = DBConnector.getDatesOfUserConversationOrSession(ConfigManager.getEnv(), getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()), "session");
        session = updateDates(session, hoursShift);
        DBConnector.updateDatesOfUserConversationOrSession(ConfigManager.getEnv(), getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance()), "session", session);
    }

    private Map<String, String> updateDates(Map<String, String> map, int hoursShift){
        for (String key: map.keySet()) {
            String date = map.get(key);
            date = date.substring(0, 19) + ".111";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
            map.put(key, formatter.format(dateTime.minusHours(hoursShift)));
        }
        return map;
    }


    @Then("Last visit date is changed to minus (.*) hours for twitter dm user")
    public void changeLastVisitDateForSocial(int hoursShift){
        String linkedClientProfileId = DBConnector.getLinkedClientProfileID(ConfigManager.getEnv(), TwitterUsers.getLoggedInUser().getDmUserId());
        long lastVisit = DBConnector.getLastVisitForUserProfile(ConfigManager.getEnv(), linkedClientProfileId);
        if(lastVisit!=0) {
            long lastVisitWithShift = lastVisit - (hoursShift * 60 * 60 * 1000) - (3 * 60 * 60 * 1000);
            DBConnector.updateClientLastVisitDate(ConfigManager.getEnv(), linkedClientProfileId, lastVisitWithShift);
        }
    }

    private AutoResponderMessage getTafMessageToUpdate(String messageId){
        return ApiHelper.getAutoResponderMessage(messageId);
    }


    private String generateNewMessageText(String tafMessageId){
        return "randow "+tafMessageId+" message:" + faker.lorem().characters(8, 13, true);
    }

}
