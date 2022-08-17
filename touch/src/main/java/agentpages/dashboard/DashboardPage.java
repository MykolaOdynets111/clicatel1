package agentpages.dashboard;


import agentpages.dashboard.uielements.*;
import agentpages.uielements.C2pSendForm;
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

    @FindBy(css = "div[ng-show='agentsChatsStats && !agentsChatsStats.length'] h3.empty-notification")
    private WebElement noAgentsNotification;

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

    @FindBy(css = "[data-testid='tab-dashboard-tabs-1']")
    private WebElement agentsPerformanceTabButton;

    private final String spinner = "//div[@class='spinner']";

    private AgentPerformanceTab agentPerformanceTab;
    private LiveAgentsTableDashboard agentsTableDashboard;
    private CustomersOverviewTab customersOverviewTab;

    private C2pSendForm customerPaytoExtension;

    private CustomersHistory customersHistory;
    private LiveCustomersTab liveCustomersTab;
    private NetPromoterScoreSection netPromoterScoreSection;
    private CustomerSatisfactionSection customerSatisfactionSection;
    private LiveChatsByChannel liveChatsByChannel;
    private LiveChatsByChannel verifyChatBackground;
    private GeneralSentimentPerChannel generalSentimentPerChannel;
    private AttendedVsUnattendedChats attendedVsUnattendedChats;
    // == Constructors == //

    public DashboardPage() {
        super();
    }

    public DashboardPage(String agent) {
        super(agent);
    }

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

    public C2pSendForm getCustomerPaytoExtension() {
        customerPaytoExtension.setCurrentDriver(this.getCurrentDriver());
        return customerPaytoExtension;
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

    public LiveChatsByChannel getChatBackground(){
        verifyChatBackground.setCurrentDriver(this.getCurrentDriver());
        return verifyChatBackground;
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
        return getTextFromElem(this.getCurrentDriver(), chatsWaitingCounter, 0, "Customers waiting for response");
    }

    public String getLiveChatsNumber() {
        return getTextFromElem(this.getCurrentDriver(), liveChatsCounter, 6, "Customer engaging with an Agent");
    }

    public String getAgentsOnlineNumber() {
        return getTextFromElem(this.getCurrentDriver(), agentsOnlineCounter, 6, "Total Agents online");
    }

    public String getWidgetValue(String value) {
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

    public String getAverageChatsPerAgent() {
        return getTextFromElem(this.getCurrentDriver(), averageChatsPerAgent, 3, "Average chats per Agent").split(" ")[0];
    }

    public boolean isNoAgentsOnlineShown() {
        return isElementShown(this.getCurrentDriver(), noAgentsNotification, 8);
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
