package agentpages;

import abstractclasses.AgentAbstractPage;
import datamanager.Agents;
import driverfactory.DriverFactory;
import driverfactory.URLs;
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
           new AgentHomePage(ordinalAgentNumber).getPageHeader().logOut();
           logIn(agent, ordinalAgentNumber);
       }
       return this;
   }

   private void logIn(Agents agent, String ordinalAgentNumber){
       waitForElementToBeVisible(this.getCurrentDriver(),userNameInput, 5);
       userNameInput.sendKeys(agent.getAgentEmail());
       userPassInput.sendKeys(agent.getAgentPass());
       loginButton.click();
   }

   public void waitForLoginPageToOpen(String agent) {
        waitForElementToBeVisible(this.getCurrentDriver(), loginForm, 6);
   }

    public String getLoginButtonColor() {
        waitForElementToBeVisible(this.getCurrentDriver(), loginButton, 6);
        return Color.fromString(loginButton.getCssValue("color")).asHex();
    }

    public String getStringForgotColor() {
        return Color.fromString(stringForgotPassword.getCssValue("color")).asHex();
    }
}
