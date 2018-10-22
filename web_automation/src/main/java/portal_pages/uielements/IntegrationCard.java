package portal_pages.uielements;

import interfaces.WebActions;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class IntegrationCard extends Widget implements WebActions {

    @FindBy(css = "p.title")
    private WebElement integrationName;

    @FindBy(css = "button.status-button")
    private WebElement statusButton;

    public IntegrationCard(WebElement element) {
        super(element);
    }


    public String getIntegrationName(){
        return integrationName.getText();
   }


   public String getStatus(){
        return statusButton.getText();
   }
}
