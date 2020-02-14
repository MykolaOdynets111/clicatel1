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
            @FindBy(css = "[selenium-id=history-item]"),
            @FindBy(css = ".cl-r-tab-history-chat")
    })
    private List<WebElement> chatHistoryList;

    public ChatInActiveChatHistory getSecondChatHistoryItems(){
        try{
            return new ChatInActiveChatHistory(chatHistoryList.get(1)).setCurrentDriver(this.getCurrentDriver());
        } catch (IndexOutOfBoundsException e){
            Assert.fail("Chat history container in active chat is empty");
            return null;
        }
    }





}
