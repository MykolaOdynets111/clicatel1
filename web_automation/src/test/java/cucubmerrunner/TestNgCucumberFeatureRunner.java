package cucubmerrunner;

import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.CucumberFeatureWrapperImpl;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.runtime.model.CucumberFeature;
import driverManager.DriverFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class TestNgCucumberFeatureRunner{

    private TestNGCucumberRunner testNGCucumberRunner;
    private CucumberFeatureWrapper cucumberFeature;

    public TestNgCucumberFeatureRunner(CucumberFeature cucumberFeature2, Object runner) {
        testNGCucumberRunner = new TestNGCucumberRunner(runner.getClass());
        cucumberFeature = new CucumberFeatureWrapperImpl(cucumberFeature2);
    }

//    @BeforeClass(alwaysRun = true)
//    public void startContainer(){
//        DriverFactory.startBrowser();
//        System.out.println("!!!!!!!!!!!!!!!Cucumber feature :" + cucumberFeature.toString());
//    }

    @Test(groups = {"cucumber"})
    public void feature() {
        this.testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass(){
//        DriverFactory.closeBrowser();
//        DriverFactory.closeSecondBrowser();
//        this.testNGCucumberRunner.finish();
    }

}
