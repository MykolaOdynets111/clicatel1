package portaluielem;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;


@FindBy(css = ".cl-preferences-page")
public class PreferencesWindow extends BasePortalWindow {

    @FindBy(css = "[name='maxChatsPerAgent']")
    private WebElement chatsAvailable;

    @FindBy(css = ".cl-form-group__error-text")
    private WebElement chatsErrorMessageDecimalNumbers;

    @FindBy(css = "[for='agent-note'] .cl-r-toggle-btn__label")
    private WebElement toggleChatConclusion;

    @FindBy(css = "[for='auto-scheduler'] .cl-r-toggle-btn__label")
    private WebElement toggleAutoScheduler;

    @FindBy(xpath = ".//div[text()='Select department']/ancestor::div[contains(@class,'cl-select__control')]")
    private WebElement defaultDepartmentsDropdown;

    @FindBy(css = ".cl-select__menu-list .cl-select__option")
    private List<WebElement> departments;

    @FindBy(xpath = "//label[input[@name='lastAgentMode']]")
    private WebElement liveChatRoatingCheckbox;

    @FindBy(css = "[name='agentInactivityTimeoutHours']")
    private WebElement agentInactivityTimeoutHours;

    @FindBy(css = "[name='agentInactivityTimeoutMins']")
    private WebElement agentInactivityTimeoutMinutes;

    @FindBy(xpath = ".//input[@name='agentInactivityTimeoutHours']/following-sibling::div/div")
    private WebElement inactivityTimeoutLimitError;

    @FindBy(css = "[name='attachmentLifeTimeDays']")
    private WebElement attachmentLifeTimeDays;

    @FindBy(xpath = ".//input[@name='attachmentLifeTimeDays']/following-sibling::div/div")
    private WebElement attachmentLifeTimeDaysLimitError;

    @FindBy(css = "[name='ticketTimeoutHours']")
    private WebElement ticketExpirationHours;

    @FindBy(xpath = ".//input[@name='ticketTimeoutHours']/following-sibling::div/div")
    private WebElement ticketExpirationHoursLimitError;

    @FindBy(css = "[name='globalInactivityTimeoutHours']")
    private WebElement globalInactivityTimeoutHours;

    @FindBy(css = "[name='pendingChatAutoClosureTimeHours']")
    private WebElement pendingChatAutoClosureHours;

    @FindBy(css = ".cl-form-group__error-text")
    private WebElement pendingErrorMessageDecimalNumbers;

    private WebElement getToggleElementByName(String name) {
        return findElementByXpath(this.getCurrentDriver(), String.format(".//h3[text() = '" + name
                + "']/..//div[@class = 'cl-toggle__label']", name), 10);
    }

    public boolean verifyToggleIsChecked(String name, boolean status) {
        return getToggleElementByName(name).findElement(By.xpath("./../input"))
                .getAttribute("value").equalsIgnoreCase(String.valueOf(status));
    }

    public void activateToggle(String toggleName) {
        if (!verifyToggleIsChecked(toggleName, true)) {
            scrollAndClickElem(currentDriver, getToggleElementByName(toggleName), 10, toggleName);
        }
    }

    public void deactivateToggle(String toggleName) {
        if (!verifyToggleIsChecked(toggleName, false)) {
            scrollAndClickElem(currentDriver, getToggleElementByName(toggleName), 10, toggleName);
        }
    }

    public void setChatsAvailable(String chats) {
        waitForElementToBeVisible(this.getCurrentDriver(), chatsAvailable, 5);
        scrollToElem(this.getCurrentDriver(), chatsAvailable, "Chat available");
        chatsAvailable.clear();
        inputText(this.getCurrentDriver(), chatsAvailable, 1, "Chat available", chats);
    }

    public void setPendingChatAutoClosure(String pendingTime) {
        waitForElementToBeVisible(this.getCurrentDriver(), pendingChatAutoClosureHours, 5);
        scrollToElem(this.getCurrentDriver(), pendingChatAutoClosureHours, "Pending Auto closure time");
        pendingChatAutoClosureHours.clear();
        inputText(this.getCurrentDriver(), pendingChatAutoClosureHours, 1, "Pending Auto closure time", pendingTime);
    }

    public String getChatsAvailable() {
        waitForElementToBeVisible(this.getCurrentDriver(), chatsAvailable, 5);
        scrollToElem(this.getCurrentDriver(), chatsAvailable, "Chat available");
        return chatsAvailable.getAttribute("value");
    }

    public String errorMessageShown() {
        return pendingErrorMessageDecimalNumbers.getText();
    }


    public void clickOnOffChatConclusion() {
        scrollAndClickElem(this.getCurrentDriver(), toggleChatConclusion, 5, "Chat conclusion toggle");
    }

    public PreferencesWindow clickOnOffAutoScheduler() {
        scrollAndClickElem(this.getCurrentDriver(), toggleAutoScheduler, 5, "Auto scheduler toggle");
        return this;
    }

    public void selectDefaultDepartment(String name) {
        activateToggle("Route to Specific Departments");
        scrollAndClickElem(this.getCurrentDriver(), defaultDepartmentsDropdown, 5, "Default Department Dropdown");
        waitForElementsToBeVisible(this.getCurrentDriver(), departments, 3);
        departments.stream().filter(a -> a.getText().contains(name))
                .findFirst().orElseThrow(() -> new AssertionError("Cannot find department: " + name)).click();
    }

    public PreferencesWindow clickOnLiveChatRoating() {
        scrollAndClickElem(this.getCurrentDriver(), liveChatRoatingCheckbox, 5, "Live Chat Roating Checkbox");
        return this;
    }

    public PreferencesWindow setAgentInactivityTimeout(int hours, int minutes) {
        waitForElementToBeVisible(this.getCurrentDriver(), agentInactivityTimeoutHours, 5);
        scrollToElem(this.getCurrentDriver(), agentInactivityTimeoutHours, "Inactivity Timeout Hours");
        inputText(this.getCurrentDriver(), agentInactivityTimeoutHours, 1, "Inactivity Timeout Hours", String.valueOf(hours));
        inputText(this.getCurrentDriver(), agentInactivityTimeoutMinutes, 1, "Inactivity Timeout Minutes", String.valueOf(minutes));
        return this;
    }

    public String getAgentChatTimeout() {
        waitForElementToBeVisible(this.getCurrentDriver(), agentInactivityTimeoutHours, 1);
        scrollToElem(this.getCurrentDriver(), agentInactivityTimeoutHours, "Inactivity Timeout Hours");
        return agentInactivityTimeoutHours.getAttribute("value") + "h " + agentInactivityTimeoutMinutes.getAttribute("value") + "m";
    }

    public String getAgentInactivityTimeoutLimitError() {
        return getTextFromElem(this.getCurrentDriver(), inactivityTimeoutLimitError, 1, "Inactivity timeout limit message").trim();
    }

    public boolean isAgentInactivityTimeoutLimitErrorShown() {
        return isElementShown(this.getCurrentDriver(), inactivityTimeoutLimitError, 1);
    }


    public void setAttachmentLifeTimeDays(int days) {
        waitForElementToBeVisible(this.getCurrentDriver(), attachmentLifeTimeDays, 5);
        scrollToElem(this.getCurrentDriver(), attachmentLifeTimeDays, "Attachment Life Time Days");
        attachmentLifeTimeDays.clear();
        inputText(this.getCurrentDriver(), attachmentLifeTimeDays, 1, "Attachment Life Time Days", String.valueOf(days));
    }


    public String getAttachmentLifeTimeDaysLimitError() {
        return getTextFromElem(this.getCurrentDriver(), attachmentLifeTimeDaysLimitError, 1, "Attachment Life Time Days Limit Error").trim();
    }

    public boolean isAttachmentLifeTimeDaysLimitErrorShown() {
        return isElementShown(this.getCurrentDriver(), attachmentLifeTimeDaysLimitError, 1);
    }

    public PreferencesWindow setTicketExpirationHours(int hours) {
        waitForElementToBeVisible(this.getCurrentDriver(), ticketExpirationHours, 1);
        scrollToElem(this.getCurrentDriver(), ticketExpirationHours, "Ticket Expiration Hours");
        inputText(this.getCurrentDriver(), ticketExpirationHours, 1, "Ticket Expiration Hours", String.valueOf(hours));
        return this;
    }

    public String getTicketExpirationLimitError() {
        return getTextFromElem(this.getCurrentDriver(), ticketExpirationHoursLimitError, 1, "Ticket Expiration Hours Limit Error").trim();
    }

    public boolean isTicketExpirationLimitErrorShown() {
        return isElementShown(this.getCurrentDriver(), ticketExpirationHoursLimitError, 1);
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
        waitForElementToBeVisible(this.getCurrentDriver(), pendingChatAutoClosureHours, 10);
        scrollToElem(this.getCurrentDriver(), pendingChatAutoClosureHours, "Pending Chat Auto-Closure Hours");
        return pendingChatAutoClosureHours.getAttribute("value");
    }

    public void isErrorMessageShown(String errorMessage) {
        Assert.assertEquals(chatsErrorMessageDecimalNumbers.getText(), errorMessage);
    }
}
