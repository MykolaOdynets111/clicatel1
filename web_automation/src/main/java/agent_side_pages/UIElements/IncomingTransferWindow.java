package agent_side_pages.UIElements;

import abstract_classes.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = "div.touch-pop-up")
public class IncomingTransferWindow extends AbstractUIElement {

    @FindBy(xpath = "//button[text()='Accept transfer']")
    private WebElement acceptTransfetButton;

    @FindBy(xpath = "//dt[text()='Note:']/following-sibling::dd")
    private WebElement transferNotes;

    public void acceptTransfer(){
        acceptTransfetButton.click();
    }

    public String getTransferNotes(){
        waitForElementToBeVisibleAgent(transferNotes,10, "second agent");
        return  transferNotes.getText();
    }
}
