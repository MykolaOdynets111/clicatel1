package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@FindBy(css = "[selenium-id=notes-tab]")
public class CRMTicketContainer extends AbstractUIElement {

    @FindBy(css = "[selenium-id=note-card]")
    private List<WebElement> crmTickets;

    @FindBy(xpath = "//div[@class='user-tickets-container']/preceding-sibling::h2")
    private WebElement containerHeader;

    private String ticketContainer = "div.user-tickets-container";

    public boolean isTicketContainerShown(){
        return isElementShown(this.getCurrentDriver(),this.getWrappedElement(), 4);
    }

    public boolean isTicketContainerRemoved(){
        return isElementRemovedByCSS( this.getCurrentDriver(), ticketContainer, 4);
    }

    public CRMTicket getFirstTicket(){
        return new CRMTicket(crmTickets.get(0)).setCurrentDriver(this.getCurrentDriver());
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
                .map(e -> new CRMTicket(e).setCurrentDriver(this.getCurrentDriver()))
                .map(e -> e.getTicketInfo())
                .collect(Collectors.toList());
    }

    public List<Map<String, String>> getAllTicketsInfoExceptDate(){
        return crmTickets.stream()
                .map(e -> new CRMTicket(e).setCurrentDriver(this.getCurrentDriver()))
                .map(e -> e.getTicketInfoExceptDate())
                .collect(Collectors.toList());
    }
}
