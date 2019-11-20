package portalpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portaluielem.AddNewAgentWindow;
import portaluielem.PortalUserRow;

import java.util.List;

public class PortalManageAgentUsersPage extends PortalAbstractPage {

    @FindBy(css = "table.table-integration.cl-table--ui>tbody>tr")
    private List<WebElement> userRows;

    private AddNewAgentWindow addNewAgentWindow;

    // == Constructors == //

    public PortalManageAgentUsersPage() {
        super();
    }
    public PortalManageAgentUsersPage(String agent) {
        super(agent);
    }
    public PortalManageAgentUsersPage(WebDriver driver) {
        super(driver);
    }

    public AddNewAgentWindow getAddNewAgentWindow(){
        addNewAgentWindow.setCurrentDriver(this.getCurrentDriver());
        return addNewAgentWindow;
    }

    public PortalUserRow getTargetUserRow(String fullName){
        waitForElementsToBeVisible(this.getCurrentDriver(), userRows, 3 );
        return new PortalUserRow (
                userRows.stream().filter(e -> new PortalUserRow(e).setCurrentDriver(this.getCurrentDriver())
                        .getAgentFullName().equals(fullName))
                .findFirst().orElseThrow(() ->
                        new AssertionError(fullName + " user is not shown on Manage Agent User page"))
        );
    }

    public PortalUserEditingPage clickManageButtonForUser(String fullName){
        getTargetUserRow(fullName).clickManageButton();
        return new PortalUserEditingPage(this.getCurrentDriver());
    }

    public boolean isUserShown(String username, int wait){
        boolean isPresent = false;
        for(int i=0; i<(wait/100); i++) {
            if(!isPresent){
                waitFor(100);
                isPresent = userRows.stream()
                        .anyMatch(e -> new PortalUserRow(e).getAgentFullName().equals(username));
            } else{
                break;
            }
        }
        return isPresent;
    }
}
