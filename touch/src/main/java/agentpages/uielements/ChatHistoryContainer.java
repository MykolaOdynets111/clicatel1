package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

@FindBy(css = ".iscroll-main.iscroll-main-clRTabHistoryList")
public class ChatHistoryContainer extends AbstractUIElement {

    @FindAll({
            @FindBy(css = "[data-testid=history-item]"),
            @FindBy(css = "[selenium-id='chat-history']") //todo old locator
    })
    private List<WebElement> chatHistoryList;

    public ChatInActiveChatHistory getChatHistoryItemsByIndex(int i){
        try{
            return new ChatInActiveChatHistory(chatHistoryList.get(i)).setCurrentDriver(this.getCurrentDriver());
        } catch (IndexOutOfBoundsException e){
            Assert.fail("Chat history container in active chat is empty");
            return null;
        }
    }





}
