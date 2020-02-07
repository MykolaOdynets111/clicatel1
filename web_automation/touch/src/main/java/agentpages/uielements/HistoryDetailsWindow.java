package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;


//@FindAll({
//        @FindBy(css = "[selenium-id=history-detail]"),
        @FindBy(css = ".cl-r-history-chat-view-fly-out")
//})
public class HistoryDetailsWindow extends AbstractUIElement {

    @FindAll({
            @FindBy(css = ".cl-r-header-user-info-name"),
            @FindBy(css = "[selenium-id=history-detail-user-name]")
    })
    public WebElement chatTitle;

    @FindAll({
            @FindBy(css = ".cl-r-header-user-info-data"),
            @FindBy(css = "[selenium-id=history-detail-time]")
    })
    public WebElement chatStartDate;

    @FindBy(css = "[selenium-id=chat-message]")
    private List<WebElement> messagesInChatBody;

    @FindAll({
            @FindBy(css = ".cl-r-button.cl-r-button--reset-only"),
            @FindBy(css = "[selenium-id=history-detail-close]")
    })
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
