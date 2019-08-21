package portaluielem;

import drivermanager.ConfigManager;
import io.qameta.allure.Step;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;


@FindBy(css = "div.cl-wizzard.create-integration-container")
public class UpgradeYourPlanWindow  extends BasePortalWindow {

    @FindBy(css = "button.bttn-add-agents-seats")
    private WebElement addAgentSeatsButton;

    @FindBy(xpath = "//span[contains(@value, 'MONTH')]//span[contains(@class, 'cl-input-label')]")
    private WebElement monthlyRadioButton;

    @FindBy(css = "span.x-agents-label")
    private WebElement agentSeatsLabel;

    @FindBy(css = "div.agents-seats-calculator-panel input")
    private WebElement agentSeatsInput;

    @Step(value = "Select {seats} agent seats")
    public UpgradeYourPlanWindow selectAgentSeats(int seats){
        try {
            waitForElementToBeClickable(this.getCurrentDriver(), addAgentSeatsButton, 25);
        } catch(TimeoutException e){
            Assert.fail("'Add agent seats' button is not clickable.\n " +
                    "Please check the screenshot");
        }
        for(int i =1; i<seats; i++ ){
            addAgentSeatsButton.click();
        }
        return this;
    }

    @Step(value = "Select monthly")
    public UpgradeYourPlanWindow selectMonthly(){
        monthlyRadioButton.click();
        return this;
    }

    @Step(value = "Verify Add agent seats button shown")
    public boolean isAddAgentSeatsButtonShown(){
        return isElementShown(this.getCurrentDriver(), addAgentSeatsButton, 5);
    }

    @Step(value = "Get agent seats value")
    public String getAgentSeatsInfo(){
        return getAttributeFromElem(this.getCurrentDriver(), agentSeatsInput, 3, "Agents seats input", "value") + " " +
                getTextFromElem(this.getCurrentDriver(), agentSeatsLabel, 3, "Agent seats label");
    }

    @Step(value = "Verify on prod like envs Monthly selected by default")
    public boolean verifyMonthlySelected(){
        if(ConfigManager.getEnv().equals("prod") |
                ConfigManager.getEnv().equals("stage")){
            return getAttributeFromElem(this.getCurrentDriver(), monthlyRadioButton, 3,
                    "Monthly radio", "class").contains("ng-valid-parse");
        }else {
            return true; // the verification is valid only for stage and prod envs
        }

    }
}
