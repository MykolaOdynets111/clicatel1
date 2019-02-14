package portal_pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portal_pages.uielements.PortalUserRow;

import java.util.List;

public class PortalManagingUsersPage extends PortalAbstractPage {

    @FindBy(css = "table.table-integration.cl-table--ui>tbody>tr")
    public List<WebElement> userRows;


    private PortalUserRow getTargetUserRow(String fullName){
        return new PortalUserRow (
                userRows.stream().filter(e -> new PortalUserRow(e).getAgentFullName().equals(fullName))
                .findFirst().get()
        );
    }

    public PortalUserManagementPage clickManageButtonForUser(String fullName){
        getTargetUserRow(fullName).clickManageButton();
        return new PortalUserManagementPage();
    }
}
