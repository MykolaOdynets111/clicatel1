package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@FindBy(css = "[selenium-id=delete-note-conffirmation]")
public class DeleteCRMConfirmationPopup extends AbstractUIElement {

    private String overlappedPage = "//div[@id='app'][@aria-hidden='true']";

    @FindBy(css = "[selenium-id=delete-note-decline]")
    private WebElement cancelButton;

    @FindBy(css = "[selenium-id=delete-note-confirm]")
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
