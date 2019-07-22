package portalpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portalpages.uielements.ChatConsoleInboxRow;

import java.util.List;
import java.util.stream.Collectors;


public class ChatConsoleInboxPage extends PortalAbstractPage {

    @FindBy(xpath = "//div[@id='react-select-3--value']//span[@id='react-select-3--value-item']")
    private WebElement filterByDefault;

    @FindBy(xpath = "//div[@class='cl-table']/div[@class='cl-table-row']")
    private List<WebElement> chatConsoleInboxRow;

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
        return getTextFromElem(this.getCurrentDriver(),
                                    findElemByXPATH(this.getCurrentDriver(), filterByDefaultXpath), 5,
                            "Default filter");
    }

    public void exitChatConsoleInbox() {
        this.getCurrentDriver().switchTo().defaultContent();
    }

    public ChatConsoleInboxRow getChatConsoleInboxRow(String userName){
         return chatConsoleInboxRow.stream()
                 .map(e -> new ChatConsoleInboxRow(e).setCurrentDriver(this.getCurrentDriver()))
                 .collect(Collectors.toList())
                 .stream().filter(a -> a.getChatConsoleInboxRowName().toLowerCase()
                        .contains(userName.toLowerCase()))
                .findFirst().get();
    }

}
