package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@FindBy(xpath = "//h4[text()='Delete Note']/ancestor::div[@class='modal-dialog']")
public class DeleteCRMConfirmationPopup extends AbstractUIElement {

    private String overlappedPage = "//div[@id='app'][@aria-hidden='true']";

    @FindBy(xpath = ".//button[text()='Cancel']")
    private WebElement cancelButton;

    @FindBy(xpath = ".//button[@type='submit']")
    private WebElement deleteButton;

    public void clickCancel() {
        clickElem(this.getCurrentDriver(), cancelButton, 3,"'Cancel' deleting CRM button" );
        waitForElementToBeInvisibleByXpath(this.getCurrentDriver(), overlappedPage, 7);
    }

    public void clickDelete() {
        clickElem(this.getCurrentDriver(), deleteButton, 3,"'Cancel' deleting CRM button" );
        waitForElementToBeInvisibleByXpath(this.getCurrentDriver(), overlappedPage, 7);
    }

    public boolean isOpened(){
        return isElementShown(this.getCurrentDriver(), this.getWrappedElement(), 3);
    }

}
