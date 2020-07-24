package portaluielem;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;


@FindBy(css = ".preferences-page")
public class PreferencesWindow extends BasePortalWindow {

    @FindBy(css = "[selenium-id='sessionsCapacity']")
    private WebElement chatsAvailable;

    @FindBy(xpath = ".//div[text() = 'You must specify a number']")
    private WebElement chatsErrorMessage;

    @FindBy(css = "[for='agent-note'] .cl-r-toggle-btn__label")
    private WebElement toggleChatConclusion;

    @FindBy(css = "[for='auto-scheduler'] .cl-r-toggle-btn__label")
    private WebElement toggleAutoScheduler;

    @FindBy(css = "[for = 'department-primary-status'] .cl-r-toggle-btn__label")
    private WebElement defaultDepartmentCheckbox;

    @FindBy(xpath = "//div[text()='Select department']/ancestor::div[contains(@class,'cl-r-select__control')]")
    private WebElement defaultDepartmentsDropdown;

    @FindBy(css =".cl-r-select__menu-list .cl-r-select__option")
    private List<WebElement> departments;

    @FindBy(css ="[for=last-agent-mode] div[class='cl-r-toggle-btn__label']")
    private WebElement liveChatRoatingCheckbox;

    public void setChatsAvailable(String chats){
        waitForElementToBeVisible(this.getCurrentDriver(), chatsAvailable, 5);
        scrollToElem(this.getCurrentDriver(), chatsAvailable,"Chat available");
        inputText(this.getCurrentDriver(), chatsAvailable,1, "Chat available", chats);
    }

    public String getChatsAvailable(){
        waitForElementToBeVisible(this.getCurrentDriver(), chatsAvailable, 5);
        scrollToElem(this.getCurrentDriver(), chatsAvailable,"Chat available");
        return chatsAvailable.getAttribute("value");
    }

    public boolean isErrorMessageShown(){
       return isElementShown(this.getCurrentDriver(), chatsErrorMessage,5);
    }


    public void clickOnOffChatConclusion(){
        scrollAndClickElem(this.getCurrentDriver(), toggleChatConclusion, 5, "Chat conclusion toggle");
    }

    public PreferencesWindow clickOnOffAutoScheduler(){
        scrollAndClickElem(this.getCurrentDriver(), toggleAutoScheduler, 5,"Auto scheduler toggle");
    return this;
    }

    public PreferencesWindow activateDefaultDepartmentCheckbox(){
        scrollAndClickElem(this.getCurrentDriver(), defaultDepartmentCheckbox, 5,"Default Department Checkbox");
        return this;
    }

    public void selectDefaultDepartment(String name){
        clickElem(this.getCurrentDriver(), defaultDepartmentsDropdown, 5,"Default Department Dropdown");
        waitForElementsToBeVisible(this.getCurrentDriver(), departments, 3);
        departments.stream().filter(a -> a.getText().contains(name))
                .findFirst().orElseThrow(() -> new AssertionError("Cannot find department: " + name)).click();
    }

    public PreferencesWindow activateLiveChatRoatingCheckbox(){
        scrollAndClickElem(this.getCurrentDriver(), liveChatRoatingCheckbox, 5,"Live Chat Roating Checkbox");
        return this;
    }

}
