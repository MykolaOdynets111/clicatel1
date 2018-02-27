package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


//@Test(groups = "cucumber")
@Test(groups = "cucumber", dependsOnGroups = {"acceptance"})
@CucumberOptions(
        plugin={"pretty",
                "com.github.kirlionik.cucumberallure.AllureReporter"
        },
        monochrome = true,
        features ="src/test/java/scenario/general_bank_demo",
        glue ="steps")
//public class RunGenBankTest extends SuiteCucumberRunner {
public class RunGenBankTest {
    private TestNGCucumberRunner testNGCucumberRunner;

    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws Exception {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
        }

    @Test(description = "Runs Cucumber Feature", dataProvider = "features")
    public void mainFlowFeature(CucumberFeatureWrapper cucumberFeature) {
        testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
        }

    @DataProvider
    public Object[][] features() {
        return testNGCucumberRunner.provideFeatures();
        }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        testNGCucumberRunner.finish();

        }
}
