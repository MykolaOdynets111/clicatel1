package agent_side_pages.UIElements;

import abstract_classes.AbstractUIElement;
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

    private String openedMenu = "div.Select-menu-outer";

    @FindBy(css = "textarea")
    private WebElement noteInput;


    public void transferChat() {
        waitForElementToBeClickableAgent(openDropdownButton, 6, "main agent");
        openDropdownButton.click();
        try{
            isElementShown(findElement(By.cssSelector(openedMenu)), 5);
        } catch(NoSuchElementException e){
            openDropdownButton.click();
        }
        waitForElementToBeVisibleAgent(availableAgent,5);
        for(int i=0; i<15; i++){
            if(availableAgent.getText().contains("AQA")) break;
            else waitFor(500);
        }
        availableAgent.click();
        noteInput.sendKeys("Please take care of this one");
        submitTransferChatButton.click();
    }

}
