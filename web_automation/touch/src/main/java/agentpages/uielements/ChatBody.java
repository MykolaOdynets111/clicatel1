package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import driverfactory.DriverFactory;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@FindBy(css = "div.chat-body")
public class ChatBody extends AbstractUIElement {

    private String scrollElement = "div.chat-body";

    private String fromUserMessagesXPATH = "//li[contains(@class, 'from')]//span[text()='%s']";

    private String messagesInChatBodyXPATH = "//ul[@class='chat-container']//li[not(@class='empty')]";

    private String toUserMessagesCSS = "li.to";

    private String agentIconWIthInitialsCSS = "li.to div.empty-icon";

    private String currentAgent;

    public void setCurrentAgent(String agent){
        this.currentAgent = agent;
    }

    @FindBy(css = "li.from")
    private List<WebElement> fromUserMessages;

    @FindBy(css = "li.to")
    private List<WebElement> toUserMessages;

    @FindBy(css = "li.to div.empty-icon")
    private WebElement agentIconWIthInitials;

    @FindBy(xpath = "//li[contains(@class, 'otp')]/div")
    private List<WebElement> otpDividersBlocks;

    @FindBy(xpath = "(//li[contains(@class, 'otp')]/div)[last()]")
    private WebElement lastOTPDividerBlock;

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

        public boolean isUserMessageShown(String usrMessage) {
        try {
            waitForElementToBeVisibleByCss(this.getCurrentDriver(), scrollElement, 5);
        } catch(TimeoutException e){
            Assert.fail("Chat body is not visible");
        }
        String locator = String.format(fromUserMessagesXPATH, usrMessage);
        // ToDo: update timeout after it is provided in System timeouts confluence page
        // ToDo: If for social chatting timeout is bigger - introduce another method for social
        if(!isElementShownByXpath(this.getCurrentDriver(), locator, 15)){
            scrollToElem(this.getCurrentDriver(), locator,
                    "'" +usrMessage + "' user message on chatdesk");
        }
        return isElementShownByXpath(this.getCurrentDriver(), locator, 10);
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
        boolean result = false;
        for(int i = 0; i<3; i++){
            try {
                result =  findElemsByCSS(this.getCurrentDriver(), toUserMessagesCSS).
                    stream().anyMatch(e -> e.getText().contains(userMessage));
            }catch(StaleElementReferenceException ex){
                waitFor(200);
                result =  findElemsByCSS(this.getCurrentDriver(), toUserMessagesCSS).
                    stream().anyMatch(e -> e.getText().contains(userMessage));
            }
            if (result) break;
        }
        return result;
    }

    public List<String> getAllMessages(){
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

    public String getTenantMsgColor() {
        return Color.fromString(toUserMessages.get(0).getCssValue("color")).asHex();
    }
}
