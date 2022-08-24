package endtoend;


import dbmanager.DBConnector;
import drivermanager.ConfigManager;
import endtoend.basetests.BaseTest;
import io.qameta.allure.*;
import listeners.TestAllureListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({TestAllureListener.class})
@Test(testName = "DB connection test")
@TmsLink("TECH-5731")
public class DBConnectionTest extends BaseTest {


    public void checkLaunchpadSectionsNewAccount(){
        Assert.assertEquals(DBConnector.getAccountActivationIdFromMC2DB(ConfigManager.getEnv(), "ff808081582b1aec01582b24e78c0001"),
                "ff808081582b1aec01582b24e7980002");

    }


}
