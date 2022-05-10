package facebook.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = "[data-testid='Keycommand_wrapper_ModalLayer']")
public class MessengerWindow extends AbstractUIElement {

    private String inputFieldXPATHLocator = ".//div[@role='textbox']";
    private String toUserMessage = ".//div[text()='%s']//following::div[contains(text(), \"%s\")]";

    @FindBy(xpath = ".//div[@aria-label='Chat Settings']")
    private WebElement headerChatSettingsButton;

    @FindBy(xpath = "//span[text()='Delete conversation']")
    private WebElement deleteConversationButton;

    @FindBy(xpath = "//div[@aria-label='Delete Conversation' and @tabindex='0']")
    private WebElement confirmDeletingConversationButton;

    public void waitUntilLoaded() {
        waitForElementToBeVisible(this.getCurrentDriver(), this, 5);
    }

    public void enterMessage(String message){
        waitForElementToBeVisibleByXpath(this.getCurrentDriver(), inputFieldXPATHLocator, 6);
        findElemByXPATH(this.getCurrentDriver(), inputFieldXPATHLocator).sendKeys(message);
        pressEnterForWebElem(this.getCurrentDriver(),
                findElemByXPATH(this.getCurrentDriver(), inputFieldXPATHLocator), 5,
                "Input field");
    }

    public void deleteConversation(){
//        enterMessage("end");
        clickElem(this.getCurrentDriver(), headerChatSettingsButton, 1 ,"Chat Setting");
        clickElem(this.getCurrentDriver(), deleteConversationButton, 3 ,"Delete conversation");
        clickElem(this.getCurrentDriver(), confirmDeletingConversationButton, 3 ,"Delete conversation");
    }

    public boolean isExpectedToUserMessageShown(String userMessage, String expectedResponse, int wait) {
        String expectedElem = String.format(toUserMessage, userMessage, expectedResponse);
        try {
            waitForElementToBeVisibleByXpath(this.getCurrentDriver(), expectedElem, wait);
            return true;
        } catch(TimeoutException e){
            return false;
        }
    }

    public void waitForWelcomeMessage(String welcomeMessage, int wait){
        try{
            waitForElementToBeVisibleByXpath(this.getCurrentDriver(),"//div[text()=\""+welcomeMessage+"\"]", wait);
        } catch (WebDriverException ignored){

        }
    }

}
