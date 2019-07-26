package cucubmerrunner;

import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.runtime.model.CucumberFeature;
import org.testng.annotations.Factory;

import java.util.ArrayList;
import java.util.List;


public abstract class SuiteCucumberRunner {

    @Factory
    public Object[] features() {
        List<TestNgCucumberFeatureRunner> objects = new ArrayList<>();
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
        for (CucumberFeature testDatum : testNGCucumberRunner.getFeatures()) {
            objects.add(new TestNgCucumberFeatureRunner(testDatum, this));
        }
        return objects.toArray();
    }

}
