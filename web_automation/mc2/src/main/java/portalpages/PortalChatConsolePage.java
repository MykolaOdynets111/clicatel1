package portalpages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portaluielem.AgentsTableChatConsole;

public class PortalChatConsolePage extends PortalAbstractPage {

    @FindBy(xpath = "//p[text()='Chats waiting in your queue']/following-sibling::div[@class='average-number--blue ng-binding']")
    private WebElement chatsWaitingCounter;

    @FindBy(xpath = "//p[text()='Live chats active']/following-sibling::div[@class='average-number--blue ng-binding']")
    private WebElement liveChatsCounter;

    @FindBy(xpath = "//p[text()='Agents online']/following-sibling::div[@class='average-number--blue ng-binding']")
    private WebElement agentsOnlineCounter;

    @FindBy(xpath = "//p[text()='Live chats active']//following-sibling::p/b")
    private WebElement averageChatsPerAgent;

    @FindBy(css = "div[ng-show='agentsChatsStats && !agentsChatsStats.length'] h3.empty-notification")
    private WebElement noAgentsNotification;

    private AgentsTableChatConsole agentsTableChatConsole;

    // == Constructors == //

    public PortalChatConsolePage() {
        super();
    }
    public PortalChatConsolePage(String agent) {
        super(agent);
    }
    public PortalChatConsolePage(WebDriver driver) {
        super(driver);
    }

    public AgentsTableChatConsole getAgentsTableChatConsole(){
        agentsTableChatConsole.setCurrentDriver(this.getCurrentDriver());
        return agentsTableChatConsole;
    }

    public String getWaitingChatsNumber(){
        return getTextFromElem(this.getCurrentDriver(), chatsWaitingCounter, 6,"Customers waiting for response");
    }

    public String getLiveChatsNumber(){
        return getTextFromElem(this.getCurrentDriver(), liveChatsCounter, 6,"Customer engaging with an Agent");
    }

    public String getAgentsOnlineNumber(){
        return getTextFromElem(this.getCurrentDriver(), agentsOnlineCounter, 6,"Total Agents online");
    }

    public String getWidgetValue(String value){
        switch (value) {
            case "Customers waiting for response":
                return getWaitingChatsNumber();
            case "Customer engaging with an Agent":
                return getLiveChatsNumber();
            case "Total Agents online":
                return getAgentsOnlineNumber();
        }
        return "invalid widget name";
    }

    public String getAverageChatsPerAgent(){
        return getTextFromElem(this.getCurrentDriver(), averageChatsPerAgent, 3,"Average chats per Agent");
    }

    public boolean isNoAgentsOnlineShown(){
        return isElementShown(this.getCurrentDriver(), noAgentsNotification, 8);
    }
}
