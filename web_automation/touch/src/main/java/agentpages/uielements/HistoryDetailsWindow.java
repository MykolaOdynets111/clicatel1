package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

@FindBy(css = "[selenium-id=history-detail]")
public class HistoryDetailsWindow extends AbstractUIElement {

    @FindBy(css = "[selenium-id=history-detail-user-name]")
    public WebElement chatTitle;

    @FindBy(css = "[selenium-id=history-detail-time]")
    public WebElement chatStartDate;

    @FindBy(xpath = ".//ul[@class='chat-container']//li[not(@class='empty')]")
    private List<WebElement> messagesInChatBody;

    @FindBy(css = "[selenium-id=history-detail-close]")
    public WebElement closeHistoryDetailsButton;

    public String getUserName(){
        return chatTitle.getText();
    }

    public String getChatStartDate(){
        return chatStartDate.getText();
    }

    public List<String> getAllMessages(){
        waitForElementToBeVisible(this.getCurrentDriver(), messagesInChatBody.get(1), 5);
        try {
            return messagesInChatBody.stream().map(e -> new AgentDeskChatMessage(e).setCurrentDriver(this.getCurrentDriver()))
                    .map(e -> e.getMessageInfo())
                    .collect(Collectors.toList());
        }catch (NoSuchElementException e1){
            waitFor(2000);
            return messagesInChatBody.stream().map(e -> new AgentDeskChatMessage(e).setCurrentDriver(this.getCurrentDriver()))
                    .map(e -> e.getMessageInfo())
                    .collect(Collectors.toList());
        }

    }

    public void closeChatHistoryDetailsPopup(){
        closeHistoryDetailsButton.click();
    }
}
