package agentpages.dashboard.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = ".live-box")
public class LiveChatsByChannel extends AbstractUIElement {
    @FindBy(css = ".channel-stats__charts")
    private WebElement noLiveChatsMessage;

    @FindBy(css = ".bar-webchat")
    private WebElement webChatChart;

    public boolean isNoLiveChatsDisplayed() {
        return isElementShown(this.getCurrentDriver(), noLiveChatsMessage, 5);
    }

    public boolean isWebChatChartIsDisplayed() {
        return isElementShown(this.getCurrentDriver(), webChatChart, 5);
    }
}
