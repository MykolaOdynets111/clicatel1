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
//@Test(groups = {"cucumber"}, dependsOnGroups = {"acceptance"})
@CucumberOptions(
        plugin={"pretty",
                "com.github.kirlionik.cucumberallure.AllureReporter"
        },
        monochrome = true,
        features ="src/test/java/scenario/general_bank_demo",
        glue ="steps")
public class RunGenBankTest {

        @Factory
        public Object[] features() {
                List objects = new ArrayList<>();
                TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
                for (CucumberFeature testDatum : testNGCucumberRunner.getFeatures()) {
                        objects.add(new TestNgCucumberFeatureRunner(testDatum, this));
                }
                return objects.toArray();
        }
//        private TestNGCucumberRunner testNGCucumberRunner;
//        private CucumberFeatureWrapper cucumberFeature;
//
//        @Override
//        @Test(groups = {"cucumber"}, dependsOnGroups = {"acceptance"})
//        public void feature() {
//        this.testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
//}

}
