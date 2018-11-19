package touch_pages.uielements;

import abstract_classes.AbstractUIElement;
import api_helper.ApiHelper;
import dataManager.VMQuoteRequestUserData;
import interfaces.WebActions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import touch_pages.uielements.messages.FromUserMessage;
import touch_pages.uielements.messages.ToUserMessageWithActions;
import touch_pages.uielements.messages.ToUserTextMessage;

import java.util.List;
import java.util.stream.Collectors;

@FindBy(css = "div.ctl-conversation-area")
public class WidgetConversationArea extends AbstractUIElement implements WebActions{

    @FindBy(css = "li.ctl-chat-message-container.message-to")
    private WebElement salutationElement;

    @FindBy(css = "li.ctl-chat-message-container.message-from")
    private List<WebElement> fromUserMessages;

    private String targetFromUserMessage = "//li[contains(@class, message-from)]//*[text()='%s']";

    private String targetTextInConversationArea = "//li[@class='ctl-chat-message-container message-to']//span[@class=' text-break-mod' and contains(text(), \"%s\")]";

    /*
    Getting the last (the newest) from user message in touch with searched text
     */
    private WebElement getFromUserWebElement(String messageText) {
        try {
             List<FromUserMessage> listWithUserMessages = fromUserMessages.stream().map(FromUserMessage::new)
                    .filter(e1 -> e1.getMessageText().equals(messageText))
                    .collect(Collectors.toList());
            FromUserMessage theMessage = listWithUserMessages.get(listWithUserMessages.size()-1);
            return theMessage.getWrappedElement();
        }catch (java.util.NoSuchElementException|ArrayIndexOutOfBoundsException e1){
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

    public boolean isTextResponseShownAmongOtherForUserMessage(String userInput, String expectedResponse) {
        return new ToUserTextMessage(getFromUserWebElement(userInput)).isTextResponseShownAmongOthers(expectedResponse);
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

    public String getCardAboveButtonsTextForUserMessage(String userMessageText) {
        return new ToUserMessageWithActions(getFromUserWebElement(userMessageText)).getTextFromAboveCardButton();
    }

    public boolean isCardButtonsShownFor(String userMessageText, List<String> buttons) {
        boolean result = false;
        for (String button : buttons) {
            result = new ToUserMessageWithActions(getFromUserWebElement(userMessageText)).isButtonShown(button.trim());
        }
        return result;
    }

    public boolean isCardContainsButton(String userMessageText, String button) {
        return new ToUserMessageWithActions(getFromUserWebElement(userMessageText)).isButtonShown(button.trim());
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

    public void submitCardWithUserInfo(String userMessageText, VMQuoteRequestUserData userData) {
        new ToUserMessageWithActions(getFromUserWebElement(userMessageText))
                                        .fillInInputFieldWithAPlaceholder("Last Name", userData.getLastName())
                                        .fillInInputFieldWithAPlaceholder("Contact Number", userData.getContactNumber())
                                        .fillInInputFieldWithAPlaceholder("Email", userData.getEmail())
                                        .clickButton("Submit");
    }

    public void clickSubmitButton(String userMessageText) {
        new ToUserMessageWithActions(getFromUserWebElement(userMessageText)).clickButton("Submit");
    }

    public void fillInTheField(String userMessageText, String field, String value) {
        new ToUserMessageWithActions(getFromUserWebElement(userMessageText))
                .fillInInputFieldWithAPlaceholder(field, value);
    }

    public int getNumberOfFieldRequiredErrorsInCardOnUserMessage(String userMessageText){
        return new ToUserMessageWithActions(getFromUserWebElement(userMessageText)).getNumberOfFieldRequiredErrors();
    }

    public boolean areFieldRequiredErrorsInCardOnUserMessageShown(String userMessageText){
        return new ToUserMessageWithActions(getFromUserWebElement(userMessageText)).areRequiredFieldErrorsShown();
    }

    public void provideInfoBeforeGoingToAgent(String userMessageText, String userName, String userPass) {
        new ToUserMessageWithActions(getFromUserWebElement(userMessageText))
                .fillInInputFieldWithAPlaceholder("Name", userName)
                .fillInInputFieldWithAPlaceholder("Email", userPass);
    }
}
