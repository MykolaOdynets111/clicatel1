package portal_pages.uielements;

import abstract_classes.AbstractUIElement;
import interfaces.WebActions;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portal_pages.CartPage;


public class IntegrationRow extends Widget implements WebActions {

    @FindBy(css = "td>a")
    private WebElement integrationName;

    @FindBy(css = "button.bttn-toggle")
    private WebElement toggle;

    @FindBy(css = "button.status-button")
    private WebElement statusField;

    public IntegrationRow(WebElement element) {
        super(element);
    }

   public String getIntegrationName(){
        return integrationName.getText();
   }

   public void clickToggle(){
        toggle.click();
   }

   public String getStatus(){
        return statusField.getText();
   }
}
