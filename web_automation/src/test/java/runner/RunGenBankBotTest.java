package runner;


import cucubmerrunner.TestNgCucumberFeatureRunner;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.runtime.model.CucumberFeature;
import dataprovider.Tenants;
import org.testng.SkipException;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Test(groups = "cucumber")
@CucumberOptions(
        plugin={"pretty",
                "com.github.kirlionik.cucumberallure.AllureReporter"
        },
        monochrome = true,
        features ="src/test/java/scenario/general_bank_demo/botflows",
        glue ="steps")
public class RunGenBankBotTest {

        @Factory
        public Object[] features() {
                List objects = new ArrayList<>();
                TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
                for (CucumberFeature testDatum : testNGCucumberRunner.getFeatures()) {
                        objects.add(new TestNgCucumberFeatureRunner(testDatum, this));
                }
                return objects.toArray();
        }

}