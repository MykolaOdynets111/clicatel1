package runner;


import cucubmerrunner.TestNgCucumberFeatureRunner;
import cucumber.api.CucumberOptions;
import dataprovider.Tenants;
import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

//@Test(groups = "cucumber")
@Test(groups = "cucumber")
@CucumberOptions(
        plugin={"pretty",
                "com.github.kirlionik.cucumberallure.AllureReporter"
        },
        monochrome = true,
        features ="src/test/java/scenario/general_bank_demo",
        glue ="steps")
public class RunGenBankTest extends TestNgCucumberFeatureRunner{
//
//        @Override
//        @BeforeTest
//        public void checkFailure() {
//                if (!Tenants.RESULT){
//                        throw new SkipException("Skipped");
//                }
//        }

}
