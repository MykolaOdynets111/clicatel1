package agentpages.uielements;

import abstractclasses.AbstractUIElementDeprecated;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@FindBy(xpath = "//div[text()='Delete Note']/parent::div")
public class DeleteCRMConfirmationPopup extends AbstractUIElementDeprecated {

    private String overlappedPage = "//div[@id='app'][@aria-hidden='true']";

    @FindBy(xpath = ".//button[text()='Cancel']")
    private WebElement cancelButton;

    @FindBy(xpath = ".//button[@type='submit']")
    private WebElement deleteButton;

    public void clickCancel() {
        clickElemAgent(cancelButton, 3, "main agent", "'Cancel' deleting CRM button" );
        waitForElementsToBeInvisibleByXpathAgent(overlappedPage, 7, "main agent");
    }

    public void clickDelete() {
        clickElemAgent(deleteButton, 3, "main agent", "'Cancel' deleting CRM button" );
        waitForElementsToBeInvisibleByXpathAgent(overlappedPage, 7, "main agent");
    }

    public boolean isOpened(){
        return isElementShownAgent(this.getWrappedElement());
    }

}
