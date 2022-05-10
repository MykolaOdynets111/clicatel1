package agentpages.dashboard.uielements;

import abstractclasses.AbstractUIElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@FindBy(css = "#agents-performance-tabs")
public class AgentPerformanceTab extends AbstractUIElement {

    private final String expandAgentsTableForDepartmentButtonXpath =
            "//h3[contains(@class, 'setting-title') and text()='%s']/following-sibling::button";

    @FindBy(css = ".cl-empty-state")
    private WebElement noActiveAgentsMessage;

    public boolean isNoActiveAgentsMessageDisplayed() {
        return isElementShown(this.getCurrentDriver(), noActiveAgentsMessage, 5);
    }

    public void clickExpandAgentsTableForDepartmentButton(String department) {
        clickElem(this.getCurrentDriver(), findElemByXPATH(this.getCurrentDriver(),
                String.format(expandAgentsTableForDepartmentButtonXpath, department)),
                5, String.format("Expand button for department %s", department));
    }
}
