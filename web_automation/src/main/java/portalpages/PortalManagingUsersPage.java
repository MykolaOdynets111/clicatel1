package portalpages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portalpages.uielements.PortalUserRow;

import java.util.List;
import java.util.stream.Collectors;

public class PortalManagingUsersPage extends PortalAbstractPage {

    @FindBy(css = "table.table-integration.cl-table--ui>tbody>tr")
    private List<WebElement> userRows;


    private PortalUserRow getTargetUserRow(String fullName){
        waitForElementsToBeVisibleAgent(userRows, 3, "admin");
        List<String> aa = userRows.stream().map(e -> new PortalUserRow(e).getAgentFullName())
                .collect(Collectors.toList());
        return new PortalUserRow (
                userRows.stream().filter(e -> new PortalUserRow(e).getAgentFullName().equals(fullName))
                .findFirst().get()
        );
    }

    public PortalUserEditingPage clickManageButtonForUser(String fullName){
        getTargetUserRow(fullName).clickManageButton();
        return new PortalUserEditingPage();
    }
}
