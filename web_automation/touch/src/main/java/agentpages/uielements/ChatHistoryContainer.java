package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

@FindBy(css = "div.chat-history")
public class ChatHistoryContainer extends AbstractUIElement {

    @FindBy(css = "div.history-item")
    private List<WebElement> chatHistoryList;

    public ChatInActiveChatHistory getFirstChatHistoryItems(){
        try{
            return new ChatInActiveChatHistory(chatHistoryList.get(0)).setCurrentDriver(this.getCurrentDriver());
        } catch (IndexOutOfBoundsException e){
            Assert.fail("Chat history container in active chat is empty");
            return null;
        }
    }





}
