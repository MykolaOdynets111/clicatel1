package portalpages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portalpages.uielements.AddNewAgentWindow;
import portalpages.uielements.PortalUserRow;

import java.util.List;

public class PortalManageAgentUsersPage extends PortalAbstractPage {

    @FindBy(css = "table.table-integration.cl-table--ui>tbody>tr")
    private List<WebElement> userRows;

    private AddNewAgentWindow addNewAgentWindow;

    public AddNewAgentWindow getAddNewAgentWindow(){
        return addNewAgentWindow;
    }

    private PortalUserRow getTargetUserRow(String fullName){
        waitForElementsToBeVisibleAgent(userRows, 3, "admin");
        return new PortalUserRow (
                userRows.stream().filter(e -> new PortalUserRow(e).getAgentFullName().equals(fullName))
                .findFirst().get()
        );
    }

    public PortalUserEditingPage clickManageButtonForUser(String fullName){
        getTargetUserRow(fullName).clickManageButton();
        return new PortalUserEditingPage();
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
