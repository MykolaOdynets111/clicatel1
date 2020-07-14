package steps.portalsteps;

import apihelper.ApiHelper;
import com.github.javafaker.Faker;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import datamanager.SurveyManagement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import portaluielem.SurveyWebChatForm;

import java.util.List;
import java.util.Map;

public class SurveyManagementSteps extends AbstractPortalSteps {

    private SurveyWebChatForm surveyWebChatForm;
    private static ThreadLocal<String> questionUpdate = new ThreadLocal<>();
    private static ThreadLocal<String> thankMessageUpdate = new ThreadLocal<>();
    Faker faker = new Faker();
    public static ThreadLocal<SurveyManagement> surveyConfiguration = new ThreadLocal<>();

    @Then("^Update survey management chanel (.*) settings by ip for (.*)")
    public void updateSurveyManagementSettings(String chanel, String tenantOrgName, Map<String, String> map){
        String channelID = ApiHelper.getChannelID(tenantOrgName, chanel);
        SurveyManagement configuration = ApiHelper.getSurveyManagementAttributes(channelID);
            for(String key : map.keySet()){
                configuration.updateSomeValueByMethodName(key, map.get(key));
            }
        surveyConfiguration.set(configuration);
        ApiHelper.updateSurveyManagement(tenantOrgName, configuration, channelID);
    }

    @Then("^Survey Management page should be shown$")
    public void verifyDepartmentsManagementPageShown(){
        Assert.assertTrue(getSurveyManagementPage().isSurveyManagementPage(),
                "Survey Management page not shown");
    }

    @Then("^Selects (.*) survey type")
    public void verifyCSATSurvey(String type){
        surveyWebChatForm = getSurveyManagementPage().getSurveyWebChatForm();
        if (type.equalsIgnoreCase("CSAT")) {
            surveyWebChatForm.clickCSATRadioButton();
        } else if (type.equalsIgnoreCase("NPS")){
            surveyWebChatForm.clickNPSRadioButton();
        }
    }

    @When("^Switch to whatsapp survey configuration$")
    public void switchToWhatsapp(){
        getSurveyManagementPage().switchToWhatsappTab();
    }

    @Then("^CSAT scale has correct limit variants (.*) in dropdown and (.*) set as type$")
    public void verifyCSATNumbers(List<String> expRangeOfNumbers, String type){
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(surveyWebChatForm.getVariationOfRatingCSATScale(),
                expRangeOfNumbers, "CSAT range of numbers in dropdown is not as expected");
        soft.assertTrue(surveyWebChatForm.isRateHasCorrectIcons(type), "No " + type + " type of Icons are displayed in Survey Preview");
        soft.assertAll();
    }

    @When("^Agent select (.*) as number limit from dropdown$")
    public void selectLimitOption(String limitNumber){
        surveyWebChatForm.selectDropdownOption(limitNumber);
    }

    @When("^Agent click save survey configuration button$")
    public void clickSaveButton(){
        surveyWebChatForm.clickSaveButton();
//        getSurveyManagementPage().waitSaveMessage();
    }

    @When("^Agent switch \"Allow customer to leave a note\" in survey management$")
    public void enableComments(){
        surveyWebChatForm.clickCommentSwitcher();
    }

    @Then("^Agent sees comment field in Survey management form$")
    public void isCommenfFormShown(){
        Assert.assertTrue(surveyWebChatForm.isCommentFieldShown(), "Comment Form is not shown");
    }


    @Then("^Agent see survey range (.*) in rating scale$")
    public void verifyCorectNumbersDisplayed(String numbers){
        Assert.assertEquals(surveyWebChatForm.getSizeOfRateInputNumbers(), numbers, "Quantity of numbers is not the same");
    }

    @When("^Agent select (.*) as and icon for rating range$")
    public void selectRangeIcon(String iconName){
        surveyWebChatForm.selectRateIcon(iconName);
    }

    @When("^Customize your survey \"(.*)\" question$")
    public void setSurveyQuestion(String question){
        questionUpdate.set(question + " " + faker.rockBand().name());
        surveyWebChatForm.changeQuestion(questionUpdate.get());
    }

    @When("^Customize your survey thank message$")
    public void setSurveyThankMessage(){
        thankMessageUpdate.set("Thank you for taking the time to provide us with your feedback. " + faker.gameOfThrones().dragon());
        surveyWebChatForm.setThankMessage(thankMessageUpdate.get());
    }

    @Then ("^Thank Survey thank message was updated on backend for (.*) and (.*) chanel$")
    public void verifyThankQuestionIsUpdated(String tenantOrgName, String chanel){
        String channelID = ApiHelper.getChannelID(tenantOrgName, chanel);
        SurveyManagement configuration = ApiHelper.getSurveyManagementAttributes(channelID);
        Assert.assertEquals(configuration.getRatingThanksMessage(), thankMessageUpdate.get(), "Thank survey message is not the same as was set");
    }

    @Then("^Survey backend was updated for (.*) and (.*) chanel with following attribute$")
    public void verifyThatSurveyBackendWasUpdated(String tenantOrgName, String chanel, Map<String, String> map){
        String channelID = ApiHelper.getChannelID(tenantOrgName, chanel);
        SurveyManagement configuration = ApiHelper.getSurveyManagementAttributes(channelID);
        String attributeToCheck = map.keySet().stream().findFirst().get();
        Assert.assertEquals(configuration.getSomeValueByMethodName(attributeToCheck), map.get(attributeToCheck), attributeToCheck + " is not updated on backend");

    }

    @Then("^Preview question is updated successfully$")
    public void verifyQuestionPreview(){
        Assert.assertEquals(surveyWebChatForm.getPreviewQuestion(), questionUpdate.get(), "Preview question is not the same as was set");
    }

    @Then("^Preview question is updated successfully for (.*) and (.*) chanel")
    public void verifyQuestionPreviewAPI(String tenantOrgName, String chanel){
        String channelID = ApiHelper.getChannelID(tenantOrgName, chanel);
        SurveyManagement configuration = ApiHelper.getSurveyManagementAttributes(channelID);
        Assert.assertEquals(configuration.getSurveyQuestionTitle(), questionUpdate.get(), "Preview question is not the same as was set");
    }

    @Then("^Star and Smile Buttons are Disabled$")
    public void starAndSmileButtonsAreDisabled(){
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(surveyWebChatForm.isSmileButtonDisabled(), "Smile button should not be enabled");
        soft.assertTrue(surveyWebChatForm.isStarButtonDisabled(), "Star button should not be enabled");
        soft.assertAll();
    }


}
