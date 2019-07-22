package portalpages.uielements;

import abstractclasses.AbstractWidget;
import drivermanager.DriverFactory;
import interfaces.JSHelper;
import interfaces.WebActionsDeprecated;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.*;


public class IntegrationCard extends AbstractWidget {

    private WebElement baseWebElem = this.getWrappedElement();

    private String integrationCardButton = ".//button[contains(text(),'Configure') or contains(text(),'Manage') or contains(text(),'Pay now')]";

    private String integrationName =  "p.title";

    private String statusButton = "button.status-button";

    public IntegrationCard(WebElement element) {
        super(element);
    }

    public IntegrationCard setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
        return this;
    }

    public String getIntegrationName(){
        return baseWebElem.findElement(By.cssSelector(integrationName)).getText();
   }


   public String getStatus(){
       waitForElementToBeVisible(this.getCurrentDriver(), baseWebElem, 2);
       try {
            return baseWebElem.findElement(By.cssSelector(statusButton)).getText();
        }catch (StaleElementReferenceException e){
            waitFor(300);
            return baseWebElem.findElement(By.cssSelector(statusButton)).getText();
        }
   }

   public void clickActionButton(){
        try {
            executeJSclick(baseWebElem.findElement(By.xpath(integrationCardButton)),
                    this.getCurrentDriver());
        }catch (NoSuchElementException e){
            waitFor(700);
            executeJSclick(baseWebElem.findElement(By.xpath(integrationCardButton)),
                    this.getCurrentDriver());
        }
   }
}
