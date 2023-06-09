package agentpages.dashboard;


import agentpages.dashboard.uielements.*;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import portalpages.PortalAbstractPage;


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

    @FindBy(xpath = "//a[text()='Settings']")
    private WebElement settingsButton;

    @FindBy(xpath = "//a[text()='Launch Supervisor Desk']")
    private WebElement launchSupervisorButton;

    @FindBy(xpath = "//a[text()='Launch Agent Desk']")
    private WebElement launchAgentDeskButton;

    @FindBy(xpath = "//a[text()='Departments Management']")
    private WebElement departmentsManagementButton;

    @FindAll({
            @FindBy(css = "[data-testid='tab-navigation-panel-customers']"),
            @FindBy(css = "[selenium-id='tab-dashboard-tabs-2']")
    })
    private WebElement customersOverviewTabButton;

    @FindBy(css = "[data-testid='tab-navigation-panel-agents-performance']")
    private WebElement agentsPerformanceTabButton;

    private final String spinner = "//div[@class='spinner']";

    private AgentPerformanceTab agentPerformanceTab;
    private LiveAgentsTableDashboard agentsTableDashboard;
    private CustomersOverviewTab customersOverviewTab;
    private CustomersHistory customersHistory;
    private LiveCustomersTab liveCustomersTab;
    private NetPromoterScoreSection netPromoterScoreSection;
    private CustomerSatisfactionSection customerSatisfactionSection;
    private LiveChatsByChannel liveChatsByChannel;
    private GeneralSentimentPerChannel generalSentimentPerChannel;
    private AttendedVsUnattendedChats attendedVsUnattendedChats;
    // == Constructors == //

    public DashboardPage(WebDriver driver) {
        super(driver);
        if (isElementShown(this.getCurrentDriver(), mainFrame, 10)) {
            waitForConnectingDisappear(2, 5);
        }
    }

    public LiveAgentsTableDashboard getAgentsTableDashboard() {
        agentsTableDashboard.setCurrentDriver(this.getCurrentDriver());
        return agentsTableDashboard;
    }

    public AgentPerformanceTab getAgentPerformanceTab() {
        agentPerformanceTab.setCurrentDriver(this.getCurrentDriver());
        scrollToElem(this.getCurrentDriver(), agentPerformanceTab.getWrappedElement(), "Live Chats By Chanel");
        return agentPerformanceTab;
    }

    public CustomersOverviewTab getCustomersOverviewTab() {
        customersOverviewTab.setCurrentDriver(this.getCurrentDriver());
        return customersOverviewTab;
    }

    public CustomersHistory getCustomersHistory() {
        customersHistory.setCurrentDriver(this.getCurrentDriver());
        scrollToElem(this.getCurrentDriver(), customersHistory, "Customers History");
        return customersHistory;
    }

    public LiveCustomersTab getLiveCustomersTab() {
        liveCustomersTab.setCurrentDriver(this.getCurrentDriver());
        return liveCustomersTab;
    }

    public NetPromoterScoreSection getNetPromoterScoreSection() {
        netPromoterScoreSection.setCurrentDriver(this.getCurrentDriver());
        scrollToElem(this.getCurrentDriver(), netPromoterScoreSection.getWrappedElement(), "Net Promoter Score Section");
        return netPromoterScoreSection;
    }

    public CustomerSatisfactionSection getCustomerSatisfactionSection() {
        customerSatisfactionSection.setCurrentDriver(this.getCurrentDriver());
        scrollToElem(this.getCurrentDriver(), customerSatisfactionSection.getWrappedElement(), "Customer Satisfaction Section");
        return customerSatisfactionSection;
    }

    public LiveChatsByChannel getLiveChatsByChannel() {
        liveChatsByChannel.setCurrentDriver(this.getCurrentDriver());
        scrollToElem(this.getCurrentDriver(), liveChatsByChannel.getWrappedElement(), "Live Chats By Chanel");
        return liveChatsByChannel;
    }

    public GeneralSentimentPerChannel getGeneralSentimentPerChannel() {
        generalSentimentPerChannel.setCurrentDriver(this.getCurrentDriver());
        return generalSentimentPerChannel;
    }

    public AttendedVsUnattendedChats getAttendedVsUnattendedChats() {
        attendedVsUnattendedChats.setCurrentDriver(this.getCurrentDriver());
        return attendedVsUnattendedChats;
    }

    public String getWaitingChatsNumber() {
        return getTextFromElem(this.getCurrentDriver(), chatsWaitingCounter, 0, "Chats waiting in queue");
    }

    public String getLiveChatsNumber() {
        return getTextFromElem(this.getCurrentDriver(), liveChatsCounter, 6, "Active live chats");
    }

    public String getAgentsOnlineNumber() {
        return getTextFromElem(this.getCurrentDriver(), agentsOnlineCounter, 6, "Agents available");
    }

    public int getCounterValue(String value) {
        scrollToElem(this.getCurrentDriver(), chatsWaitingCounter, "How are your Agents performing right now?");
        switch (value) {
            case "Chats waiting in queue":
                return Integer.parseInt(getWaitingChatsNumber());
            case "Active live chats":
                return Integer.parseInt(getLiveChatsNumber());
            case "Agents available":
                return Integer.parseInt(getAgentsOnlineNumber());
            default:
                return -1;
        }
    }

    public String getAverageChatsPerAgent() {
        return getTextFromElem(this.getCurrentDriver(), averageChatsPerAgent, 3, "Average chats per Agent").split(" ")[0];
    }

    public boolean waitForConnectingDisappear(int waitForSpinnerToAppear, int waitForSpinnerToDisappear) {
        try {
            try {
                waitForElementToBeVisibleByXpath(this.getCurrentDriver(), spinner, waitForSpinnerToAppear);
            } catch (TimeoutException e) {
            }
            waitForElementToBeInvisibleByXpath(this.getCurrentDriver(), spinner, waitForSpinnerToDisappear);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public DashboardSettingsPage openSettingsPage() {
        clickElem(this.getCurrentDriver(), settingsButton, 5, "Settings button");
        return new DashboardSettingsPage(this.getCurrentDriver());
    }

    public void clickOnCustomersOverviewTab() {
        scrollAndClickElem(this.getCurrentDriver(), customersOverviewTabButton, 5, "Customers Overview Tab");
    }

    public void clickOnAgentsPerformanceTab() {
        clickElem(this.getCurrentDriver(), agentsPerformanceTabButton, 5, "Agents Performance Tab");
    }

    public boolean isWelcomeToTheChatDeskDashboardDisplayed() {
        return isElementShown(this.getCurrentDriver(), mainFrame, 5);
    }

    public void clickLaunchSupervisor() {
        clickElem(this.getCurrentDriver(), launchSupervisorButton, 5, "Launch Supervisor");
    }

    public void clickLaunchAgentDesk() {
        clickElem(this.getCurrentDriver(), launchAgentDeskButton, 5, "Launch Agent Desk");
    }

    public void clickDepartmentsManagement() {
        clickElem(this.getCurrentDriver(), departmentsManagementButton, 5, "Departments Management");
    }
}
