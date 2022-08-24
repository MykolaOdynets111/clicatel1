package portaluielem;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@FindBy(css ="div.supervisor-chat-history")
public class InboxChatBody extends AbstractUIElement {

    @FindBy(css =".msg-client_message.from .msg")
    private WebElement clientMessage;

    @FindBy(css =".msg-agent_message.to .msg")
    private WebElement agentMessage;

    @FindBy(css =" .msg-agent_message.to span")
    private WebElement agentMessageTime;

    @FindBy(css =".supervisor-chat-history__state-and-type span b")
    private WebElement chatStatus;


    public String getClientMessageText(){
        return getTextFromElem(this.getCurrentDriver(), clientMessage, 2, "Client Message");
    }

    public String getAgentMessageText(){
        return getTextFromElem(this.getCurrentDriver(), agentMessage, 2, "Agent Message");
    }

    public String getAgentMessageTime(){
        return getTextFromElem(this.getCurrentDriver(), agentMessageTime, 2, "Agent Message time");
    }

    public String getChatStatus(){
        return getTextFromElem(this.getCurrentDriver(), chatStatus, 2, "Chat status");
    }






}
