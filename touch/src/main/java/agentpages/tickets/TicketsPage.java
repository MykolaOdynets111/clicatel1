package agentpages.tickets;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portalpages.PortalAbstractPage;

import java.util.List;
import java.util.stream.Collectors;

public class TicketsPage extends PortalAbstractPage {

    @FindBy(css = ".app-tickets-actions-bar")
    private WebElement quickActionBar;

    @FindBy(css = "#tickets-asssigned")
    private WebElement ticketAssignedToastMessage;

    @FindBy(xpath = "//div[@class='tippy-content']/div")
    private WebElement toolTip;

    @FindBy(css = ".toast-content-message")
    private WebElement ticketAssignedToastMessageContent;

    @FindBy(css = ".cl-table-header .cl-table-cell")
    private List<WebElement> ticketColumnHeaders;

    public TicketsPage(WebDriver driver) {
        super(driver);
    }

    private TicketsTable ticketsTable;
    private TicketChatView supervisorTicketChatView;
    private MessageCustomerWindow messageCustomerWindow;

    private TicketsQuickActionBar ticketsQuickActionBar;

    public TicketsQuickActionBar getTicketsQuickActionBar() {
        ticketsQuickActionBar.setCurrentDriver(this.getCurrentDriver());
        return ticketsQuickActionBar;
    }

    public TicketsTable getTicketsTable() {
        ticketsTable.setCurrentDriver(this.getCurrentDriver());
        return ticketsTable;
    }

    public TicketChatView getSupervisorTicketClosedChatView() {
        supervisorTicketChatView.setCurrentDriver(this.getCurrentDriver());
        return supervisorTicketChatView;
    }

    public MessageCustomerWindow getMessageCustomerWindow() {
        messageCustomerWindow.setCurrentDriver(this.getCurrentDriver());
        return messageCustomerWindow;
    }

    public boolean quickActionBarVisibility(String isVisible) {
        if (isVisible.trim().equalsIgnoreCase("visible")) {
            return isElementShown(this.getCurrentDriver(), quickActionBar, 4);
        } else if (isVisible.trim().equalsIgnoreCase("not visible")) {
            return isElementRemoved(this.getCurrentDriver(), quickActionBar, 4);
        } else {
            return false;
        }
    }

    public void toastMessageVisibility() {
        waitUntilElementNotDisplayed(this.getCurrentDriver(), ticketAssignedToastMessage, 3);
    }

    public String getToolTipText() {
        return getTextFromElem(this.getCurrentDriver(), toolTip, 4, "Tool Tip");
    }

    public String getToastMessageText() {
        return getTextFromElem(this.getCurrentDriver(), ticketAssignedToastMessageContent, 6, "Toast message");
    }

    public List<String> getTicketsColumnHeaders() {
        waitForFirstElementToBeVisible(this.getCurrentDriver(), ticketColumnHeaders, 7);
        return ticketColumnHeaders.stream().map(a -> a.getText()).collect(Collectors.toList());
    }
}