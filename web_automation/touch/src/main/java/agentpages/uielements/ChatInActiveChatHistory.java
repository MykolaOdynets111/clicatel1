package agentpages.uielements;

import abstractclasses.AbstractWidget;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;


public class ChatInActiveChatHistory extends AbstractWidget {

    @FindAll({
            @FindBy(css = ".history-chat-info-time"),
            @FindBy(css = "[selenium-id=history-item-time]")
    })
    private WebElement timeContainer;

    @FindBy(css =".chat-info-date")
    private WebElement dateContainer;

    @FindAll({
            @FindBy(css = ".tab-history-chat-message"),
            @FindBy(css = "[selenium-id=history-item-message]")
    })
    private WebElement userMessage;

    @FindAll({
            @FindBy(css = ".cl-r-icon.cl-r-icon-expand.cl-r-icon--fill"),
            @FindBy(css = "[selenium-id=history-item-detail]")
    })
    public WebElement viewDetailsButton;

    public ChatInActiveChatHistory(WebElement element) {
        super(element);
    }

    public ChatInActiveChatHistory setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
        return this;
    }

    public String getChatHistoryTime(){
        return  dateContainer.getText() + " " +timeContainer.getText();
    }

    public String getChatHistoryUserMessage(){
        return userMessage.getText();
    }

    public void clickViewButton(){
        Actions action = new Actions(getCurrentDriver());
        action.moveToElement(viewDetailsButton).perform();
        viewDetailsButton.click();
    }

}