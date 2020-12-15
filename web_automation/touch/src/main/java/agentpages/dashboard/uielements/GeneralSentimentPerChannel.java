package agentpages.dashboard.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = ".sentiments-box")
public class GeneralSentimentPerChannel extends AbstractUIElement {
    private final String numberOfSentimentInTooltipCss = ".sentiment-tooltip>b";
    private final String sentimentChartsByChannel =
            ".//span[text()='%s']/../following-sibling::div//div[contains(concat(' ', @class, ' '), ' cl-bar-chart__bar ')]";

    @FindBy(css = ".channel-stats .cl-bar-chart__bar")
    private List<WebElement> sentimentsCharts;

    public boolean isChartsForChannelShown(String channel) {
        //get wrapped element for avoiding charts from other graphs on the page
        return this.getWrappedElement()
                .findElements(By.xpath(String.format(sentimentChartsByChannel, channel)))
                .stream().allMatch(sentimentChart -> isElementShown(this.getCurrentDriver(), sentimentChart, 3));
    }

    public boolean isNumberOfSentimentsShownForAllSentimentsCharts() {
        return sentimentsCharts.stream().allMatch(chart -> {
            hoverElem(this.getCurrentDriver(), chart, 3, "Sentiment Chart tooltip");
            return isNumberOfSentimentShownInTooltip();
        });
    }

    public boolean isNumberOfSentimentShownInTooltip() {
        return isElementShownByCSS(this.getCurrentDriver(), numberOfSentimentInTooltipCss, 3);
    }
}
