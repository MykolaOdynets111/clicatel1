package runner.generalbank;

import apihelper.ApiHelper;
import cucubmerrunner.TestNgCucumberFeatureRunner;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.runtime.model.CucumberFeature;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Test(groups = {"abc"})
@CucumberOptions(
        plugin={"com.github.kirlionik.cucumberallure.AllureReporter"
        },
        monochrome = true,
        features ="src/test/java/scenario/generalbank/abc",
        glue ="steps")
public class RunAbcTest {

    @Factory
    public Object[] features() {
        ApiHelper.ratingEnabling("General Bank Demo", false,"abc");
        List objects = new ArrayList<>();
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
        for (CucumberFeature testDatum : testNGCucumberRunner.getFeatures()) {
            objects.add(new TestNgCucumberFeatureRunner(testDatum, this));
        }
        return objects.toArray();
    }

}
