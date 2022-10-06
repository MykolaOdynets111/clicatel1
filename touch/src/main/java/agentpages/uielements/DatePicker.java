package agentpages.uielements;

import abstractclasses.AgentAbstractPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@FindAll({
        @FindBy(css = ".search-filter-bar-agent__filters"),
        @FindBy(css = "[class='full-height show']")
})
public class DatePicker extends AgentAbstractPage {

    @FindBy(xpath = ".//div[contains(@class,'react-datepicker__day react-datepicker__day')]")
    private List<WebElement> calendarDatePicker;

    @FindBy(xpath = ".//button[@aria-label='Previous Month']")
    private WebElement backButton;

    @FindBy(xpath = ".//input[@class = 'cl-form-control cl-form-control--input']")
    private WebElement startDateInput;

    @FindBy(xpath = ".//input[contains(@class, 'cl-form-control cl-form-control--input cl-end-date')]")
    private WebElement endDateInput;

    public DatePicker(String agent) {
        super(agent);
    }

    public boolean checkBackButtonVisibilityThreeMonthsBack(String filterType, Long day) {
        switch (filterType) {
            case "start date":
                clickElem(this.getCurrentDriver(), startDateInput, 5, "Start date element");
                break;
            case "end date":
                clickElem(this.getCurrentDriver(), endDateInput, 5, "End date element");
                break;
            default:
                throw new AssertionError("Incorrect filter type was provided: " + filterType);
        }

        LocalDate startDate = LocalDate.now().minusDays(day);
        int month = startDate.getDayOfMonth();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(getPattern(month));
        String expectedDate = startDate.format(formatter);

        clickElem(this.getCurrentDriver(), backButton, 5, "Back button element");
        clickElem(this.getCurrentDriver(), backButton, 5, "Back button element");

        List<WebElement> date = calendarDatePicker.stream().filter(e -> e.getAttribute("aria-label").contains(expectedDate)).collect(Collectors.toList());

        if (date.size() == 0) {
            clickElem(this.getCurrentDriver(), backButton, 5, "Back button element");
            date = calendarDatePicker.stream().filter(e -> e.getAttribute("aria-label").contains(expectedDate)).collect(Collectors.toList());
        }

        switch (filterType) {
            case "start date":
                if (date.get(0).getAttribute("aria-disabled").equalsIgnoreCase("true")) {
                    return true;
                }
                break;
            case "end date":
                if (date.get(0).getAttribute("aria-disabled").equalsIgnoreCase("false")) {
                    return true;
                }
                break;
        }

        return false;
    }

    public static String getPattern(int month) {
        String first = "MMMM d";
        String last = ", yyyy";
        String pos = (month == 1 || month == 21 || month == 31) ? "'st'" : (month == 2 || month == 22) ? "'nd'" : (month == 3 || month == 23) ? "'rd'" : "'th'";

        return first + pos + last;
    }
}