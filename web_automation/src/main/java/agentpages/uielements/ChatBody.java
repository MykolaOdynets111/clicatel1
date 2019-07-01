package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import drivermanager.DriverFactory;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.*;
import java.util.stream.Collectors;

@FindBy(css = "div.chat-body")
public class ChatBody extends AbstractUIElement {

    private String scrollElement = "div.chat-body";

    private String fromUserMessagesXPATH = "//li[@class='from']//span[text()='%s']";

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

    private WebElement getFromUserWebElement(String messageText) {
        try {
            AgentDeskChatMessage theMessage = fromUserMessages.stream().map(e -> new AgentDeskChatMessage(e))
                    .filter(e1 -> e1.getMessageText().equals(messageText))
                    .findFirst().get();
            return theMessage.getWrappedElement();
        } catch (NoSuchElementException e){
            Assert.assertTrue(false, "There is no such user message: "+messageText+"");
            return null;
        }
    }

    public boolean isUserMessageShown(String usrMessage) {
        try {

            waitForElementsToBeVisible(fromUserMessages, 15);
            for (int i = 0; i < 35; i++) {
                if (checkThatExpectedUserMessageOnAgentDesk(usrMessage)) {
                    return true;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return false;
        } catch(TimeoutException e1){
            return false;
        }
    }

    public boolean isUserMessageShown(String usrMessage, String agent) {
        try {
            waitForElementToBeVisibleByCssAgent(scrollElement, 5, agent);
        } catch(TimeoutException e){
            Assert.assertTrue(false, "Chat body is not visible");
        }
        String locator = String.format(fromUserMessagesXPATH, usrMessage);
        // ToDo: update timeout after it is provided in System timeouts confluence page
        // ToDo: If for social chatting timeout is bigger - introduce another method for social
        if(!isElementShownAgentByXpath(locator, 15, agent)){
            scrollToElem(DriverFactory.getDriverForAgent(agent), locator,
                    "'" +usrMessage + "' user message on chatdesk");
        }
        return isElementShownAgentByXpath(locator, 10, agent);
    }

    private boolean checkThatExpectedUserMessageOnAgentDesk(String usrMessage) {
        return findElemsByXPATHAgent(fromUserMessagesXPATH).stream()
                .map(AgentDeskChatMessage::new)
                .anyMatch(e2 -> e2.getMessageText().equalsIgnoreCase(usrMessage));
    }

    public boolean isMoreThanOneUserMassageShown() {
        if (fromUserMessages.size()>1){
            return true;
        } else{
            return false;
        }
    }

    public boolean isResponseOnUserMessageShown(String userMessage) {
        return new AgentDeskChatMessage(getFromUserWebElement(userMessage)).isToUserTextResponseShown(5);
    }

    public String getAgentEmojiResponseOnUserMessage(String userMessage) {
        return new AgentDeskChatMessage(getFromUserWebElement(userMessage))
                .getAgentResponseEmoji(this.currentAgent);
    }

    public boolean isToUserMessageShown(String userMessage){
        boolean result = false;
        for(int i = 0; i<3; i++){
            try {
                result =  findElemsByCSSAgent(toUserMessagesCSS).
                    stream().anyMatch(e -> e.getText().contains(userMessage));
            }catch(StaleElementReferenceException ex){
                waitFor(200);
                result =  findElemsByCSSAgent(toUserMessagesCSS).
                    stream().anyMatch(e -> e.getText().contains(userMessage));
            }
            if (result) break;
        }
        return result;
    }

    public List<String> getAllMessages(){
        return findElemsByXPATHAgent(messagesInChatBodyXPATH)
                    .stream().map(e -> new AgentDeskChatMessage(e))
                    .map(e -> e.getMessageInfo().replace("\n", " "))
                    .collect(Collectors.toList());

    }

    public String getTenantMsgColor() {
        return Color.fromString(toUserMessages.get(0).getCssValue("color")).asHex();
    }
}
