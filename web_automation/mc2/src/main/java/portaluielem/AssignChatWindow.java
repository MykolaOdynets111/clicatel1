package portaluielem;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;


@FindBy(css = "div.transfer-chat-modal.modal-dialog")
public class AssignChatWindow extends BasePortalWindow {

    @FindBy(xpath = ".//label[@for='agentsList']/following-sibling::div//div[@class='Select-control']")
    private WebElement openDropdownButton;

    @FindBy(xpath = ".//div[@class='Select-menu-outer']")
    private WebElement availableAgent;

    @FindBy(xpath = ".//div[@class='Select-menu-outer']//div[contains(@class,'Select-option')]")
    private List<WebElement> availableAgentList;

    @FindBy(css = "button[type='submit']")
    private WebElement assignChatButton;


    private void openDropDownAgent() {
        clickElem(this.getCurrentDriver(), openDropdownButton,5,"Open drop down button");
    }

    public void selectDropDownAgent(String agentName) {
        openDropDownAgent();
        waitForElementToBeVisible(this.getCurrentDriver(), availableAgent,5);
        WebElement agent = availableAgentList.stream()
                    .filter(e -> e.getText().toLowerCase().equals(agentName.toLowerCase()))
                    .findFirst().orElseThrow(() -> new AssertionError("Cannot find " + agentName + " agent"));
        agent.click();
    }

    public void clickAssignChatButton(){
        clickElem(this.getCurrentDriver(), assignChatButton,3,"'Assign chat' button");
    }
}
