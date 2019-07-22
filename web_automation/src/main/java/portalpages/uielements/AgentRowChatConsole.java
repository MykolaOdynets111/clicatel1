package portalpages.uielements;

import abstractclasses.AbstractWidget;
import interfaces.WebActionsDeprecated;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;


public class AgentRowChatConsole extends AbstractWidget {

    private WebElement baseWebElem = this.getWrappedElement();

    @FindBy(css = "div.cl-chat-data-agent-status--holder span.ng-binding")
    private WebElement agentName;

    @FindBy(css = "div>*.cl-chat-data-agent-status.cl-chat-data-agent-status--active")
    private WebElement activeChatsIcon;

    @FindBy(css = "div>*.cl-chat-data-agent-status.cl-chat-data-agent-status--idle")
    private WebElement noActiveChatsIcon;

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

    public AgentRowChatConsole setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
        return this;
    }

    public String getAgentName(){
        return agentName.getText();
    }

   public boolean isActiveChatsIconShown(int wait){
        return isElementShown(this.getCurrentDriver(), activeChatsIcon, wait);
   }

    public int getActiveChatsNumber(){
        return Integer.valueOf(activeChatsNumber.getText());
    }

    public boolean isNoActiveChatsIconShown(int wait){
        return isElementShown(this.getCurrentDriver(), noActiveChatsIcon, wait);
    }

    public void clickExpandButton(){
        clickElem(this.getCurrentDriver(), expandButton, 3,"Expand agent row button");
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

    public boolean isChatShownFromUserShown(String clientId, int secondWait){
        boolean result = chattingTo.stream().map(e -> e.getText()).anyMatch(e -> e.equals(clientId));
        for (int i = 0; i<secondWait; i++){
            if(result) break;
            else{
                waitFor(1000);
                result = chattingTo.stream().map(e -> e.getText()).anyMatch(e -> e.equals(clientId));
            }
        }
        return result;
    }
}
