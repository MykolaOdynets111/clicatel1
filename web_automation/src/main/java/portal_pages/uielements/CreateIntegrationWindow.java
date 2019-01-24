package portal_pages.uielements;

import driverManager.DriverFactory;
import facebook.FBLoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.FindBy;
import org.testcontainers.containers.SeleniumUtils;
import portal_pages.PortalAbstractPage;

@FindBy(css = "div.cl-wizzard.create-integration-container")
public class CreateIntegrationWindow extends BasePortalWindow {

    @FindBy(css = "button.facebook-login-bttn")
    private WebElement loginToFBButton;

    @FindBy(css = "span[aria-label='Select box activate']")
    private WebElement selectFBPage;

    public void setUpFBIntegration(String fbPage){
        String currentWindowHandle = DriverFactory.getDriverForAgent("admin").getWindowHandle();
        clickLoginToFacebookButton(currentWindowHandle);
        if(DriverFactory.getDriverForAgent("admin").getWindowHandles().size()<2) executeJSclick(loginToFBButton, DriverFactory.getAgentDriverInstance());
        if(!DriverFactory.getDriverForAgent("admin").getCurrentUrl().contains("facebook")){
            DriverFactory.getDriverForAgent("admin").close();
            DriverFactory.getDriverForAgent("admin").switchTo().window(currentWindowHandle);
        }
        clickLoginToFacebookButton(currentWindowHandle);
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

    private void clickLoginToFacebookButton(String currentWindowHandle){

        JavascriptExecutor executor = (JavascriptExecutor) DriverFactory.getAgentDriverInstance();
//        executor.executeScript( "document.querySelector('button.facebook-login-bttn').dispatchEvent(new Event('click'));");
        executor.executeScript( "arguments[0].dispatchEvent(new Event('click'));", findElemByCSSAgent("div[ng-form='setupTwitterForm'] button"));
        for(String handle : DriverFactory.getDriverForAgent("admin").getWindowHandles()){
            if(!handle.equals(currentWindowHandle)) DriverFactory.getDriverForAgent("admin").switchTo().window(handle);
        }
    }
}
