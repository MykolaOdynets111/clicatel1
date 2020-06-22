package portaluielem;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;


@FindBy(css = "div.cl-r-modal")
public class AssignChatWindow extends BasePortalWindow {

    @FindBy(xpath = ".//div[text()='Select an agent...']")
    private WebElement openAgentDropdownButton;

    @FindBy(css = ".cl-r-select__menu-list div")
    private WebElement availableAgent;

    @FindBy(css = ".cl-r-select__menu-list div")
    private List<WebElement> availableAgentList;

    @FindBy(css = "button[type='submit']")
    private WebElement assignChatButton;


    private void openDropDownAgent() {
        clickElem(this.getCurrentDriver(), openAgentDropdownButton,5,"Open drop down button");
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
