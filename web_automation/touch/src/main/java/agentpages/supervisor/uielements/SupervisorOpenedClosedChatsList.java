package agentpages.supervisor.uielements;

import abstractclasses.AbstractUIElement;
import agentpages.supervisor.SupervisorDeskPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = ".chats-list")
public class SupervisorOpenedClosedChatsList extends AbstractUIElement {

    @FindBy(css = ".cl-r-chat-item")
    private List<WebElement> closedChats;

    public boolean isClosedChatsHaveSendEmailButton() {
        for(WebElement closedChat : closedChats) {
            closedChat.click();
            SupervisorDeskPage supervisorDeskPage = new SupervisorDeskPage(this.getCurrentDriver());
            if(!supervisorDeskPage.isSendEmailForOpenedClosedChatShown())
                return false;
        }
        return true;
    }
}
