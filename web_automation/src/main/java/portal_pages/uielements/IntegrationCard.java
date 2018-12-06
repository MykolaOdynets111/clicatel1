package portal_pages.uielements;

import interfaces.WebActions;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;


public class IntegrationCard extends Widget implements WebActions {

    private WebElement baseWebElem = this.getWrappedElement();

    private String integrationCardButton = ".//button[contains(text(),'Configure') or contains(text(),'Manage')]";

    private String integrationName =  "p.title";

    private String statusButton = "button.status-button";

    public IntegrationCard(WebElement element) {
        super(element);
    }


    public String getIntegrationName(){
        return baseWebElem.findElement(By.cssSelector(integrationName)).getText();
   }


   public String getStatus(){
       waitForElementToBeVisibleAgent(baseWebElem, 2);
       try {
            return baseWebElem.findElement(By.cssSelector(statusButton)).getText();
        }catch (StaleElementReferenceException e){
            waitFor(300);
            return baseWebElem.findElement(By.cssSelector(statusButton)).getText();
        }
   }

   public void clickActionButton(){
        try {
            baseWebElem.findElement(By.xpath(integrationCardButton)).click();
        }catch (NoSuchElementException e){
            waitFor(700);
            baseWebElem.findElement(By.xpath(integrationCardButton)).click();
        }
   }
}
