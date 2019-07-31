package listeners;

import driverfactory.MC2DriverFactory;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestAllureListener implements ITestListener {

    @Attachment(type = "image/png")
    protected byte[] screenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Override
    public void onTestStart(ITestResult result) {
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        screenshot(MC2DriverFactory.getPortalDriver());
//        MC2DriverFactory.closePortalBrowser();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        screenshot(MC2DriverFactory.getPortalDriver());
//        MC2DriverFactory.closePortalBrowser();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        screenshot(MC2DriverFactory.getPortalDriver());
//        MC2DriverFactory.closePortalBrowser();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        screenshot(MC2DriverFactory.getPortalDriver());
//        MC2DriverFactory.closePortalBrowser();
    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {
    }
}
