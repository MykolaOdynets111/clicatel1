package agentpages.dashboard.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = ".customers-overview")
public class CustomersOverviewTab extends AbstractUIElement {

    @FindBy(css = "[selenium-id='tab-customers-overview-tabs-Customers History']")
    private WebElement customersHistoryButton;

    @FindBy(css = "[selenium-id='tab-customers-overview-tabs-Live Customers']")
    private WebElement liveCustomersButton;

    @FindBy(css = ".tabs-dropdowns-wrapper .cl-r-form-group:first-child .cl-r-select__indicators")
    private WebElement channelFilterDropdown;

    @FindBy(css = ".tabs-dropdowns-wrapper .cl-r-form-group:nth-child(2) .cl-r-select__indicators")
    private WebElement periodFilterDropdown;

    @FindBy(xpath=".//div[contains(@id, 'react-select')]")
    private List<WebElement> dropdownOptions;

    public void clickOnCustomersHistory() {
        clickElem(this.getCurrentDriver(), customersHistoryButton, 5, "Customers History Tab");
    }

    public void clickOnLiveCustomers() {
        waitForElementToBeVisible(this.getCurrentDriver(), liveCustomersButton, 5);
        liveCustomersButton.click();
    }

    public void selectChannelForReport(String channel) {
        clickElem(this.getCurrentDriver(), channelFilterDropdown, 1, "Channels Dropdown");
        dropdownOptions.stream().filter(e -> e.getText().equalsIgnoreCase(channel))
                .findFirst().orElseThrow(() ->
                new AssertionError(channel + " channel is not found")).click();
    }

    public void selectPeriodForReport(String period) {
        scrollToElem(this.getCurrentDriver(), periodFilterDropdown,  "Period Dropdown");
        clickElem(this.getCurrentDriver(), periodFilterDropdown, 1, "Period Dropdown");
        dropdownOptions.stream().filter(e -> e.getText().equalsIgnoreCase(period))
                .findFirst().orElseThrow(() ->
                new AssertionError(period + " period is not found")).click();
    }

}
