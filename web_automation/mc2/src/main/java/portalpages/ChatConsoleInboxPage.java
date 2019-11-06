package portalpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
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

    private String filterByDefaultXpath = "//div[@id='react-select-3--value']//span[@id='react-select-3--value-item']";

    private String iframeId = "ticketing-iframe";

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
        return chatConsoleInboxRows.stream()
                 .map(e -> new ChatConsoleInboxRow(e).setCurrentDriver(this.getCurrentDriver()))
                 .collect(Collectors.toList())
                 .stream().filter(a -> a.getChatConsoleInboxRowName().toLowerCase()
                        .contains(userName.toLowerCase()))
                .findFirst().get();
    }

    public void clickRouteToSchedulerButton(){
        this.getCurrentDriver().switchTo().frame(iframeId);
        clickElem(this.getCurrentDriver(), routeToSchedulerButton, 5, "'Route to scheduler' button");
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
}
