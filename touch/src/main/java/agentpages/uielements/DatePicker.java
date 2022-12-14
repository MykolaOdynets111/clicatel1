package agentpages.uielements;

import abstractclasses.AgentAbstractPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

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

    public DatePicker openDatePicker(String filterType){
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
        return this;
    }

    public boolean verifyExpectedDateIsDisabled(int days) {

        String expectedDate = getExpectedDayForVerification(days);

        return isDateSelectable(expectedDate);
    }

    public boolean isBackButtonShown(){
        return isElementShown(this.getCurrentDriver(), backButton, 2);
    }

    private boolean isDateSelectable(String expectedDate){
        WebElement expectedDateElement = clickBackButtonTillExpectedDate(expectedDate);
        return Boolean.valueOf(expectedDateElement.getAttribute("aria-disabled")) ? false : true;
    }

    private String getExpectedDayForVerification(int days){
        LocalDate startDate = LocalDate.now().minusDays(days);
        int dayOfMonth = startDate.getDayOfMonth();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(getPattern(dayOfMonth), Locale.US);
        return startDate.format(formatter);
    }

    private WebElement clickBackButtonTillExpectedDate(String expectedDate){
        for (int i = 4; i>0; i--){
            if (calendarDatePicker.stream().anyMatch(e -> e.getAttribute("aria-label").contains(expectedDate))) {
                break;
            }
            clickElem(this.getCurrentDriver(), backButton, 5, "Back button element");
        }
        return calendarDatePicker.stream().filter(e -> e.getAttribute("aria-label").contains(expectedDate)).findFirst()
                .orElseThrow(() -> new AssertionError("Expected Date: " +expectedDate+ " was not reached"));
    }

    private static String getPattern(int day) {
        String month = "MMMM";
        String last = ", yyyy";
        String dayWithEnding = (day == 1 || day == 21 || day == 31) ? " d'st'" : (day == 2 || day == 22) ? " d'nd'" : (day == 3 || day == 23) ? " d'rd'" : " d'th'";

        return month + dayWithEnding + last;
    }
}