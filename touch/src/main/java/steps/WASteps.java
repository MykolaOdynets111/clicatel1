package steps;

import io.cucumber.java.en.Given;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static utils.PropertiesReader.getProperty;

public class WASteps {

    AndroidDriver<MobileElement> androidDriver;

    @Given("^Setup appium whatsapp integration for (.*) tenant$")
    public void createAppiumWAIntegration(String tenantName) throws Exception {

        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9");
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel_2");
        caps.setCapability("appPackage", "com.whatsapp");
        caps.setCapability("appActivity", "com.whatsapp.HomeActivity");
        caps.setCapability("noReset", true);
        caps.setCapability("newCommandTimeout", 20000);

        URL url = new URL("http://0.0.0.0:4723/wd/hub");

        androidDriver = new AndroidDriver<>(url, caps);
        androidDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        androidDriver.findElementById("com.whatsapp:id/menuitem_search").click();
        androidDriver.findElementById("com.whatsapp:id/search_input").setValue(getProperty("environment"));
        androidDriver.findElementById("com.whatsapp:id/conversations_row_header").click();

    }

    @Given("^Send (.*) message by Appium Whatsapp$")
    public void sendAppiumMessage(String message) throws InterruptedException {
        androidDriver.findElementById("com.whatsapp:id/entry").sendKeys(message);
        androidDriver.findElementById("com.whatsapp:id/send").click();
        Thread.sleep(5000);
    }

    @Given("^Check received (.*) message in Appium Whatsapp$")
    public void checkAppiumReceivedMessage(String message) throws InterruptedException {
        Thread.sleep(10000);
        List<MobileElement> mobileElements = androidDriver.findElementsById("com.whatsapp:id/message_text");
        String actualMessage = mobileElements.get(mobileElements.size() - 1).getText();

        assertThat(actualMessage)
                .as(format("Last message must be %s", message))
                .isEqualTo(message);
    }


    @Given("^User closes whatsapp integration$")
    public void closeWhatsAppChannel() throws InterruptedException {
        androidDriver.findElementById("com.whatsapp:id/entry").sendKeys("//end");
        androidDriver.findElementById("com.whatsapp:id/send").click();
        Thread.sleep(5000);
        if (getProperty("environment").equals("prod")) {
            androidDriver.findElementById("com.whatsapp:id/entry").sendKeys("skip");
            androidDriver.findElementById("com.whatsapp:id/send").click();
        }
        Thread.sleep(5000);
        androidDriver.navigate().back();
        androidDriver.quit();
    }

}
