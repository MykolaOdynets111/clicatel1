package portal_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portal_pages.uielements.*;

import java.util.List;
import java.util.stream.Collectors;

public class PortalIntegrationsPage extends PortalAbstractPage {

    @FindBy(css = "tr[ng-repeat='channel in channels']")
    private List<WebElement> integrationRows;

    @FindBy(css = "div.integration-type.cl-card")
    private List<WebElement> integrationCards;

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
        getTargetIntegrationRow(integrationName).clickToggle();
    }

    public String getIntegrationRowStatus(String integrationName){
        return getTargetIntegrationRow(integrationName).getStatus();
    }

    public String getIntegrationCardStatus(String integrationName){
        return getTargetIntegrationCard(integrationName).getStatus();
    }

}
