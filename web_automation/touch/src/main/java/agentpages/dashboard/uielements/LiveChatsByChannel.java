package agentpages.dashboard.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = ".live-box")
public class LiveChatsByChannel extends AbstractUIElement {
    private final String numberOfLiveChatsInTooltipCss = ".tippy-box b";

    @FindBy(css = ".channel-stats__charts")
    private WebElement noLiveChatsMessage;

    @FindBy(css = ".bar-webchat")
    private WebElement webChatChart;

    public boolean isNoLiveChatsDisplayed() {
        return isElementShown(this.getCurrentDriver(), noLiveChatsMessage, 5);
    }

    public boolean isWebChatChartIsDisplayed() {
        return isElementShown(this.getCurrentDriver(), webChatChart, 10);
    }

    public boolean isNumberOfLiveChatsShownForWebChatChart() {
        hoverElem(this.getCurrentDriver(), webChatChart, 3, "Sentiment Chart tooltip");
        return isElementShown(this.getCurrentDriver(),
                findElemByCSS(this.getCurrentDriver(), numberOfLiveChatsInTooltipCss), 3);
    }
}
