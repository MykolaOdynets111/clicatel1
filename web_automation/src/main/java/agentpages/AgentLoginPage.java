package agentpages;

import abstractclasses.AgentAbstractPage;
import datamanager.Agents;
import drivermanager.DriverFactory;
import drivermanager.URLs;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;

public class AgentLoginPage extends AgentAbstractPage {

    private String loginFormXPATH = "//div[@class='login-form']";

    @FindBy(css = "div.login-form")
    private WebElement loginForm;

    @FindBy(css = "input[type='email']")
    private WebElement userNameInput;

    @FindBy(css = "input[type='password']")
    private WebElement userPassInput;

    @FindBy(css = "button[type='submit']")
    private WebElement loginButton;

    @FindBy(css = "div.text-center > a")
    private WebElement stringForgotPassword;

    public AgentLoginPage(String ordinalAgentNumber) {
        super(ordinalAgentNumber);
    }

    public static AgentLoginPage openAgentLoginPage(String ordinalAgentNumber, String tenantOrgName) {
       DriverFactory.getDriverForAgent(ordinalAgentNumber).get(URLs.getAgentURL(tenantOrgName, true));
       return new AgentLoginPage(ordinalAgentNumber);
   }

   public AgentLoginPage loginAsAgentOf(String tenantOrgName, String ordinalAgentNumber) {
       Agents agent = Agents.getAgentFromCurrentEnvByTenantOrgName(tenantOrgName, ordinalAgentNumber);
       try{
           logIn(agent, ordinalAgentNumber);
       }catch(NoSuchElementException|TimeoutException e){
           new AgentHomePage(ordinalAgentNumber).getPageHeader().logOut(ordinalAgentNumber);
           logIn(agent, ordinalAgentNumber);
       }
       return this;
   }

   private void logIn(Agents agent, String ordinalAgentNumber){
       waitForElementToBeVisibleAgent(userNameInput, 5, ordinalAgentNumber);
       userNameInput.sendKeys(agent.getAgentEmail());
       userPassInput.sendKeys(agent.getAgentPass());
       loginButton.click();
   }

   public void waitForLoginPageToOpen(String agent) {
        waitForElementToBeVisibleAgent(loginForm, 6, agent);
   }

    public String getLoginButtonColor() {
        waitForElementToBeVisibleAgent(loginButton, 6, "second agent");
        return Color.fromString(loginButton.getCssValue("color")).asHex();
    }

    public String getStringForgotColor() {
        return Color.fromString(stringForgotPassword.getCssValue("color")).asHex();
    }
}
