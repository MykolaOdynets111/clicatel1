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

    @FindBy(css="[button.cl-button.cl-button--reset-only.cl-button--full-width.cl-button--ac-left.cl-send-external-message__item]")
    private WebElement whatsappIcon;

    @FindBy(css = "[data-testid='tab-customers-overview-tabs-0']")
    private WebElement liveCustomersButton;

    @FindBy(css ="[.cl-chat-item cl-chat-item--selected]")
    private WebElement LiveCustomer;

    @FindBy(css="[a.cl-routed-tabs__tab.cl-routed-tabs__tab--selected]")
    private WebElement PendingTab;

    @FindBy(xpath = "//*[@selenium-id='chat-list-item-018147f695c441cf58eb55a422d6150f']")
    private WebElement AgentLiveCustomer;

    @FindBy(css = "cl-card--c2p cl-card--c2p-extension xh-highlight")
    private WebElement ChatToPay;

    @FindBy(xpath = "//button[@type='button']/text()='Cancel Payment'")
    private WebElement cancelPaymentButton;

    @FindBy(xpath = "//button[@type='button' and (text()='Close Chat' or.='Close Chat')]")
    private WebElement closedChatButton;

    @FindBy(css="button.cl-button.cl-button--primary.cl-c2p-close-modal-button")
    private WebElement MoveToPending;

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
        clickElem(this.getCurrentDriver(), liveCustomersButton, 5,"Click on Live Customer");
    }

    public void clickOnWhatsapp()
    {
        scrollAndClickElem(this.getCurrentDriver(), whatsappIcon, 5,"Click on Whatsapp Icon");
    }

    public void clickOnLiveCustomer() {
        clickElem(this.getCurrentDriver(),LiveCustomer,5,"Live Customer" );
    }

    public void clickOnPendingTab()
    {
        clickElem(this.getCurrentDriver(),PendingTab,5,"Pending Tab");
    }

    public void clickOnPaymentExtension() {
        clickElem(this.getCurrentDriver(), AgentLiveCustomer, 5, "Click on Payment Extension");
    }

    public void clickCancelPaymentButton() {
        clickElem(this.getCurrentDriver(),cancelPaymentButton,5,"cancel Payment");
    }
    public void clickClosedChatButton() {
        scrollAndClickElem(this.getCurrentDriver(),closedChatButton,5, "closed chat");
    }

    public void clickMoveToPendingButton(){
        clickElem(this.getCurrentDriver(),MoveToPending,5,"Move to Pending");
    }
    public void clickOnChatToPayOption()
    {
        clickElem(this.getCurrentDriver(), ChatToPay, 5,"Chat to Pay option");
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
