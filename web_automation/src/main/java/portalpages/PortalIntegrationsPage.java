package portalpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import portalpages.uielements.*;

import java.util.List;
import java.util.stream.Collectors;

public class PortalIntegrationsPage extends PortalAbstractPage {

    @FindBy(css = "tr[ng-repeat='channel in channels']")
    private List<WebElement> integrationRows;

    @FindBy(xpath = "//div[contains(@class,'integration-type cl-card')]")
    private List<WebElement> integrationCards;

    private CreateIntegrationWindow createIntegrationWindow;

    // == Constructors == //

    public PortalIntegrationsPage() {
        super();
    }
    public PortalIntegrationsPage(String agent) {
        super(agent);
    }
    public PortalIntegrationsPage(WebDriver driver) {
        super(driver);
    }

    public CreateIntegrationWindow getCreateIntegrationWindow() {
        createIntegrationWindow.setCurrentDriver(this.getCurrentDriver());
        return createIntegrationWindow;
    }

    private IntegrationRow getTargetIntegrationRow(String integrationName){
        return integrationRows.stream().map(e -> new IntegrationRow(e).setCurrentDriver(this.getCurrentDriver()))
                .collect(Collectors.toList())
                .stream().filter(a -> a.getIntegrationName().toLowerCase().contains(integrationName.toLowerCase()))
                .findFirst().get();
    }

    private IntegrationCard getTargetIntegrationCard(String integrationName){
        return integrationCards.stream().map(e -> new IntegrationCard(e).setCurrentDriver(this.getCurrentDriver()))
                .collect(Collectors.toList())
                .stream().filter(a -> a.getIntegrationName().toLowerCase().contains(integrationName.toLowerCase()))
                .findFirst().get();
    }

    public void clickToggleFor(String integrationName){
        try {
            getTargetIntegrationRow(integrationName).clickToggle();
        } catch(java.util.NoSuchElementException e){
            Assert.fail("Toggle for managing '"+integrationName+"' integration is not shown on the page");
        }
    }

    // changing toggle state if it's required
    public void switchToggleStateTo(String integrationName, String toggleAction){
        if ((toggleAction.equalsIgnoreCase("enable") && getTargetIntegrationRow(integrationName).isToggleEnabled()) ||
            (toggleAction.equalsIgnoreCase("disable") && !getTargetIntegrationRow(integrationName).isToggleEnabled()))
            return;  // we are in required toggle state
        else
            clickToggleFor(integrationName);
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
