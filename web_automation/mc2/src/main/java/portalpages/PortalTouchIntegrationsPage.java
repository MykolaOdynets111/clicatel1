package portalpages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import portaluielem.CreateIntegrationWindow;
import portaluielem.IntegrationCard;
import portaluielem.IntegrationRow;

import java.util.List;
import java.util.stream.Collectors;

public class PortalTouchIntegrationsPage extends PortalAbstractPage {

    @FindBy(css = "tr[ng-repeat='channel in channels']")
    private List<WebElement> integrationRows;

    @FindBy(xpath = "//div[contains(@class,'integration-type cl-card')]")
    private List<WebElement> integrationCards;

    @FindBy(css = "div.mod-touch-integration")
    private WebElement touchPage;

    private CreateIntegrationWindow createIntegrationWindow;

    // == Constructors == //

    public PortalTouchIntegrationsPage() {
        super();
    }
    public PortalTouchIntegrationsPage(String agent) {
        super(agent);
    }
    public PortalTouchIntegrationsPage(WebDriver driver) {
        super(driver);
    }

    public CreateIntegrationWindow getCreateIntegrationWindow() {
        createIntegrationWindow.setCurrentDriver(this.getCurrentDriver());
        return createIntegrationWindow;
    }

    private IntegrationRow getTargetIntegrationRow(String integrationName){
        waitForAngularRequestsToFinish(this.getCurrentDriver());
        waitForAngularToBeReady(this.getCurrentDriver());
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

    @Step(value = "Get '{integrationName}' integration status in the table")
    public String getIntegrationRowStatus(String integrationName){
        return getTargetIntegrationRow(integrationName).getStatus();
    }

    @Step(value = "Get '{integrationName}' integration status in the card")
    public String getIntegrationCardStatus(String integrationName){
        return getTargetIntegrationCard(integrationName).getStatus();
    }

    public void clickActionButtonForIntegration(String integrationName){
        getTargetIntegrationCard(integrationName).clickActionButton();
    }

    @Step(value = "Is Touch Integrations page opened")
    public boolean isPageOpened(){
        return  isElementShown(this.getCurrentDriver(), touchPage, 3);
    }
}
