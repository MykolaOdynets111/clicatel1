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


}
