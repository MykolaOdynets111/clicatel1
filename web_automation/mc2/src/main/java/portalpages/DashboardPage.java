package portalpages;


import org.openqa.selenium.TimeoutException;
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

    @FindBy(xpath ="//a[text()='Settings']")
    private WebElement settingsButton;

    private String spinner = "//div[@class='spinner']";

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
        if (isElementShown(this.getCurrentDriver(), mainFrame, 10)){
            waitForConnectingDisappear(2,5);
        }
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

    public boolean waitForConnectingDisappear(int waitForSpinnerToAppear, int waitForSpinnerToDisappear){
        try{
            try {
                waitForElementToBeVisibleByXpath(this.getCurrentDriver(), spinner, waitForSpinnerToAppear);
            }catch (TimeoutException e){ }
            waitForElementToBeInvisibleByXpath(this.getCurrentDriver(), spinner, waitForSpinnerToDisappear);
            return true;
        }
        catch (TimeoutException e){
            return false;
        }
    }

    public SettingsPage openSettingsPage(){
        clickElem(this.getCurrentDriver(), settingsButton, 4, "Settings button");
        return new SettingsPage(this.getCurrentDriver());
    }
}
