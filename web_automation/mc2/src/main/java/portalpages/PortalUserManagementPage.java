package portalpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portaluielem.PortalUserRow;

import java.util.List;

public class PortalUserManagementPage extends PortalAbstractPage {

    @FindBy(css = "table.table-integration.cl-table--ui>tbody>tr")
    private List<WebElement> userRows;

    // == Constructors == //

    public PortalUserManagementPage() {
        super();
    }
    public PortalUserManagementPage(String agent) {
        super(agent);
    }
    public PortalUserManagementPage(WebDriver driver) {
        super(driver);
    }

    private PortalUserRow getTargetUserRow(String fullName){
        waitForElementsToBeVisible(this.getCurrentDriver(), userRows, 3);
        return new PortalUserRow (
                userRows.stream().filter(e -> new PortalUserRow(e).setCurrentDriver(this.getCurrentDriver())
                        .getAgentFullName().equals(fullName))
                .findFirst().get()
        );
    }


    public boolean isUserShown(String username, int wait){
        boolean isPresent = false;
        for(int i=0; i<(wait/100); i++) {
            if(!isPresent){
                waitFor(100);
                isPresent = userRows.stream()
                        .anyMatch(e -> new PortalUserRow(e).setCurrentDriver(this.getCurrentDriver())
                                .getAgentFullName().equals(username));
            } else{
                break;
            }
        }
        return isPresent;
    }
}
