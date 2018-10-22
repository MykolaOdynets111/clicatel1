package portal_pages.uielements;

import abstract_classes.AbstractUIElement;
import interfaces.WebActions;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portal_pages.CartPage;


public class IntegrationRow extends Widget implements WebActions {

    private WebElement baseWebElem = this.getWrappedElement();

    private String integrationName = ".//td/a";

    private String toggle = "button.bttn-toggle";

    private String statusField = "button.status-button";

    public IntegrationRow(WebElement element) {
        super(element);
    }

   public String getIntegrationName(){
        return baseWebElem.findElement(By.xpath(integrationName)).getText();
   }

   public void clickToggle(){
       baseWebElem.findElement(By.cssSelector(toggle)).click();
   }

   public String getStatus(){
        return baseWebElem.findElement(By.cssSelector(statusField)).getText();
   }
}
