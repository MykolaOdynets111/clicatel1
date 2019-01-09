package steps;

import api_helper.ApiHelper;
import com.github.javafaker.Faker;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import dataManager.jackson_schemas.TafMessage;
import driverManager.DriverFactory;
import org.openqa.selenium.JavascriptExecutor;

import java.util.Random;

public class CamundaFlowsSteps {

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

    private TafMessage getTafMessageToUpdate(String messageId){
        return ApiHelper.getTafMessages().stream().filter(e -> e.getId().equals(messageId)).findFirst().get();
    }


    private String generateNewMessageText(){
        return "randow welcome message:" + faker.lorem().characters(8, 13, true);
    }


}
