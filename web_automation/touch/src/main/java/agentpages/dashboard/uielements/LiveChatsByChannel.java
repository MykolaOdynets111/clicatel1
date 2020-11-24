package agentpages.dashboard.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = ".live-box")
public class LiveChatsByChannel extends AbstractUIElement {
    @FindBy(css = ".channel-stats__charts")
    private WebElement noLiveChatsMessage;

    @FindBy(css = ".cl-bar-chart__bar bar-webchat")
    private WebElement webChatChart;

    public boolean isNoLiveChatsDisplayed() {
        return isElementShown(this.getCurrentDriver(), noLiveChatsMessage, 5);
    }

    //TODO: need to investigate why web chat chart is not displayed
    public boolean isWebChatChartIsDisplayed() {
        return isElementShown(this.getCurrentDriver(), webChatChart, 5);
    }
}
