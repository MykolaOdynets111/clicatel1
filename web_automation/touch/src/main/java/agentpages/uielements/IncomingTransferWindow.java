package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;

@FindBy(css = "div.touch-pop-up")
public class IncomingTransferWindow extends AbstractUIElement {

    @FindBy(css = "div.touch-header h2")
    private WebElement transferWindowHeader;

    @FindBy(xpath = "//button[text()='Accept transfer']")
    private WebElement acceptTransferButton;

    @FindBy(xpath = "//button[text()='Accept']")
    private WebElement acceptRejectedButton;

    @FindBy(xpath = "//button[text()='Reject transfer']")
    private WebElement rejectTransfetButton;

    @FindBy(xpath = "(//dt[text()='Transfer from:']/following-sibling::dd)[1]")
    private WebElement fromAgentName;

    @FindBy(xpath = "//dt[text()='Note:']/following-sibling::dd")
    private WebElement transferNotes;

    @FindBy(css = "div.client-name-text")
    private WebElement clientName;

    @FindBy(css = "div.chat-context")
    private WebElement clientMessage;

    @FindBy(css = "dl.dl-horizontal")
    private WebElement rejectedBy;

    @FindBy(xpath = "//span[@class='profile-icon']")
    private WebElement transferPicture;

    @FindBy(xpath = "//div[@class='icons']/span/*")
    private WebElement transferChannel;

    @FindBy(xpath = "//div/div[@class='icons']/span[contains(@class,'icon icon-')]")
    private WebElement transferSentiment;

    public void acceptTransfer(){
        acceptTransferButton.click();
    }

    public void rejectTransfer(){
        rejectTransfetButton.click();
    }

    public void acceptRejectTransfer(){
        clickElem(this.getCurrentDriver(), acceptRejectedButton, 3, "'Accept' rejected transfer button");
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
        return getTextFromElem(this.getCurrentDriver(), transferWindowHeader, 2,"Transfer chat window header");
    }

    public boolean isTransferWindowHeaderNotShown(){
        return isElementRemoved(this.getCurrentDriver(), transferWindowHeader,2);
    }

    public String getRejectedBy(){
        return getTextFromElem(this.getCurrentDriver(),rejectedBy, 2,"Transfer chat window header");
    }

    public boolean isValidImgTransferPicture(String userName) {
        isElementShown(this.getCurrentDriver(), transferPicture, 5);
        return transferPicture.getText().equalsIgnoreCase(String.valueOf(userName.charAt(0)));
    }

    public boolean isValidImTransferChannel() {
        File image = new File(System.getProperty("user.dir")+"/touch/src/test/resources/transferchatimg/transferChannel.png");
        return isWebElementEqualsImage(this.getCurrentDriver(), transferChannel, image);
    }

    public boolean isValidImgTransferSentiment() {
        File image = new File(System.getProperty("user.dir")+"/touch/src/test/resources/transferchatimg/transferSentiment.png");
        return isWebElementEqualsImage(this.getCurrentDriver(), transferSentiment, image);
    }

    public boolean isRigthSideTransferChatWindow() {
        int xcordElement = transferWindowHeader.getLocation().getX();
        int xcordWindow = findElemByXPATH(this.getCurrentDriver(),"//body").getSize().getWidth()/2;
        return    xcordElement > xcordWindow;
    }

}
