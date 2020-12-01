package agentpages.dashboard.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = ".sentiments-box")
public class GeneralSentimentPerChannel extends AbstractUIElement {
    private final String numberOfSentimentInTooltipCss = ".sentiment-tooltip>b";

    @FindBy(css = ".channel-stats .cl-bar-chart__bar")
    private List<WebElement> sentimentsCharts;

    public boolean isNumberOfSentimentsShownForAllSentimentsCharts() {
        return sentimentsCharts.stream().allMatch(chart -> {
            hoverElem(this.getCurrentDriver(), chart, 3, "Sentiment Chart tooltip");
            return isNumberOfSentimentShownInTooltip();
        });
    }

    public boolean isNumberOfSentimentShownInTooltip() {
        return isElementShown(this.getCurrentDriver(),
                //should be like that, because tooltip is float element and appears in body directly
                findElemByCSS(this.getCurrentDriver(), numberOfSentimentInTooltipCss), 3);
    }
}
