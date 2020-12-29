package agentpages.dashboard.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static io.appium.java_client.pagefactory.utils.WebDriverUnpackUtility.unpackWebDriverFromSearchContext;

@FindBy(css = ".attendance-box")
public class AttendedVsUnattendedChats extends AbstractUIElement {
    private final String numberOfAttendedVsUnattendedChatsInTooltipCss = ".tippy-box b";
    private final String attendedVsUnattendedChatsCharts =
            ".//span[text()='%s']/../following-sibling::div//div[contains(concat(' ', @class, ' '), ' cl-bar-chart__bar ')]";
    private final String attendedChatChartXpath =
            ".//span[text()='%s']/ancestor::div[@class='channel-stats']//div[contains(@class, 'bar-successfulChats')]";

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
        hoverElemByXpath(unpackWebDriverFromSearchContext(this.getWrappedElement()),
                String.format(attendedChatChartXpath, channel), 5,
                "Attended chart");
        return isNumberOfAttendedVsUnattendedChatsInTooltipDisplayed();
    }

    private boolean isNumberOfAttendedVsUnattendedChatsInTooltipDisplayed() {
        return isElementShownByCSS(this.getCurrentDriver(), numberOfAttendedVsUnattendedChatsInTooltipCss, 3);
    }
}
