package portalpages.uielements;

import interfaces.WebActions;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;


public class AgentRowChatConsole extends Widget implements WebActions {
    private WebElement baseWebElem = this.getWrappedElement();

    @FindBy(css = "tr.animate-repeat.ng-scope td[cl-mobile-title='Agent']")
    private WebElement agentName;

    @FindBy(css = "div>*.cl-chat-data-agent-status.cl-chat-data-agent-status--active")
    private WebElement activeStatus;

    @FindBy(css = "svg#Layer_1")
    private WebElement expandButton;

    @FindBy(css = "td[cl-mobile-title='Channels'] span.ng-binding.ng-scope")
    private List<WebElement> channels;

    @FindBy(css = "td[cl-mobile-title='Intent'] span.ng-binding.ng-scope")
    private List<WebElement> intents;

    @FindBy(css = "td[cl-mobile-title='Sentimnet'] div.cl-chat-data-item.ng-scope span")
    private List<WebElement> sentiments;

    @FindBy(css = "td[cl-mobile-title='Chatting to'] div.cl-chat-data-item.ng-scope span")
    private List<WebElement> chattingTo;

    public AgentRowChatConsole(WebElement element) {
        super(element);
    }

    public String getAgentName(){
        return agentName.getText();
   }

   public boolean isActiveChatIconShown(int wait){
        return isElementShownAgent(activeStatus, wait);
   }

    public boolean isNoActiveChatsIconShown(int wait){
        return isElementShownAgent(activeStatus, wait);
    }

    public void clickExpandButton(){
        clickElemAgent(expandButton, 3, "admin", "Expand agent row button");
    }

}
