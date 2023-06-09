package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@FindBy(css = ".ReactModal__Content.ReactModal__Content--after-open.cl-modal")
public class TransferChatWindow extends AbstractUIElement {

    @FindBy(css = "[data-testid=transfer-chat-modal-cancel]")
    private WebElement cancelTransferButton;

    @FindBy(xpath = "//button[text()='Transfer']")
    private WebElement submitTransferChatButton;

    @FindBy(xpath = ".//div[contains(text(),'agent')]/parent::div/following-sibling::div")
    private WebElement openAgentDropdownButton;

    @FindBy(xpath = ".//div[contains(text(),'department')]/parent::div/following-sibling::div")
    private WebElement openDepartmentDropdownButton;

    @FindBy(xpath = " //div[contains(@class, 'cl-select__department')or contains(@class,'cl-select__option')]")
    private WebElement availableAgentOrDepartment;

    @FindBy(xpath = " //div[contains(@class, 'cl-select__department')or contains(@class,'cl-select__option')]")
    private List<WebElement> availableAgentsOrDepartmentsList;

    @FindBy(css = "[data-testid=transfer-chat-notes-textarea]")
    private WebElement noteInput;

    @FindBy(xpath = "//label[@for='agentsList']/following-sibling::div//div[@class='Select-placeholder']")
    private WebElement selectAgentPlaceholder;

    @FindBy(css = ".cl-r-form-group__error-text")
    private WebElement noteInputError;

    @FindBy(css = ".cl-loading-overlay")
    private WebElement loadingAvailableAgents;

    @FindBy(css = ".cl-r-icon-refresh")
    private WebElement refreshButton;

    @FindBy(css = "svg[name = close]")
    private WebElement closeButton;

    @FindBy(xpath = "//div[contains(text(),'agent')]/..//input")
    private WebElement agentsInput;

    public String transferChat(String agent) {
        openDropDownAgent();
        String agentSurname= getUserSurname(agent);
        String agentName = selectDropDownAgent(agentSurname);
        sentNote();
        if(isElementShown(this.getCurrentDriver(), selectAgentPlaceholder, 1)){
            clickElem(this.getCurrentDriver(), cancelTransferButton, 1,"Cancel transfer button");
            new ChatHeader(this.getCurrentDriver()).clickTransferButton();
            waitForUpdatingAvailableAgents();
            agentName = selectDropDownAgent(agentSurname);
            sentNote();
        }
        clickTransferChatButton();
        return agentName;
    }

    public String transferOvernightTicket(String agent) {
        openDropDownAgent();
        String agentSurname= getUserSurname(agent);
        String agentName = selectDropDownAgent(agentSurname);
        if(isElementShown(this.getCurrentDriver(), selectAgentPlaceholder, 1)){
            clickElem(this.getCurrentDriver(), cancelTransferButton, 1,"Cancel transfer button");
            new ChatHeader(this.getCurrentDriver()).clickTransferButton();
            waitForUpdatingAvailableAgents();
            agentName = selectDropDownAgent(agentSurname);
            sentNote();
        }
        clickTransferChatButton();
        return agentName;
    }

    private String getUserSurname(String agent){
        if(agent.contains("Second")){
            return "Main";
        }
        return "Second";
    }

    public void transferChatToDepartment(String departmentName){
        openDropDownDepartment();
        selectDepartmentFromDropDown(departmentName);
        clickTransferChatButton();
    }

    public boolean isTransferChatShown() {
        return isElementShown(this.getCurrentDriver(), submitTransferChatButton, 8);
    }

    public boolean isTransferChatEnabled() {
        return submitTransferChatButton.isEnabled();
    }

    public void openDropDownAgent() {
        clickElem(this.getCurrentDriver(), openAgentDropdownButton,5,"Open Agents drop down button");
    }

    public void openDropDownDepartment() {
        clickElem(this.getCurrentDriver(), openDepartmentDropdownButton,5,"Open Departments drop down button");
    }

    public void selectDepartmentFromDropDown(String departmentName) {
        for (int i=0; i < 3; i++) {
            if(isElementRemoved(this.getCurrentDriver(), availableAgentOrDepartment, 2))
                openDropDownDepartment();
            try {
                waitForElementToBeVisible(this.getCurrentDriver(), availableAgentOrDepartment,5);
            } catch (TimeoutException ignored){
            }

            if(availableAgentsOrDepartmentsList.size() > 0) {
                availableAgentsOrDepartmentsList.stream().filter(e -> e.getText().contains(departmentName)).findFirst().get().click();
                return;

            } else {
                if(isElementShown(this.getCurrentDriver(), refreshButton, 2)) {
                    clickElem(this.getCurrentDriver(), refreshButton, 1, "Refresh transfer pop-up");
                } else {
                    clickElem(this.getCurrentDriver(), cancelTransferButton, 1,"Cancel transfer button");
                    new ChatHeader(this.getCurrentDriver()).clickTransferButtonByXpath();
                }
                waitForUpdatingAvailableAgents();
            }
        }
        throw new AssertionError("Cannot find '" + departmentName + "' department from dropdown list");
    }

    public String selectDropDownAgent(String agentSurname) {
        for(int i=0; i<15; i++){
            if(!isElementShown(this.getCurrentDriver(), availableAgentOrDepartment, 2)) openAgentDropdownButton.click();
            waitForElementToBeVisible(this.getCurrentDriver(), availableAgentOrDepartment,5);
            if(availableAgentsOrDepartmentsList.size() >= 2) {
                WebElement currentAgent = availableAgentsOrDepartmentsList.stream().filter(e -> (e.getText().contains(agentSurname))).findFirst().get();
                String agentName = currentAgent.getText();
                executeJSclick(this.getCurrentDriver(), currentAgent);
                return agentName;
            }
            else waitFor(500);
        }
        new AssertionError("Agent for chat transferring is not shown");
        return null;
    }

    public List<String> getAvailableAgentsFromDropdown() {
        if(isElementRemoved(this.getCurrentDriver(), availableAgentOrDepartment, 2))
            clickElem(this.getCurrentDriver(), openAgentDropdownButton, 2, "Open agent dropdown");
        waitForElementToBeVisible(this.getCurrentDriver(), availableAgentOrDepartment,5);
        return availableAgentsOrDepartmentsList.stream()
                        .map(e -> getTextFromElem(this.getCurrentDriver(), e, 3, "Agent in dropdown"))
                        .filter(e -> !(e.contains("current chat assignment")))
                        .collect(Collectors.toList());
    }

    public String getCurrentAgentAssignment() {
        return getCurrentAgentFromAssignmentDropdown().getText();
    }

    public boolean isAssignedAgentDisabledToSelect(){
        return getCurrentAgentFromAssignmentDropdown().isEnabled();
    }

    private WebElement getCurrentAgentFromAssignmentDropdown(){
        if(isElementRemoved(this.getCurrentDriver(), availableAgentOrDepartment, 2))
            clickElem(this.getCurrentDriver(), openAgentDropdownButton, 2, "Open agent dropdown");
        waitForElementToBeVisible(this.getCurrentDriver(), availableAgentOrDepartment,5);
        return availableAgentsOrDepartmentsList.stream()
                .filter(e -> e.getText().contains("current chat assignment")).findFirst().orElseThrow(() -> new AssertionError("Cannot find the current agent assignment in dropdown"));
    }

    public void clickTransferChatButton() {
        waitForElementToBeVisible(this.getCurrentDriver(), submitTransferChatButton,5);
        executeJSclick(this.getCurrentDriver(), submitTransferChatButton);
    }

    public void sentNote() {
        try {
            waitForElementToBeVisible(this.getCurrentDriver(), noteInput, 5);
            noteInput.sendKeys("Please take care of this one");
        }catch (TimeoutException e){
            Assert.fail("Notes field is not shown");
        }
    }

    public String getNoteInputColor() {
     return   noteInput.getCssValue("border-color");
    }

    public boolean isNoteShown(){
        return isElementShown(this.getCurrentDriver(), noteInput, 5);
    }

    public String getNoteInputErrorText(){
        return getTextFromElem(this.getCurrentDriver(), noteInputError, 3, "Required notes error");
    }

    public void waitForUpdatingAvailableAgents() {
        waitForAppearAndDisappear(this.getCurrentDriver(), loadingAvailableAgents, 1, 5);
    }

    public void clickCloseButton() {
        clickElem(this.getCurrentDriver(), closeButton, 2, "Close button");
    }

    public TransferChatWindow selectAgent(String agentName){
        inputText(this.getCurrentDriver(), agentsInput, 2, "Agents input", agentName);

        availableAgentsOrDepartmentsList.stream()
                .filter(e -> e.getText().contains(agentName))
                .findFirst().orElseThrow(() -> new NoSuchElementException("There is no such agent: " + agentName))
                .click();
        return this;
    }

    public List<String> getOnlineAgentsFromDropdown() {
        return getAvailableAgentsFromDropdown().stream()
                .filter(a -> !a.contains("offline"))
                .collect(Collectors.toList());
    }
}
