package steps;

import apihelper.ApiHelper;
import cucumber.api.java.en.Then;
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
        getSurveyManagementPage().getSurveyWebChatForm().clickCSATRadioButton();
        getSurveyManagementPage().switchToDefaultFrame();
    }

    @Then("^CSAT scale start form (.*) and has correct limit variants (.*) in dropdown$")
    public void verifyCSATNumbers(String startFrom, List<String> expRangeOfNumbers){
        getSurveyManagementPage().switchToFrame();
        SoftAssert soft = new SoftAssert();
        surveyWebChatForm = getSurveyManagementPage().getSurveyWebChatForm();
        soft.assertEquals(surveyWebChatForm.getFromCount(), startFrom + " to", "From count is not as expected");
        soft.assertEquals(surveyWebChatForm.getVariationOfRatingCSATScale(),
                expRangeOfNumbers, "CSAT range of numbers in dropdown is not as expected");
        soft.assertAll();
        getSurveyManagementPage().switchToDefaultFrame();
    }


}
