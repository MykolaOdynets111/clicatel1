package portaluielem;

import abstractclasses.AbstractWidget;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ConfigureSMSIntegrationRow extends AbstractWidget {

    @FindBy(css = "a[ng-bind='integration.details.name']")
    private WebElement integrationName;

    public ConfigureSMSIntegrationRow(WebElement element) {
        super(element);
    }

    public ConfigureSMSIntegrationRow setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
        return this;
    }

    public String getIntegrationName(){
        return getTextFromElem(this.getCurrentDriver(), integrationName, 5, "Integration name");
    }

    public void openIntegration(){
        clickElem(this.getCurrentDriver(), integrationName, 2, "Integration name");
    }
}
