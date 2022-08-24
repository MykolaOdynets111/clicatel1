package listeners;

import driverfactory.MC2DriverFactory;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.internal.BaseTestMethod;
import org.testng.internal.TestResult;

import java.lang.reflect.Field;
import java.util.Date;

public class TestAllureListener implements ITestListener {

    @Attachment(type = "image/png")
    protected byte[] screenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment
    private String chatDeskConsoleOutput(WebDriver driver){
        StringBuilder result = new StringBuilder();
        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
        for (LogEntry entry : logEntries) {
            result.append(new Date(entry.getTimestamp())).append(", ").append(entry.getLevel()).append(", ").append(entry.getMessage()).append(";  \n");
        }
        return  result.toString();
    }

    @Override
    public void onTestStart(ITestResult result) {
        }

    @Override
    public void onTestSuccess(ITestResult result) {
        screenshot(MC2DriverFactory.getPortalDriver());
        MC2DriverFactory.closePortalBrowser();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        screenshot(MC2DriverFactory.getPortalDriver());
        chatDeskConsoleOutput(MC2DriverFactory.getPortalDriver());
        MC2DriverFactory.closePortalBrowser();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        screenshot(MC2DriverFactory.getPortalDriver());
        chatDeskConsoleOutput(MC2DriverFactory.getPortalDriver());
        MC2DriverFactory.closePortalBrowser();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        screenshot(MC2DriverFactory.getPortalDriver());
        MC2DriverFactory.closePortalBrowser();
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
