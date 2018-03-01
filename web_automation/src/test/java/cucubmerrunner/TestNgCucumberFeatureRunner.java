//package cucubmerrunner;
//
//import cucumber.api.testng.CucumberFeatureWrapper;
//import cucumber.api.testng.CucumberFeatureWrapperImpl;
//import cucumber.api.testng.TestNGCucumberRunner;
//import cucumber.runtime.model.CucumberFeature;
//import driverManager.DriverFactory;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//
//public class TestNgCucumberFeatureRunner{
//
//    private TestNGCucumberRunner testNGCucumberRunner;
//    private CucumberFeatureWrapper cucumberFeature;
//
//    public TestNgCucumberFeatureRunner(CucumberFeature cucumberFeature2, Object runner) {
//        testNGCucumberRunner = new TestNGCucumberRunner(runner.getClass());
//        cucumberFeature = new CucumberFeatureWrapperImpl(cucumberFeature2);
//    }
//
////    @BeforeClass(alwaysRun = true)
////    public void startContainer(){
////        DriverFactory.openUrl();
////        System.out.println("!!!!!!!!!!!!!!!Cucumber feature :" + cucumberFeature.toString());
////    }
//
//    @Test(groups = {"cucumber"})
//    public void feature() {
//        this.testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
//    }
//
//    @AfterClass(alwaysRun = true)
//    public void tearDownClass(){
////        DriverFactory.closeBrowser();
////        DriverFactory.closeSecondBrowser();
////        this.testNGCucumberRunner.finish();
//    }
//
//}
package cucubmerrunner;

import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.CucumberFeatureWrapperImpl;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.runtime.model.CucumberFeature;
import dataprovider.Tenants;
import org.testng.SkipException;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

public class TestNgCucumberFeatureRunner{

    private TestNGCucumberRunner testNGCucumberRunner;
    private CucumberFeatureWrapper cucumberFeature;

//    public TestNgCucumberFeatureRunner(){}


    public TestNgCucumberFeatureRunner(CucumberFeature cucumberFeature2, Object runner) {
        testNGCucumberRunner = new TestNGCucumberRunner(runner.getClass());
        cucumberFeature = new CucumberFeatureWrapperImpl(cucumberFeature2);}

//        @Factory
//        public Object[] features() {
//        List objects = new ArrayList<>();
//        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
//        for (CucumberFeature testDatum : testNGCucumberRunner.getFeatures()) {
//            objects.add(new TestNgCucumberFeatureRunner(testDatum, this));
//        }
//            return objects.toArray();
//    }

    @Test(groups = {"cucumber"})
    public void feature() {
        this.testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass(){this.testNGCucumberRunner.finish();
    }
}