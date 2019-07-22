package agentpages.uielements;

import abstractclasses.AbstractUIElementDeprecated;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

@FindBy(css = "div.chat-history")
public class ChatHistoryContainer extends AbstractUIElementDeprecated {

    @FindBy(css = "div.history-item")
    private List<WebElement> chatHistoryList;

    public ChatInActiveChatHistory getFirstChatHistoryItems(){
        try{
            return new ChatInActiveChatHistory(chatHistoryList.get(0));
        } catch (IndexOutOfBoundsException e){
            Assert.fail("Chat history container in active chat is empty");
            return null;
        }
    }





}
