package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

@FindBy(css = "div.touch-pop-up")
public class IncomingTransferWindow extends AbstractUIElement {

    @FindBy(xpath = "//button[text()='Accept transfer']")
    private WebElement acceptTransfetButton;

    @FindBy(xpath = "(//dt[text()='Transfer from:']/following-sibling::dd)[1]")
    private WebElement fromAgentName;

    @FindBy(xpath = "//dt[text()='Note:']/following-sibling::dd")
    private WebElement transferNotes;

    @FindBy(css = "div.client-name-text")
    private WebElement clientName;

    @FindBy(css = "div.chat-context")
    private WebElement clientMessage;

    public void acceptTransfer(){
        acceptTransfetButton.click();
    }

    public String getTransferNotes(){
        // ToDo: update timeout after it is provided in System timeouts confluence page
        try {
            waitForElementToBeVisibleAgent(transferNotes, 10, "second agent");
            return transferNotes.getText();
        } catch (TimeoutException e){
            Assert.assertTrue(false, "Transfer notes are not visible.\n Please see the screenshot");
            return "no text notes";
        }
    }

    public String getClientName(){
        return  clientName.getText();
    }

    public String getClientMessage(){
        return  clientMessage.getText();
    }

    public String getFromAgentName(){
        return  fromAgentName.getText();
    }
}