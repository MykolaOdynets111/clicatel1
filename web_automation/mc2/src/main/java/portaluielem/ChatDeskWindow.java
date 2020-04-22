package portaluielem;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;


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

    @FindBy(css = "[ng-model=departmentPrimaryStatus] button")
    private WebElement defaultDepartmentCheckbox;

    @FindBy(xpath = "//span[@placeholder='Select default departments']/ancestor::div[@class='cl-multi-select-container']")
    private WebElement defaultDepartmentsDropdown;

    @FindBy(css =".option-text.ng-binding")
    private List<WebElement> departments;

    @FindBy(css ="[ng-model=lastAgentMode] button[class$='toggle-off']")
    private WebElement liveChatRoatingCheckbox;

    public void setChatsAvailable(String chats){
        waitForElementToBeVisible(this.getCurrentDriver(), chatsAvailable, 5);
        chatsAvailable.clear();
        chatsAvailable.sendKeys(chats);
    }

    public String getChatsAvailable(){
        waitForElementToBeVisible(this.getCurrentDriver(), chatsAvailable, 5);
        return chatsAvailable.getAttribute("value");
    }

    public boolean isErrorMessageShown(){
       return isElementShown(this.getCurrentDriver(), chatsErrorMessage,5);
    }

    public void clickChatsPlus(int times){
        waitForElementToBeVisible(this.getCurrentDriver(), chatsPlus, 5);
        for (int i = 0; i<times;i++){
        chatsPlus.click();
        }
        waitForElementToBeVisible(this.getCurrentDriver(), chatsAvailable, 5);
    }

    public void clickChatsMinus(int times){
        waitForElementToBeVisible(this.getCurrentDriver(), chatsMinus, 5);
        for (int i = 0; i<times;i++){
            chatsMinus.click();
        }
        waitForElementToBeVisible(this.getCurrentDriver(), chatsAvailable, 5);
    }

    public void clickOnOffChatConclusion(){
        clickElem(this.getCurrentDriver(), toggleChatConclusion, 5, "Chat conclusion toggle");
    }

    public void clickOnOffAutoScheduler(){
        clickElem(this.getCurrentDriver(), toggleAutoScheduler, 5,"Auto scheduler toggle");
    }

    public ChatDeskWindow activateDefaultDepartmentCheckbox(){
        if(!defaultDepartmentCheckbox.getAttribute("class").contains("active")){
            clickElem(this.getCurrentDriver(), defaultDepartmentCheckbox, 5,"Default Department Checkbox");
        }
        return this;
    }

    public void selectDefaultDepartment(String name){
        clickElem(this.getCurrentDriver(), defaultDepartmentsDropdown, 5,"Default Department Dropdown");
        departments.stream().filter(a -> a.getText().contains(name))
                .findFirst().orElseThrow(() -> new AssertionError("Cannot find department: " + name)).click();
    }

    public ChatDeskWindow activateLiveChatRoatingCheckbox(){
        clickElem(this.getCurrentDriver(), liveChatRoatingCheckbox, 5,"Live Chat Roating Checkbox");
        return this;
    }


}
