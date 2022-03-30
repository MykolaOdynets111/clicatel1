package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import apihelper.ApiHelperTie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;

@FindBy(css = "[selenium-id=touch-pop-up]")
public class IncomingTransferWindow extends AbstractUIElement {

    @FindBy(css = "[selenium-id=touch-header-title]")
    private WebElement transferWindowHeader;

    @FindBy(xpath = ".//button[text()='Accept']")
    private WebElement acceptTransferButton;

    @FindBy(xpath = ".//button[text()='Reject']")
    private WebElement rejectTransfetButton;

    @FindBy(css = "[selenium-id=touch-transfer-from]")
    private WebElement fromAgentName;

    @FindBy(css = "[selenium-id=touch-transfer-note]")
    private WebElement transferNotes;

    @FindBy(css = "[selenium-id=touch-client-name]")
    private WebElement clientName;

    @FindBy(css = ".cl-transfer-chat-info__latest-message")
    private WebElement clientMessage;

    @FindBy(css = ".cl-r-transfer-source")
    private WebElement rejectedBy;

    @FindBy(css = ".cl-r-avatar.cl-r-avatar--medium")
    private WebElement transferPicture;

    @FindBy(css = ".cl-transfer-chat-info__context svg")
    private WebElement transferChannel;

    @FindBy(css = "[selenium-id=touch-icons-info] svg")
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
        // ToDo: update timeout after it is provided in System timeouts confluence page
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
        return getAttributeFromElem(this.getCurrentDriver(), transferSentiment, 5, "Channel icon", "data-selenium-id")
                .contains(expSentiment.toLowerCase());
    }

    public boolean isRigthSideTransferChatWindow() {
        int xcordElement = transferWindowHeader.getLocation().getX();
        int xcordWindow = findElemByXPATH(this.getCurrentDriver(),"//body").getSize().getWidth()/2;
        return    xcordElement > xcordWindow;
    }

}
