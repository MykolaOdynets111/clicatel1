package agentpages;

import abstractclasses.AgentAbstractPage;
import datamanager.Tenants;
import driverfactory.URLs;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class AgentLoginPage extends AgentAbstractPage {

    @FindBy(css = "[id='tenants']")
    private WebElement tenantsDropdown;

    @FindBy(css = "[id='agents']")
    private WebElement agentsDropdown;

    @FindBy(css = "[id='auth-button']")
    private WebElement authenticateButton;

    public AgentLoginPage(String ordinalAgentNumber) {
        super(ordinalAgentNumber);
    }

    public AgentLoginPage openPortalLoginPage() {
        getCurrentDriver().get(URLs.getTouchLoginForm());
        return this;
    }

    private Select dropdownSelect;

    public AgentLoginPage selectTenant(String tenantName){
        waitForElementToBeVisible(this.getCurrentDriver(), tenantsDropdown, 3);
        dropdownSelect = new Select(tenantsDropdown);
        waitForOptionsIsDownloaded(dropdownSelect);
        dropdownSelect.selectByVisibleText(tenantName);
        return this;
    }

    private List<WebElement> waitForOptionsIsDownloaded(Select dropdownSelect){
        for(int i=0; i<10; i++) {
            if (dropdownSelect.getOptions().size() > 1){
                break;
            }
            waitFor(1000);
        }
        return dropdownSelect.getOptions();
    }

    public AgentLoginPage selectAgent(String agent){
        if (agent.equalsIgnoreCase("agent")) {
            agent = "main";
        } else
        {
            agent = "second";
        }
        clickElem(this.getCurrentDriver(), agentsDropdown, 2, "Agents Dropdown");
        dropdownSelect = new Select(agentsDropdown);
        List<WebElement> options = waitForOptionsIsDownloaded(dropdownSelect);
        String finalAgent = agent;
        String agentName = options.stream().filter(a -> a.getText().toLowerCase().contains(finalAgent)).findFirst().get().getText();
        dropdownSelect.selectByVisibleText(agentName);
        return this;
    }

    public AgentLoginPage selectAmritOrcAgent(String agent){
        Tenants.setTenantUnderTestOrgName(agent);
        clickElem(this.getCurrentDriver(), agentsDropdown, 2, "Agents Dropdown");
        dropdownSelect = new Select(agentsDropdown);
        waitForOptionsIsDownloaded(dropdownSelect);
        dropdownSelect.selectByVisibleText(agent);
        return this;
    }

    public AgentLoginPage clickAuthenticateButton(){
        clickElem(this.getCurrentDriver(), authenticateButton, 2, "Authenticate");
        verifyJVT();
        return this;
    }

    private void verifyJVT(){
        Cookie cookie = this.getCurrentDriver().manage().getCookieNamed("chatdesk");
        for (int i = 0; i<3; i++ ){
            if (cookie == null){
                waitFor(1000);
                clickElem(this.getCurrentDriver(), authenticateButton, 2, "Authenticate");
                cookie = this.getCurrentDriver().manage().getCookieNamed("chatdesk");
            } else {
                return;
            }
        }
        throw new AssertionError("JWT token was not generated");
    }
}
