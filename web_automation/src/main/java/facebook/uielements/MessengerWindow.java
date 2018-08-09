package facebook.uielements;

import abstract_classes.AbstractUIElement;
import api_helper.ApiHelper;
import driverManager.DriverFactory;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(xpath = "//a[@data-hover='tooltip'][@data-tooltip-position='above'][not(contains(@class, 'close button'))]//ancestor::div[@role='complementary']")
public class MessengerWindow extends AbstractUIElement {

    private String inputFieldXPATHLocator = "//div[@role='combobox']";
    private String toUserMessage = "//span[text()='%s']//following::span[text()=\"%s\"]";

    @FindBy(xpath = ".//div[@role='heading']/following-sibling::ul")
    private WebElement headerActionButonsContainer;

    @FindBy(xpath = ".//div[@role='heading']/following-sibling::ul//a[@aria-label='Options']")
    private WebElement gearIcon;

    @FindBy(xpath = "//ul[@role='menu']")
    private WebElement popupMenu;

    @FindBy(xpath = "//span[text()='Delete Conversation...']")
    private WebElement deleteConversationButton;

    @FindBy(xpath = "//a[text()='Delete Conversation']")
    private WebElement confirmDeletingConverstionButton;

    public void waitUntilLoaded() {
        waitForElementToBeVisible(this);
    }

    public void enterMessage(String message){
        waitForElementToBeVisibleByXpath(inputFieldXPATHLocator, 6);
        findElemByXPATH(inputFieldXPATHLocator).sendKeys(message);
        pressEnterForWebElem(findElemByXPATH(inputFieldXPATHLocator));
    }

    public void deleteConversation(){
//        moveToElement(DriverFactory.getTouchDriverInstance(),headerActionButonsContainer);
        gearIcon.click();
        gearIcon.click();
        waitForElementToBeVisible(popupMenu, 9);
        deleteConversationButton.click();
        waitForElementToBeVisible(confirmDeletingConverstionButton, 9);
        confirmDeletingConverstionButton.click();
    }

    public boolean isExpectedToUserMessageShown(String userMessage, String expectedResponse, int wait) {
        String expectedElem = String.format(toUserMessage, userMessage, expectedResponse);
        try {
            waitForElementToBeVisibleByXpath(expectedElem, wait);
            return true;
        } catch(TimeoutException e){
            return false;
        }
    }

    public void waitForWelcomeMessage(int wait){
        String welcomeMessage = ApiHelper.getTenantMessageText("welcome_message");
        try{
            waitForElementToBeVisibleByXpath("//span[text()=\""+welcomeMessage+"\"]", wait);
        } catch (WebDriverException e){

        }
    }

}
