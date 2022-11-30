package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import apihelper.ApiHelper;
import datamanager.Tenants;
import org.apache.commons.lang3.Range;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@FindBy(css = "section.chat-body")
public class ChatBody extends AbstractUIElement {

    private final String scrollElement = ".chat-body.chat-box__messages";

    private final String fromUserMessagesXPATH = ".//div[contains(@class,'from')]//*[text()='%s']";

    private final String messagesInChatBodyXPATH = ".//div[contains(@data-testid, 'chat-message-content-PlainMessage')]";

    private final String messagesInChatBodyLinkXPATH = ".//div[contains(@data-testid, 'chat-message-content-PlainMessage')]//a";

    private final String messagesInChatBodyHistoryXPATH = ".//div[contains(@data-testid, 'history-detail')]//div[contains(@data-testid, 'chat-message-content-PlainMessage')]";

    @FindBy(css = ".spinner")
    private WebElement spinner;

    @FindBy(css = ".channel-separator div")
    private WebElement visualIndicator;

    @FindBy(xpath = ".//li[contains(@class, 'from')]/div[contains(@class, 'avatar')]")
    private WebElement userProfileIcon;

    @FindBy(css = ".cl-r-message.msg-agent_message.to .cl-r-avatar__image")
    private WebElement agentImage;

    @FindBy(css = "div.from")
    private List<WebElement> fromUserMessages;

    @FindBy(css = "div.to .msg")
    private List<WebElement> toUserMessages;

    @FindBy(css = "div.to .msg > span")
    private List<WebElement> toUserMessagesEmoji;

    @FindBy(css = "div.from .msg > span")
    private List<WebElement> fromUserMessagesEmoji;
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
    private WebElement selectedRate;

    @FindBy(css = ".rate-card-feedback-text")
    private WebElement rateCardComment;

    @FindBy(css = ".msg-MediaMessage")
    private WebElement attachmentMessage;

    @FindBy(css = "[selenium-id='chat-message-content-opted-out']")
    private WebElement stopCard;

    @FindBy(css = "[selenium-id='chat-message-content-opted-out'] p")
    private WebElement stopCardText;

    @FindBy(css ="div[title='AGENT_RECEIVE_CHAT']")
    private WebElement agentReceiveIndicator;

    @FindBy(css = "div[title='FLAG_CHAT']")
    private WebElement agentFlagIndicator;

    @FindBy(css = "div[title='UNFLAG_CHAT']")
    private WebElement agentUnflagIndicator;

    @FindBy(css = "div[title='CLOSE_CHAT']")
    private WebElement agentCloseChatIndicator;

    @FindBy(css = "div[title='INVITE_AGENT']")
    private WebElement transferIndicator;

    @FindBy(css = "div[title='AGENT_REJECT_CHAT']")
    private WebElement rejectTransferIndicator;
    @FindAll({
            @FindBy(css = "[selenium-id='map-chat-message-content-LocationMessage']"),
            @FindBy(css = "[data-testid='map-chat-message-content-LocationMessage']")
    })
    private WebElement locationHREFFromAgent;

    @FindBy(css = "[data-testid='map-chat-message-content-LocationMessage']")
    private WebElement locationHREFFormUser;

    @FindBy(xpath = ".//div[@class='channel-separator-title mb-2 mt-2']")
    private List<WebElement> channelSeparators;

    @FindBy(css=".cl-c2p-event-message-title")
    private WebElement paymentLink_c2p_text;

    @FindBy (css = "[data-testid='card']")
    private WebElement c2pCard;

    @FindBy (css = ".cl-extension-item")
    private WebElement extensionItem;

    @FindBy(css=".cl-c2p-message-footer-cancel-payment-text")
    private WebElement cancelPaymentButton;

    public List<String> getChanelSeparatorsText() {
        return channelSeparators.stream().map(e->e.getText()).collect(Collectors.toList());
    }

    public String getLocationURLFromAgent() {
        return getAttributeFromElem(this.getCurrentDriver(), locationHREFFromAgent,10, "Location href", "href");
    }

    public String getLocationURLFromUser() {
        return getAttributeFromElem(this.getCurrentDriver(), locationHREFFormUser,5, "Location href", "href");
    }

    public String getC2pPaymentCardsText(){
        return getTextFromElem(this.getCurrentDriver(), paymentLink_c2p_text, 5,"Expire c2p text");
    }

    public String getC2pCardsText(){
        return getTextFromElem(this.getCurrentDriver(), c2pCard, 5,"c2p text");
    }

    public String getExtensionCardText(){
        return getTextFromElem(this.getCurrentDriver(), extensionItem, 5,"Extension Card text");
    }

    public boolean isHSMShownForAgent(){
        return isElementExistsInDOMCss(this.getCurrentDriver(),"[data-testid='chat-message-content-HsmMessage']",4);
    }

    private WebElement getFromUserWebElement(String messageText) {
        try {
            AgentDeskChatMessage theMessage = fromUserMessages.stream().map(e -> new AgentDeskChatMessage(e).setCurrentDriver(this.getCurrentDriver()))
                    .filter(e1 -> e1.getMessageText().equals(messageText))
                    .findFirst().get();
            return theMessage.getWrappedElement();
        } catch (NoSuchElementException e) {
            Assert.fail("There is no such user message: " + messageText + "");
            return null;
        }
    }

    public boolean isAttachmentMessageShown() {
        return wheelScrollDownAndIsDisplayed(this.getCurrentDriver(),
                this.getCurrentDriver().findElement(By.cssSelector(scrollElement)),
                attachmentMessage, 10);
        // return isElementShown(this.getCurrentDriver(), attachmentMessage, 3);
    }

    public ChatAttachment getAttachmentFile() {
        return new ChatAttachment(attachmentMessage).setCurrentDriver(this.getCurrentDriver());
    }

    public String getPersonalInfoText() {
        return getTextFromElem(this.getCurrentDriver(), userInfoCard, 2, "Personal Info Card");
    }

    public boolean isUserMessageShown(String usrMessage) {
        try {
            waitForElementToBeVisibleByCss(this.getCurrentDriver(), scrollElement, 10);
        } catch (TimeoutException e) {
            Assert.fail("Chat body is not visible");
        }
        waitForAppearAndDisappear(this.getCurrentDriver(), spinner, 1, 10);
        String locator = String.format(fromUserMessagesXPATH, usrMessage);
        waitForElementToBePresentByXpath(this.getCurrentDriver(), locator, 10);
        WebElement message = this.getCurrentDriver().findElement(By.xpath(locator));
        if (isElementExistsInDOMXpath(this.getCurrentDriver(), locator, 3)) {
            return true;
        }
        return wheelScrollUpAndIsDisplayed(this.getCurrentDriver(), this.getCurrentDriver().findElement(By.cssSelector(scrollElement)), message);
    }

    public boolean isMoreThanOneUserMassageShown() {
        return fromUserMessages.size() > 1;
    }

    public boolean isResponseOnUserMessageShown(String userMessage) {
        return new AgentDeskChatMessage(getFromUserWebElement(userMessage)).setCurrentDriver(this.getCurrentDriver())
                .isToUserTextResponseShown(5);
    }

    public boolean getAgentEmojiResponseOnUserMessage(String userMessage) {
        for (int i = toUserMessagesEmoji.size() - 1; i >= 0; i--) {
            scrollToElem(this.getCurrentDriver(), toUserMessagesEmoji.get(i), "User agent message element");

            if (toUserMessagesEmoji.get(i).getAttribute("aria-label").trim().contains(userMessage)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAgentEmojiUserMessageShown(String userMessage) {
        for (int i = fromUserMessagesEmoji.size() - 1; i >= 0; i--) {
            scrollToElem(this.getCurrentDriver(), fromUserMessagesEmoji.get(i), "User chat message element");

            if (fromUserMessagesEmoji.get(i).getAttribute("aria-label").trim().contains(userMessage)) {
                return true;
            }
        }
        return false;
    }

    public boolean isToUserMessageShown(String userMessage) {
        for (int i = toUserMessages.size() - 1; i >= 0; i--) {
            wheelScrollUpToElement(this.getCurrentDriver(),
                    this.getCurrentDriver().findElement(By.cssSelector(scrollElement)),
                    toUserMessages.get(i), 1);

            if (toUserMessages.get(i).getText().trim().contains(userMessage)) {
                wheelScroll(this.getCurrentDriver(),
                        this.getCurrentDriver().findElement(By.cssSelector(scrollElement)),
                        2000, 0, 0);
                return true;
            }
        }
        return false;
    }

    public List<String> getAllMessages() {
        List<String> allMessages = new ArrayList<>();
        waitForElementsToBePresentByXpath(this.getCurrentDriver(), messagesInChatBodyXPATH, 10);
        findElemsByXPATH(this.getCurrentDriver(), messagesInChatBodyXPATH)
                .stream().forEach(e -> {
                    allMessages.add(e.getText());
                });
        return allMessages;
    }

    public ChatBody clickLatestLinkMessage(String text) {
        clickElem(this.getCurrentDriver(), findElemByXPATH(this.getCurrentDriver(), messagesInChatBodyLinkXPATH), 10, "Latest user chat message");
        return this;
    }

    public List<String> getHistoryMessages() {
        List<String> historyMessages = new ArrayList<>();

        waitForElementsToBePresentByXpath(this.getCurrentDriver(), messagesInChatBodyHistoryXPATH, 10);
        findElemsByXPATH(this.getCurrentDriver(), messagesInChatBodyHistoryXPATH)
                .stream().forEach(e -> {
                    historyMessages.add(e.getText());
                });
        return historyMessages;
    }

    public boolean isOTPDividerDisplayed() {
        if (otpDividersBlocks.size() > 0) {
            return isElementShown(this.getCurrentDriver(), otpDividersBlocks.get(otpDividersBlocks.size() - 1), 3);
        } else
            return false;
    }

    public String getLastOTPCode() {
        return getTextFromElem(this.getCurrentDriver(), lastOTPDividerBlock, 2, "Last OTP divider block")
                .substring(4, 8);
    }

    public boolean isNewOTPCodeDifferent() {
        String lastCode = getTextFromElem(this.getCurrentDriver(), (otpDividersBlocks.get(otpDividersBlocks.size() - 1)),
                3, "");
        String previousCode = getTextFromElem(this.getCurrentDriver(), (otpDividersBlocks.get(otpDividersBlocks.size() - 2)),
                3, "");
        return !lastCode.equals(previousCode);
    }

    public boolean verifyMessagesPosition() {
        Rectangle rect = this.getWrappedElement().getRect();
        int chatBodyCenter = rect.getX() + rect.getWidth() / 2;
        boolean agentMessageRight = toUserMessages.stream()
                .allMatch(e -> (e.getRect().getX() + e.getRect().getWidth()) > chatBodyCenter);
        boolean chatMessageLeft = fromUserMessages.stream()
                .allMatch(e -> e.getLocation().getX() < chatBodyCenter);

        return agentMessageRight && chatMessageLeft;
    }

    public boolean verifyAgentMessageColours() {
        String primaryHex = ApiHelper.getTenantInfo(Tenants.getTenantUnderTestOrgName())
                .getBody().jsonPath().getString("tenantProperties.value[0]");
        String primaryColorRgb = Color.fromString("#" + primaryHex).asRgb();
        int expRrbSum = calculateRgbSum(primaryColorRgb);
        Range<Integer> expRange = Range.between(expRrbSum - 2, expRrbSum + 2);

        boolean agentMessageColor = toUserMessages.stream()
                .map(e -> Color.fromString(e.getCssValue("background-color")).asRgb())
                .map(this::calculateRgbSum)
                .allMatch(expRange::contains);

        String agentIconRgb = Color.fromString(agentIconWIthInitials.getCssValue("background-color")).asRgb();
        boolean agentIconColor = expRange.contains(calculateRgbSum(agentIconRgb));

        return agentMessageColor && agentIconColor;
    }

    private int calculateRgbSum(String rgb) {
        return Arrays.stream(rgb.split("\\(")[1].replace(")", "").split(","))
                .map(String::trim).mapToInt(Integer::parseInt).sum();
    }

    public String isValidDefaultUserProfileIcon() {
        wheelScrollUpToElement(this.getCurrentDriver(), this.getWrappedElement(), userProfileIcon, 0);
        return getTextFromElem(this.getCurrentDriver(), userProfileIcon, 1,"User Initial Icon").toUpperCase();
    }

    public boolean isValidAgentAvatarIsShown() {
        File image = new File(System.getProperty("user.dir") + "/touch/src/test/resources/agentphoto/chatAgentIcon.png");
        Boolean isValidIcon = isWebElementEqualsImage(this.getCurrentDriver(), agentImage, image);
        return isValidIcon;
    }

    public boolean isRateCardShown() {
        return isElementShown(this.getCurrentDriver(), rateCard, 3);
    }

    public String getRate() {
        return selectedRate.getAttribute("value");
    }

    public String getRateCardComment() {
        return getTextFromElem(this.getCurrentDriver(), rateCardComment, 1, "Comment in rate Card");
    }

    public boolean isStopCardShown() {
        return isElementShown(this.getCurrentDriver(), stopCard, 3);
    }

    public String getStopCardText() {
        return getTextFromElem(this.getCurrentDriver(), stopCardText, 1, "Stop Card text").trim();
    }

    public String getIndicatorsText(String indicator) {
        switch (indicator) {
            case "AGENT_RECEIVE_CHAT":
                return getTextFromElem(this.getCurrentDriver(), agentReceiveIndicator, 1, indicator);
            case "FLAG_CHAT":
                return getTextFromElem(this.getCurrentDriver(), agentFlagIndicator, 1, indicator);
            case "UNFLAG_CHAT":
                return getTextFromElem(this.getCurrentDriver(), agentUnflagIndicator, 1, indicator);
            case "CLOSE_CHAT":
                return getTextFromElem(this.getCurrentDriver(), agentCloseChatIndicator, 1, indicator);
            case "INVITE_AGENT":
                return  getTextFromElem(this.getCurrentDriver(), transferIndicator, 1, indicator);
            case "AGENT_REJECT_CHAT":
                return  getTextFromElem(this.getCurrentDriver(), rejectTransferIndicator, 1, indicator);
        }
        return "Incorrect indicator was provided in steps";
    }

    public void clickCancelPaymentButton(){
        clickElem(this.getCurrentDriver(), cancelPaymentButton, 10, "Cancel Payment button");
    }

    public boolean isVisualIndicatorTextShown(int wait, String visualIndicatorText) {
        for (int i = 0; i < wait; i++) {
            if (getTextFromElem(this.getCurrentDriver(), visualIndicator, 2, "visual Indicator")
                    .contains(visualIndicatorText)) return true;
            waitFor(1000);
        }
        return false;
    }
}
