package agentpages.uielements;

import abstractclasses.AbstractWidget;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ChatInActiveChatHistory extends AbstractWidget {

    @FindBy(css = "div.time span")
    private WebElement timeContainer;

    @FindBy(css = "div.history-item p")
    private WebElement userMessage;

    @FindBy(css = "div.details a")
    public WebElement viewDetailsButton;

    public ChatInActiveChatHistory(WebElement element) {
        super(element);
    }

    public ChatInActiveChatHistory setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
        return this;
    }

    public String getChatHistoryTime(){
        return timeContainer.getText();
    }

    public String getChatHistoryUserMessage(){
        return userMessage.getText();
    }

    public boolean isViewButtonClickable(){
        return isElementEnabled(this.getCurrentDriver(), viewDetailsButton, 4);
    }

    public void clickViewButton(){
        viewDetailsButton.click();
    }

}