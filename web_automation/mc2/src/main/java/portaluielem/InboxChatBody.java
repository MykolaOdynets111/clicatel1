package portaluielem;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@FindBy(css ="div.supervisor-chat-history")
public class InboxChatBody extends AbstractUIElement {

    @FindBy(css =".msg-client_message.from .msg")
    private WebElement clientMessage;

    public String getClientMessageText(){
        return getTextFromElem(this.getCurrentDriver(), clientMessage, 2, "Client Message");
    }


}
