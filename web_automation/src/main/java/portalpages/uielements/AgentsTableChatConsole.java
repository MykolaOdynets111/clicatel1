package portalpages.uielements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css ="section.cl-content-area.mod-portal.mod-small-cards")
public class AgentsTableChatConsole extends BasePortalWindow {

    private String currentAgent = "admin";
    private String agentNameXpath = "//div[@class='animation-box-wrapper']//span[text()='%s']";

    @FindBy(css = "tr.animate-repeat.ng-scope")
    private List<WebElement> agentsRow;

    public boolean isAgentShown(String agent, int wait){
        String targetLocator = String.format(agentNameXpath, agent);
        return isElementShownAgentByXpath(targetLocator, wait, currentAgent);
    }

    public AgentRowChatConsole getTargetAgentRow(String agentName){
        return agentsRow.stream().map(e -> new AgentRowChatConsole(e))
                .filter(a -> a.getAgentName().equalsIgnoreCase(agentName))
                .findFirst().get();
    }

}
