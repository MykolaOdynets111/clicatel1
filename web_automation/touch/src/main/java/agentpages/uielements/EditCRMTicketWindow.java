package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Map;


@FindBy(css = "[selenium-id=edit-crm-ticket]")
public class EditCRMTicketWindow extends AbstractUIElement {

    private String overlappedPage = "//div[@id='app'][@aria-hidden='true']";

    @FindBy(css = "[selenium-id=edit-crm-ticket-cancel]")
    private WebElement cancelButton;

    @FindBy(css = "[selenium-id=edit-crm-ticket-save]")
    private WebElement edidTicketButton;

    @FindBy(css = "[selenium-id=crm-note]")
    private WebElement noteInput;

    @FindBy(css = "[selenium-id=crm-link]")
    private WebElement linkInput;

    @FindBy(css = "[selenium-id=crm-ticket-number]")
    private WebElement ticketNumberInput;

    public void clickCancel() {
        clickElem(this.getCurrentDriver(), cancelButton, 5, "Cancel CRM editing button" );
        waitForElementToBeInvisibleByXpath(this.getCurrentDriver(), overlappedPage, 7);
    }

    public EditCRMTicketWindow provideCRMNewTicketInfo(Map<String, String> info){
        waitForElementToBeClickable(this.getCurrentDriver(), noteInput, 3);
        noteInput.click();
        noteInput.clear();
        noteInput.sendKeys(info.get("agentNote"));
        linkInput.clear();
        linkInput.sendKeys(info.get("link"));
        ticketNumberInput.clear();
        ticketNumberInput.sendKeys(info.get("ticketNumber"));
        return this;
    }


    public void saveChanges() {
        clickElem(this.getCurrentDriver(), edidTicketButton, 5,"Edit CRM ticket button" );
        waitForElementToBeInvisibleByXpath(this.getCurrentDriver(), overlappedPage, 7);
        waitFor(200); //Just for test
    }

    public boolean isOpened(){
        return isElementShown(this.getCurrentDriver(), this.getWrappedElement(), 3);
    }
}
