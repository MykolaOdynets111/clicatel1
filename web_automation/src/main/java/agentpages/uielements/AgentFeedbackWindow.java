package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@FindBy(css = "div.modal-content")
public class AgentFeedbackWindow extends AbstractUIElement {

    @FindBy(xpath = "//button[text()='Cancel']")
    private WebElement cancelButton;

    public void clickCancel() {
        waitForElementToBeVisibleAgent(cancelButton,7);
        cancelButton.click();
        waitForElementToBeVisibleAgent(cancelButton,7);
    }



}
