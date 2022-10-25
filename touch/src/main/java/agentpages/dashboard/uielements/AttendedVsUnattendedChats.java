package agentpages.dashboard.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static io.appium.java_client.pagefactory.utils.WebDriverUnpackUtility.unpackWebDriverFromSearchContext;
import static java.lang.String.format;

@FindBy(css = ".attendance-box")
public class AttendedVsUnattendedChats extends AbstractUIElement {
    private final String numberOfAttendedVsUnattendedChatsInTooltipCss = ".tippy-box b";
    private final String attendedVsUnattendedChatsCharts =
            ".//span[text()='%s']/../following-sibling::div//div[contains(concat(' ', @class, ' '), ' cl-bar-chart__bar ')]";

    private WebElement getAttendedChartForChannel(String channel){
        String attendedChart = format(".//div[contains(@class,'attendance')]//span[text() = '%s']" +
                "/ancestor::div[@class = 'channel-stats']", channel);
        return findElementByXpath(this.getCurrentDriver(), attendedChart, 5);
    }

    @FindBy(css = ".channel-stats .cl-bar-chart__bar")
    private List<WebElement> attendedVsUnattendedCharts;

    public boolean isChartsForChannelShown(String channel) {
        return findElemsByXPATH(unpackWebDriverFromSearchContext(this.getWrappedElement()),
                String.format(attendedVsUnattendedChatsCharts, channel))
                .stream().allMatch(sentimentChart -> isElementShown(this.getCurrentDriver(), sentimentChart, 3));
    }

    public boolean isNumberOfAttendedVsUnattendedChatsDisplayed() {
        return attendedVsUnattendedCharts.stream().allMatch(chart -> {
            hoverElem(this.getCurrentDriver(), chart, 3, "Attended vs Unattended chart");
            return isNumberOfAttendedVsUnattendedChatsInTooltipDisplayed();
        });
    }

    public boolean isNumberOfAttendedChatsDisplayedForChannel(String channel) {
        WebElement attendedChart = getAttendedChartForChannel(channel)
                .findElement(By.xpath(".//div[@class = 'cl-bar-chart__bar-container']"));
        hoverElem(this.getCurrentDriver(), attendedChart, 5, "Attended chart");
        return isNumberOfAttendedVsUnattendedChatsInTooltipDisplayed();
    }

    private boolean isNumberOfAttendedVsUnattendedChatsInTooltipDisplayed() {
        return isElementShownByCSS(this.getCurrentDriver(), numberOfAttendedVsUnattendedChatsInTooltipCss, 3);
    }
}
