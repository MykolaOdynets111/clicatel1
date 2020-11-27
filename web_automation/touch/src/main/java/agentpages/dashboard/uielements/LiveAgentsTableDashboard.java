package agentpages.dashboard.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = ".cl-collapsible-table")
public class LiveAgentsTableDashboard extends AbstractUIElement {

    @FindBy(css = "tr.cl-collapsible-table__row")
    private List<WebElement> agentsRow;

    @FindBy(css = "tr.cl-collapsible-row")
    private List<WebElement> usersRow;


    public LiveAgentRowDashboard getTargetAgentRow(String agentFirstName, String agentLastName) {
        return agentsRow.stream().map(e -> new LiveAgentRowDashboard(e).setCurrentDriver(this.getCurrentDriver()))
                .filter(a -> a.getFistAgentName().equalsIgnoreCase(agentFirstName)
                        && a.getLastAgentName().equalsIgnoreCase(agentLastName))
                .findFirst().get();
    }

    public LiveAgentsCustomerRow getOpenCustomersRow(String customerName) {
        return usersRow.stream().map(e -> new LiveAgentsCustomerRow(e).setCurrentDriver(this.getCurrentDriver()))
                .filter(a -> customerName.contains(a.getCustomerName().replace("...", "")))
                .findFirst().get();
    }

}
