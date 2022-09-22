package agentpages.supervisor.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;
import java.util.stream.Collectors;

@FindBy(css = ".cl-routed-tabs__tab-panel")
public class SupervisorLeftPanel extends AbstractUIElement {

    @FindBy(xpath = "//div[@class='cl-routed-tabs__tab-list']//a")
    private List<WebElement> listchat;

    @FindBy(xpath="//a[contains(@class, 'selected')]//div[@class='cl-chats-group-item__name']")
    private WebElement defaultFilter;

    @FindBy(css = ".cl-chats-group-item__name")
    private List<WebElement> ticketsFilterNames;

    @FindBy(css = ".cl-chats-group-item__inner")
    private List<WebElement> liveChatsFilters;

    @FindBy(css = ".cl-chats-group-item__name")
    private  WebElement allChats;

    @FindBy(xpath="//div[@class='chats-list live-chats-list']")
    private  WebElement livechatsinfo;

    @FindBy(xpath = "//span[@data-testid='chat-item-icons-holder']")
    private WebElement leftChatPendingIcon;

    @FindBy(xpath = "//button[@data-testid='header-toggle-pending']")
    private WebElement leftChatPendingOn;

    public String getFilterByDefaultName(){
        return getTextFromElem(this.getCurrentDriver(), defaultFilter,5,"Default filter").trim();
    }

    public List<String> getFilterNames(){
        return ticketsFilterNames.stream().map(a-> a.getText().trim()).collect(Collectors.toList());
    }

     public void selectTicketType(String type) {
         ticketsFilterNames.stream().filter(a -> a.getText().trim().equalsIgnoreCase(type)).findFirst()
                 .orElseThrow(() -> new AssertionError("Cannot find " + type + " conversation type filter")).click();
     }

    private WebElement getLiveFilterType(String agentName) {
       return liveChatsFilters.stream().filter(a -> a.getText().trim().contains(agentName)).findFirst()
                .orElseThrow(() -> new AssertionError("Can't find Live Chats filter for " + agentName ));
    }

    public int getLiveChatsNumberForAgent(String agentName){
        return Integer.valueOf(getLiveFilterType(agentName).findElement(By.cssSelector(".cl-chats-group-item__count--round")).getText().trim());
    }

    public void getChatElement(){

        for(int i=0;i<listchat.size();i++){
            if((i==0) && (listchat.get(i).getText().equalsIgnoreCase("Chats"))){
                Assert.assertEquals(listchat.get(i).getText(),"Chats","Chats element is not display at first in the Page");
                break;
            }
        }
    }
    public void verifyChatgroup(String liveChats){
        Assert.assertEquals(allChats.getText(),liveChats,"all chats text is not selected default");
    }

    public void verifyLiveChatInfo() {
        Assert.assertTrue(livechatsinfo.isDisplayed(),"Live chat not displayed ");
    }
}