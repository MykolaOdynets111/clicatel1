package agentpages.supervisor.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@FindBy(css = ".cl-r-modal")
public class SupervisorAvailableAsAgentDialog extends AbstractUIElement {

    @FindBy(css = ".cl-agent-view-modal-text>div")
    private List<WebElement> messages;

    @FindBy(css = ".cl-r-button--primary")
    private WebElement launchButton;

    public String getFullMessage() {
        StringBuffer fullMessage = new StringBuffer();
        for(WebElement message : messages) {
            fullMessage.append(message.getText()).append(" ");
        }
        return fullMessage.toString().trim();
    }

    public void clickLaunch() {
        launchButton.click();
    }
}
