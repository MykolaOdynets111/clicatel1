package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

@FindBy(css = ".chat.chat--in-flyout")
public class HistoryDetailsWindow extends AbstractUIElement {

    @FindAll({
            @FindBy(css = ".cl-r-header-user-info-name"),
            @FindBy(css = "[data-testid=history-detail-user-name]")
    })
    public WebElement chatTitle;

    @FindAll({
            @FindBy(css = ".cl-r-header-user-info-data"),
            @FindBy(css = "[data-testid=history-detail-time]")
    })
    public WebElement chatStartDate;

    @FindBy(css = ".cl-r-message-wrapper")
    private List<WebElement> messagesInChatBody;

    @FindAll({
            @FindBy(css = ".cl-r-button.cl-r-button--reset-only"),
            @FindBy(css = "[data-testid=history-detail-close]")
    })
    public WebElement closeHistoryDetailsButton;

    @FindBy(css = "[selenium-id='map-chat-message-content-LocationMessage']")
    private WebElement locationHREF;

    public String getUserName(){
        return chatTitle.getText();
    }

    public String getChatStartDate(){
        return chatStartDate.getText();
    }

    public List<String> getAllMessages(){
        waitForElementsToBeVisible(this.getCurrentDriver(), messagesInChatBody, 5);
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

    public String getLocationURL() {
        return getAttributeFromElem(this.getCurrentDriver(), locationHREF,3, "Location href", "href");
    }

    public void closeChatHistoryDetailsPopup(){
        closeHistoryDetailsButton.click();
    }
}
