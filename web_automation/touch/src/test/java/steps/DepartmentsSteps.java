package steps;

import cucumber.api.java.en.Then;
import datamanager.Tenants;
import org.testng.Assert;
import steps.agentsteps.AbstractAgentSteps;
import steps.portalsteps.AbstractPortalSteps;

import java.util.ArrayList;
import java.util.List;

public class DepartmentsSteps extends AbstractPortalSteps {


    @Then("^Departments Management page should be shown$")
    public void verifyDepartmentsManagementPageShown(){
        Assert.assertTrue(getDepartmentsManagementPage().isDepartmentsPageOpened(),
                "Departments Management page not shown");
    }

    @Then("^Create Department with (.*) name (.*) description and (.*) Agent$")
    public void createDepartment(String name, String description, String agent){
        getDepartmentsManagementPage().clickAddNewDepartmentButton().setNameField(name).setDescriptionForm(description).selectDepartmentAgentsCheckbox(agent).clickCreateButton();
        Assert.assertTrue(getDepartmentsManagementPage().isCardPresent(name, 5),
                "Departments was not created");
    }

    @Then("^Create Department with (.*) name (.*) description and with (.*) Agents")
    public void createDepartmentWithSeveralAgents(String name, String description, int agent){
        List<String> agents = new ArrayList<String>();
        agents.add(Tenants.getTenantUnderTestOrgName());
        for (int i = agent-1; i > 0; i--){
            agents.add(AbstractAgentSteps.getListOfCreatedAgents().get((i-1)).get("name"));
        }
        getDepartmentsManagementPage().clickAddNewDepartmentButton().setNameField(name).setDescriptionForm(description).selectSeveralDepartmentAgentsCheckbox(agents).clickCreateButton();
        Assert.assertTrue(getDepartmentsManagementPage().isCardPresent(name, 5),
                "Departments was not created");
    }


    @Then("^Remove Department with (.*) name and (.*) description$")
    public void removeDepartment(String name, String description){
        if(!getDepartmentsManagementPage().switchToFrame().isCardPresent(name, 1)){
            Assert.fail("Department with '" + name + "' name was not shown");
        }

        getDepartmentsManagementPage().findCardByNameAndDescription(name, description).clickDeleteDepartmentButton();
        Assert.assertFalse(getDepartmentsManagementPage().isCardPresent(name, 1),
                "Departments was not removed");
    }


    @Then("^Verify that (.*) number of agents present in card with (.*) name and (.*) description$")
    public void verifyAgentsQuantity(int userNumber, String cardName, String description){
       Assert.assertEquals(getDepartmentsManagementPage().findCardByNameAndDescription(cardName, description).getNumberOfAgents(), userNumber,
                "Number of agents should be " + userNumber);
    }

}
