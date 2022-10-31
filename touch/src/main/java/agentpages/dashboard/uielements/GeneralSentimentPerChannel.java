package agentpages.dashboard.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static io.appium.java_client.pagefactory.utils.WebDriverUnpackUtility.unpackWebDriverFromSearchContext;
import static java.lang.String.format;

@FindBy(css = ".sentiments-box")
public class GeneralSentimentPerChannel extends AbstractUIElement {

    @FindBy(css = ".channel-stats .cl-bar-chart__bar")
    private List<WebElement> sentimentsCharts;

    private final String sentimentChartsByChannelXpath =
            ".//span[text()='%s']/ancestor::div[@class='channel-stats']//div[contains(@class, 'cl-bar-chart__bar ')]";

    private WebElement getSentimentsChartForChannel(String channel){
        String sentimentsChart = format(".//span[text()='%s']/ancestor::div[@class='channel-stats']" +
                "//div[contains(@class, 'channel-stats__charts channel-stats__charts--sentiments')]", channel);
        return findElementByXpath(this.getCurrentDriver(), sentimentsChart, 5);
    }

    public boolean isChartsForChannelShown(String channel) {
        return findElemsByXPATH(unpackWebDriverFromSearchContext(this.getWrappedElement()),
                format(sentimentChartsByChannelXpath, channel))
                .stream().allMatch(sentimentChart -> isElementShown(this.getCurrentDriver(), sentimentChart, 3));
    }

    public boolean isNumberOfSentimentsShownForAllSentimentsCharts() {
        return sentimentsCharts.stream().allMatch(chart -> {
            hoverElem(this.getCurrentDriver(), chart, 3, "Sentiment Chart tooltip");
            return isNumberOfSentimentShownInTooltip();
        });
    }

    public boolean isNumberOfSentimentsShownForChart(String channel) {
        WebElement sentimentsChartForChannel = getSentimentsChartForChannel(channel)
                .findElement(By.xpath(".//div[@class = 'cl-bar-chart__bar-container']"));
        hoverElem(this.getCurrentDriver(), sentimentsChartForChannel, 5, "Neutral sentiment chart");
        if (!isNumberOfSentimentShownInTooltip()){
            hoverElem(this.getCurrentDriver(), sentimentsChartForChannel, 5, "Neutral sentiment chart");
        }
        return isNumberOfSentimentShownInTooltip();
    }

    public boolean isNumberOfSentimentShownInTooltip() {
        String numberOfSentimentInTooltipCss = ".sentiment-tooltip>b";
        return isElementShownByCSS(this.getCurrentDriver(), numberOfSentimentInTooltipCss, 5);
    }
}
