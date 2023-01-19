package agentpages.tickets;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = ".app-tickets-actions-bar")
public class TicketsQuickActionBar extends AbstractUIElement {

    @FindBy(css = ".quick-accept-bar div")
    private WebElement questionInfoButton;

    @FindBy(xpath = "//button[text()='Accept']")
    private WebElement acceptButton;

    @FindBy(xpath = "//button[text()='Close']")
    private WebElement closeButton;

    @FindBy(css = "[name='customNumberOfTickets']")
    private WebElement numberOfTicketsInput;

    public void hoverQuickActionBar() {
        waitForElementToBeVisible(this.getCurrentDriver(), questionInfoButton, 5);
        moveToElement(this.getCurrentDriver(), questionInfoButton);
    }

    public void inputNumberOfTicketsForAccept(String numberOfTickets) {
        inputText(this.getCurrentDriver(), numberOfTicketsInput, 5, "Input field tickets", numberOfTickets);
    }

    public void clickAcceptButtonInHeader() {
        clickElem(this.getCurrentDriver(), acceptButton, 5, "Accept Button");
    }

    public boolean closeButtonStatusQuickAction(){
        return Boolean.parseBoolean(getAttributeFromElem(this.getCurrentDriver(), closeButton, 5, "Close ticket button", "disabled"));
    }
}