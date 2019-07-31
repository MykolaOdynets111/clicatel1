package endtoend;

import driverfactory.MC2DriverFactory;
import drivermanager.ConfigManager;
import org.testng.ITest;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

@Test
public class BaseTest {

    @AfterTest(alwaysRun = true)
    protected void closeBrowser(){
        MC2DriverFactory.closePortalBrowser();
    }

    @AfterSuite(alwaysRun = true)
    protected void setUpEnvsProp(){
        try {
            FileInputStream in = new FileInputStream("src/test/resources/allureconfigs/environment.properties");
            Properties props = new Properties();
            props.load(in);
            in.close();

            FileOutputStream out = new FileOutputStream("src/test/resources/allureconfigs/environment.properties");
            props.setProperty("Browser", ConfigManager.getDriverType().name());
            props.setProperty("Env", ConfigManager.getEnv());

            props.store(out, null);
            out.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }


}
