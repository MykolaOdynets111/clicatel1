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

    @FindBy(xpath = "//div[@class='user-tickets-container']/preceding-sibling::h2")
    private WebElement containerHeader;

    public boolean isTicketContainerShown(){
        return isElementShownAgent(this.getWrappedElement(), 4);
    }

    public boolean isTicketContainerRemoved(){
        return isElementNotShownAgentByCSS( "div.user-tickets-container", 4);
    }

    public CRMTicket getFirstTicket(){
        return new CRMTicket(crmTickets.get(0));
    }

    public String getContainerHeader(){ return containerHeader.getText(); }


}
