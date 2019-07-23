package agentpages.uielements;

import abstractclasses.AbstractUIElementDeprecated;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

@FindBy(css = "div.history-details")
public class HistoryDetailsWindow extends AbstractUIElementDeprecated {

    @FindBy(css = "div.title h2")
    public WebElement chatTitle;

    @FindBy(css = "div.title span")
    public WebElement chatStartDate;

    @FindBy(xpath = ".//ul[@class='chat-container']//li[not(@class='empty')]")
    private List<WebElement> messagesInChatBody;

    @FindBy(css = "span.icon.icon-close")
    public WebElement closeHistoryDetailsButton;

    private String agentIconWIthInitials = ".//li[@class='to']//div[@class='empty-icon']";

    public String getUserName(){
        return chatTitle.getText();
    }

    public String getChatStartDate(){
        return chatStartDate.getText();
    }

    public List<String> getAllMessages(){
        waitForElementToBeVisibleAgent(messagesInChatBody.get(1), 5, "main");
        try {
            return messagesInChatBody.stream().map(e -> new AgentDeskChatMessage(e))
                    .map(e -> e.getMessageInfo())
                    .collect(Collectors.toList());
        }catch (NoSuchElementException e1){
            waitForDeprecated(2000);
            return messagesInChatBody.stream().map(e -> new AgentDeskChatMessage(e))
                    .map(e -> e.getMessageInfo())
                    .collect(Collectors.toList());
        }

    }

    public void closeChatHistoryDetailsPopup(){
        closeHistoryDetailsButton.click();
    }
}
