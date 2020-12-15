package agentpages.dashboard.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = ".attendance-box")
public class AttendedVsUnattendedChats extends AbstractUIElement {
    private final String numberOfAttendedVsUnattendedChatsInTooltipCss = ".tippy-box b";
    private final String attendedVsUnattendedChatsChats =
            ".//span[text()='%s']/../following-sibling::div//div[contains(concat(' ', @class, ' '), ' cl-bar-chart__bar ')]";

    @FindBy(css = ".channel-stats .cl-bar-chart__bar")
    private List<WebElement> attendedVsUnattendedCharts;

    public boolean isChartsForChannelShown(String channel) {
        //get wrapped element for avoiding charts from other graphs on the page
        return this.getWrappedElement()
                .findElements(By.xpath(String.format(attendedVsUnattendedChatsChats, channel)))
                .stream().allMatch(sentimentChart -> isElementShown(this.getCurrentDriver(), sentimentChart, 3));
    }

    public boolean isNumberOfAttendedVsUnattendedChatsDisplayed() {
        return attendedVsUnattendedCharts.stream().allMatch(chart -> {
            hoverElem(this.getCurrentDriver(), chart, 3, "Attended vs Unattended chart");
            return isNumberOfAttendedVsUnattendedChatsInTooltipDisplayed();
        });
    }

    private boolean isNumberOfAttendedVsUnattendedChatsInTooltipDisplayed() {
        return isElementShownByCSS(this.getCurrentDriver(), numberOfAttendedVsUnattendedChatsInTooltipCss, 3);
    }
}
