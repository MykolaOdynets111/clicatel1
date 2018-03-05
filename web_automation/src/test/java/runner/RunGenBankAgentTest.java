package runner;


import cucubmerrunner.TestNgCucumberFeatureRunner;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.runtime.model.CucumberFeature;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

@Test(groups = "cucumber")
//@Test(groups = {"cucumber"}, dependsOnGroups = {"acceptance"})
@CucumberOptions(
        plugin={"pretty",
                "com.github.kirlionik.cucumberallure.AllureReporter"
        },
        monochrome = true,
        features ="src/test/java/scenario/general_bank_demo/agentflows",
        glue ="steps")
public class RunGenBankAgentTest {

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
