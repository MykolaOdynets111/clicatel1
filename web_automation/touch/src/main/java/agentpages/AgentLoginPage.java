package agentpages;

import abstractclasses.AgentAbstractPage;
import datamanager.Agents;
import driverfactory.DriverFactory;
import driverfactory.URLs;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

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


    private Select dropdownSelect;

    public void selectTenant(String tenantName){
        waitForElementToBeVisible(this.getCurrentDriver(), tenantsDropdown, 3);
        dropdownSelect = new Select(tenantsDropdown);
        dropdownSelect.selectByValue(tenantName);
    }

    public void selectAgent(String tenantName){
        dropdownSelect = new Select(agentsDropdown);
        dropdownSelect.selectByValue(tenantName);
    }

    public void clickAuthenticateButton(){
        clickElem(this.getCurrentDriver(), authenticateButton, 2, "Authenticate");
    }
}
