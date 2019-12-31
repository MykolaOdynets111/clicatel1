package portalpages;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portaluielem.AssignChatWindow;
import portaluielem.ChatConsoleInboxRow;
import portaluielem.InboxChatBody;

import java.util.List;
import java.util.stream.Collectors;


public class ChatConsoleInboxPage extends PortalAbstractPage {

    @FindBy(xpath = "//div[@id='react-select-3--value']//span[@id='react-select-3--value-item']")
    private WebElement filterByDefault;

    @FindBy(xpath = "//div[@class='cl-table']//div[@class='cl-table-row']")
    private List<WebElement> chatConsoleInboxRows;

    @FindBy(xpath = "//span[text() ='Conversation type:']/following-sibling::div")
    private WebElement conversationTypeDropdown;

    @FindBy(xpath = "//span[text() ='Ticket type:']/following-sibling::div")
    private WebElement ticketTypeDropdown;

    @FindBy(xpath = "//div[@aria-label]")
    private List<WebElement> dropdownsTypesOptions;

    @FindBy(xpath = "//button[text() ='Apply filters']")
    private WebElement applyFiltersButton;

    @FindBy(xpath = "//span[text() = 'Route to scheduler']")
    private WebElement routeToSchedulerButton;

    @FindBy(xpath = "//span[text() = 'Assign manually']")
    private WebElement assignManuallyButton;

    @FindBy(css = "div.cl-actions-bar button")
    private WebElement loadMoreButton;

    @FindBy(css = "div.cl-actions-bar div")
    private WebElement numberOfChats;

    @FindBy (css = ".fade.dialog.in.modal")
    private WebElement assignWindowsDialog;

    private String spinner = "//div[@class='spinner']";

    private String filterByDefaultXpath = "//div[@id='react-select-3--value']//span[@id='react-select-3--value-item']";

    private String iframeId = "ticketing-iframe";

    private AssignChatWindow assignChatWindow;
    private InboxChatBody inboxChatBody;

    // == Constructors == //

    public ChatConsoleInboxPage() {
        super();
    }
    public ChatConsoleInboxPage(String agent) {
        super(agent);
    }
    public ChatConsoleInboxPage(WebDriver driver) {
        super(driver);
    }

    public AssignChatWindow getAssignChatWindow(){
        assignChatWindow.setCurrentDriver(this.getCurrentDriver());
        return assignChatWindow;
    }

    public String getFilterByDefault(){
        this.getCurrentDriver().switchTo().frame(iframeId);
        waitForElementsToBeVisibleByXpath(this.getCurrentDriver(),filterByDefaultXpath,2);
        return getTextFromElem(this.getCurrentDriver(),
                                    findElemByXPATH(this.getCurrentDriver(), filterByDefaultXpath), 5,
                            "Default filter");
    }

    public void exitChatConsoleInbox() {
        this.getCurrentDriver().switchTo().defaultContent();
    }

    public ChatConsoleInboxRow getChatConsoleInboxRow(String userName){
        this.getCurrentDriver().switchTo().frame(iframeId);
        return chatConsoleInboxRows.stream()
                 .map(e -> new ChatConsoleInboxRow(e).setCurrentDriver(this.getCurrentDriver()))
                 .collect(Collectors.toList())
                 .stream().filter(a -> a.getChatConsoleInboxRowName().toLowerCase()
                        .contains(userName.toLowerCase()))
                .findFirst().orElseThrow(() -> new AssertionError("Cannot find chat with user " + userName));
    }



    public void clickRouteToSchedulerButton(){
        this.getCurrentDriver().switchTo().frame(iframeId);
        clickElem(this.getCurrentDriver(), routeToSchedulerButton, 5, "'Route to scheduler' button");
        exitChatConsoleInbox();
    }

    public void clickAssignManuallyButton(){
        this.getCurrentDriver().switchTo().frame(iframeId);
        clickElem(this.getCurrentDriver(), assignManuallyButton, 5, "'Route to scheduler' button");
        exitChatConsoleInbox();
    }

    public void clickThreeDotsButton(String userName){
        getChatConsoleInboxRow(userName).clickThreeDots();
        exitChatConsoleInbox();
    }

    public String getCurrentAgentOfTheChat(String userName){
        String currentAgent = getChatConsoleInboxRow(userName).getCurrentAgent();
        exitChatConsoleInbox();
        return currentAgent;
    }

    public boolean isAssignChatWindowOpened(){
        this.getCurrentDriver().switchTo().frame(iframeId);
        boolean result = isElementShown(this.getCurrentDriver(), getAssignChatWindow().getWrappedElement(), 4);
        exitChatConsoleInbox();
        return result;
    }

    public void assignChatOnAgent(String agentName){
        this.getCurrentDriver().switchTo().frame(iframeId);
        getAssignChatWindow().selectDropDownAgent(agentName);
        getAssignChatWindow().clickAssignChatButton();
        waitUntilElementNotDisplayed(this.getCurrentDriver(), assignWindowsDialog, 3);
        exitChatConsoleInbox();
    }

    public List<String> getUsersNames(){
        this.getCurrentDriver().switchTo().frame(iframeId);
        List<String> list =  chatConsoleInboxRows.stream()
                .map(e -> new ChatConsoleInboxRow(e).setCurrentDriver(this.getCurrentDriver()))
                .map(e -> e.getUserName())
                .collect(Collectors.toList());
        exitChatConsoleInbox();
        return list;
    }

    public void clickLoadMore(){
        this.getCurrentDriver().switchTo().frame(iframeId);
        scrollToElem(this.getCurrentDriver(), loadMoreButton,  "'Load more'");
        clickElem(this.getCurrentDriver(), loadMoreButton, 3, "'Load more'");
        waitForConnectingDisappear(2,3);
        exitChatConsoleInbox();
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
        this.getCurrentDriver().switchTo().frame(iframeId);
        scrollToElem(this.getCurrentDriver(), numberOfChats, "Numbers of chats");
        String info = getTextFromElem(this.getCurrentDriver(), numberOfChats, 3, "Number of chats");
        exitChatConsoleInbox();
        return info;
    }

    public boolean areNewChatsLoaded(int previousChats, int wait){
        for(int i = 0; i< wait*2; i++){
            if(getUsersNames().size() > previousChats) return true;
            else waitFor(500);
        }
        return false;
    }

    public ChatConsoleInboxPage selectConversationType(String option){
        this.getCurrentDriver().switchTo().frame(iframeId);
        clickElem(this.getCurrentDriver(), conversationTypeDropdown, 1, "Conversation type dropdown");
        dropdownsTypesOptions.stream().filter(a-> a.getText().trim().equalsIgnoreCase(option)).findFirst()
                .orElseThrow(() -> new AssertionError("Cannot find" + option + " conversation type dropdown option")).click();
        clickElem(this.getCurrentDriver(), applyFiltersButton, 1, "Apply Filters Button");
        exitChatConsoleInbox();
        return this;
    }

    public ChatConsoleInboxPage selectTicketType(String option){
        this.getCurrentDriver().switchTo().frame(iframeId);
        clickElem(this.getCurrentDriver(), ticketTypeDropdown, 1, "Conversation type dropdown");
        dropdownsTypesOptions.stream().filter(a-> a.getText().trim().equalsIgnoreCase(option)).findFirst()
                .orElseThrow(() -> new AssertionError("Cannot find " + option + " conversation type dropdown option")).click();
        clickElem(this.getCurrentDriver(), applyFiltersButton, 1, "Apply Filters Button");
        exitChatConsoleInbox();
        return this;
    }

    public List<String> getTicketTypes(){
        this.getCurrentDriver().switchTo().frame(iframeId);
        clickElem(this.getCurrentDriver(), ticketTypeDropdown, 1, "Conversation type dropdown");
        List<String> ticketTypes = dropdownsTypesOptions.stream().map(a-> a.getText().trim()).collect(Collectors.toList());
        clickElem(this.getCurrentDriver(), ticketTypeDropdown, 1, "Conversation type dropdown");
        exitChatConsoleInbox();
        return ticketTypes;
    }

    public InboxChatBody openInboxChatBody(String userName){
        getChatConsoleInboxRow(userName).clickOnUserName();
        inboxChatBody.setCurrentDriver(this.getCurrentDriver());
        return inboxChatBody;
    }

}
