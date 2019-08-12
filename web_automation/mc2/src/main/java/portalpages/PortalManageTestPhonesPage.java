package portalpages;

import org.openqa.selenium.WebDriver;
import portaluielem.ManageTestPhonesTable;

public class PortalManageTestPhonesPage extends PortalAbstractPage {

    private ManageTestPhonesTable manageTestPhonesTable;

    // == Constructors == //

    public PortalManageTestPhonesPage() {
        super();
    }
    public PortalManageTestPhonesPage(String agent) {
        super(agent);
    }
    public PortalManageTestPhonesPage(WebDriver driver) {
        super(driver);
    }

    public ManageTestPhonesTable getManageTestPhonesTable(){
        waitForAngularRequestsToFinish(this.getCurrentDriver());
        manageTestPhonesTable.setCurrentDriver(this.getCurrentDriver());
        return manageTestPhonesTable;
    }
}
