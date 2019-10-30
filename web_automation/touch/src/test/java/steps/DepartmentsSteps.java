package steps;

import cucumber.api.java.en.Then;
import org.testng.Assert;
import steps.portalsteps.AbstractPortalSteps;

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

    @Then("^Remove Department with (.*) name and (.*) description$")
    public void removeDepartment(String name, String description){
        if(!getDepartmentsManagementPage().switchToFrame().isCardPresent(name, 1)){
            Assert.fail("Department with '" + name + "' name was not shown");
        }

        getDepartmentsManagementPage().findCardByNameAndDescription(name, description).clickDeleteDepartmentButton();
        Assert.assertFalse(getDepartmentsManagementPage().isCardPresent(name, 1),
                "Departments was not removed");
    }

}
