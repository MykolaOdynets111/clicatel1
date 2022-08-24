package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import apihelper.ApiHelperTie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;

@FindBy(css = "[data-testid='transfer-flyout']")
public class IncomingTransferWindow extends AbstractUIElement {

    @FindBy(css = "[data-testid='transfer-header-title']")
    private WebElement transferWindowHeader;

    @FindBy(xpath = ".//button[text()='Accept']")
    private WebElement acceptTransferButton;

    @FindBy(xpath = ".//button[text()='Reject']")
    private WebElement rejectTransfetButton;

    @FindBy(css = "[data-testid=touch-transfer-from]")
    private WebElement fromAgentName;

    @FindBy(css = "[data-testid=touch-transfer-note]")
    private WebElement transferNotes;

    @FindBy(css = "[data-testid=touch-client-name]")
    private WebElement clientName;

    @FindBy(css = ".cl-transfer-chat-info__latest-message")
    private WebElement clientMessage;

    @FindBy(css = ".cl-r-transfer-source")
    private WebElement rejectedBy;

    @FindBy(css = "[data-testid='avatar']")
    private WebElement transferPicture;

    @FindBy(css = ".cl-transfer-chat-info__context svg")
    private WebElement transferChannel;

    @FindBy(css = "[data-testid=touch-icons-info] svg")
    private WebElement transferSentiment;

    public void acceptTransfer(){
        acceptTransferButton.click();
    }

    public void rejectTransfer(){
        rejectTransfetButton.click();
    }

    public void acceptRejectTransfer(){
        clickElem(this.getCurrentDriver(), acceptTransferButton, 3, "'Accept' rejected transfer button");
    }

    public String getTransferNotes(){
        return getTextFromElem(this.getCurrentDriver(), transferNotes, 10,"Transfer notes");
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

    public String getTransferWindowHeader(){
        return getTextFromElem(this.getCurrentDriver(), transferWindowHeader, 6,"Transfer chat window header").trim();
    }

    public boolean isTransferWindowHeaderNotShown(){
        return isElementRemoved(this.getCurrentDriver(), transferWindowHeader,6);
    }

    public String getRejectedBy(){
        return getTextFromElem(this.getCurrentDriver(),rejectedBy, 2,"Transfer chat window header");
    }

    public boolean isValidImgTransferPicture(String userName) {
        isElementShown(this.getCurrentDriver(), transferPicture, 5);
        return transferPicture.getText().equalsIgnoreCase(String.valueOf(userName.charAt(0)));
    }

    public boolean isValidImTransferChannel(String channel) {
        File image = new File(System.getProperty("user.dir")+"/touch/src/test/resources/adaptericons/" + channel + ".png");
        return isWebElementEqualsImage(this.getCurrentDriver(), transferChannel, image);
    }

    public boolean isValidImgTransferSentiment(String userMessage) {
        String expSentiment = ApiHelperTie.getTIESentimentOnMessage(userMessage);
        return getAttributeFromElem(this.getCurrentDriver(), transferSentiment, 5, "Channel icon", "data-data-testid")
                .contains(expSentiment.toLowerCase());
    }

    public boolean isRigthSideTransferChatWindow() {
        int xcordElement = transferWindowHeader.getLocation().getX();
        int xcordWindow = findElemByXPATH(this.getCurrentDriver(),"//body").getSize().getWidth()/2;
        return    xcordElement > xcordWindow;
    }

}
