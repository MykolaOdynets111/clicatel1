package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.*;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@FindBy(css = ".cl-app-chat-header")
public class ChatHeader extends AbstractUIElement {

    @FindAll({
            @FindBy(css = "[data-testid=header-exit-chat]"),
            @FindBy(css = "[selenium-id='header-exit-chat']") //old locator
    })
    private WebElement endChatButton;

    @FindAll({
            @FindBy(css = "[data-testid=header-toggle-flag]"),
            @FindBy(xpath = "//div[text()='Flag chat']")
    })
    private WebElement flagChatButton;

    @FindAll({
            @FindBy(css = "[data-testid=header-toggle-flag]"),
            @FindBy(css = "cl-chat-header-btn-label")
    })
    private WebElement unflagChatButton;

    @FindAll({
            @FindBy(css = "[data-testid=header-transfer-chat]"),
            @FindBy(css = ".cl-r-icon-transfer")
    })
    private WebElement transferButton;

    @FindBy(css = "[data-testid=header-transfer-chat]")
    private WebElement reassignButton;

    @FindBy(css = "[data-testid=chat-header-title]")
    private WebElement chatHeaderTitle;

    @FindBy(css = "[data-testid=chat-header-channel-icon]")
    private WebElement channelImg;

    @FindBy(xpath = ".//span[@class= 'cl-r-chat-header-time'][1]")
    private WebElement timeStamp;

    @FindBy(css = ".cl-chat-header-time")
    private WebElement dateTime;

    @FindBy(css = "[data-testid=header-transfer-chat]")
    private WebElement cancelTransferButton;

    @FindBy(css = ".cl-r-avatar")
    private WebElement userAvatar;

    @FindBy(xpath = "//div[text()='Flag ON']")
    private WebElement flagOnIcon;

    @FindBy(css =".cl-transfer-history__agent-name")
    private WebElement agentName;

    @FindAll({
            @FindBy(css = "[selenium-id='header-toggle-pending']"),
            @FindBy(css = "button[data-testid='header-toggle-pending']")
    })
    private WebElement pendingButton;

    @FindBy(css = "svg[name = '3-dot-menu-vert']")
    private WebElement threeDotsVerticalMenu;

    @FindBy(xpath = "//button[contains(text(), 'Assign' )]")
    private WebElement assignButton;

    @FindBy(xpath = "//div[@class='tippy-content']/div")
    private WebElement flaggedCloseChatToolTip;

    @FindBy(xpath = ".//button[@name = 'Close_Ticket']")
    private WebElement closeTicket;

    private final String transferChatButtonXpath =  ".//button[@selenium-id='header-transfer-chat']";
    private final String sendSMSXpath = ".//button[@selenium-id='header-send-sms']";
    private final String sendWhatsAppXpath = ".//button[text()='Send WhatsApp']";

    public ChatHeader (WebDriver current){
        this.currentDriver = current;
    }

    public ChatHeader (){
        super();
    }

    public void clickEndChatButton() {
        if (!isEndChatShown()) {
            Assert.fail("'End chat' button is not shown.");
        } else {
            clickElem(this.getCurrentDriver(), endChatButton, 6, "End chat button");
        }
    }
    public void hoverEndChatButton() {
            hoverElem(this.getCurrentDriver(), endChatButton, 4, "End chat button");
    }

    public boolean isEndChatShown(){
        return isElementShown(this.getCurrentDriver(), endChatButton,4);

    }

    public void clickTransferButton(){
        clickElem(this.getCurrentDriver(), transferButton, 5, "Transfer button");
        waitForAngularToBeReady(this.getCurrentDriver());
        waitForAngularRequestsToFinish(this.getCurrentDriver());
    }

    //when create this object by constuctor then elements is not initialized and appears NullPointerException
    //that's why we should this method
    public void clickTransferButtonByXpath(){
        clickElemByXpath(this.getCurrentDriver(), transferChatButtonXpath, 5, "Transfer button");
        waitForAngularToBeReady(this.getCurrentDriver());
        waitForAngularRequestsToFinish(this.getCurrentDriver());
    }

    public boolean isButtonEnabled(String buttonTitle){
        try {
            switch (buttonTitle) {
                case "Transfer chat":
                    waitForElementToBeVisibleByXpath(this.getCurrentDriver(), transferChatButtonXpath, 5);
                    return findElemByXPATH(this.getCurrentDriver(), transferChatButtonXpath).isEnabled();
                case "Send SMS":
                    waitForElementToBeVisibleByXpath(this.getCurrentDriver(), sendSMSXpath, 5);
                    return findElemByXPATH(this.getCurrentDriver(), sendSMSXpath).isEnabled();
                case "Send WhatsApp":
                    waitForElementToBeVisibleByXpath(this.getCurrentDriver(), sendWhatsAppXpath, 5);
                    return findElemByXPATH(this.getCurrentDriver(), sendWhatsAppXpath).isEnabled();

                default:
                    throw new NoSuchElementException("Button '" + buttonTitle + "' wasn't found");
            }
        }catch (TimeoutException e){
            return false;
        }
    }

    public boolean isButtonDisabled(String buttonTitle){
        try {
            switch (buttonTitle) {
                case "Transfer chat":
                    return isElementDisabledByXpath(this.getCurrentDriver(), transferChatButtonXpath, 5);
                case "Send SMS":
                    return isElementDisabledByXpath(this.getCurrentDriver(), sendSMSXpath, 5);
                case "Send WhatsApp":
                    return isElementDisabledByXpath(this.getCurrentDriver(), sendWhatsAppXpath, 5);
                default:
                    throw new NoSuchElementException("Button '" + buttonTitle + "' wasn't found");
            }
        }catch (TimeoutException e){
            return false;
        }
    }

    public String getChatHeaderText(){
        return chatHeaderTitle.getText();
    }

    public  String getChatDateText(){return dateTime.getText();}

    public void clickFlagChatButton(){
        clickElem(this.getCurrentDriver(), flagChatButton, 2,"Flag chat");
    }

    public void clickPendingChatButton(){
        clickElem(this.getCurrentDriver(), pendingButton, 2,"Pending chat");
    }

    public void clickflagOnButton(){
        clickElem(this.getCurrentDriver(), unflagChatButton, 2, "Unflag chat");
    }

    public void clickCancelTransferButton(){
        clickElem(this.getCurrentDriver(), cancelTransferButton, 2, "Cancel transfer");
    }

    public String getEndChatButtonColor() {
        return Color.fromString(endChatButton.getCssValue("color")).asHex();
    }

    public String getTransferButtonColor() {
        return Color.fromString(transferButton.getCssValue("color")).asHex();
    }

    public String getPinChatButtonColor() {
        return  Color.fromString(flagChatButton.getCssValue("color")).asHex();
    }

    public boolean isValidChannelImg(String channelPictureName) {
        File image = new File(System.getProperty("user.dir") + "/src/test/resources/adaptericons/" + channelPictureName +".png");
        return isWebElementEqualsImage(this.getCurrentDriver(), channelImg, image);
    }

    public String getChatIconName() {
        return channelImg.findElement(By.cssSelector(" svg")).getAttribute("name").trim();
    }
    public String getTimeStamp() {
        return timeStamp.getAttribute("textContent").trim();
    }

    public String getTextHeader() {
        return  chatHeaderTitle.getText().toLowerCase();
    }

    public LocalDateTime getDateTime() {
        String stringDate =  getTextFromElem(this.getCurrentDriver(), dateTime, 5, "Date time in header");
        return LocalDateTime.parse(stringDate, DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm",  Locale.US));
    }

    public boolean isAvatarContainUserNameFirstLetter(String userName){
        String expectedInitials;
        List<String> userNames = Arrays.asList(userName.split(" "));
        if(userNames.size() == 2){
            expectedInitials = userNames.get(0).substring(0) + userNames.get(1).substring(0);
        } else {
            expectedInitials = userNames.get(0).substring(0,1);
        }
        String letter = getTextFromElem(this.getCurrentDriver(), userAvatar, 1,"Avatar");
        return letter.equalsIgnoreCase(expectedInitials);
    }

    public boolean isFlagOnButtonDisplayed() {
        return isElementShown(getCurrentDriver(), flagOnIcon, 5);
    }

    public String getAgentName(){
        return getTextFromElem(getCurrentDriver(),agentName,2,"Agent Name");
    }

    public void clickOnAssignButton() {
        clickElem(this.getCurrentDriver(), threeDotsVerticalMenu, 3, "Three Dots Vertical Menu");
        clickElem(this.getCurrentDriver(), assignButton, 3, "Assign Chat Button");
    }
    public String getFlaggedMessageText() {
        return getTextFromElem(this.getCurrentDriver(), flaggedCloseChatToolTip, 4, "Tool Tip for flagged chat");
    }
    public boolean isCloseChatClickable() {
        return isElementEnabled(this.getCurrentDriver(), endChatButton,4);
    }

    public boolean isTransferButtonDisplayed() {
        return isElementShown(getCurrentDriver(), transferButton, 5);
    }

    public boolean isReassignButtonDisplayed() {
        return isElementShown(getCurrentDriver(), reassignButton, 5);
    }

    public boolean isTransferButtonNotDisplayed() {
        return isElementRemoved(getCurrentDriver(), transferButton, 5);
    }

    public boolean getCloseButtonStatus(){
        return Boolean.parseBoolean(getAttributeFromElem(this.getCurrentDriver(), closeTicket, 5, "Close ticket button", "disabled"));
    }
}
