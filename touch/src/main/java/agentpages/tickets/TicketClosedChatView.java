package agentpages.tickets;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = ".chat-view")
public class TicketClosedChatView extends AbstractUIElement {

    @FindBy(xpath = ".//button[text()='Start Chat' or text()='Message Customer']")
    private WebElement messageCustomerOrStartChatButton;

    @FindBy(xpath = "//button[@name = 'Close Ticket']")
    private WebElement closeTicket;

    public void clickOnMessageCustomerOrStartChatButton(){
        clickElem(this.getCurrentDriver(), messageCustomerOrStartChatButton, 8, "Message Customer");
    }

    public void clickOnCloseTicketButton(){
        clickElem(this.getCurrentDriver(), closeTicket, 3, "Close ticket");
    }

    public boolean isMessageCustomerButtonOrStartChatPresent(){
        return isElementShown(this.getCurrentDriver(), messageCustomerOrStartChatButton, 3);
    }

    public void hoverCloseTicket() {
        waitForElementToBeVisible(this.getCurrentDriver(), closeTicket, 5);
        moveToElement(this.getCurrentDriver(), closeTicket);
    }
}
