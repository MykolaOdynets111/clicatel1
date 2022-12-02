package agentpages.tickets;

import abstractclasses.AbstractUIElement;

public class TicketsPage extends AbstractUIElement {

    private TicketsTable ticketsTable;
    private TicketClosedChatView supervisorTicketChatView;
    private MessageCustomerWindow messageCustomerWindow;

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
