package touch_pages.uielements;

import abstract_classes.AbstractUIElement;
import com.github.javafaker.Faker;
import interfaces.WebActions;
import interfaces.WebWait;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import touch_pages.uielements.messages.FromUserMessage;
import touch_pages.uielements.messages.ToUserMessageWithActions;
import touch_pages.uielements.messages.ToUserTextMessage;

import java.util.List;

@FindBy(css = "div.ctl-conversation-area")
public class WidgetConversationArea extends AbstractUIElement implements WebActions{

    @FindBy(css = "li.ctl-chat-message-container.message-to")
    private WebElement salutationElement;

    @FindBy(css = "li.ctl-chat-message-container.message-from")
    private List<WebElement> fromUserMessages;

    private String targetFromUserMessage = "//li[contains(@class, message-from)]//*[text()='%s']";

    public String targetTextInConversationArea = "//li[@class='ctl-chat-message-container message-to']//span[@class=' text-break-mod' and contains(text(), \"%s\")]";

    private WebElement getFromUserWebElement(String messageText) {
        try {
            FromUserMessage theMessage = fromUserMessages.stream().map(e -> new FromUserMessage(e))
                    .filter(e1 -> e1.getMessageText().equals(messageText))
                    .findFirst().get();
            return theMessage.getWrappedElement();
        }catch (java.util.NoSuchElementException e){
            Assert.assertTrue(false,
                    "Expected user message \""+messageText+"\" is not shown in widget conversation area");
            return null;
        }
    }

    public String getResponseTextOnUserInput(String userMessageText) {
            return new ToUserTextMessage(getFromUserWebElement(userMessageText)).getMessageText();
    }

    public String getSecondResponseTextOnUserInput(String userMessageText) {
        return new ToUserTextMessage(getFromUserWebElement(userMessageText)).getSecondMessageText();
    }


    public boolean isTextResponseShownFor(String userMessageText, int wait) {
        return new ToUserTextMessage(getFromUserWebElement(userMessageText)).isTextResponseShown(wait);
    }

    public boolean isTextResponseShownAmongOtherForUserMessage(String userInput, String expectedRisponse) {
        return new ToUserTextMessage(getFromUserWebElement(userInput)).isTextResponseShownAmongOthers(expectedRisponse);
    }

    public boolean isOnlyOneTextResponseShownFor(String userMessage) {
        return new ToUserTextMessage(getFromUserWebElement(userMessage)).isOnlyOneTextResponseShwon();
    }

    public boolean isCardShownFor(String userMessageText, int wait) {
        return new ToUserMessageWithActions(getFromUserWebElement(userMessageText)).isTextInCardShown(wait);
    }

    public boolean isCardNotShownFor(String userMessageText, int wait) {
        return new ToUserMessageWithActions(getFromUserWebElement(userMessageText)).isCardNotShown(wait);
    }

    public String getCardTextForUserMessage(String userMessageText) {
        return new ToUserMessageWithActions(getFromUserWebElement(userMessageText)).getTextFromCard();
    }

    public boolean isCardButtonsShownFor(String userMessageText, List<String> buttons) {
        boolean result = false;
        for (String button : buttons) {
            result = new ToUserMessageWithActions(getFromUserWebElement(userMessageText)).isButtonShown(button.trim());
        }
        return result;
    }

    public void  waitForSalutation() {
        try {
            waitForElementToBeVisible(salutationElement, 10);
        } catch (TimeoutException e) {
        }
    }

    public void waitForMessageToAppearInWidget(String text){
        try {
            waitForElementToBeVisibleByXpath(String.format(targetFromUserMessage, text), 10);
        } catch (TimeoutException e) {
        }
    }

    public void clickOptionInTheCard(String userMessageText, String buttonName) {
        new ToUserMessageWithActions(getFromUserWebElement(userMessageText)).clickButton(buttonName);
    }

    public boolean isTextShown(String text, int wait){

        for (int i=0; i<wait*2; i++){
            try {
                findElemByXPATH(String.format(targetTextInConversationArea, text));
                return true;
            } catch (NoSuchElementException e){
                waitFor(500);
            }
        }
        return false;
    }

    public void submitCardWithUserInfo(String userMessageText) {
        Faker faker = new Faker();
        new ToUserMessageWithActions(getFromUserWebElement(userMessageText))
                                        .fillInInputFieldWithAPlaceholder("Last Name", "AQA")
                                        .fillInInputFieldWithAPlaceholder("Contact Number", faker.phoneNumber().cellPhone())
                                        .fillInInputFieldWithAPlaceholder("Email", "aqa_"+System.currentTimeMillis()+"@aqa.com")
                                        .clickButton("Submit");
int a=2;
    }
}
