package steps;

import apihelper.ApiHelper;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import datamanager.Tenants;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
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
        getDepartmentsManagementPage().switchToFrame();
        getDepartmentsManagementPage().clickAddNewDepartmentButton().setNameField(name).setDescriptionForm(description).selectDepartmentAgentsCheckbox(agent).clickCreateButton();
        Assert.assertTrue(getDepartmentsManagementPage().isCardPresent(name, 5),
                "Departments was not created");
        getDepartmentsManagementPage().getCurrentDriver().switchTo().defaultContent();
    }

    @Then("^Create Department with (.*) name (.*) description and with (.*) Agents")
    public void createDepartmentWithSeveralAgents(String name, String description, int agent){
        List<String> agents = new ArrayList<String>();
        agents.add(Tenants.getTenantUnderTestOrgName());
        for (int i = agent-1; i > 0; i--){
            agents.add(AbstractAgentSteps.getListOfCreatedAgents().get((i-1)).get("name"));
        }
        getDepartmentsManagementPage().switchToFrame();
        getDepartmentsManagementPage().clickAddNewDepartmentButton().setNameField(name).setDescriptionForm(description).selectSeveralDepartmentAgentsCheckbox(agents).clickCreateButton();
        Assert.assertTrue(getDepartmentsManagementPage().isCardPresent(name, 5),
                "Departments was not created");
        getDepartmentsManagementPage().getCurrentDriver().switchTo().defaultContent();
    }

    @And("^Edit department with (.*) name (.*) description")
    public void editDepartment(String name, String description){
        getDepartmentsManagementPage().switchToFrame().openDepartmentManageForm(name, description).
                setNameField(name + "Edited").setDescriptionForm(description + "Edited").clickSaveButton();
        getDepartmentsManagementPage().findCardByNameAndDescription(name+ "Edited", description+ "Edited");
        getDepartmentsManagementPage().getCurrentDriver().switchTo().defaultContent();
    }

    @Then ("^Verify that card has (.*) name and (.*) description")
    public void verifyDepartmentNameAndDescription(String name, String description){
        getDepartmentsManagementPage().switchToFrame().findCardByNameAndDescription(name, description);
        getDepartmentsManagementPage().getCurrentDriver().switchTo().defaultContent();
    }

    @Then("^Remove Department with (.*) name and (.*) description$")
    public void removeDepartment(String name, String description){
        getDepartmentsManagementPage().switchToFrame();
        if(!getDepartmentsManagementPage().isCardPresent(name, 1)){
            Assert.fail("Department with '" + name + "' name was not shown");
        }

        getDepartmentsManagementPage().findCardByNameAndDescription(name, description).clickDeleteDepartmentButton();
        Assert.assertFalse(getDepartmentsManagementPage().isCardDisappeared(name, 1),
                "Departments was not removed");
        getDepartmentsManagementPage().getCurrentDriver().switchTo().defaultContent();
    }


    @Then("^Verify that card with (.*) name and (.*) description has (.*) total (.*) offline and (.*) active agents$")
    public void verifyAgentsQuantity(String cardName, String description, int total, int offline, int online){
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(getDepartmentsManagementPage().switchToFrame().findCardByNameAndDescription(cardName, description).getNumberOfAgents(), total,
                "Number of agents should be " + total);
        softAssert.assertEquals(getDepartmentsManagementPage().findCardByNameAndDescription(cardName, description).getOfflineAgentsNumber(), offline,
                "Number of agents should be " + offline);
        softAssert.assertEquals(getDepartmentsManagementPage().findCardByNameAndDescription(cardName, description).getOnlineAgentsNumber(), online,
                "Number of agents should be " + online);
        getDepartmentsManagementPage().getCurrentDriver().switchTo().defaultContent();
        softAssert.assertAll();
    }

    @When("Add newly created agent to department with (.*) name and (.*) description")
    public void addNewAgentToDepartment(String name, String description){
        String newAgent = AbstractAgentSteps.getListOfCreatedAgents().get((0)).get("name");
        getDepartmentsManagementPage().switchToFrame().openDepartmentManageForm(name, description).selectDepartmentAgentsCheckbox(newAgent).clickSaveButton();
        getDepartmentsManagementPage().getCurrentDriver().switchTo().defaultContent();
    }

    @Given("^New departments with (.*) name and (.*) description is created$")
    public void createNewDepartment(String name, String department){
        ApiHelper.createDepartmen(name, department);
    }

    @Then("Verify Department Duplication Alert message displayed")
    public void verifyDuplicationAlert(){
        Assert.assertEquals(getDepartmentsManagementPage().switchToFrame().getDuplicationAlertText(), "Department already exist.", "Duplication message is not the same");
        getDepartmentsManagementPage().getCurrentDriver().switchTo().defaultContent();
    }
}
