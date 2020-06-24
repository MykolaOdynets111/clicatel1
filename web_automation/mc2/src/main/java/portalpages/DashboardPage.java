package portalpages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portaluielem.AgentsTableChatConsole;


public class DashboardPage extends PortalAbstractPage {

    @FindBy(css = ".welcome-dashboard")
    private WebElement mainFrame;

    @FindBy(xpath = "//div[text()='Chats waiting in queue']/parent::div/div[@class = 'cl-activity-overview-item-value']")
    private WebElement chatsWaitingCounter;

    @FindBy(xpath = "//div[text()='Active live chats']/parent::div/div[@class = 'cl-activity-overview-item-value']")
    private WebElement liveChatsCounter;

    @FindBy(xpath = "//div[text()='Agents available']/parent::div/div[@class = 'cl-activity-overview-item-value']")
    private WebElement agentsOnlineCounter;

    @FindBy(xpath = "//div[text()='Active live chats']/parent::div//span[@class = 'cl-activity-overview-item-average-value']")
    private WebElement averageChatsPerAgent;

    @FindBy(css = "div[ng-show='agentsChatsStats && !agentsChatsStats.length'] h3.empty-notification")
    private WebElement noAgentsNotification;

    private AgentsTableChatConsole agentsTableChatConsole;

    // == Constructors == //

    public DashboardPage() {
        super();
    }
    public DashboardPage(String agent) {
        super(agent);
    }
    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public AgentsTableChatConsole getAgentsTableChatConsole(){
        agentsTableChatConsole.setCurrentDriver(this.getCurrentDriver());
        return agentsTableChatConsole;
    }

    public String getWaitingChatsNumber(){
        return getTextFromElem(this.getCurrentDriver(), chatsWaitingCounter, 0,"Customers waiting for response");
    }

    public String getLiveChatsNumber(){
        return getTextFromElem(this.getCurrentDriver(), liveChatsCounter, 6,"Customer engaging with an Agent");
    }

    public String getAgentsOnlineNumber(){
        return getTextFromElem(this.getCurrentDriver(), agentsOnlineCounter, 6,"Total Agents online");
    }

    public String getWidgetValue(String value){
        waitForElementToBeVisible(this.getCurrentDriver(), mainFrame, 10);
        scrollToElem(this.getCurrentDriver(), chatsWaitingCounter, "How are your Agents performing right now?");
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
