package runner.generalbank;

import cucubmerrunner.TestNgCucumberFeatureRunner;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.runtime.model.CucumberFeature;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

@Test(groups = "tie one thread")
@CucumberOptions(
        plugin={"pretty",
                "com.github.kirlionik.cucumberallure.AllureReporter"
        },
        monochrome = true,
        features ="src/test/java/scenario/generalbank/tieonethread",
        glue ="steps")
public class RunTIEOneThreadTest {

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