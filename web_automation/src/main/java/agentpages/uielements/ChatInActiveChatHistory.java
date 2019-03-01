package agentpages.uielements;

import interfaces.ActionsHelper;
import interfaces.JSHelper;
import interfaces.WebActions;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ChatInActiveChatHistory extends Widget implements WebActions, ActionsHelper, JSHelper {

    @FindBy(css = "div.time span")
    private WebElement timeContainer;

    @FindBy(css = "div.history-item p")
    private WebElement userMessage;

    @FindBy(css = "div.details a")
    public WebElement viewDetailsButton;

    public ChatInActiveChatHistory(WebElement element) {
        super(element);
        PageFactory.initElements(new AppiumFieldDecorator(element), this);
    }

    public String getChatHistoryTime(){
        return timeContainer.getText();
    }

    public String getChatHistoryUserMessage(){
        return userMessage.getText();
    }

    public boolean isViewButtonClickable(String agent){
        return isElementEnabledAgent(viewDetailsButton, 4, agent);
    }

    public void clickViewButton(){
        viewDetailsButton.click();
    }

}