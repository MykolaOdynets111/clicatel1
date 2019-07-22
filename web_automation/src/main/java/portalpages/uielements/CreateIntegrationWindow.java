package portalpages.uielements;

import drivermanager.DriverFactory;
import facebook.FBLoginPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portalpages.PortalAbstractPage;

@FindBy(css = "div.cl-wizzard.create-integration-container")
public class CreateIntegrationWindow extends BasePortalWindow {

    @FindBy(css = "button.facebook-login-bttn")
    private WebElement loginToFBButton;

    @FindBy(css = "span[aria-label='Select box activate']")
    private WebElement selectFBPage;

    public void setUpFBIntegration(String fbPage){
        String currentWindowHandle = this.getCurrentDriver().getWindowHandle();
        clickLoginToFacebookButton(currentWindowHandle);
        if(this.getCurrentDriver().getWindowHandles().size()<2)
            executeJSclick(loginToFBButton, this.getCurrentDriver());
        if(!this.getCurrentDriver().getCurrentUrl().contains("facebook")){
            this.getCurrentDriver().close();
            this.getCurrentDriver().switchTo().window(currentWindowHandle);
        }
        clickLoginToFacebookButton(currentWindowHandle);
        new FBLoginPage(this.getCurrentDriver()).loginUserForFBIntegration();
        waitForElementToBeVisible(this.getCurrentDriver(), selectFBPage, 5);
        selectFBPage.click();
        waitForElementsToBeVisible(this.getCurrentDriver(), selectOptionsInDropdown, 6);
        selectOptionsInDropdown.stream()
                .filter(e -> e.getText().equalsIgnoreCase(fbPage))
                .findFirst().get().click();
        nextButton.click();
        waitForElementToBeVisibleByCss(this.getCurrentDriver(), PortalAbstractPage.getNotificationAlertLocator(), 6);
        waitForElementToBeInvisible(this.getCurrentDriver(),
                findElemByCSS(this.getCurrentDriver(), PortalAbstractPage.getNotificationAlertLocator()), 6);
    }

    private void clickLoginToFacebookButton(String currentWindowHandle){
        JavascriptExecutor executor = (JavascriptExecutor) this.getCurrentDriver();
//        executor.executeScript( "document.querySelector('button.facebook-login-bttn').dispatchEvent(new Event('click'));");
        executor.executeScript( "arguments[0].dispatchEvent(new Event('click'));",
                findElemByCSS(this.getCurrentDriver(), "div[ng-form='setupTwitterForm'] button"));
        for(String handle : this.getCurrentDriver().getWindowHandles()){
            if(!handle.equals(currentWindowHandle)) DriverFactory.getDriverForAgent("admin").switchTo().window(handle);
        }
    }
}
