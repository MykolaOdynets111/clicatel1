package agentpages.leftmenu;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

@FindBy(css = ".cl-chats-group-panel")
public class SupervisorAndTicketsPart extends AbstractUIElement {

    @FindBy(xpath="//a[contains(@class, 'selected')]//div[@class='cl-chats-group-item__name']")
    private WebElement defaultFilter;

    @FindBy(css = ".cl-chats-group-item__name")
    private List<WebElement> filters;

    @FindBy(xpath="//div[@class='chats-list live-chats-list']")
    private  WebElement liveChatsInfo;

    private final String ticketsCountCSS = "[href='/%s/tickets/%s/'] .cl-chats-group-item__count";

    public String getFilterByDefaultName(){
        return getTextFromElem(this.getCurrentDriver(), defaultFilter,5,"Default filter").trim();
    }

    public int getTicketsCount(String platformType, String filterType){
        String locator = String.format(ticketsCountCSS, platformType, filterType);
        WebElement element = waitForElementToBePresentByCss(this.getCurrentDriver(), locator, 10);
        return Integer.parseInt(getTextFromElem(this.getCurrentDriver(), element,5,"Default filter").trim());
    }

    public List<String> getFilterNames(){
        return filters.stream().map(a-> a.getText().trim()).collect(Collectors.toList());
    }

    public void selectFilter(String type) {
        waitForElementsToBeVisible(this.getCurrentDriver(), filters, 10);
        if (filters.stream().filter(a -> a.getText().trim().equalsIgnoreCase(type)).collect(Collectors.toList()).size() > 0) {
            filters.stream().filter(a -> a.getText().trim().equalsIgnoreCase(type)).findFirst()
                    .orElseThrow(() -> new AssertionError("Cannot find " + type + " conversation type filter")).click();
        } else {
            getCurrentDriver().navigate().refresh();

            waitForElementsToBeVisible(this.getCurrentDriver(), filters, 10);
            filters.stream().filter(a -> a.getText().trim().equalsIgnoreCase(type)).findFirst()
                    .orElseThrow(() -> new AssertionError("Cannot find " + type + " conversation type filter")).click();
        }
    }

    private WebElement getFilter(String agentName) {
        return filters.stream().filter(a -> a.getText().trim().contains(agentName)).findFirst()
                .orElseThrow(() -> new AssertionError("Can't find Live Chats filter for " + agentName ));
    }

    public int getLiveChatsNumberForAgent(String agentName){
        return Integer.valueOf(getFilter(agentName).findElement(By.cssSelector(".cl-chats-group-item__count--round")).getText().trim());
    }

    public String getFirstFilterName(){
        return getTextFromElem(this.getCurrentDriver(), filters.get(0), 2,"All Chats");
    }

    public boolean isLiveChatsInfoDisplayed() {
        return isElementShown(this.getCurrentDriver(), liveChatsInfo,2);
    }

}
