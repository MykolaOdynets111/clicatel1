package apitests;

import drivermanager.ConfigManager;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

@Test
public class BaseApiTest {

    @AfterSuite(alwaysRun = true)
    public void setUpEnvsProp(){
        try(FileInputStream in = new FileInputStream("src/test/resources/allureconfigs/environment.properties");
            FileOutputStream out = new FileOutputStream("src/test/resources/allureconfigs/environment.properties");
            ) {
                Properties props = new Properties();
                props.load(in);

                props.setProperty("Browser", "API tests");
                props.setProperty("Env", ConfigManager.getEnv());

                props.store(out, null);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
