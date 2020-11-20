package touchpages.uielements.supervisor;

import abstractclasses.AbstractUIElement;
import agentpages.SupervisorDeskPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = ".chats-list")
public class SupervisorOpenedClosedChatsList extends AbstractUIElement {
    private final String closedChatCss = ".cl-r-chat-item";

    @FindBy(css = closedChatCss)
    private List<WebElement> closedChats;

    public boolean isClosedChatsHaveSendEmailButton() {
        waitForNumberOfElementsBeGreaterThenZero(getCurrentDriver(), closedChatCss, 5);
        for(WebElement closedChat : closedChats) {
            clickElem(getCurrentDriver(), closedChat, 2, "Closed Chat");
            SupervisorDeskPage supervisorDeskPage = new SupervisorDeskPage(this.getCurrentDriver());
            if(!supervisorDeskPage.isSendEmailForOpenedClosedChatShown())
                return false;
        }
        return true;
    }
}
