package portal_pages;

import driverManager.DriverFactory;
import facebook.FBLoginPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import portal_pages.uielements.*;

import java.util.List;
import java.util.stream.Collectors;

public class PortalIntegrationsPage extends PortalAbstractPage {

    @FindBy(css = "tr[ng-repeat='channel in channels']")
    private List<WebElement> integrationRows;

    @FindBy(xpath = "//div[contains(@class,'integration-type cl-card')]")
    private List<WebElement> integrationCards;

    private CreateIntegrationWindow createIntegrationWindow;

    public CreateIntegrationWindow getCreateIntegrationWindow() {
        return createIntegrationWindow;
    }

    private IntegrationRow getTargetIntegrationRow(String integrationName){
        return integrationRows.stream().map(IntegrationRow::new).collect(Collectors.toList())
                .stream().filter(a -> a.getIntegrationName().toLowerCase().contains(integrationName.toLowerCase()))
                .findFirst().get();
    }

    private IntegrationCard getTargetIntegrationCard(String integrationName){
        return integrationCards.stream().map(IntegrationCard::new).collect(Collectors.toList())
                .stream().filter(a -> a.getIntegrationName().toLowerCase().contains(integrationName.toLowerCase()))
                .findFirst().get();
    }

    public void clickToggleFor(String integrationName){
        try {
            getTargetIntegrationRow(integrationName).clickToggle();
        } catch(java.util.NoSuchElementException e){
            Assert.assertTrue(false, "Toggle for managing '"+integrationName+"' integration is not shown on the page");
        }
    }

    public String getIntegrationRowStatus(String integrationName){
        return getTargetIntegrationRow(integrationName).getStatus();
    }

    public String getIntegrationCardStatus(String integrationName){
        return getTargetIntegrationCard(integrationName).getStatus();
    }

    public void clickActionButtonForIntegration(String integrationName){
        getTargetIntegrationCard(integrationName).clickActionButton();
    }
}
