package listeners;

import driverfactory.MC2DriverFactory;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.internal.BaseTestMethod;
import org.testng.internal.TestResult;

import java.lang.reflect.Field;

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
//        setTestNameInXml(result);
        screenshot(MC2DriverFactory.getPortalDriver());
        MC2DriverFactory.closePortalBrowser();
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

    private void setTestNameInXml(ITestResult tr)
    {
        try
        {
            Field method = TestResult.class.getDeclaredField("m_method");
            method.setAccessible(true);
            method.set(tr, tr.getMethod().clone());
            Field methodName = BaseTestMethod.class.getDeclaredField("m_methodName");
            methodName.setAccessible(true);
            methodName.set(tr.getMethod(),tr.getParameters()[0]);
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
    }
}
