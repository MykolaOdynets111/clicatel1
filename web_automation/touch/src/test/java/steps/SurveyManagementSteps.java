package steps;

import apihelper.ApiHelper;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import datamanager.SurveyManagement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import portaluielem.SurveyWebChatForm;
import steps.portalsteps.AbstractPortalSteps;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SurveyManagementSteps extends AbstractPortalSteps {

   private SurveyWebChatForm surveyWebChatForm;

    @Then("^Update survey management chanel (.*) settings by ip for (.*)")
    public void updateSurveyManagementSettings(String chanel, String tenantOrgName, Map<String, String> map){
        String channelID = ApiHelper.getChannelID(tenantOrgName, chanel);
        SurveyManagement configuration = ApiHelper.getSurveyManagementAttributes(channelID);
            for(String key : map.keySet()){
                configuration.updateSomeValueByMethodName(key, map.get(key));
            }
        ApiHelper.updateSurveyManagement(tenantOrgName, configuration, channelID);
    }

    @Then("^Survey Management page should be shown$")
    public void verifyDepartmentsManagementPageShown(){
        Assert.assertTrue(getSurveyManagementPage().isSurveyManagementPage(),
                "Survey Management page not shown");
    }

    @Then("^Selects CSAT survey type")
    public void verifyCSATSurvey(){
        getSurveyManagementPage().switchToFrame();
        surveyWebChatForm = getSurveyManagementPage().getSurveyWebChatForm();
        surveyWebChatForm.clickCSATRadioButton();
        getSurveyManagementPage().switchToDefaultFrame();
    }

    @Then("^CSAT scale start form (.*) and has correct limit variants (.*) in dropdown and (.*) set as type$")
    public void verifyCSATNumbers(String startFrom, List<String> expRangeOfNumbers, String type){
        getSurveyManagementPage().switchToFrame();
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(surveyWebChatForm.getFromCount(), startFrom + " to", "From count is not as expected");
        soft.assertEquals(surveyWebChatForm.getVariationOfRatingCSATScale(),
                expRangeOfNumbers, "CSAT range of numbers in dropdown is not as expected");
        soft.assertTrue(surveyWebChatForm.isRateHasCorrectIcons(type), "Incorrect type of Icon is displayed in range row");
        soft.assertAll();
        getSurveyManagementPage().switchToDefaultFrame();
    }

    @When("^Agent select (.*) as number limit from dropdown$")
    public void selectNumberLimit(String limitNumber){
        getSurveyManagementPage().switchToFrame();
        surveyWebChatForm.selectNumberFromDropdown(limitNumber);
        getSurveyManagementPage().switchToDefaultFrame();
    }

    @When("^Agent click save survey configuration button$")
    public void clickSaveButton(){
        getSurveyManagementPage().switchToFrame();
        surveyWebChatForm.clickSaveButton();
        getSurveyManagementPage().switchToDefaultFrame();
        getSurveyManagementPage().waitSaveMessage();
    }

    @When("^Agent switch \"Allow customer to leave a note\" in survey management$")
    public void enableComments(){
        getSurveyManagementPage().switchToFrame();
        surveyWebChatForm.clickCommentSwitcher();
        getSurveyManagementPage().switchToDefaultFrame();
    }

    @Then("^Agent sees comment field in Survey management form$")
    public void isCommenfFormShown(){
        getSurveyManagementPage().switchToFrame();
        Assert.assertTrue(surveyWebChatForm.isCommentFieldShown(), "Comment Form is not shown");
        getSurveyManagementPage().switchToDefaultFrame();
    }


    @Then("^Agent see survey range (.*) in rating scale$")
    public void verifyCorectNumbersDisplayed(String numbers){
        getSurveyManagementPage().switchToFrame();
        Assert.assertEquals(surveyWebChatForm.getSizeOfRateInputNumbers(), numbers, "Quantity of numbers is not the same");
        getSurveyManagementPage().switchToDefaultFrame();
    }

    @When("^Agent select (.*) as and icon for rating range$")
    public void selectRangeIcon(String iconName){
        getSurveyManagementPage().switchToFrame();
        surveyWebChatForm.selectRateIcon(iconName);
        getSurveyManagementPage().switchToDefaultFrame();
    }

}
