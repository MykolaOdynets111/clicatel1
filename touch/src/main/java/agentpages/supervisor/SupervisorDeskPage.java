package agentpages.supervisor;

import agentpages.supervisor.uielements.*;
import agentpages.uielements.ChatBody;
import agentpages.uielements.ChatHeader;
import agentpages.uielements.Profile;
import agentpages.uielements.RightPanelWindow;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import portalpages.PortalAbstractPage;
import portaluielem.AssignChatWindow;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class SupervisorDeskPage extends PortalAbstractPage {

    @FindBy(css = ".cl-chat-item")
    private List<WebElement> chatsLive;

    @FindBy (css = ".ReactModal__Content.ReactModal__Content--after-open.cl-modal")
    private WebElement assignWindowsDialog;

    @FindAll({
            @FindBy(css = ".bottom-action-bar--send-notification>button"),
            @FindBy(css = "[data-testid=chat-form-send-email]")
    })
    private WebElement openedClosedChatMessageUserButton;

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

    @FindBy(xpath = "//*[local-name()='svg' and @name='clock']/*[local-name()='g']")
    private WebElement leftChatPendingIcon;

    @FindBy(xpath= "//div[contains(text(),'Pending On')]")
    private WebElement leftChatPendingOn;

    @FindBy(css="svg[name='puzzle']")
    private WebElement c2pButton;

    private String chatName = "//h2[@class='cl-chat-item-user-name' and text() ='%s']";

    @FindBy(css = ".cl-details-value")
    private WebElement profileName;

    @FindAll({
            @FindBy(css = ".cl-modal-default-header-title']"),
            @FindBy(css = "Chat transferred to another agent")
    })
    private WebElement chatTransferAlert;

    private AssignChatWindow assignChatWindow;
    private ChatBody chatBody;
    private SupervisorTicketsTable supervisorTicketsTable;
    private SupervisorClosedChatsTable supervisorClosedChatsTable;
    private SupervisorOpenedClosedChatsList supervisorOpenedClosedChatsList;
    private SupervisorLeftPanel supervisorLeftPanel;
    private RightPanelWindow supervisorRightPanel;
    private ChatHeader chatHeader;
    private Profile profile;
    private SupervisorTicketClosedChatView supervisorTicketChatView;
    private MessageCustomerWindow messageCustomerWindow;
    private SupervisorDeskHeader supervisorDeskHeader;
    private SupervisorAvailableAsAgentDialog supervisorAvailableAsAgentDialog;

    public SupervisorDeskPage(WebDriver driver) {
        super(driver);
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

    public RightPanelWindow getSupervisorRightPanel(){
        supervisorRightPanel.setCurrentDriver(this.getCurrentDriver());
        return supervisorRightPanel;
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
        return getLiveChatRows()
                 .stream().filter(a -> a.getUserName().toLowerCase().contains(userName.toLowerCase()))
                .findFirst().orElseThrow(() -> new AssertionError("Cannot find chat with user " + userName));
    }

    public boolean verifyChanelFilter(){
        Set<String> channels = getLiveChatRows()
                .stream().map(a -> a.getIconName()).collect(Collectors.toSet());
        return channels.size() == 1;
    }

    public List<String> getChatsNames(){
        return getLiveChatRows().stream().map(SupervisorDeskLiveRow::getUserName).collect(Collectors.toList());
    }

    public boolean verifyChanelOfTheChatIsPresent(String channelName){
        waitForFirstElementToBeVisible(this.getCurrentDriver(), chatsLive, 7);
        return getLiveChatRows().get(0).getIconName().equalsIgnoreCase(channelName);
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

    public ChatBody getTicketChatBody(){
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

    public boolean verifyChatAlertIsPresent(int wait) {
        return isElementShown(this.getCurrentDriver(), chatTransferAlert, wait);
    }

    public boolean isChatPendingIconShown() {
        return isElementShown(this.getCurrentDriver(), leftChatPendingIcon, 3);
    }

    public boolean isChatPendingOnShown() {
        return isElementShown(this.getCurrentDriver(), leftChatPendingOn, 3);
    }

    public boolean isUpdatedProfileNameShown() {
       return isElementShown(this.getCurrentDriver(), profileName, 3);
    }

    public boolean isC2pButtonPresent() {
        return isElementShown(this.getCurrentDriver(), c2pButton, 5);
    }

    @NotNull
    private List<SupervisorDeskLiveRow> getLiveChatRows() {
        return chatsLive.stream()
                .map(e -> new SupervisorDeskLiveRow(e).setCurrentDriver(this.getCurrentDriver()))
                .collect(Collectors.toList());
    }
}
