package agentpages.tickets;

import agentpages.survey.uielements.SurveyForm;
import driverfactory.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import portalpages.PortalAbstractPage;

import java.util.List;
import java.util.stream.Collectors;

public class TicketsPage extends PortalAbstractPage {

    private TicketsTable ticketsTable;

    private TicketRow ticketRow;
    private TicketClosedChatView supervisorTicketChatView;
    private MessageCustomerWindow messageCustomerWindow;

    private TicketsQuickActionBar ticketsQuickActionBar;

    @FindBy(css = ".app-tickets-actions-bar")
    private WebElement quickActionBar;

    @FindBy(css = "#tickets-asssigned")
    private WebElement ticketAssignedToastMessage;

    @FindBy(xpath = "//div[@class='tippy-content']/div")
    private WebElement flaggedCloseChatToolTip;

    @FindBy(css = "[data-testid = 'bulk-messages-toggle']")
    private WebElement bulkButton;

    @FindBy(css = ".toast-content-message")
    private WebElement ticketAssignedToastMessageContent;

    @FindBy(css = "[role='columnheader']")
    private List<WebElement> ticketColumnHeaders;

    public TicketsPage(WebDriver driver) {
        super(driver);
    }

    public TicketsQuickActionBar getTicketsQuickActionBar(){
        ticketsQuickActionBar.setCurrentDriver(this.getCurrentDriver());
        return ticketsQuickActionBar;
    }

    public TicketsTable getTicketsTable(){
        ticketsTable.setCurrentDriver(this.getCurrentDriver());
        return ticketsTable;
    }

    public TicketClosedChatView getSupervisorTicketClosedChatView(){
        supervisorTicketChatView.setCurrentDriver(this.getCurrentDriver());
        return supervisorTicketChatView;
    }

    public MessageCustomerWindow getMessageCustomerWindow(){
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

    public String getQuickActionToolTipText() {
        return getTextFromElem(this.getCurrentDriver(), flaggedCloseChatToolTip, 4, "Tool Tip for Quick Action bar");
    }

    public String getToastMessageText() {
        return getTextFromElem(this.getCurrentDriver(), ticketAssignedToastMessageContent, 6, "Toast message");
    }

    public WebElement getTicketsColumnHeader(String headerName){
        waitForFirstElementToBeVisible(this.getCurrentDriver(), ticketColumnHeaders, 7);
        return ticketColumnHeaders.stream().filter(a -> a.getText().equalsIgnoreCase(headerName))
                .findFirst().orElseThrow(() -> new AssertionError("Cannot find ticket column header with name " + headerName));
    }

}