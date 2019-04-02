package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Map;

@FindBy(css = "div.user-tickets-container")
public class CRMTicketContainer extends AbstractUIElement {

    @FindBy(css = "div.ticket-item-block")
    private List<WebElement> crmTickets;

    public boolean isTicketContainerShown(){
        return isElementShown(this.getWrappedElement(), 4);
    }


    public Map<String, String> getFirstTicketInfoMap(){
        return new CRMTicket(crmTickets.get(0)).getTicketInfo();
    }



}
