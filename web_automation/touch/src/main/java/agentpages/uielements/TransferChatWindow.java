package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

@FindBy(css = "div.modal-content")
public class TransferChatWindow extends AbstractUIElement {

    @FindBy(xpath = ".//button[text()='Cancel']")
    private WebElement cancelTransferButton;

    @FindBy(xpath = ".//button[text()='Transfer chat']")
    private WebElement submitTransferChatButton;

    @FindBy(xpath = ".//label[@for='agentsList']/following-sibling::div//div[@class='Select-control']")
    private WebElement openDropdownButton;

    @FindBy(xpath = ".//div[@class='Select-menu-outer']")
    private WebElement availableAgent;

    @FindBy(xpath = ".//div[@class='Select-menu-outer']//div[contains(@class,'Select-option')]")
    private List<WebElement> availableAgentList;

    @FindBy(xpath = ".//div[@class='Select-control']")
    private WebElement dropDown;

    private String openedMenu = "div.Select-menu-outer";

    @FindBy(css = "textarea")
    private WebElement noteInput;

    @FindBy(xpath = "//label[@for='agentsList']/following-sibling::div//div[@class='Select-placeholder']")
    private WebElement selectAgentPlaceholder;

    public String transferChat(String agent) {
        openDropDownAgent();
        String agentName = selectDropDownAgent(agent);
        sentNote();
        if(isElementShown(this.getCurrentDriver(), selectAgentPlaceholder, 1)){
            clickElem(this.getCurrentDriver(), cancelTransferButton, 1,"Cancel transfer button");
            new ChatHeader().clickTransferButton(agent);
            agentName = selectDropDownAgent(agent);
            sentNote();
        }
        clickTransferChatButton();
        return agentName;
    }

    public String transferOvernightTicket(String agent) {
        openDropDownAgent();
        String agentName = selectDropDownAgent(agent);
        if(isElementShown(this.getCurrentDriver(), selectAgentPlaceholder, 1)){
            clickElem(this.getCurrentDriver(), cancelTransferButton, 1,"Cancel transfer button");
            new ChatHeader().clickTransferButton(agent);
            agentName = selectDropDownAgent(agent);
            sentNote();
        }
        clickTransferChatButton();
        return agentName;
    }

    public boolean isTransferChatShown() {
        return  isElementShown(this.getCurrentDriver(), submitTransferChatButton, 5);
    }

    public void openDropDownAgent() {
        clickElem(this.getCurrentDriver(), openDropdownButton,5,"Open drop down button");
    }

    public String selectDropDownAgent(String agent) {
        if(!isElementShown(this.getCurrentDriver(), availableAgent, 2)) openDropdownButton.click();
        waitForElementToBeVisible(this.getCurrentDriver(), availableAgent,5);
        for(int i=0; i<10; i++){
            if(availableAgent.getAttribute("innerText").contains("AQA")) {
                WebElement currentAgent = availableAgentList.stream().filter(e -> e.getText().toUpperCase().contains("AQA")).findFirst().get();
                String agentName = currentAgent.getText();
                currentAgent.click();
                return agentName;
            }
            else waitFor(500);
        }
        String agentName = availableAgent.getAttribute("innerText");
        availableAgent.click();
        return agentName;
    }

    public String getTextDropDownMessage() {
        return getTextFromElem(this.getCurrentDriver(), availableAgent,6,"Drop down menu");
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

}
