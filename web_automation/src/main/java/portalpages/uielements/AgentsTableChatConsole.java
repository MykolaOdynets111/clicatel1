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

    private AgentRowChatConsole getTargetAgentRow(String agentName){
        return agentsRow.stream().map(e -> new AgentRowChatConsole(e))
                .filter(a -> a.getAgentName().equalsIgnoreCase(agentName))
                .findFirst().get();
    }

    public boolean isAgentMarkedWithGreenDot(String agentName, int wait){
        return getTargetAgentRow(agentName).isActiveChatIconShown(wait);
    }

    public boolean isAgentMarkedWithYellowDot(String agentName, int wait){
        return getTargetAgentRow(agentName).isNoActiveChatsIconShown(wait);
    }

    public void clickExpandRow(String agentName){
        getTargetAgentRow(agentName).clickExpandButton();
    }

    public List<String> getShownChatsForAgent(String agentName){
        return getTargetAgentRow(agentName).getChattingTo();
    }

    public List<String> getShownChannelsForAgent(String agentName){
        return getTargetAgentRow(agentName).getChannels();
    }

    public List<String> getShownIntentsForAgent(String agentName){
        return getTargetAgentRow(agentName).getIntents();
    }

    public List<String> getShownSentimentForAgent(String agentName){
        return getTargetAgentRow(agentName).getSentiments();
    }
}
