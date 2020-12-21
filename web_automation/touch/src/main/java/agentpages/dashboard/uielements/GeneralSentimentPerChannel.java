package agentpages.dashboard.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static io.appium.java_client.pagefactory.utils.WebDriverUnpackUtility.unpackWebDriverFromSearchContext;

@FindBy(css = ".sentiments-box")
public class GeneralSentimentPerChannel extends AbstractUIElement {
    private final String numberOfSentimentInTooltipCss = ".sentiment-tooltip>b";
    private final String sentimentChartsByChannelXpath = ".//span[text()='%s']/../following-sibling::div//div[contains(concat(' ', @class, ' '), ' cl-bar-chart__bar ')]";
    private final String positiveSentimentChartByChannelXpath =
            ".//span[text()='%s']/../following-sibling::div//div[contains(concat(' ', @class, ' '), ' bar-positiveSentiments ')]";

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
                String.format(positiveSentimentChartByChannelXpath, channel), 5,
                "Positive sentiment chart");
        return isNumberOfSentimentShownInTooltip();
    }

    public boolean isNumberOfSentimentShownInTooltip() {
        return isElementShownByCSS(this.getCurrentDriver(), numberOfSentimentInTooltipCss, 3);
    }
}
