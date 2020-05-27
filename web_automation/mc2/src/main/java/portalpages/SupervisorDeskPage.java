package portalpages;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portaluielem.AssignChatWindow;
import portaluielem.ChatConsoleInboxRow;
import portaluielem.InboxChatBody;
import portaluielem.SupervisorTicketsTable;

import java.util.List;
import java.util.stream.Collectors;


public class SupervisorDeskPage extends PortalAbstractPage {

    @FindBy(xpath = "//div[@id='react-select-3--value']//span[@id='react-select-3--value-item']")
    private WebElement filterByDefault;

    @FindBy(xpath = ".cl-table-body .cl-table-row")
    private List<WebElement> chatConsoleInboxRows;

    @FindBy(xpath = "//span[text() ='Conversation type:']/following-sibling::div")
    private WebElement conversationTypeDropdown;

    @FindBy(xpath = "//span[text() ='Ticket type:']/following-sibling::div")
    private WebElement ticketTypeDropdown;

    @FindBy(css = ".cl-r-select__option")
    private List<WebElement> dropdownsTypesOptions;

    @FindBy(xpath = "//button[text() ='Apply filters']")
    private WebElement applyFiltersButton;

    @FindBy(xpath = "//span[text() = 'Route to scheduler']")
    private WebElement routeToSchedulerButton;

    @FindBy(css = "div.cl-actions-bar button")
    private WebElement loadMoreButton;

    @FindBy(css = "div.cl-actions-bar div")
    private WebElement numberOfChats;

    @FindBy (css = ".fade.dialog.in.modal")
    private WebElement assignWindowsDialog;

    @FindBy(id = "ticketing-iframe")
    private WebElement iframeIdElement;

    private String spinner = "//div[@class='spinner']";

    @FindBy(xpath = "//span[text()='Conversation status:']//following-sibling::div//div[@class='cl-r-select__single-value css-1uccc91-singleValue']")
    private WebElement filterByDefaultXpath;

    //private String filterByDefaultXpath = "//span[text()='Conversation status:']//following-sibling::div//div[@class='cl-r-select__single-value css-1uccc91-singleValue']";

    private String iframeId = "ticketing-iframe";

    private AssignChatWindow assignChatWindow;
    private InboxChatBody inboxChatBody;
    private SupervisorTicketsTable supervisorTicketsTable;

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



    public String getFilterByDefault(){
        return getTextFromElem(this.getCurrentDriver(),filterByDefaultXpath,5,"Default filter");
    }


    public ChatConsoleInboxRow getChatConsoleInboxRow(String userName){
        return chatConsoleInboxRows.stream()
                 .map(e -> new ChatConsoleInboxRow(e).setCurrentDriver(this.getCurrentDriver()))
                 .collect(Collectors.toList())
                 .stream().filter(a -> a.getChatConsoleInboxRowName().toLowerCase()
                        .contains(userName.toLowerCase()))
                .findFirst().orElseThrow(() -> new AssertionError("Cannot find chat with user " + userName));
    }



    public void clickRouteToSchedulerButton(){
        clickElem(this.getCurrentDriver(), routeToSchedulerButton, 5, "'Route to scheduler' button");
    }

//    public void clickThreeDotsButton(String userName){
//        getChatConsoleInboxRow(userName).clickThreeDots();
//        exitChatConsoleInbox();
//    }

    public String getCurrentAgentOfTheChat(String userName){
        String currentAgent = getChatConsoleInboxRow(userName).getCurrentAgent();
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

    public List<String> getUsersNames(){
        List<String> list =  chatConsoleInboxRows.stream()
                .map(e -> new ChatConsoleInboxRow(e).setCurrentDriver(this.getCurrentDriver()))
                .map(e -> e.getUserName())
                .collect(Collectors.toList());
        return list;
    }

    public void clickLoadMore(){
        scrollToElem(this.getCurrentDriver(), loadMoreButton,  "'Load more'");
        clickElem(this.getCurrentDriver(), loadMoreButton, 3, "'Load more'");
        waitForConnectingDisappear(2,3);
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

    public String getNumberOfChats(){
        scrollToElem(this.getCurrentDriver(), numberOfChats, "Numbers of chats");
        String info = getTextFromElem(this.getCurrentDriver(), numberOfChats, 3, "Number of chats");
        return info;
    }

    public boolean areNewChatsLoaded(int previousChats, int wait){
        for(int i = 0; i< wait*2; i++){
            if(getUsersNames().size() > previousChats) return true;
            else waitFor(500);
        }
        return false;
    }

    public SupervisorDeskPage selectConversationType(String option){
        clickElem(this.getCurrentDriver(), conversationTypeDropdown, 1, "Conversation type dropdown");
        dropdownsTypesOptions.stream().filter(a-> a.getText().trim().equalsIgnoreCase(option)).findFirst()
                .orElseThrow(() -> new AssertionError("Cannot find" + option + " conversation type dropdown option")).click();
        clickElem(this.getCurrentDriver(), applyFiltersButton, 1, "Apply Filters Button");
        return this;
    }

    public SupervisorDeskPage selectTicketType(String option){
        clickElem(this.getCurrentDriver(), ticketTypeDropdown, 1, "Conversation type dropdown");
        dropdownsTypesOptions.stream().filter(a-> a.getText().trim().equalsIgnoreCase(option)).findFirst()
                .orElseThrow(() -> new AssertionError("Cannot find " + option + " conversation type dropdown option")).click();
        clickElem(this.getCurrentDriver(), applyFiltersButton, 1, "Apply Filters Button");
        return this;
    }

    public List<String> getTicketTypes(){
        clickElem(this.getCurrentDriver(), ticketTypeDropdown, 1, "Conversation type dropdown");
        List<String> ticketTypes = dropdownsTypesOptions.stream().map(a-> a.getText().trim()).collect(Collectors.toList());
        clickElem(this.getCurrentDriver(), ticketTypeDropdown, 1, "Conversation type dropdown");
        return ticketTypes;
    }

    public InboxChatBody openInboxChatBody(String userName){
        getChatConsoleInboxRow(userName).clickOnUserName();
        inboxChatBody.setCurrentDriver(this.getCurrentDriver());
        return inboxChatBody;
    }

}
