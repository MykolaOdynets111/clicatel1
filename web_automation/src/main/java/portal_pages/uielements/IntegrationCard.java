package portal_pages.uielements;

import interfaces.WebActions;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class IntegrationCard extends Widget implements WebActions {

    private WebElement baseWebElem = this.getWrappedElement();

    private String integrationName =  "p.title";

    private String statusButton = "button.status-button";

    public IntegrationCard(WebElement element) {
        super(element);
    }


    public String getIntegrationName(){
        return baseWebElem.findElement(By.cssSelector(integrationName)).getText();
   }


   public String getStatus(){
        return  baseWebElem.findElement(By.cssSelector(statusButton)).getText();
   }
}
