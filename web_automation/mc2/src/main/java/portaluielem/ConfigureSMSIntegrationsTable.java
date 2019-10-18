package portaluielem;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;


@FindBy(css = "div.cl-content-area.mod-portal.mod-small-cards")
public class ConfigureSMSIntegrationsTable extends BasePortalWindow {

    @FindBy(css = "tr.environment-sandbox")
    private List<WebElement> integrationRows;

    public ConfigureSMSIntegrationRow getIntegrationRow(String integration){
        try{
            return integrationRows.stream().map( e -> new ConfigureSMSIntegrationRow(e).setCurrentDriver(this.getCurrentDriver()))
                    .filter(e -> e.getIntegrationName().equals(integration)).findFirst().orElseGet(null);
        }catch (NullPointerException e){
            return null;
        }
    }

    @Step(value = "Open integration")
    public void clickOnIntegration(String integration, int wait){
        ConfigureSMSIntegrationRow integrationRow = getIntegrationRow(integration);
        for(int i = 0; i<wait; i++){
            if(integrationRow==null){
                waitFor(1000);
                integrationRow = getIntegrationRow(integration);
            }
            else break;
        }
        if(integrationRow == null) Assert.fail("Cannot find '" + integration + "' integration on Configure SMS integrations page");
        integrationRow.openIntegration();
    }

}
