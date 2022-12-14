package agentpages.tickets;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = ".chat-view")
public class TicketClosedChatView extends AbstractUIElement {

    @FindBy(xpath = ".//button[text()='Start Chat' or text()='Message Customer']")
    private WebElement messageCustomerOrStartChatButton;

    public void clickOnMessageCustomerOrStartChatButton(){
        clickElem(this.getCurrentDriver(), messageCustomerOrStartChatButton, 8, "Message Customer");
    }

    public boolean isMessageCustomerButtonOrStartChatPresent(){
        return isElementShown(this.getCurrentDriver(), messageCustomerOrStartChatButton, 3);
    }

}
