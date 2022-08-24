package agentpages.supervisor.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = ".chat-view")
public class SupervisorTicketClosedChatView extends AbstractUIElement {

    @FindBy(xpath = ".//button[text()='Message Customer']")
    private WebElement messageCustomerButton;

    public void clickOnMessageCustomerButton(){
        clickElem(this.getCurrentDriver(), messageCustomerButton, 3, "Message Customer");
    }

    public boolean isMessageCustomerButtonPresent(){
        return isElementShown(this.getCurrentDriver(), messageCustomerButton, 3);
    }

}
