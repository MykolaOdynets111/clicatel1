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
        if(chatHistoryList.size()==0){
            Assert.assertTrue(false, "Chat history container in active chat is empty");
        }
        return new ChatInActiveChatHistory(chatHistoryList.get(0));
    }





}
