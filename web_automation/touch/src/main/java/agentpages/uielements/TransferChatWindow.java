package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

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

    @FindBy(css = "[class^='cl-r-select__option']")
    private WebElement availableAgent;

    @FindBy(css = ".cl-r-select__option")
    private List<WebElement> availableAgentList;

    @FindBy(xpath = ".//div[@class='Select-control']")
    private WebElement dropDown;

    private String openedMenu = "div.Select-menu-outer";

    @FindBy(css = "[selenium-id=transfer-chat-notes-textarea]")
    private WebElement noteInput;

    @FindBy(xpath = "//label[@for='agentsList']/following-sibling::div//div[@class='Select-placeholder']")
    private WebElement selectAgentPlaceholder;

    @FindBy(css = "[selenium-id=textarea-input-error]")
    private WebElement noteInputError;

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
        return isElementShown(this.getCurrentDriver(), submitTransferChatButton, 5);
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

    public void selectDepartmentFromDropDown(String departmentName){
        if(!isElementShown(this.getCurrentDriver(), availableAgent, 2)) openDepartmentDropdownButton.click();
        waitForElementToBeVisible(this.getCurrentDriver(), availableAgent,5);
        WebElement currentAgent = availableAgentList.stream().filter(e -> e.getText().contains(departmentName)).findFirst().
                orElseThrow(() -> new AssertionError("Cannot find '" + departmentName + "' department from dropdown list"));
        currentAgent.click();
    }

    public String selectDropDownAgent() {
        if(!isElementShown(this.getCurrentDriver(), availableAgent, 2)) openAgentDropdownButton.click();
        waitForElementToBeVisible(this.getCurrentDriver(), availableAgent,5);
        for(int i=0; i<10; i++){
            if(availableAgentList.size() >= 2) {
                WebElement currentAgent = availableAgentList.stream().filter(e -> !(e.getText().toUpperCase().contains("current chat assignment"))).findFirst().get();
                String agentName = currentAgent.getText();
                executeJSclick(this.getCurrentDriver(), currentAgent);
                return agentName;
            }
            else waitFor(500);
        }
        new AssertionError("Agent for chat transferring is not shown");
        return null;
    }

    public String getTextDropDownMessage() {
        return getTextFromElem(this.getCurrentDriver(), availableAgent,6,"Drop down menu");
    }

    public boolean isAssignedAgentDisabledToSelect(){
        return availableAgent.getAttribute("class").contains("disabled");
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
}
