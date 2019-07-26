package agentpages.uielements;

import abstractclasses.AbstractUIElementDeprecated;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@FindBy(css = "div.user-tickets-container")
public class CRMTicketContainer extends AbstractUIElementDeprecated {

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

    public String getContainerHeader(){
        try {
            return containerHeader.getText();
        } catch(NoSuchElementException e) {
            Assert.fail("CRM ticket container is not shown");
        }
        return "";
    }

    public int getNumberOfTickets(){return crmTickets.size();}

    public List<Map<String, String>> getAllTicketsInfo(){
        return crmTickets.stream()
                .map(e -> new CRMTicket(e))
                .map(e -> e.getTicketInfo())
                .collect(Collectors.toList());
    }

    public List<Map<String, String>> getAllTicketsInfoExceptDate(){
        return crmTickets.stream()
                .map(e -> new CRMTicket(e))
                .map(e -> e.getTicketInfoExceptDate())
                .collect(Collectors.toList());
    }
}
