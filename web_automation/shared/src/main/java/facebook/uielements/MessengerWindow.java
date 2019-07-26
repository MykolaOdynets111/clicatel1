package facebook.uielements;

import abstractclasses.AbstractUIElement;
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
        moveToElement(this.getCurrentDriver(), headerGearButtonContainer);
        gearButton.click();
        if(!isElementShown(this.getCurrentDriver(), popupMenu,2)) gearButton.click();
        waitForElementToBeVisible(this.getCurrentDriver(), popupMenu, 9);
        deleteConversationButton.click();
        waitForElementToBeVisible(this.getCurrentDriver(), confirmDeletingConverstionButton, 9);
        confirmDeletingConverstionButton.click();
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
            waitForElementToBeVisibleByXpath(this.getCurrentDriver(),"//span[text()=\""+welcomeMessage+"\"]", wait);
        } catch (WebDriverException ignored){

        }
    }

}
