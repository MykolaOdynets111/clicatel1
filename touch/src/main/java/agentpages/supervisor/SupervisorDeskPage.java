package agentpages.supervisor;

import agentpages.supervisor.uielements.*;
import agentpages.uielements.ChatBody;
import agentpages.uielements.ChatHeader;
import agentpages.uielements.Profile;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import portalpages.PortalAbstractPage;
import portaluielem.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class SupervisorDeskPage extends PortalAbstractPage {

    @FindBy(css = ".cl-chat-item")
    private List<WebElement> chatsLive;

    @FindBy(xpath = "//h2[text() ='Loading...']")
    private WebElement loading;

    @FindBy (css = ".ReactModal__Content.ReactModal__Content--after-open.cl-modal")
    private WebElement assignWindowsDialog;

    @FindBy(css = ".chats-list-extended-view-header-text")
    private WebElement openedChatHeader;

    @FindAll({
            @FindBy(css = ".bottom-action-bar--send-notification>button"),
            @FindBy(css = "[data-testid=chat-form-send-email]")
    })
    private WebElement openedClosedChatMessageUserButton;

    @FindBy(xpath = "//span[contains(@class,'chats-list-extended-view-header-text')]/following-sibling::a[@href='/supervisor/closed']")
    private WebElement closeOpenedClosedChatView;

    @FindBy(xpath = "//div[text()='Loading results']")
    private WebElement loadingResults;

    @FindBy(xpath = "//div[@class='spinner']")
    private WebElement spinner;

    @FindAll({
            @FindBy(css = ".chats-list .cl-empty-state"),
            @FindBy(css = ".cl-table-body .cl-empty-state")
    })
    private WebElement noChatsErrorMessage;

    @FindBy(css = ".cl-agent-view-trigger-btn")
    private WebElement supervisorButton;

    @FindBy(css = ".cl-agent-view-launch-btn")
    private WebElement launchAgentButton;

    @FindBy(xpath = "//button[@aria-label='Previous Month']")
    private WebElement backButton;

    @FindBy(css = "[class='cl-form-control cl-form-control--input']")
    private WebElement startDateInput;

    @FindBy(xpath = "//input[contains(@class, 'cl-form-control cl-form-control--input cl-end-date')]")
    private WebElement endDateInput;


    @FindBy(xpath = "//*[local-name()='svg' and @name='clock']/*[local-name()='g']")
    private WebElement leftChatPendingIcon;

    @FindBy(xpath= "//div[contains(text(),'Pending On')]")
    private WebElement leftChatPendingOn;

    @FindBy(css="svg[name='puzzle']")
    private WebElement c2pButton;

    private String backButtonString = "//button[@aria-label='Previous Month']";

    private String chatName = "//h2[@class='cl-chat-item-user-name' and text() ='%s']";

    @FindBy(css = ".cl-details-value")
    private WebElement profileName;

    //private String filterByDefaultXpath = "//span[text()='Conversation status:']//following-sibling::div//div[@class='cl-r-select__single-value css-1uccc91-singleValue']";

    private String iframeId = "ticketing-iframe";

    private AssignChatWindow assignChatWindow;
    private ChatBody chatBody;
    private SupervisorTicketsTable supervisorTicketsTable;
    private SupervisorClosedChatsTable supervisorClosedChatsTable;
    private SupervisorOpenedClosedChatsList supervisorOpenedClosedChatsList;
    private SupervisorLeftPanel supervisorLeftPanel;
    private ChatHeader chatHeader;
    private Profile profile;
    private SupervisorTicketClosedChatView supervisorTicketChatView;
    private MessageCustomerWindow messageCustomerWindow;
    private SupervisorDeskHeader supervisorDeskHeader;
    private SupervisorAvailableAsAgentDialog supervisorAvailableAsAgentDialog;


    // == Constructors == //

    public SupervisorDeskPage() {
        super();
    }
    public SupervisorDeskPage(String agent) {
        super(agent);
    }
    public SupervisorDeskPage(WebDriver driver) {
        super(driver);
    }

    public boolean checkBackButtonVisibilityThreeMonthsBack(String filterType) {
        if (filterType.equalsIgnoreCase("start date")) {
            clickElem(this.getCurrentDriver(), startDateInput, 5, "Start date element");
        } else if (filterType.equalsIgnoreCase("end date")) {
            clickElem(this.getCurrentDriver(), endDateInput, 5, "End date element");
        }

        //Clicking back button 3 times for back button invisibility check
        clickElem(this.getCurrentDriver(), backButton, 5, "Back button element");
        clickElem(this.getCurrentDriver(), backButton, 5, "Back button element");
        clickElem(this.getCurrentDriver(), backButton, 5, "Back button element");

        try {
            if (filterType.equalsIgnoreCase("start date")) {
                waitForElementToBeInvisibleByXpath(this.getCurrentDriver(), backButtonString, 5);
            } else if (filterType.equalsIgnoreCase("end date")) {
                waitForElementToBeVisibleByXpath(this.getCurrentDriver(), backButtonString, 5);
            }
            return true;
        } catch (Exception e) {
            throw new AssertionError("There is issue with Back button visibility for selected filter");
        }
    }

    public AssignChatWindow getAssignChatWindow(){
        assignChatWindow.setCurrentDriver(this.getCurrentDriver());
        return assignChatWindow;
    }

    public SupervisorTicketsTable getSupervisorTicketsTable(){
        supervisorTicketsTable.setCurrentDriver(this.getCurrentDriver());
        return supervisorTicketsTable;
    }

    public SupervisorLeftPanel getSupervisorLeftPanel(){
        supervisorLeftPanel.setCurrentDriver(this.getCurrentDriver());
        return supervisorLeftPanel;
    }

    public ChatHeader getChatHeader() {
        chatHeader.setCurrentDriver(this.getCurrentDriver());
        return chatHeader;
    }

    public Profile getProfile() {
        profile.setCurrentDriver(this.getCurrentDriver());
        return profile;
    }

    public SupervisorTicketClosedChatView getSupervisorTicketClosedChatView(){
        supervisorTicketChatView.setCurrentDriver(this.getCurrentDriver());
        return supervisorTicketChatView;
    }

    public MessageCustomerWindow getMessageCustomerWindow(){
        messageCustomerWindow.setCurrentDriver(this.getCurrentDriver());
        return messageCustomerWindow;
    }

    public SupervisorClosedChatsTable getSupervisorClosedChatsTable(){
        supervisorClosedChatsTable.setCurrentDriver(this.getCurrentDriver());
        return supervisorClosedChatsTable;
    }

    public SupervisorDeskHeader getSupervisorDeskHeader(){
        supervisorDeskHeader.setCurrentDriver(this.getCurrentDriver());
        return  supervisorDeskHeader;
    }

    public SupervisorOpenedClosedChatsList getSupervisorOpenedClosedChatsList(){
        supervisorOpenedClosedChatsList.setCurrentDriver(this.getCurrentDriver());
        return supervisorOpenedClosedChatsList;
    }

    public SupervisorAvailableAsAgentDialog getSupervisorAvailableAsAgentDialog() {
        supervisorAvailableAsAgentDialog.setCurrentDriver(this.getCurrentDriver());
        return supervisorAvailableAsAgentDialog;
    }

    public boolean isLiveChatShownInSD(String userName, int wait) {
        return isElementShownByXpath(this.getCurrentDriver(), String.format(chatName, userName), wait);
    }

    public SupervisorDeskLiveRow getSupervisorDeskLiveRow(String userName){
        waitForFirstElementToBeVisible(this.getCurrentDriver(), chatsLive, 7);
        waitForElementToBeInvisibleByXpath(this.getCurrentDriver(), "//h2[text() ='Loading...']", 6);
        return chatsLive.stream()
                 .map(e -> new SupervisorDeskLiveRow(e).setCurrentDriver(this.getCurrentDriver()))
                 .collect(Collectors.toList())
                 .stream().filter(a -> a.getUserName().toLowerCase()
                        .contains(userName.toLowerCase()))
                .findFirst().orElseThrow(() -> new AssertionError("Cannot find chat with user " + userName));
    }

    public boolean verifyChanelFilter(){
        Set<String> channels = chatsLive.stream()
                .map(e -> new SupervisorDeskLiveRow(e).setCurrentDriver(this.getCurrentDriver()))
                .collect(Collectors.toList())
                .stream().map(a -> a.getIconName()).collect(Collectors.toSet());
        return channels.size() == 1;
    }

    public boolean verifyChanelOfTheChatIsPresent(String channelName){
        waitForFirstElementToBeVisible(this.getCurrentDriver(), chatsLive, 7);
        return  chatsLive.stream()
                .map(e -> new SupervisorDeskLiveRow(e).setCurrentDriver(this.getCurrentDriver()))
                .collect(Collectors.toList()).get(0).getIconName().equalsIgnoreCase(channelName);
    }

    public String getCurrentAgentOfTheChat(String userName){
        String currentAgent = getSupervisorTicketsTable().getTicketByUserName(userName).getCurrentAgent();
        return currentAgent.substring(3);
    }

    public boolean isAssignChatWindowOpened(){
        return isElementShown(this.getCurrentDriver(), getAssignChatWindow().getWrappedElement(), 4);
    }

    public void assignChatOnAgent(String agentName){
        getAssignChatWindow().selectDropDownAgent(agentName);
        getAssignChatWindow().clickAssignChatButton();
        waitUntilElementNotDisplayed(this.getCurrentDriver(), assignWindowsDialog, 3);
    }

    public void scrollTicketsDown(){
        getSupervisorTicketsTable().scrollTicketsToTheButtom();
        getSupervisorTicketsTable().waitForMoreTicketsAreLoading(2,5);
    }

    public void waitForConnectingDisappear(int waitForSpinnerToAppear, int waitForSpinnerToDisappear){
        waitForAppearAndDisappear(this.getCurrentDriver(), spinner, waitForSpinnerToAppear, waitForSpinnerToDisappear);
    }

     public void waitForLoadingResultsDisappear(int timeToAppear, int timeToDisappear){
         waitForAppearAndDisappear(this.getCurrentDriver(), loadingResults, timeToAppear, timeToDisappear);
     }

    public boolean areNewChatsLoaded(int previousChats, int wait){
        for(int i = 0; i< wait*2; i++){
            if(getSupervisorTicketsTable().getUsersNames().size() > previousChats) return true;
            else waitFor(500);
        }
        return false;
    }

    public SupervisorDeskPage selectTicketType(String option){
        getSupervisorLeftPanel().selectTicketType(option);
        return this;
    }

    public List<String> getTicketTypes(){
        return getSupervisorLeftPanel().getFilterNames();
    }

    public ChatBody openInboxChatBody(String userName){
        getSupervisorDeskLiveRow(userName).clickOnUserName();
        chatBody.setCurrentDriver(this.getCurrentDriver());
        return chatBody;
    }

    public String getNoChatsErrorMessage(){
        return getTextFromElem(this.getCurrentDriver(), noChatsErrorMessage, 3, "No Chats Error Message");
    }

    public boolean isSendEmailForOpenedClosedChatShown() {
        return isElementShown(this.getCurrentDriver(), openedClosedChatMessageUserButton, 5);
    }

    public void clickOnLaunchAgent() {
        clickElem(this.getCurrentDriver(), supervisorButton, 3, "Supervisor Button Dropdown");
        clickElem(this.getCurrentDriver(), launchAgentButton, 3, "Launch supervisor as agent button");
    }

    public void loadAllTickets() {
        getSupervisorTicketsTable().loadAllFoundTickets();
    }

    public void loadAllClosedChats() {
        getSupervisorClosedChatsTable().loadAllFoundChats();
    }

    public void verifyChatPendingIcon() {
        Assert.assertTrue(leftChatPendingIcon.isDisplayed(),"Pending icon not displayed ");
    }

    public void verifyChatPendingOn() {
        Assert.assertTrue(leftChatPendingOn.isDisplayed(), "Pending icon not displayed ");
    }

    public void verifyProfileNameUpdated() {
        isElementShown(this.getCurrentDriver(), profileName, 3);
    }

    public void verifyInitiatePayment() {
        Assert.assertFalse(isElementShown(this.getCurrentDriver(), c2pButton, 5));
    }
}
