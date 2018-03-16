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

    public static AgentLoginPage openAgentLoginPage(String tenantOrgName) {
       DriverFactory.getSecondDriverInstance().get(URLs.getAgentURL(tenantOrgName, true));
       return new AgentLoginPage();
   }

   public AgentHomePage loginAsAgentOf(String tenantOrgName) {
       Agents agent = Agents.getAgentFromCurrentEnvByTenantOrgName(tenantOrgName);
       waitForLoginPageToOpen();
//       waitForElementToBeVisible(userNameInput);
       userNameInput.sendKeys(agent.getAgentName());
       userPassInput.sendKeys(agent.getAgentPass());
       loginButton.click();
       return new AgentHomePage();
   }

   public void waitForLoginPageToOpen() {
        waitForElementToBeVisible(loginForm, 6);
   }
}
