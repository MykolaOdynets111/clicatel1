package steps;

import apihelper.ApiHelper;
import com.github.javafaker.Faker;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import datamanager.TwitterUsers;
import datamanager.jacksonschemas.TafMessage;
import dbmanager.DBConnector;
import drivermanager.ConfigManager;
import drivermanager.DriverFactory;
import interfaces.JSHelper;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;

import java.util.Random;

public class CamundaFlowsSteps implements JSHelper {

    Faker faker = new Faker();

    @Given("^Taf (.*) is set to (.*) for (.*) tenant$")
    public void updateTafMessageStatus(String tafMessageId, boolean status, String tenantOrgName){
        TafMessage tafMessageUpdates = getTafMessageToUpdate(tafMessageId);
        tafMessageUpdates.setEnabled(status);
        ApiHelper.updateTafMessage(tafMessageUpdates);

    }

    @Given("^Taf (.*) message text is updated for (.*) tenant$")
    public void updateTafMessageText(String tafMessageId, String tenantOrgName){
        TafMessage tafMessageUpdates = getTafMessageToUpdate(tafMessageId);
        tafMessageUpdates.setText(generateNewMessageText());
        ApiHelper.updateTafMessage(tafMessageUpdates);

    }

    @When("^Changes user id$")
    public void changeUserId(){
        Random r = new Random( System.currentTimeMillis() );
        JavascriptExecutor jsExec = (JavascriptExecutor)  DriverFactory.getTouchDriverInstance();
        jsExec.executeScript("window.localStorage.setItem('ctlUsername', 'testing_"+
                ((1 + r.nextInt(2)) * 1000000 + r.nextInt(1000000))+"');");
    }

    @Then("^Last visit date is saved to DB after (.*) minutes$")
    public void checkThanLastVisitDateIsSaved(int minutes){
        String clientProfileID = DBConnector.getClientProfileID(ConfigManager.getEnv(), getUserNameFromLocalStorage());
        Assert.assertTrue(DBConnector.isLastVisitSavedInDB(ConfigManager.getEnv(), clientProfileID, minutes),
                "It takes more than " + minutes +" minutes to save lastVisit after last response to user in widget");

    }

    @Then("^Last visit date is changed to minus (.*) hours$")
    public void changeLastVisitDate(int hoursShift){
        String clientProfileID = DBConnector.getClientProfileID(ConfigManager.getEnv(), getUserNameFromLocalStorage());
        long lastVisit = DBConnector.getLastVisitForUserProfile(ConfigManager.getEnv(), clientProfileID);
//        long lastVisitWithShift = lastVisit - (hoursShift*60*60*1000) - (3*60*60*1000);
        if(lastVisit!=0) {
            long lastVisitWithShift = lastVisit - (hoursShift * 60 * 60 * 1000) - (3 * 60 * 60 * 1000);
            DBConnector.updateClientLastVisitDate(ConfigManager.getEnv(), clientProfileID, lastVisitWithShift);
        }
    }


    @Then("Last visit date is changed to minus (.*) hours for twitter dm user")
    public void changeLastVisitDateForSocial(int hoursShift){
        String clientProfileID = DBConnector.getClientProfileID(ConfigManager.getEnv(), TwitterUsers.getLoggedInUser().getDmUserId());
        long lastVisit = DBConnector.getLastVisitForUserProfile(ConfigManager.getEnv(), clientProfileID);
//        long lastVisitWithShift = lastVisit - (hoursShift*60*60*1000) - (3*60*60*1000);
        if(lastVisit!=0) {
            long lastVisitWithShift = lastVisit - (hoursShift * 60 * 60 * 1000) - (3 * 60 * 60 * 1000);
            DBConnector.updateClientLastVisitDate(ConfigManager.getEnv(), clientProfileID, lastVisitWithShift);
        }
    }

    private TafMessage getTafMessageToUpdate(String messageId){
        return ApiHelper.getTafMessages().stream().filter(e -> e.getId().equals(messageId)).findFirst().get();
    }


    private String generateNewMessageText(){
        return "randow welcome message:" + faker.lorem().characters(8, 13, true);
    }


}
