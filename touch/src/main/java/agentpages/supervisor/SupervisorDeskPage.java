package agentpages.supervisor;

import agentpages.supervisor.uielements.SupervisorAvailableAsAgentDialog;
import agentpages.supervisor.uielements.SupervisorClosedChatsTable;
import agentpages.supervisor.uielements.SupervisorDeskClosedRow;
import agentpages.supervisor.uielements.SupervisorDeskLiveRow;
import agentpages.supervisor.uielements.SupervisorOpenedClosedChatsList;
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
    private List<WebElement> chatsList;

    @FindBy(css = ".extended-chat-list-item")
    private List<WebElement> chatsListClosed;

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
    private SupervisorClosedChatsTable supervisorClosedChatsTable;
    private SupervisorOpenedClosedChatsList supervisorOpenedClosedChatsList;
    private RightPanelWindow supervisorRightPanel;
    private ChatHeader chatHeader;
    private Profile profile;
    private SupervisorAvailableAsAgentDialog supervisorAvailableAsAgentDialog;

    public SupervisorDeskPage(WebDriver driver) {
        super(driver);
    }

    public AssignChatWindow getAssignChatWindow(){
        assignChatWindow.setCurrentDriver(this.getCurrentDriver());
        return assignChatWindow;
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

    public SupervisorClosedChatsTable getSupervisorClosedChatsTable(){
        supervisorClosedChatsTable.setCurrentDriver(this.getCurrentDriver());
        return supervisorClosedChatsTable;
    }

    public SupervisorAvailableAsAgentDialog getSupervisorAvailableAsAgentDialog() {
        supervisorAvailableAsAgentDialog.setCurrentDriver(this.getCurrentDriver());
        return supervisorAvailableAsAgentDialog;
    }

    public boolean isLiveChatShownInSD(String userName, int wait) {
        return isElementShownByXpath(this.getCurrentDriver(), String.format(chatName, userName), wait);
    }

    public SupervisorDeskLiveRow getSupervisorDeskLiveRow(String chatName){
        waitForFirstElementToBeVisible(this.getCurrentDriver(), chatsList, 7);
        waitForElementToBeInvisibleByXpath(this.getCurrentDriver(), "//h2[text() ='Loading...']", 6);
        return getLiveChatRows()
                 .stream().filter(a -> a.getUserName().toLowerCase().contains(chatName.toLowerCase()))
                .findFirst().orElseThrow(() -> new AssertionError("Cannot find chat with user " + chatName));
    }

    public boolean verifyChanelFilter(){
        Set<String> channels = getLiveChatRows()
                .stream().map(SupervisorDeskLiveRow::getIconName)
                .collect(Collectors.toSet());
        return channels.size() == 1;
    }

    public List<String> getClosedChatNames(){
        return getClosedChatRows().stream()
                .map(SupervisorDeskClosedRow::getUserName)
                .collect(Collectors.toList());
    }

    public boolean verifyChanelOfTheChatIsPresent(String channelName){
        waitForFirstElementToBeVisible(this.getCurrentDriver(), chatsList, 7);
        return getLiveChatRows().get(0).getIconName().equalsIgnoreCase(channelName);
    }

    public boolean isAssignChatWindowOpened(){
        return isElementShown(this.getCurrentDriver(), getAssignChatWindow().getWrappedElement(), 10);
    }

    public void assignChat(String dropdownType, String agentName){
        getAssignChatWindow().selectDropDown(dropdownType, agentName);
        getAssignChatWindow().clickAssignChatButton();
        waitUntilElementNotDisplayed(this.getCurrentDriver(), assignWindowsDialog, 3);
    }

    public void waitForConnectingDisappear(int waitForSpinnerToAppear, int waitForSpinnerToDisappear){
        waitForAppearAndDisappear(this.getCurrentDriver(), spinner, waitForSpinnerToAppear, waitForSpinnerToDisappear);
    }

     public void waitForLoadingResultsDisappear(int timeToAppear, int timeToDisappear){
         waitForAppearAndDisappear(this.getCurrentDriver(), loadingResults, timeToAppear, timeToDisappear);
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
        return chatsList.stream()
                .map(e -> new SupervisorDeskLiveRow(e).setCurrentDriver(this.getCurrentDriver()))
                .collect(Collectors.toList());
    }

    private List<SupervisorDeskClosedRow> getClosedChatRows() {
        return chatsListClosed.stream()
                .map(e -> new SupervisorDeskClosedRow(e).setCurrentDriver(this.getCurrentDriver()))
                .collect(Collectors.toList());
    }
}
