package agentpages.tickets;

import org.openqa.selenium.WebDriver;
import portalpages.PortalAbstractPage;

public class TicketsPage extends PortalAbstractPage {

    private TicketsTable ticketsTable;
    private TicketClosedChatView supervisorTicketChatView;
    private MessageCustomerWindow messageCustomerWindow;

    public TicketsPage(WebDriver driver) {
        super(driver);
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

}
