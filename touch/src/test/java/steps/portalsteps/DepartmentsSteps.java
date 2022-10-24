package steps.portalsteps;

import apihelper.ApiHelper;
import apihelper.ApiHelperDepartments;
import datamanager.Tenants;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import portalpages.DepartmentsManagementPage;
import portaluielem.CreateDepartmentForm;
import steps.agentsteps.AbstractAgentSteps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DepartmentsSteps extends AbstractPortalSteps {


    @Then("^Departments Management page should be shown$")
    public void verifyDepartmentsManagementPageShown(){
        Assert.assertTrue(getDepartmentsManagementPage().isDepartmentsPageOpened(),
                "Departments Management should be shown");
    }

    @Then("^Agent create Department with (.*) name (.*) description and (.*) Agent$")
    public void createDepartment(String name, String description, String agent){
        getDepartmentsManagementPage().clickAddNewDepartmentButton().setNameField(name).setDescriptionForm(description).selectDepartmentAgentsCheckbox(agent).clickCreateButton();
        Assert.assertTrue(getDepartmentsManagementPage().isCardPresent(name, 5),
                "Departments was not created");
    }

    @Then("^Create Department with (.*) name (.*) description and with (.*) agents$")
    public void createDepartmentWithSeveralAgents(String name, String description, int numberOfAgents) throws Exception {
        List<String> agentsNames = new ArrayList<>();
        List<Map> agents = ApiHelper.getAgentsInfo(Tenants.getTenantUnderTestOrgName());
        if (agents.size()>=numberOfAgents){
        for(int i=0; i<numberOfAgents; i++){
            agentsNames.add(String.valueOf(agents.get(i).get("fullName")));
        }
        getDepartmentsManagementPage().clickAddNewDepartmentButton().setNameField(name).setDescriptionForm(description).selectSeveralDepartmentAgentsCheckbox(agentsNames).clickCreateButton();
        Assert.assertTrue(getDepartmentsManagementPage().isCardPresent(name, 5),
                "Departments was not created");}
        else {
            throw new Exception("Currently, this tenant has " + agents.size()+ " total agents but required agents are: " +numberOfAgents+" ");
        }
    }

    @And("^Edit department with (.*) name (.*) description")
    public void editDepartment(String name, String description){
        DepartmentsManagementPage dep =getDepartmentsManagementPage();
        dep.isCardPresent(name, 5);
        dep.openDepartmentManageForm(name, description).
                setNameField(name + "Edited").setDescriptionForm(description + "Edited").clickSaveButton();
        dep.isCardPresent(name+ "Edited", 2);
        dep.findCardByNameAndDescription(name+ "Edited", description+ "Edited");
    }

    @Then ("^Verify that card has (.*) name and (.*) description")
    public void verifyDepartmentNameAndDescription(String name, String description){
        getDepartmentsManagementPage().findCardByNameAndDescription(name, description);
    }

    @Then("^Remove Department with (.*) name and (.*) description$")
    public void removeDepartment(String name, String description){
        if(!getDepartmentsManagementPage().isCardPresent(name, 1)){
            Assert.fail("Department with '" + name + "' name was not shown");
        }

        getDepartmentsManagementPage().findCardByNameAndDescription(name, description).clickDeleteDepartmentButton();
        getDepartmentsManagementPage().confirmDeletingDepartment();
        Assert.assertTrue(getDepartmentsManagementPage().isCardDisappeared(name, 2),
                "Departments was not removed");
    }


    @Then("^Verify that card with (.*) name and (.*) description has (.*) total (.*) offline and (.*) active agents$")
    public void verifyAgentsQuantity(String cardName, String description, int total, int offline, int online){
        waitFor(1000);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(getDepartmentsManagementPage().findCardByNameAndDescription(cardName, description).getNumberOfAgents(), total,
                "Number of agents should be " + total);
        softAssert.assertEquals(getDepartmentsManagementPage().findCardByNameAndDescription(cardName, description).getOfflineAgentsNumber(), offline,
                "Number of agents should be " + offline);
        softAssert.assertEquals(getDepartmentsManagementPage().findCardByNameAndDescription(cardName, description).getOnlineAgentsNumber(), online,
                "Number of agents should be " + online);
        softAssert.assertAll();
    }

    @When("Add newly created agent to department with (.*) name and (.*) description")
    public void addNewAgentToDepartment(String name, String description){
        String newAgent = AbstractAgentSteps.getListOfCreatedAgents().get((0)).get("name");
        getDepartmentsManagementPage().openDepartmentManageForm(name, description).selectDepartmentAgentsCheckbox(newAgent).clickSaveButton();
    }

    @Given("^New departments with (.*) name (.*) description and (.*) is created$")
    public void createNewDepartment(String name, String department, String agent){
        ApiHelperDepartments.createDepartment(name, department, agent);
    }

    @Then("Verify Agent cannot create department with duplicate (.*) name")
    public void verifyDuplicationAlert(String name)
    {
        SoftAssert softAssert = new SoftAssert();
        CreateDepartmentForm createDepartmentForm = getDepartmentsManagementPage().clickAddNewDepartmentButton();
        softAssert.assertEquals(createDepartmentForm.setNameField(name).getDuplicateNameErrorMessage(), "Department name already exists and cannot be duplicated.", "Duplication message is not the same");
        softAssert.assertFalse(createDepartmentForm.isCreateButtonActive(), "Create button should be disabled");
        softAssert.assertAll();
    }


}
