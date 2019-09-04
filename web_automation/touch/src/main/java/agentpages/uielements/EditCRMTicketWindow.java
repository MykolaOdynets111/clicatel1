package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Map;


@FindBy(xpath = "//h4[text()='Edit ticket']/ancestor::div[@class='modal-dialog']")
public class EditCRMTicketWindow extends AbstractUIElement {

    private String overlappedPage = "//div[@id='app'][@aria-hidden='true']";

    @FindBy(xpath = ".//button[text()='Cancel']")
    private WebElement cancelButton;

    @FindBy(xpath = ".//button[@type='submit']")
    private WebElement edidTicketButton;

    @FindBy(css = "textarea#CRMNote")
    private WebElement noteInput;

    @FindBy(css = "input#CRMLink")
    private WebElement linkInput;

    @FindBy(css = "input#CRMTicketNumber")
    private WebElement ticketNumberInput;

    public void clickCancel() {
        clickElem(this.getCurrentDriver(), cancelButton, 5, "Cancel CRM editing button" );
        waitForElementToBeInVisibleByXpath(this.getCurrentDriver(), overlappedPage, 7);
    }

    public EditCRMTicketWindow provideCRMNewTicketInfo(Map<String, String> info){
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
        waitForElementToBeInVisibleByXpath(this.getCurrentDriver(), overlappedPage, 7);
        waitFor(200); //Just for test
    }

    public boolean isOpened(){
        return isElementShown(this.getCurrentDriver(), this.getWrappedElement(), 3);
    }
}
