package portaluielem;

import abstractclasses.AbstractWidget;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class IntegrationRow extends AbstractWidget {

    private WebElement baseWebElem = this.getWrappedElement();

    private String integrationName = ".//td/a";

    private String toggle = "button.bttn-toggle";

    private String statusField = "button.status-button";

    public IntegrationRow(WebElement element) {
        super(element);
    }

    public IntegrationRow setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
        return this;
    }

   public String getIntegrationName(){
       waitForAngularRequestsToFinish(this.getCurrentDriver());
       waitForAngularToBeReady(this.getCurrentDriver());
       try {
           return baseWebElem.findElement(By.xpath(integrationName)).getText();
       }catch (StaleElementReferenceException e){
           waitFor(500);
           return baseWebElem.findElement(By.xpath(integrationName)).getText();
       }
   }

   public boolean isToggleEnabled(){
       return baseWebElem.findElement(By.cssSelector(toggle)).getAttribute("class").contains("toggle-active");
   }

   public void clickToggle(){
       baseWebElem.findElement(By.cssSelector(toggle)).click();
   }

   public String getStatus(){
        return baseWebElem.findElement(By.cssSelector(statusField)).getText();
   }
}
