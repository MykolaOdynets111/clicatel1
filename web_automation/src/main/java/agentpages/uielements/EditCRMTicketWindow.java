package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Map;


@FindBy(xpath = "//div[text()='Edit ticket']/parent::div")
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
        clickElemAgent(cancelButton, 5, "main agent", "Cancel CRM editing button" );
        waitForElementsToBeInvisibleByXpathAgent(overlappedPage, 7, "main agent");
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
        clickElemAgent(edidTicketButton, 5, "main agent", "Edit CRM ticket button" );
        waitForElementsToBeInvisibleByXpathAgent(overlappedPage, 7, "main agent");
    }

    public boolean isOpened(){
        return isElementShownAgent(this.getWrappedElement());
    }
}
