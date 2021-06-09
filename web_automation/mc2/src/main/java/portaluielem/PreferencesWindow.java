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

    @FindBy(css ="[name='agentInactivityTimeoutHours']")
    private WebElement agentInactivityTimeoutHours;

    @FindBy(css ="[name='agentInactivityTimeoutMins']")
    private WebElement agentInactivityTimeoutMinutes;

    @FindBy(xpath =".//input[@name='agentInactivityTimeoutHours']/following-sibling::div/div")
    private WebElement inactivityTimeoutLimitError;

    @FindBy(css ="[name='attachmentLifeTimeDays']")
    private WebElement attachmentLifeTimeDays;

    @FindBy(xpath=".//input[@name='attachmentLifeTimeDays']/following-sibling::div/div")
    private WebElement attachmentLifeTimeDaysLimitError;

    @FindBy(css = "[name='ticketTimeoutHours']")
    private WebElement ticketExpirationHours;

    @FindBy(css = "[name='globalInactivityTimeoutHours']")
    private WebElement globalInactivityTimeoutHours;

    @FindBy(css = "[name='pendingChatAutoClosureHours']")
    private WebElement pendingChatAutoClosureHours;

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

    public PreferencesWindow clickOnLiveChatRoating(){
        scrollAndClickElem(this.getCurrentDriver(), liveChatRoatingCheckbox, 5,"Live Chat Roating Checkbox");
        return this;
    }

    public PreferencesWindow setAgentInactivityTimeout(int hours, int minutes){
        waitForElementToBeVisible(this.getCurrentDriver(), agentInactivityTimeoutHours, 5);
        scrollToElem(this.getCurrentDriver(), agentInactivityTimeoutHours,"Inactivity Timeout Hours");
        inputText(this.getCurrentDriver(), agentInactivityTimeoutHours,1, "Inactivity Timeout Hours", String.valueOf(hours));
        inputText(this.getCurrentDriver(), agentInactivityTimeoutMinutes,1, "Inactivity Timeout Minutes", String.valueOf(minutes));
        return this;
    }

    public String getAgentChatTimeout(){
        waitForElementToBeVisible(this.getCurrentDriver(), agentInactivityTimeoutHours, 1);
        scrollToElem(this.getCurrentDriver(), agentInactivityTimeoutHours,"Inactivity Timeout Hours");
        return agentInactivityTimeoutHours.getAttribute("value") + "h "+ agentInactivityTimeoutMinutes.getAttribute("value") + "m";
    }

    public String getAgentInactivityTimeoutLimitError(){
        return getTextFromElem(this.getCurrentDriver(), inactivityTimeoutLimitError,1, "Inactivity timeout limit message").trim();
    }


    public void setAttachmentLifeTimeDays(int days){
        waitForElementToBeVisible(this.getCurrentDriver(), attachmentLifeTimeDays, 5);
        scrollToElem(this.getCurrentDriver(), attachmentLifeTimeDays,"Attachment Life Time Days");
        inputText(this.getCurrentDriver(), attachmentLifeTimeDays,1, "Attachment Life Time Days", String.valueOf(days));
    }


    public String getAttachmentLifeTimeDaysLimitError(){
        return getTextFromElem(this.getCurrentDriver(), attachmentLifeTimeDaysLimitError,1, "Attachment Life Time Days Limit Error").trim();
    }

    public String getTicketExpirationHours() {
        waitForElementToBeVisible(this.getCurrentDriver(), ticketExpirationHours, 1);
        scrollToElem(this.getCurrentDriver(), ticketExpirationHours, "Ticket Expiration Hours");
        return ticketExpirationHours.getAttribute("value");
    }

    public String getAttachmentLifeTimeDays() {
        waitForElementToBeVisible(this.getCurrentDriver(), attachmentLifeTimeDays, 1);
        scrollToElem(this.getCurrentDriver(), attachmentLifeTimeDays, "Media Files Expiration");
        return attachmentLifeTimeDays.getAttribute("value");
    }

    public String getInactivityTimeoutHours() {
        waitForElementToBeVisible(this.getCurrentDriver(), globalInactivityTimeoutHours, 1);
        scrollToElem(this.getCurrentDriver(), globalInactivityTimeoutHours, "Inactivity Timeout Hours");
        return globalInactivityTimeoutHours.getAttribute("value");
    }

    public String getPendingChatAutoClosureHours() {
        waitForElementToBeVisible(this.getCurrentDriver(), pendingChatAutoClosureHours, 1);
        scrollToElem(this.getCurrentDriver(), pendingChatAutoClosureHours, "Pending Chat Auto-Closure Hours");
        return pendingChatAutoClosureHours.getAttribute("value");
    }






}
