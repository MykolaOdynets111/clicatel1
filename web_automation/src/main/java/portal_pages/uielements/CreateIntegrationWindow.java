package portal_pages.uielements;

import abstract_classes.AbstractUIElement;
import driverManager.DriverFactory;
import facebook.FBLoginPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@FindBy(css = "div.create-integration-container")
public class CreateIntegrationWindow extends AbstractUIElement {

    @FindBy(css = "button.facebook-login-bttn")
    private WebElement loginToFBButton;

    public void setUpFBIntegration(){
        waitForElementToBeVisibleAgent(loginToFBButton, 6);
        loginToFBButton.click();
        String currentWindowHandle = DriverFactory.getDriverForAgent("admin").getWindowHandle();
        for(String handle : DriverFactory.getDriverForAgent("admin").getWindowHandles()){
            if(!handle.equals(currentWindowHandle)) DriverFactory.getDriverForAgent("admin").switchTo().window(handle);
        }
        new FBLoginPage(DriverFactory.getAgentDriverInstance()).loginUserForFBIntegration();
    }
}
