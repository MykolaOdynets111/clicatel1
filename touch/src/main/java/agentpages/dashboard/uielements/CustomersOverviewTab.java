package agentpages.dashboard.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import javax.xml.xpath.XPath;
import java.util.List;

@FindBy(css = ".customers-overview")
public class CustomersOverviewTab extends AbstractUIElement {

    @FindBy(css = "[data-testid='tab-customers-overview-tabs-1']")
    private WebElement customersHistoryButton;

    @FindBy(css = "[data-testid='tab-customers-overview-tabs-0']")
    private WebElement liveCustomersButton;

    @FindBy(css =".cl-chat-item cl-chat-item--selected")
    private WebElement LiveCustomer;

    @FindBy(xpath = "//*[@selenium-id='chat-list-item-018147f695c441cf58eb55a422d6150f']")
    private WebElement AgentLiveCustomer;

    @FindBy(css = "cl-card--c2p cl-card--c2p-extension xh-highlight")
    private WebElement ChatToPay;

    @FindBy(xpath = "//button[@type='button']/text()='Cancel Payment'")
    private WebElement cancelPaymentButton;

    @FindBy(xpath="//*[@selenium-id='message-composer-extensions']")
    private WebElement PaymentExtensionButton;
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

    public void clickOnLiveCustomer() {
        waitForElementToBeVisible(this.getCurrentDriver(), LiveCustomer, 5);
        LiveCustomer.click();
    }

    public void clickOnPaymentExtension() {
        waitForElementToBeVisible(this.getCurrentDriver(), AgentLiveCustomer, 5);
        AgentLiveCustomer.click();
    }

    public void clickCancelPaymentButton(String CancelPaymentButton)
    {
        waitForElementToBeVisible(this.getCurrentDriver(), cancelPaymentButton, 5);
        cancelPaymentButton.click();
    }

    public void clickOnChatToPayOption()
    {
        waitForElementToBeVisible(this.getCurrentDriver(), ChatToPay, 5);
        ChatToPay.click();
    }

    public void ClickOnPaymentExtension(){
        waitForElementToBeVisible(this.getCurrentDriver(), PaymentExtensionButton, 5);
        PaymentExtensionButton.click();
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
