package portaluielem;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;


@FindBy(css = ".ReactModal__Content.ReactModal__Content--after-open.cl-modal")
public class AssignChatWindow extends BasePortalWindow {

    @FindBy(xpath = ".//label[contains(text(), 'Agents:')]/following-sibling::div//div[contains(@class, 'cl-select__indicators')]")
    private WebElement openAgentDropdownButton;

    @FindBy(xpath = "//div[contains(@class, 'cl-select__option')]")
    private WebElement availableAgent;

    @FindBy(xpath = "//div[contains(@class, 'cl-select__option')]")
    private List<WebElement> availableAgentList;

    @FindBy(css = "button[type='submit']")
    private WebElement assignChatButton;


    private void openDropDownAgent() {
        clickElem(this.getCurrentDriver(), openAgentDropdownButton, 5, "Agent drop down");
    }

    public void selectDropDownAgent(String agentName) {
        for (int i = 0; i < 3; i++) {
            if (isElementRemoved(this.getCurrentDriver(), availableAgent, 2))
                openDropDownAgent();
            waitForFirstElementToBeVisible(this.getCurrentDriver(), availableAgentList, 5);
            if (availableAgentList.size() > 0) {
                WebElement agent = availableAgentList.stream()
                        .filter(e -> e.getText().toLowerCase().equals(agentName.toLowerCase()))
                        .findFirst().get();
                executeJSclick(this.getCurrentDriver(), agent);
                return;
            } else waitFor(500);
        }
        new AssertionError("Cannot find " + agentName + " agent");
    }

    public void clickAssignChatButton() {
        clickElem(this.getCurrentDriver(), assignChatButton, 3, "'Assign chat' button");
    }
}
