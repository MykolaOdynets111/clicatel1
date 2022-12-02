package agentpages.leftmenu;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;
import java.util.stream.Collectors;

@FindBy(css = ".cl-chats-group-panel")
public class SupervisorAndTicketsPart extends AbstractUIElement {

    @FindBy(xpath="//a[contains(@class, 'selected')]//div[@class='cl-chats-group-item__name']")
    private WebElement defaultFilter;

    @FindBy(css = ".cl-chats-group-item__name")
    private List<WebElement> ticketsFilterNames;

    @FindBy(css = ".cl-chats-group-item__inner")
    private List<WebElement> liveChatsFilters;

    @FindBy(css = ".cl-chats-group-item__name")
    private  WebElement allChats;

    @FindBy(xpath="//div[@class='chats-list live-chats-list']")
    private  WebElement liveChatsInfo;


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

    public void clickFilterType(String filterName) {
        liveChatsFilters.stream().filter(a -> a.getText().trim().contains(filterName)).findFirst()
                .orElseThrow(() -> new AssertionError("Can't find Live Chats filter for " + filterName ))
                .click();
    }

    public int getLiveChatsNumberForAgent(String agentName){
        return Integer.valueOf(getLiveFilterType(agentName).findElement(By.cssSelector(".cl-chats-group-item__count--round")).getText().trim());
    }

    public String verifyChatGroup(){
        return getTextFromElem(this.getCurrentDriver(), allChats, 2,"All Chats");
    }

    public boolean isLiveChatsInfoDisplayed() {
        return isElementShown(this.getCurrentDriver(), liveChatsInfo,2);
    }

}
