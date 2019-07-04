package agentpages.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = "div.modal-content")
public class TransferChatWindow extends AbstractUIElement {

    @FindBy(xpath = "//button[text()='Transfer']")
    private WebElement submitTransferChatButton;

    @FindBy(css = "span.icon.icon-down")
    private WebElement openDropdownButton;

    @FindBy(xpath = "//div[@class='Select-menu-outer']/*")
    private WebElement availableAgent;

    @FindBy(xpath = "//div[@class='Select-control']")
    private WebElement dropDown;

    private String openedMenu = "div.Select-menu-outer";

    @FindBy(css = "textarea")
    private WebElement noteInput;


    public String transferChat(String agent) {
        openDropDownAgent();
        String agentName = selectDropDownAgent(agent);
        sentNote();
        clickTransferChatButton();
        return agentName;
    }

    public boolean isTransferChatShown() {
        return  isElementShown(submitTransferChatButton, 5);
    }

    public void openDropDownAgent() {
        clickElemAgent(openDropdownButton,5,"main", "Open drop down button");
    }

    public String selectDropDownAgent(String agent) {
//        if(!isElementShownAgentByCSS(openedMenu, 5, agent)) openDropdownButton.click();
        if(!isElementShownAgent(availableAgent, 2, agent)) openDropdownButton.click();
        waitForElementToBeVisibleAgent(availableAgent,5, agent);
        for(int i=0; i<15; i++){
            if(availableAgent.getAttribute("innerText").contains("AQA")) break;
            else waitFor(500);
        }
        String agentName = availableAgent.getAttribute("innerText");
        availableAgent.click();
        return agentName;
    }

    public String getTextDropDownMessage() {
        return getTextFromElemAgent(availableAgent,6,"main agent","Drop down menu");
    }

    public void clickTransferChatButton() {
        clickElemAgent(submitTransferChatButton,5,"main", "Transfer button");
    }

    public void sentNote() {
        noteInput.sendKeys("Please take care of this one");
    }

    public String getNoteInputColor() {
     return   noteInput.getCssValue("border-color");
    }

    public String getDropDownColor() {
        return  dropDown.getCssValue("border-color");
    }

}
