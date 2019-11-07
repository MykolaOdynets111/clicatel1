package portalpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portaluielem.AssignChatWindow;
import portaluielem.ChatConsoleInboxRow;

import java.util.List;
import java.util.stream.Collectors;


public class ChatConsoleInboxPage extends PortalAbstractPage {

    @FindBy(xpath = "//div[@id='react-select-3--value']//span[@id='react-select-3--value-item']")
    private WebElement filterByDefault;

    @FindBy(xpath = "//div[@class='cl-table']/div[@class='cl-table-row']")
    private List<WebElement> chatConsoleInboxRows;

    @FindBy(xpath = "//span[text() = 'Route to scheduler']")
    private WebElement routeToSchedulerButton;

    @FindBy(xpath = "//span[text() = 'Assign manually']")
    private WebElement assignManuallyButton;

    private String filterByDefaultXpath = "//div[@id='react-select-3--value']//span[@id='react-select-3--value-item']";

    private String iframeId = "ticketing-iframe";

    private AssignChatWindow assignChatWindow;

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

    private void exitChatConsoleInbox() {
        this.getCurrentDriver().switchTo().defaultContent();
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
        this.getCurrentDriver().switchTo().frame(iframeId);
        getChatConsoleInboxRow(userName).clickThreeDots();
        exitChatConsoleInbox();
    }

    public String getCurrentAgentOfTheChat(String userName){
        this.getCurrentDriver().switchTo().frame(iframeId);
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
        exitChatConsoleInbox();
    }
}
