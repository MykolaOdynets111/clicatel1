package touchpages.uielements;

import abstractclasses.AbstractUIElement;
import apihelper.ApiHelper;
import datamanager.Tenants;
import datamanager.VMQuoteRequestUserData;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import touchpages.uielements.messages.FromUserMessage;
import touchpages.uielements.messages.ToUserMessageWithActions;
import touchpages.uielements.messages.ToUserTextMessage;

import java.util.List;
import java.util.stream.Collectors;

@FindBy(css = "div.ctl-conversation-area")
public class WidgetConversationArea extends AbstractUIElement {

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
             List<FromUserMessage> listWithUserMessages = fromUserMessages.stream()
                     .map(e -> new FromUserMessage(e).setCurrentDriver(this.getCurrentDriver()))
                    .filter(e1 -> e1.getMessageText().equals(messageText))
                    .collect(Collectors.toList());
            FromUserMessage theMessage = listWithUserMessages.get(listWithUserMessages.size()-1);
            return theMessage.getWrappedElement();
        }catch (java.util.NoSuchElementException|ArrayIndexOutOfBoundsException e1){
            Assert.fail("Expected user message \""+messageText+"\" is not shown in widget conversation area");
            return null;
        }
    }

    public String getResponseTextOnUserInput(String userMessageText) {
            return new ToUserTextMessage(getFromUserWebElement(userMessageText))
                    .setCurrentDriver(this.getCurrentDriver()).getMessageText();
    }

    public String getSecondResponseTextOnUserInput(String userMessageText) {
        return new ToUserTextMessage(getFromUserWebElement(userMessageText))
                .setCurrentDriver(this.getCurrentDriver()).getSecondMessageText();
    }


    public boolean isTextResponseShownFor(String userMessageText, int wait) {
        return new ToUserTextMessage(getFromUserWebElement(userMessageText))
                .setCurrentDriver(this.getCurrentDriver()).isTextResponseShown(wait);
    }

    public boolean isSecondTextResponseShownFor(String userMessageText, int wait) {
        return new ToUserTextMessage(getFromUserWebElement(userMessageText))
                .setCurrentDriver(this.getCurrentDriver()).isSecondResponseShown(wait);
    }

    public boolean isSecondTextResponseNotShownFor(String userMessageText, int wait) {
        return new ToUserTextMessage(getFromUserWebElement(userMessageText))
                .setCurrentDriver(this.getCurrentDriver()).isSecondResponseNotShown(wait);
    }

    public boolean isTextResponseShownAmongOtherForUserMessage(String userInput, String expectedResponse) {
        return new ToUserTextMessage(getFromUserWebElement(userInput))
                .setCurrentDriver(this.getCurrentDriver()).isTextResponseShownAmongOthers(expectedResponse);
    }

    public boolean isTextResponseNotShownAmongOther(String userInput, String expectedResponse, int secWait) {
        return new ToUserTextMessage(getFromUserWebElement(userInput))
                .setCurrentDriver(this.getCurrentDriver()).isTextResponseNotShownAmongOthers(expectedResponse, secWait);
    }


    public boolean isOnlyOneTextResponseShownFor(String userMessage) {
        return new ToUserTextMessage(getFromUserWebElement(userMessage))
                .setCurrentDriver(this.getCurrentDriver()).isOnlyOneTextResponseShwon();
    }

    public boolean isCardShownFor(String userMessageText, int wait) {
        return new ToUserMessageWithActions(getFromUserWebElement(userMessageText))
                .setCurrentDriver(this.getCurrentDriver()).isTextInCardShown(wait);
    }

    public boolean isCardNotShownFor(String userMessageText, int wait) {
        return new ToUserMessageWithActions(getFromUserWebElement(userMessageText))
                .setCurrentDriver(this.getCurrentDriver()).isCardNotShown(wait);
    }

    public String getCardTextForUserMessage(String userMessageText) {
        return new ToUserMessageWithActions(getFromUserWebElement(userMessageText))
                .setCurrentDriver(this.getCurrentDriver()).getTextFromCard();
    }

    public String getCardAboveButtonsTextForUserMessage(String userMessageText) {
        return new ToUserMessageWithActions(getFromUserWebElement(userMessageText))
                .setCurrentDriver(this.getCurrentDriver()).getTextFromAboveCardButton();
    }

    public boolean checkIfCardButtonsShownFor(String userMessageText, List<String> buttons) {
        boolean result = false;
        for (String button : buttons) {
            result = new ToUserMessageWithActions(getFromUserWebElement(userMessageText))
                    .setCurrentDriver(this.getCurrentDriver()).isButtonShown(button.trim());
            if(!result){
                Assert.fail("Button '"+button+"' is not shown in the card on '"+userMessageText+"' user message");
            }
        }
        return result;
    }

    public boolean isCardContainsButton(String userMessageText, String button) {
        return new ToUserMessageWithActions(getFromUserWebElement(userMessageText))
                .setCurrentDriver(this.getCurrentDriver()).isButtonShown(button.trim());
    }

    public void  waitForSalutation() {
        try {
            ApiHelper.getTenantConfig(Tenants.getTenantUnderTestOrgName()); // need to add this call because backend before
            // showing welcome_message calls this API and
            // it sometimes take longer time
        }catch(java.util.NoSuchElementException|NullPointerException|AssertionError e){
            // Added in case there is no agent added (for e.g., Virgin Money tenant)
        }
        try {
            waitForElementToBeVisible(this.getCurrentDriver(), salutationElement, 10);
        } catch (TimeoutException e) {
        }
    }

    public void waitForMessageToAppearInWidget(String text){
        // ToDo: update timeout after it is provided in System timeouts confluence page
        try {
            waitForElementToBeVisibleByXpath(this.getCurrentDriver(), String.format(targetFromUserMessage, text), 10);
        } catch (TimeoutException e) {
        }
    }

    public void clickOptionInTheCard(String userMessageText, String buttonName) {
        try {
            new ToUserMessageWithActions(getFromUserWebElement(userMessageText)).setCurrentDriver(this.getCurrentDriver())
                    .clickButton(buttonName);
        }catch (ElementClickInterceptedException e){
            waitFor(1000);
            new ToUserMessageWithActions(getFromUserWebElement(userMessageText))
                    .setCurrentDriver(this.getCurrentDriver()).clickButton(buttonName);
        }
    }

    public boolean isTextShown(String text, int wait){

        for (int i=0; i<wait*2; i++){
            try {
                findElemByXPATH(this.getCurrentDriver(), String.format(targetTextInConversationArea, text));
                return true;
            } catch (NoSuchElementException e){
                waitFor(500);
            }
        }
        return false;
    }

    public void submitCardWithUserInfo(String userMessageText, VMQuoteRequestUserData userData) {
        new ToUserMessageWithActions(getFromUserWebElement(userMessageText))
                                        .setCurrentDriver(this.getCurrentDriver())
                                        .fillInInputFieldWithAPlaceholder("Last Name", userData.getLastName())
                                        .fillInInputFieldWithAPlaceholder("Contact Number", userData.getContactNumber())
                                        .fillInInputFieldWithAPlaceholder("Email", userData.getEmail())
                                        .clickButton("Submit");
    }

    public void clickSubmitButton(String userMessageText) {
        new ToUserMessageWithActions(getFromUserWebElement(userMessageText))
                .setCurrentDriver(this.getCurrentDriver()).clickButton("Submit");
    }

    public void fillInTheField(String userMessageText, String field, String value) {
        new ToUserMessageWithActions(getFromUserWebElement(userMessageText))
                .setCurrentDriver(this.getCurrentDriver())
                .fillInInputFieldWithAPlaceholder(field, value);
    }

    public int getNumberOfFieldRequiredErrorsInCardOnUserMessage(String userMessageText){
        return new ToUserMessageWithActions(getFromUserWebElement(userMessageText))
                .setCurrentDriver(this.getCurrentDriver()).getNumberOfFieldRequiredErrors();
    }

    public boolean areFieldRequiredErrorsInCardOnUserMessageShown(String userMessageText){
        return new ToUserMessageWithActions(getFromUserWebElement(userMessageText))
                .setCurrentDriver(this.getCurrentDriver()).areRequiredFieldErrorsShown();
    }

    public void provideInfoBeforeGoingToAgent(String userMessageText, String userName, String userPass) {
        new ToUserMessageWithActions(getFromUserWebElement(userMessageText))
                .setCurrentDriver(this.getCurrentDriver())
                .fillInInputFieldWithAPlaceholder("Name", userName)
                .fillInInputFieldWithAPlaceholder("Email", userPass);
    }
}
