package agentpages.dashboard.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static io.appium.java_client.pagefactory.utils.WebDriverUnpackUtility.unpackWebDriverFromSearchContext;

@FindBy(css = ".sentiments-box")
public class GeneralSentimentPerChannel extends AbstractUIElement {
    private final String numberOfSentimentInTooltipCss = ".sentiment-tooltip>b";

    private final String sentimentChartsByChannelXpath =
            ".//span[text()='%s']/ancestor::div[@class='channel-stats']//div[contains(@class, 'cl-bar-chart__bar ')]";

    private final String positiveSentimentChartByChannelXpath =
            ".//span[text()='%s']/ancestor::div[@class='channel-stats']//div[contains(@class, 'bar-positiveSentiments')]";

    private final String NerutralSentimaentChartByChannelXpath =
            ".//span[text()='%s']/ancestor::div[@class='channel-stats']//div[contains(@class, 'channel-stats__charts channel-stats__charts--sentiments')]";

    @FindBy(css = ".channel-stats .cl-bar-chart__bar")
    private List<WebElement> sentimentsCharts;

    public boolean isChartsForChannelShown(String channel) {
        return findElemsByXPATH(unpackWebDriverFromSearchContext(this.getWrappedElement()),
                String.format(sentimentChartsByChannelXpath, channel))
                .stream().allMatch(sentimentChart -> isElementShown(this.getCurrentDriver(), sentimentChart, 3));
    }

    public boolean isNumberOfSentimentsShownForAllSentimentsCharts() {
        return sentimentsCharts.stream().allMatch(chart -> {
            hoverElem(this.getCurrentDriver(), chart, 3, "Sentiment Chart tooltip");
            return isNumberOfSentimentShownInTooltip();
        });
    }

    public boolean isNumberOfSentimentsShownForAllSentimentsCharts(String channel) {
        hoverElemByXpath(unpackWebDriverFromSearchContext(this.getWrappedElement()),
                String.format(NerutralSentimaentChartByChannelXpath, channel), 5,
                "Neutral sentiment chart");
        return isNumberOfSentimentShownInTooltip();
    }

    public boolean isNumberOfSentimentShownInTooltip() {
        return isElementShownByCSS(this.getCurrentDriver(), numberOfSentimentInTooltipCss, 3);
    }
}
