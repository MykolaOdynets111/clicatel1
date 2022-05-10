package portalpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portaluielem.ConfigureSMSIntegrationsTable;
import portaluielem.ConfigureSMSSection;

import java.util.List;

public class PortalConfigureSMSPage extends PortalAbstractPage {

    @FindBy(css = "div.cl-card-content")
    private List<WebElement> configureSMSSections;

    private ConfigureSMSIntegrationsTable smsIntegrationsTable;

    // == Constructors == //

    public PortalConfigureSMSPage() {
        super();
    }
    public PortalConfigureSMSPage(String agent) {
        super(agent);
    }
    public PortalConfigureSMSPage(WebDriver driver) {
        super(driver);
    }


    public ConfigureSMSIntegrationsTable getSmsIntegrationsTable(){
        smsIntegrationsTable.setCurrentDriver(this.getCurrentDriver());
        return smsIntegrationsTable;
    }

    public ConfigureSMSSection getSection(String sectionTitle){
        waitForAngularRequestsToFinish(this.getCurrentDriver());
        waitForAngularToBeReady(this.getCurrentDriver());
        try{
            return configureSMSSections.stream().map( e -> new ConfigureSMSSection(e).setCurrentDriver(this.getCurrentDriver()))
                    .filter(e -> e.getSectionTitle().equals(sectionTitle)).findFirst().orElseGet(null);
        }catch (NullPointerException e){
            return null;
        }
    }

}
