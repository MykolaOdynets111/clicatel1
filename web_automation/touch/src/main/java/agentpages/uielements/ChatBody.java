package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import apihelper.ApiHelper;
import datamanager.Tenants;
import org.apache.commons.lang3.Range;
import org.openqa.selenium.*;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@FindBy(css = "section.chat-body")
public class ChatBody extends AbstractUIElement {

    private String scrollElement = ".chat-body.chat-box__messages";

    private String fromUserMessagesXPATH = ".//li[contains(@class, 'from')]//*[text()='%s']";

    private String messagesInChatBodyXPATH = ".//ul[contains(@class, 'chat-container')]//li[not(@class='empty')]";

    @FindBy(css = "[selenium-id=empty-avatar]")
    private WebElement userProfileIcon;

    @FindBy(css = ".cl-r-message.msg-agent_message.to .cl-r-avatar__image")
    private WebElement agentImage;

    @FindBy(css = "li.from")
    private List<WebElement> fromUserMessages;

    @FindBy(css = "li.to .msg")
    private List<WebElement> toUserMessages;

    @FindBy(css = "[selenium-id=empty-avatar]")
    private WebElement agentIconWIthInitials;

    @FindBy(xpath = "//li[contains(@class, 'otp')]/div")
    private List<WebElement> otpDividersBlocks;

    @FindBy(xpath = "(//li[contains(@class, 'otp')]/div)[last()]")
    private WebElement lastOTPDividerBlock;

    @FindBy(css = "[selenium-id=chat-message-content-InputCardMessage]")
    private WebElement userInfoCard;

    @FindBy(css = ".rate-card-submit-message")
    private WebElement rateCard;

    @FindBy(css = ".rate-input label.checked input")
    private  WebElement selectedRate;

    @FindBy(css = ".rate-card-feedback-text")
    private  WebElement rateCardComment;

    @FindBy(css = ".from.file-msg")
    private WebElement attachmentMessage;

    @FindBy(css = "[selenium-id='chat-message-content-opted-out']")
    private WebElement stopCard;

    @FindBy(css = "[selenium-id='chat-message-content-opted-out'] p")
    private WebElement stopCardText;

    private WebElement getFromUserWebElement(String messageText) {
        try {
            AgentDeskChatMessage theMessage = fromUserMessages.stream().map(e -> new AgentDeskChatMessage(e).setCurrentDriver(this.getCurrentDriver()))
                    .filter(e1 -> e1.getMessageText().equals(messageText))
                    .findFirst().get();
            return theMessage.getWrappedElement();
        } catch (NoSuchElementException e){
            Assert.fail("There is no such user message: "+messageText+"");
            return null;
        }
    }

    public boolean isAttachmentMessageShown(){
        return wheelScrollDownAndIsDisplayed(this.getCurrentDriver(),
                this.getCurrentDriver().findElement(By.cssSelector(scrollElement)),
                attachmentMessage, 3);
       // return isElementShown(this.getCurrentDriver(), attachmentMessage, 3);
    }

    public ChatAttachment getAttachmentFile(){
        return  new ChatAttachment(attachmentMessage).setCurrentDriver(this.getCurrentDriver());
    }

    public String getPersonalInfoText(){
        return getTextFromElem(this.getCurrentDriver(), userInfoCard, 2, "Personal Info Card");
    }

    public boolean isUserMessageShown(String usrMessage) {
        try {
            waitForElementToBeVisibleByCss(this.getCurrentDriver(), scrollElement, 5);
        } catch(TimeoutException e){
            Assert.fail("Chat body is not visible");
        }
        String locator = String.format(fromUserMessagesXPATH, usrMessage);
        waitForElementToBePresentByXpath(this.getCurrentDriver(), locator, 8);
        WebElement message = this.getCurrentDriver().findElement(By.xpath(locator));

        return wheelScrollUpAndIsDisplayed(this.getCurrentDriver(), this.getCurrentDriver().findElement(By.cssSelector(scrollElement)),  message);
    }

    public boolean isMoreThanOneUserMassageShown() {
        return fromUserMessages.size() > 1;
    }

    public boolean isResponseOnUserMessageShown(String userMessage) {
        return new AgentDeskChatMessage(getFromUserWebElement(userMessage)).setCurrentDriver(this.getCurrentDriver())
                .isToUserTextResponseShown(5);
    }

    public String getAgentEmojiResponseOnUserMessage(String userMessage) {
        return new AgentDeskChatMessage(getFromUserWebElement(userMessage))
                .setCurrentDriver(this.getCurrentDriver())
                .getAgentResponseEmoji();
    }

    public boolean isToUserMessageShown(String userMessage){
        for(WebElement message: toUserMessages){
            if(message.getText().trim().contains(userMessage))return true;
        }
        return false;
    }

    public List<String> getAllMessages(){
        waitForElementsToBePresentByXpath(this.getCurrentDriver(), messagesInChatBodyXPATH, 5);
        return findElemsByXPATH(this.getCurrentDriver(), messagesInChatBodyXPATH)
                    .stream().map(e -> new AgentDeskChatMessage(e).setCurrentDriver(this.getCurrentDriver()))
                    .map(e -> e.getMessageInfo().replace("\n", " "))
                    .collect(Collectors.toList());
    }

    public boolean isOTPDividerDisplayed(){
        if (otpDividersBlocks.size() > 0) {
            return isElementShown(this.getCurrentDriver(), otpDividersBlocks.get(otpDividersBlocks.size() - 1), 3);
        }
        else
            return false;
    }

    public String getLastOTPCode(){
        return getTextFromElem(this.getCurrentDriver(), lastOTPDividerBlock, 2, "Last OTP divider block")
                .substring(4, 8);
    }

    public boolean isNewOTPCodeDifferent(){
        String lastCode = getTextFromElem(this.getCurrentDriver(), (otpDividersBlocks.get(otpDividersBlocks.size()-1)),
                3, "");
        String previousCode = getTextFromElem(this.getCurrentDriver(), (otpDividersBlocks.get(otpDividersBlocks.size()-2)),
                3, "");
        return !lastCode.equals(previousCode);
    }

    public boolean verifyMessagesPosition(){
        Rectangle rect = this.getWrappedElement().getRect();
        int chatBodyCenter = rect.getX() + rect.getWidth()/2;
        boolean agentMessageRight = toUserMessages.stream()
                .allMatch(e -> (e.getRect().getX()+e.getRect().getWidth()) > chatBodyCenter);
        boolean chatMessageLeft = fromUserMessages .stream()
                .allMatch(e -> e.getLocation().getX() < chatBodyCenter);

        return agentMessageRight && chatMessageLeft;
    }

    public boolean verifyAgentMessageColours(){
        String primaryHex = ApiHelper.getTenantInfo(Tenants.getTenantUnderTestOrgName())
                .getBody().jsonPath().getString("tenantProperties.value[0]");
        String primaryColorRgb = Color.fromString("#"+primaryHex).asRgb();
        int expRrbSum = calculateRgbSum(primaryColorRgb);
        Range<Integer> expRange = Range.between(expRrbSum-2, expRrbSum+2);

        boolean agentMessageColor = toUserMessages.stream()
                .map(e -> Color.fromString(e.getCssValue("background-color")).asRgb())
                .map(this::calculateRgbSum)
                .allMatch(expRange::contains);

        String agentIconRgb = Color.fromString(agentIconWIthInitials.getCssValue("background-color")).asRgb();
        boolean agentIconColor = expRange.contains(calculateRgbSum(agentIconRgb));

        return agentMessageColor && agentIconColor;
    }

    private int calculateRgbSum(String rgb){
        return Arrays.stream(rgb.split("\\(")[1].replace(")", "").split(","))
                .map(String::trim).mapToInt(Integer::parseInt).sum();
    }

    public boolean isValidDefaultUserProfileIcon() {
        File image = new File(System.getProperty("user.dir")+"/touch/src/test/resources/profileicons/user_default.png");
        Boolean isValidIcon =  isWebElementEqualsImage(this.getCurrentDriver(), userProfileIcon, image);
        if (!isValidIcon){
            File image2 = new File(System.getProperty("user.dir")+"/touch/src/test/resources/profileicons/user_default_with_line.png");
            isValidIcon =  isWebElementEqualsImage(this.getCurrentDriver(), userProfileIcon, image2);
        }
        return isValidIcon;
    }

    public boolean isValidAgentAvatarIsShown() {
        File image = new File(System.getProperty("user.dir")+"/touch/src/test/resources/agentphoto/chatAgentIcon.png");
        Boolean isValidIcon =  isWebElementEqualsImage(this.getCurrentDriver(), agentImage, image);
        return isValidIcon;
    }



    public boolean isRateCardShown(){
        return isElementShown(this.getCurrentDriver(), rateCard, 3);
    }

    public String getRate(){
        return selectedRate.getAttribute("value");
    }

    public String getRateCardComment(){
       return getTextFromElem(this.getCurrentDriver(), rateCardComment, 1, "Comment in rate Card");
    }

    public boolean isStopCardShown(){
        return isElementShown(this.getCurrentDriver(), stopCard, 3);
    }

    public String getStopCardText(){
        return getTextFromElem(this.getCurrentDriver(), stopCardText, 1, "Stop Card text").trim();
    }

}
