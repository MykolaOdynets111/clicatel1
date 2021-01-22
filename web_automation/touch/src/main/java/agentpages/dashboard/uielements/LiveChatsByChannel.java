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

    @FindBy(css = ".bar-abc")
    private WebElement abcChart;

    public boolean isNoLiveChatsDisplayed() {
        return isElementShown(this.getCurrentDriver(), noLiveChatsMessage, 5);
    }

    public boolean isWebChatChartIsDisplayed() {
        return isElementShown(this.getCurrentDriver(), webChatChart, 10);
    }

    public boolean isAbcChartIsDisplayed() {
        scrollToElem(this.getCurrentDriver(), this.getWrappedElement(), "Live Chats By Channel");
        return isElementShown(this.getCurrentDriver(), abcChart, 15);
    }

    public boolean isNumberOfLiveChatsShownForWebChatChart() {
        hoverElem(this.getCurrentDriver(), webChatChart, 3, "Live Web Chat Chart");
        return isElementShownByCSS(this.getCurrentDriver(), numberOfLiveChatsInTooltipCss, 3);
    }

    public boolean isNumberOfLiveChatsShownForAbcChart() {
        hoverElem(this.getCurrentDriver(), abcChart, 3, "Live ABC Chart");
        return isElementShownByCSS(this.getCurrentDriver(), numberOfLiveChatsInTooltipCss, 3);
    }
}
