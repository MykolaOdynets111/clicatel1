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

    @FindBy(css = ".cl-bar-chart__bar.bar-ABC")
    //@FindBy(css = "div.cl-fetch-states:nth-child(2) div.full-height.show div.dashboard-container div.cl-routed-tabs div.cl-routed-tabs__tab-panel div.customers-overview div.cl-tabs.cl-tabs--rounded-buttons div.cl-tabs__tab-panel.px-0.react-tabs__tab-panel--selected:nth-child(2) div.live-customers div.stats-box.live-box:nth-child(3) div.channel-stats:nth-child(4) div.channel-stats__channel-info > span.channel-info__text")
    private WebElement abcChart;

    @FindBy(css = ".bar-WHATSAPP")
    private WebElement whatsAppChart;

    public String getNoLiveChatsDisplayedText() {
        return getTextFromElem(this.getCurrentDriver(), noLiveChatsMessage, 5, "No Live Chats");
    }

    public boolean isWebChatChartIsDisplayed() {
        return isElementShown(this.getCurrentDriver(), webChatChart, 25);
    }

    public boolean isAbcChartIsDisplayed() {
        scrollToElem(this.getCurrentDriver(), this.getWrappedElement(), "Live Chats By Channel");
        return isElementShown(this.getCurrentDriver(), abcChart, 15);
    }

    public boolean isWhatsAppChartIsDisplayed() {
        scrollToElem(this.getCurrentDriver(), this.getWrappedElement(), "Live Chats By Channel");
        return isElementShown(this.getCurrentDriver(), whatsAppChart, 15);
    }


    public boolean isNumberOfLiveChatsShownForWebChatChart() {
        hoverElem(this.getCurrentDriver(), webChatChart, 3, "Live Web Chat Chart");
        return isElementShownByCSS(this.getCurrentDriver(), numberOfLiveChatsInTooltipCss, 3);
    }

    public boolean isNumberOfLiveChatsShownForAbcChart() {
        hoverElem(this.getCurrentDriver(), abcChart, 3, "Live ABC Chart");
        return isElementShownByCSS(this.getCurrentDriver(), numberOfLiveChatsInTooltipCss, 3);
    }

    public boolean isNumberOfLiveChatsShownForWAChart() {
        hoverElem(this.getCurrentDriver(), whatsAppChart, 3, "Live ABC Chart");
        return isElementShownByCSS(this.getCurrentDriver(), numberOfLiveChatsInTooltipCss, 3);
    }
}
