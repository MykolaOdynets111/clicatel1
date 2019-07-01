package portalpages.uielements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@FindBy(css = "div.automated-messages.chat-desk")
public class ChatDeskWindow extends BasePortalWindow {

    @FindBy(xpath = "//input[@ng-model='maxChatsPerAgent']")
    private WebElement chatsAvailable;

    @FindBy(xpath = "//span[contains(@class,'invalid-agent-seats')]")
    private WebElement chatsErrorMessage;

    @FindBy(xpath = "//button[contains(@class,'plus')]")
    private WebElement chatsPlus;

    @FindBy(xpath = "//button[contains(@class,'minus')]")
    private WebElement chatsMinus;

    @FindBy(xpath = "//fieldset[contains(@ng-model,'chatConclusion')]//button")
    private WebElement toggleChatConclusion;

    @FindBy(xpath = "//fieldset[contains(@ng-model,'autoScheduling')]//button")
    private WebElement toggleAutoScheduler;

    public void setChatsAvailable(String chats){
        waitForElementToBeVisibleAgent(chatsAvailable, 5, "admin");
        chatsAvailable.clear();
        chatsAvailable.sendKeys(chats);
    }

    public String getChatsAvailable(){
        waitForElementToBeVisibleAgent(chatsAvailable, 5, "admin");
        return chatsAvailable.getAttribute("value");
    }

    public boolean isErrorMessageShown(){
       return isElementShownAgent(chatsErrorMessage,5,"admin");
    }

    public void clickChatsPlus(int times){
        waitForElementToBeVisibleAgent(chatsPlus, 5, "admin");
        for (int i = 0; i<times;i++){
        chatsPlus.click();
        }
        waitForElementToBeVisibleAgent(chatsAvailable, 5, "admin");
    }

    public void clickChatsMinus(int times){
        waitForElementToBeVisibleAgent(chatsMinus, 5, "admin");
        for (int i = 0; i<times;i++){
            chatsMinus.click();
        }
        waitForElementToBeVisibleAgent(chatsAvailable, 5, "admin");
    }

    public void clickOnOffChatConclusion(){
        clickElemAgent(toggleChatConclusion, 5, "admin", "Chat conclusion toggle");
    }

    public void clickOnOffAutoScheduler(){
        clickElemAgent(toggleAutoScheduler, 5, "admin", "Auto scheduler toggle");
    }
}
