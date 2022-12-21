package api;

import org.testng.annotations.BeforeSuite;

import static utils.ConfigurationProvider.setApiConfiguration;

public class BaseApiTest {

    @BeforeSuite(alwaysRun = true)
    public void setEnvironmentConfiguration() {
        setApiConfiguration();
    }

}
