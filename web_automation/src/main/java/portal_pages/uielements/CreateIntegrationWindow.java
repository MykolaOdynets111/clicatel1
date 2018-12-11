package portal_pages.uielements;

import driverManager.DriverFactory;
import facebook.FBLoginPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.FindBy;
import portal_pages.PortalAbstractPage;


public class CreateIntegrationWindow extends BasePortalWindow {

    @FindBy(css = "button.facebook-login-bttn")
    private WebElement loginToFBButton;

    @FindBy(css = "span[aria-label='Select box activate']")
    private WebElement selectFBPage;

    public void setUpFBIntegration(String fbPage){
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        CapabilityType
//        DriverFactory.getAgentDriverInstance().
        JavascriptExecutor executor = (JavascriptExecutor) DriverFactory.getAgentDriverInstance();
        executor.executeScript( "document.querySelector('button.facebook-login-bttn').click()");

//        Actions action = new Actions(DriverFactory.getAgentDriverInstance());
//        action.moveToElement(findElemByCSSAgent("button.facebook-login-bttn")).build().perform();
//        executeJSclick(findElemByCSSAgent("button.facebook-login-bttn"),
//                DriverFactory.getAgentDriverInstance());
//        findElemByCSSAgent("button.facebook-login-bttn").click();
//        waitForElementToBeClickableAgent(loginToFBButton, 6, "agent");+
//        Actions action = new Actions(DriverFactory.getAgentDriverInstance());
//        action.moveToElement(findElemByCSSAgent("cl-facebook-login-bttn")).build().perform();
//        executeJSclick(findElemByCSSAgent("cl-facebook-login-bttn"),
//                DriverFactory.getAgentDriverInstance());


//        executeJSHover(findElemByCSSAgent("button.facebook-login-bttn"), DriverFactory.getAgentDriverInstance());
//        executeJSclick(findElemByCSSAgent("button.facebook-login-bttn"), DriverFactory.getAgentDriverInstance());
//        moveToElemAndClick(DriverFactory.getAgentDriverInstance(), loginToFBButton);
//        executeJSclick(loginToFBButton, DriverFactory.getAgentDriverInstance());
        if(DriverFactory.getDriverForAgent("admin").getWindowHandles().size()<2) executeJSclick(loginToFBButton, DriverFactory.getAgentDriverInstance());
        String currentWindowHandle = DriverFactory.getDriverForAgent("admin").getWindowHandle();
        for(String handle : DriverFactory.getDriverForAgent("admin").getWindowHandles()){
            if(!handle.equals(currentWindowHandle)) DriverFactory.getDriverForAgent("admin").switchTo().window(handle);
        }
        new FBLoginPage(DriverFactory.getAgentDriverInstance()).loginUserForFBIntegration();
        waitForElementToBeVisibleAgent(selectFBPage, 5);
        selectFBPage.click();
        waitForElementsToBeVisibleAgent(selectOptionsInDropdown, 6, "agent");
        selectOptionsInDropdown.stream()
                .filter(e -> e.getText().equalsIgnoreCase(fbPage))
                .findFirst().get().click();
        nextButton.click();
        waitForElementToBeVisibleByCssAgent(PortalAbstractPage.getNotificationAlertLocator(), 6);
        waitForElementToBeInvisibleAgent(findElemByCSSAgent(PortalAbstractPage.getNotificationAlertLocator()), 6);
    }
}
