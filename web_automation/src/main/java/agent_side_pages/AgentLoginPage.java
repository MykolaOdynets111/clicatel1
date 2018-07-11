package agent_side_pages;

import abstract_classes.AgentAbstractPage;
import dataprovider.Agents;
import driverManager.DriverFactory;
import driverManager.URLs;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AgentLoginPage extends AgentAbstractPage {

    @FindBy(css = "div.login-form")
    private WebElement loginForm;

    @FindBy(css = "input[type='email']")
    private WebElement userNameInput;

    @FindBy(css = "input[type='password']")
    private WebElement userPassInput;

    @FindBy(css = "button[type='submit']")
    private WebElement loginButton;

    public AgentLoginPage(String ordinalAgentNumber) {
        super(ordinalAgentNumber);
    }

    public static AgentLoginPage openAgentLoginPage(String ordinalAgentNumber, String tenantOrgName) {
       DriverFactory.getDriverForAgent(ordinalAgentNumber).get(URLs.getAgentURL(tenantOrgName, true));
       return new AgentLoginPage(ordinalAgentNumber);
   }

   public AgentHomePage loginAsAgentOf(String tenantOrgName, String ordinalAgentNumber) {
       Agents agent = Agents.getAgentFromCurrentEnvByTenantOrgName(tenantOrgName, ordinalAgentNumber);
       waitForLoginPageToOpen();
//       waitForElementToBeVisible(userNameInput);
       userNameInput.sendKeys(agent.getAgentName());
       userPassInput.sendKeys(agent.getAgentPass());
       loginButton.click();
       return new AgentHomePage(ordinalAgentNumber);
   }

   public void waitForLoginPageToOpen() {
        waitForElementToBeVisible(loginForm, 6);
   }
}
