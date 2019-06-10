package portalpages.uielements;

import interfaces.WebActions;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;


public class AgentRowChatConsole extends Widget implements WebActions {
    private WebElement baseWebElem = this.getWrappedElement();

    @FindBy(css = "div.cl-chat-data-agent-status--holder span.ng-binding")
    private WebElement agentName;

    @FindBy(css = "div>*.cl-chat-data-agent-status.cl-chat-data-agent-status--active")
    private WebElement activeStatus;

    @FindBy(css = "div.animation-box-wrapper>div.cl-chat-data-item.ng-binding")
    private WebElement activeChatsNumber;

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
        PageFactory.initElements(new AppiumFieldDecorator(element), this);
    }

    public String getAgentName(){
        return agentName.getText();
    }

   public boolean isActiveChatsIconShown(int wait){
        return isElementShownAgent(activeStatus, wait);
   }

    public int getActiveChatsNumber(){
        return Integer.valueOf(activeChatsNumber.getText());
    }

    public boolean isNoActiveChatsIconShown(int wait){
        return isElementShownAgent(activeStatus, wait);
    }

    public void clickExpandButton(){
        clickElemAgent(expandButton, 3, "admin", "Expand agent row button");
    }


    public List<String> getChannels(){
        return channels.stream().map(e -> e.getText()).collect(Collectors.toList());
    }

    public List<String> getIntents(){
        return intents.stream().map(e -> e.getText()).collect(Collectors.toList());
    }

    public List<String> getSentiments(){
        return sentiments.stream().map(e -> e.getAttribute("class")).collect(Collectors.toList());
    }

    public List<String> getChattingTo(){
        return chattingTo.stream().map(e -> e.getText()).collect(Collectors.toList());
    }
}
