package steps;

import cucumber.api.java.en.Then;
import dbManager.DBConnector;
import org.testng.Assert;

public class DBSteps {

    @Then("^I can connect to DB: (.*), (.*)$")
    public void verifyDB(String env, String plat){
        Assert.assertTrue(DBConnector.isConnectionEstabliished(env, plat), env + " Connection failed");
    }
}
