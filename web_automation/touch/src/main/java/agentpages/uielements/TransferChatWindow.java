package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;
import java.util.stream.Collectors;

@FindBy(css = ".ReactModal__Content.ReactModal__Content--after-open.cl-r-modal")
public class TransferChatWindow extends AbstractUIElement {

    @FindBy(css = "[selenium-id=transfer-chat-modal-cancel]")
    private WebElement cancelTransferButton;

    @FindBy(xpath = "//button[text()='Transfer']")
    private WebElement submitTransferChatButton;

    @FindBy(xpath = ".//div[contains(text(),'agent')]/parent::div/following-sibling::div")
    private WebElement openAgentDropdownButton;

    @FindBy(xpath = ".//div[contains(text(),'department')]/parent::div/following-sibling::div")
    private WebElement openDepartmentDropdownButton;

    @FindBy(xpath = "//div[contains(@class, 'cl-r-select__option')]")
    private WebElement availableAgentOrDepartment;

    @FindBy(xpath = "//div[contains(@class, 'cl-r-select__option')]")
    private List<WebElement> availableAgenOrDepartmenttList;

    @FindBy(xpath = ".//div[@class='Select-control']")
    private WebElement dropDown;

    private String openedMenu = "div.Select-menu-outer";

    @FindBy(css = "[selenium-id=transfer-chat-notes-textarea]")
    private WebElement noteInput;

    @FindBy(xpath = "//label[@for='agentsList']/following-sibling::div//div[@class='Select-placeholder']")
    private WebElement selectAgentPlaceholder;

    @FindBy(css = ".cl-r-form-group__error-text")
    private WebElement noteInputError;

    @FindBy(css = ".cl-loading-overlay")
    private WebElement loadingAvailableAgents;

    @FindBy(css = ".cl-r-icon-refresh")
    private WebElement refreshButton;

    private String noAvailableAgentsMessageXpath = "//*[@id='portal-placeholder']//div[text()='No available agents']";
    private String availableDepartmentsNamesCss = ".department-name";

    public TransferChatWindow (WebDriver current){
        this.currentDriver = current;
    }
    public TransferChatWindow (){
        super();
    }

    public String transferChat() {
        openDropDownAgent();
        String agentName = selectDropDownAgent();
        sentNote();
        if(isElementShown(this.getCurrentDriver(), selectAgentPlaceholder, 1)){
            clickElem(this.getCurrentDriver(), cancelTransferButton, 1,"Cancel transfer button");
            new ChatHeader(this.getCurrentDriver()).clickTransferButton();
            waitForUpdatingAvailableAgents();
            agentName = selectDropDownAgent();
            sentNote();
        }
        clickTransferChatButton();
        return agentName;
    }

    public String transferOvernightTicket() {
        openDropDownAgent();
        String agentName = selectDropDownAgent();
        if(isElementShown(this.getCurrentDriver(), selectAgentPlaceholder, 1)){
            clickElem(this.getCurrentDriver(), cancelTransferButton, 1,"Cancel transfer button");
            new ChatHeader(this.getCurrentDriver()).clickTransferButton();
            waitForUpdatingAvailableAgents();
            agentName = selectDropDownAgent();
            sentNote();
        }
        clickTransferChatButton();
        return agentName;
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

            List<WebElement> availableDepartments = findElemsByCSS(this.getCurrentDriver(), availableDepartmentsNamesCss).stream()
                    .filter(e -> getTextFromElem(getCurrentDriver(), e, 5, "Department from dropdown")
                            .contains(departmentName))
                    .collect(Collectors.toList());

            if(availableDepartments.size() > 0) {
                clickElem(this.getCurrentDriver(), availableDepartments.get(0), 5,
                        "Department from dropdown");
                return;
            } else {
                clickElem(this.getCurrentDriver(), refreshButton, 3, "Refresh transfer pop-up");
                waitForUpdatingAvailableAgents();
            }
        }
        throw new AssertionError("Cannot find '" + departmentName + "' department from dropdown list");
    }

    public String selectDropDownAgent() {
        for(int i=0; i<15; i++){
            if(!isElementShown(this.getCurrentDriver(), availableAgentOrDepartment, 2)) openAgentDropdownButton.click();
            waitForElementToBeVisible(this.getCurrentDriver(), availableAgentOrDepartment,5);
            if(availableAgenOrDepartmenttList.size() >= 2) {
                WebElement currentAgent = availableAgenOrDepartmenttList.stream().filter(e -> !(e.getText().contains("current chat assignment"))).findFirst().get();
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
        return availableAgenOrDepartmenttList.stream()
                        .map(e -> getTextFromElem(this.getCurrentDriver(), e, 3, "Agent in dropdown"))
                        .filter(e -> !(e.contains("current chat assignment")))
                        .collect(Collectors.toList());
    }

    public String getTextDropDownMessage() {
        return getTextFromElem(this.getCurrentDriver(), availableAgentOrDepartment,6,"Drop down menu");
    }

    public boolean isAssignedAgentDisabledToSelect(){
        return availableAgentOrDepartment.getAttribute("class").contains("disabled");
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

    public String getDropDownColor() {
        return  dropDown.getCssValue("border-color");
    }

    public boolean isNoteShown(){
        return isElementShown(this.getCurrentDriver(), noteInput, 5);
    }

    public String getNoteInputErrorText(){
        return getTextFromElem(this.getCurrentDriver(), noteInputError, 3, "Required notes error");
    }

    public boolean isNoAvailableAgentsDisplayed() {
        return isElementShown(this.getCurrentDriver(),
                findElemByXPATH(this.getCurrentDriver(), noAvailableAgentsMessageXpath),
                5);
    }

    public void waitForUpdatingAvailableAgents() {
        waitForAppearAndDisappear(this.getCurrentDriver(), loadingAvailableAgents, 1, 5);
    }
}
