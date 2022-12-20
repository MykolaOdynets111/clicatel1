package steps;

import dbmanager.DBConnector;
import io.cucumber.java.en.Then;
import org.testng.Assert;

public class DBSteps {

    @Then("^I can connect to DB: (.*), (.*)$")
    public void verifyDB(String env, String plat){
        Assert.assertTrue(DBConnector.isConnectionEstablished(env, plat), env + " Connection failed");
    }
}
