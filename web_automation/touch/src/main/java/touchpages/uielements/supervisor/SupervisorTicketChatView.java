package touchpages.uielements.supervisor;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = ".chat-view")
public class SupervisorTicketChatView extends AbstractUIElement {

    @FindBy(css = ".bottom-action-bar--send-notification button")
    private WebElement messageCustomerButton;

    public void clickOnMessageCustomerButton(){
        clickElem(this.getCurrentDriver(), messageCustomerButton, 3, "Message Customer");
    }

}
