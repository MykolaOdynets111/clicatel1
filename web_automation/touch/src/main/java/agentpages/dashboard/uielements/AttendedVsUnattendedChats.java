package agentpages.dashboard.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = ".attendance-box")
public class AttendedVsUnattendedChats extends AbstractUIElement {
    private final String numberOfAttendedVsUnattendedChatsInTooltipCss = ".tippy-box b";

    @FindBy(css = ".channel-stats .cl-bar-chart__bar")
    private List<WebElement> attendedVsUnattendedChart;

    public boolean isNumberOfAttendedVsUnattendedChatsDisplayed() {
        return attendedVsUnattendedChart.stream().allMatch(chart -> {
            hoverElem(this.getCurrentDriver(), chart, 3, "Attended vs Unattended chart");
            return isNumberOfAttendedVsUnattendedChatsInTooltipDisplayed();
        });
    }

    private boolean isNumberOfAttendedVsUnattendedChatsInTooltipDisplayed() {
        return isElementShown(this.getCurrentDriver(),
                //should be like that, because tooltip is float element and appears in body directly
                findElemByCSS(this.getCurrentDriver(), numberOfAttendedVsUnattendedChatsInTooltipCss), 3);
    }
}
