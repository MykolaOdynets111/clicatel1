package agentpages;

import agentpages.uielements.ChatBody;
import agentpages.uielements.ChatHeader;
import agentpages.uielements.Profile;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portalpages.PortalAbstractPage;
import portaluielem.*;
import portaluielem.supervisor.*;

import java.util.List;
import java.util.stream.Collectors;


public class SupervisorDeskPage extends PortalAbstractPage {

    @FindBy(css = ".cl-r-chat-item")
    private List<WebElement> chatsLive;

    @FindBy (css = ".fade.dialog.in.modal")
    private WebElement assignWindowsDialog;

    @FindBy(css = ".chats-list-extended-view-header-text")
    private WebElement openedChatHeader;

    @FindBy(xpath = "//div[text()='Loading results']")
    private WebElement loadingResults;

    @FindBy(css = ".supervisor-tickets__loading-more")
    private WebElement loadingMoreTickets;

    @FindBy(xpath = "//div[@class='spinner']")
    private WebElement spinner;

    private String chatName = "//h2[@selenium-id='roster-item-user-name' and text() ='%s']";

    //private String filterByDefaultXpath = "//span[text()='Conversation status:']//following-sibling::div//div[@class='cl-r-select__single-value css-1uccc91-singleValue']";

    private String iframeId = "ticketing-iframe";

    private AssignChatWindow assignChatWindow;
    private ChatBody chatBody;
    private SupervisorTicketsTable supervisorTicketsTable;
    private SupervisorClosedChatsTable supervisorClosedChatsTable;
    private SupervisorLeftPanel supervisorLeftPanel;
    private ChatHeader chatHeader;
    private Profile profile;
    private SupervisorTicketChatView supervisorTicketChatView;
    private MessageCustomerWindow messageCustomerWindow;
    private SupervisorDeskHeader supervisorDeskHeader;

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

    public SupervisorTicketChatView getSupervisorTicketChatView(){
        supervisorTicketChatView.setCurrentDriver(this.getCurrentDriver());
        return supervisorTicketChatView;
    }

    public MessageCustomerWindow getMessageCustomerWindow(){
        messageCustomerWindow.setCurrentDriver(this.getCurrentDriver());
        return messageCustomerWindow;
    }

    public SupervisorClosedChatsTable supervisorClosedChatsTable(){
        supervisorClosedChatsTable.setCurrentDriver(this.getCurrentDriver());
        return supervisorClosedChatsTable;
    }

    public SupervisorDeskHeader supervisorDeskHeader(){
        supervisorDeskHeader.setCurrentDriver(this.getCurrentDriver());
        return  supervisorDeskHeader;
    }

    public boolean isLiveChatShownInSD(String userName, int wait) {
        return isElementShownByXpath(this.getCurrentDriver(), String.format(chatName, userName), wait);
    }

    public SupervisorDeskLiveRow getSupervisorDeskLiveRow(String userName){
        return chatsLive.stream()
                 .map(e -> new SupervisorDeskLiveRow(e).setCurrentDriver(this.getCurrentDriver()))
                 .collect(Collectors.toList())
                 .stream().filter(a -> a.getUserName().toLowerCase()
                        .contains(userName.toLowerCase()))
                .findFirst().orElseThrow(() -> new AssertionError("Cannot find chat with user " + userName));
    }

    public String getCurrentAgentOfTheChat(String userName){
        String currentAgent = getSupervisorTicketsTable().getTicketByUserName(userName).getCurrentAgent();
        return currentAgent;
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
        waitForMoreTicketsAreLoading(2,5);
    }

    public void waitForConnectingDisappear(int waitForSpinnerToAppear, int waitForSpinnerToDisappear){
        waitForAppearAndDisappear(this.getCurrentDriver(), spinner, waitForSpinnerToAppear, waitForSpinnerToDisappear);
    }

    public void waitForMoreTicketsAreLoading(int waitForSpinnerToAppear, int waitForSpinnerToDisappear){
        waitForAppearAndDisappear(this.getCurrentDriver(), loadingMoreTickets, waitForSpinnerToAppear, waitForSpinnerToDisappear);
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

    public String getOpenedChatHeader(){
        return getTextFromElem(this.getCurrentDriver(), openedChatHeader, 3, "Chat header text");
    }
}
