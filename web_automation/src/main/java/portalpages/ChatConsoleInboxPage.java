package portalpages;

import drivermanager.DriverFactory;
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

    public String getFilterByDefault(){
        waitForElementToBeVisibleByXpathAgent(filterByDefaultXpath,5,"main");
        return findElemByXPATHAgent(filterByDefaultXpath,"main").getText();
    }

    public ChatConsoleInboxPage() {
        DriverFactory.getDriverForAgent("admin").switchTo().frame(iframeId);
    }

    public void exitChatConsoleInbox() {
        DriverFactory.getAgentDriverInstance().switchTo().defaultContent();
    }

    public ChatConsoleInboxRow getChatConsoleInboxRow(String userName){
        return chatConsoleInboxRow.stream().map(ChatConsoleInboxRow::new).collect(Collectors.toList())
                .stream().filter(a -> a.getChatConsoleInboxRowName().toLowerCase().contains(userName.toLowerCase()))
                .findFirst().get();
    }

}
