package agentpages.dashboard.uielements;

import abstractclasses.AbstractWidget;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;


public class LiveAgentRowDashboard extends AbstractWidget {

    private WebElement baseWebElem = this.getWrappedElement();

    @FindBy(css = "td.cl-collapsible-table__cell--firstName>div")
    private WebElement agentFirstName;

    @FindBy(css = "td.cl-collapsible-table__cell--lastName>div")
    private WebElement agentLastName;

    @FindBy(css = "td.cl-collapsible-table__cell--liveChats")
    private WebElement liveChatsNumber;

    @FindBy(css = "td>.cl-r-button")
    private WebElement expandButton;

    @FindBy(css = "td.cl-collapsible-table__cell--channels")
    private WebElement channelsNumber;

    @FindBy(css = "td.cl-collapsible-table__cell--sentiment>svg")
    private List<WebElement> sentiments;

    @FindBy(css = "td.cl-collapsible-table__cell--chatDuration")
    private WebElement averageChatDuration;

    public LiveAgentRowDashboard(WebElement element) {
        super(element);
    }

    public LiveAgentRowDashboard setCurrentDriver(WebDriver currentDriver){
        this.currentDriver = currentDriver;
        return this;
    }

    public String getFistAgentName(){
        return getTextFromElem(this.getCurrentDriver(), agentFirstName, 5, "Agent First Name");
    }

    public String getLastAgentName(){
        return getTextFromElem(this.getCurrentDriver(), agentLastName, 5, "Agent First Name");
    }


    public int getLiveChatsNumber(){
        return Integer.parseInt(getTextFromElem(getCurrentDriver(), liveChatsNumber, 5,
                "Live Chats Number"));
    }

    public void clickExpandButton(){
        clickElem(this.getCurrentDriver(), expandButton, 3,"Expand agent row button");
    }

    public int getChannelNumber(){
        return Integer.parseInt(getTextFromElem(getCurrentDriver(), channelsNumber, 5,
                "Channels Number"));
    }

    public List<String> getSentiments(){
        return sentiments.stream().map(e -> e.getAttribute("data-data-testid")).collect(Collectors.toList());
    }

    public boolean isLiveChatsNumberShown() {
        return isElementHasAnyText(getCurrentDriver(), liveChatsNumber, 5);
    }


    public boolean isChannelsNumberShown() {
        return isElementHasAnyText(getCurrentDriver(), channelsNumber, 5);
    }

    public boolean isSentimentsShown() {
        return areElementsShown(getCurrentDriver(), sentiments, 5);
    }

    public boolean isAverageDurationShown() {
        return isElementHasAnyText(getCurrentDriver(), averageChatDuration, 5);
    }
}
