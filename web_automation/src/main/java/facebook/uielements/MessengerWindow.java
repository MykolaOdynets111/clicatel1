package facebook.uielements;

import abstractclasses.AbstractUIElement;
import apihelper.ApiHelper;
import drivermanager.DriverFactory;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(xpath = "//div[@class='fbNubFlyout fbDockChatTabFlyout uiContextualLayerParent']")
public class MessengerWindow extends AbstractUIElement {

    private String inputFieldXPATHLocator = "//div[@role='combobox']";
    private String toUserMessage = "//span[text()='%s']//following::span[contains(text(), \"%s\")]";

    @FindBy(xpath = "//div[contains(@class,'clearfix titlebar')]/div/div/div[2]/div[2]/div[3]")
    private WebElement headerGearButtonContainer;

    @FindBy(xpath = "//div[@data-testid='fanta_dropdown_menu_icon']/a")
    private WebElement gearButton;

    @FindBy(xpath = "//ul[@role='menu']")
    private WebElement popupMenu;

    @FindBy(xpath = "//div[@data-testid='fanta_dropdown_menu_delete_conversation']")
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
//        enterMessage("end");
        moveToElement(DriverFactory.getTouchDriverInstance(), headerGearButtonContainer);
        gearButton.click();
        if(!isElementShown(popupMenu,2)) gearButton.click();
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
