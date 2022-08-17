package steps.portalsteps;

import apihelper.ApiHelper;
import com.github.javafaker.Faker;
import datamanager.SurveyManagement;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import steps.ORCASteps;

import java.util.List;
import java.util.Map;

public class SurveyManagementSteps extends AbstractPortalSteps {
    private static ThreadLocal<String> questionUpdate = new ThreadLocal<>();
    private static ThreadLocal<String> thankMessageUpdate = new ThreadLocal<>();
    Faker faker = new Faker();
    public static ThreadLocal<SurveyManagement> surveyConfiguration = new ThreadLocal<>();

    @Then("^Update survey management chanel (.*) settings by ip for (.*)")
    public void updateSurveyManagementSettings(String chanel, String tenantOrgName, Map<String, String> map) {
        String channelID = ApiHelper.getChannelID(tenantOrgName, chanel);
        SurveyManagement configuration = ApiHelper.getSurveyManagementAttributes(channelID);
        for (String key : map.keySet()) {
            configuration.updateSomeValueByMethodName(key, map.get(key));
        }
        surveyConfiguration.set(configuration);
        ApiHelper.updateSurveyManagement(tenantOrgName, configuration, channelID, chanel);

    }

    @Then("^Survey Management page should be shown$")
    public void verifyDepartmentsManagementPageShown() {
        Assert.assertTrue(getSurveyManagementPage().isSurveyManagementPage(),
                "Survey Management page not shown");
    }

    @Then("^Selects (.*) survey type")
    public void selectSurvey(String type) {
        String id = ORCASteps.getChannelId();

        if (type.equalsIgnoreCase("CSAT")) {
            getSurveyManagementPage().getSurveyForm(id).clickCSATRadioButton();
        } else if (type.equalsIgnoreCase("NPS")) {
            getSurveyManagementPage().getSurveyForm(id).clickNPSRadioButton();
        }
    }

    @Then("^Admin selects (.*) survey type for (.*) survey form")
    public void selectSurvey(String type, String surveyForm) {
        String id = ORCASteps.getChannelId();
        if (type.equalsIgnoreCase("CSAT")) {
            getSurveyManagementPage().getSurveyForm(id).clickCSATRadioButton();
        } else if (type.equalsIgnoreCase("NPS")) {
            getSurveyManagementPage().getSurveyForm(id).clickNPSRadioButton();
        }
    }

    @When("^Admin clicks on channel toggle button for survey form")
    public void selectToggleButton() {
        String id = ORCASteps.getChannelId();
        getSurveyManagementPage().getSurveyForm(id).clickToggleButton();
    }

    @When("^Admin clicks on channel expand button for survey form")
    public void selectExpandButton() {
        String id = ORCASteps.getChannelId();
        getSurveyManagementPage().getSurveyForm(id).clickExpandButton(id);
    }

    @When("^Switch to whatsapp survey configuration$")
    public void switchToWhatsapp() {
        getSurveyManagementPage().switchToWhatsappTab();
    }

    @Then("^CSAT scale has correct limit variants (.*) in dropdown and (.*) set as type$")
    public void verifyCSATNumbers(List<String> expRangeOfNumbers, String type) {
        String id = ORCASteps.getChannelId();
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(getSurveyManagementPage().getSurveyForm(id).getVariationOfRatingCSATScale(),
                expRangeOfNumbers, "CSAT range of numbers in dropdown is not as expected");
        soft.assertTrue(getSurveyManagementPage().getSurveyForm(id).isRateHasCorrectIcons(type), "No " + type + " type of Icons are displayed in Survey Preview");
        soft.assertAll();
    }

    @When("^Agent select (.*) as number limit from dropdown$")
    public void selectLimitOption(String limitNumber) {
        String id = ORCASteps.getChannelId();
        getSurveyManagementPage().getSurveyForm(id).selectDropdownOption(limitNumber);
    }

    @When("^Agent select (.*) as number limit from dropdown for (.*) survey form$")
    public void selectLimitOption(String limitNumber, String surveyForm) {
        String id = ORCASteps.getChannelId();
        if (surveyForm.equalsIgnoreCase("abc")) {
            getSurveyManagementPage().getSurveyForm(id).selectDropdownOption(limitNumber);
        } else if (surveyForm.equalsIgnoreCase("whatsapp")) {
            getSurveyManagementPage().getSurveyForm(id).selectDropdownOption(limitNumber);
        } else {
            this.selectLimitOption(limitNumber);
        }
    }

    @When("^Agent click save survey configuration button for (.*) survey form$")
    public void clickSaveButton(String surveyForm) {
        String id = ORCASteps.getChannelId();
        getSurveyManagementPage().getSurveyForm(id).clickSaveButton();
    }

    @When("^Agent switch \"Allow customer to leave a note\" in survey management$")
    public void enableComments() {
        String channelID = ORCASteps.getChannelId();
        getSurveyManagementPage().getSurveyForm(channelID).clickCommentSwitcher();
    }

    @Then("^Agent sees comment field in Survey management form$")
    public void isCommenfFormShown() {
        String channelID = ORCASteps.getChannelId();
        Assert.assertTrue(getSurveyManagementPage().getSurveyForm(channelID).isCommentFieldShown(), "Comment Form is not shown");
    }


    @Then("^Agent see survey range (.*) in rating scale$")
    public void verifyCorectNumbersDisplayed(String numbers) {
        String id = ORCASteps.getChannelId();
        Assert.assertEquals(getSurveyManagementPage().getSurveyForm(id).getSizeOfRateInputNumbers(), numbers, "Quantity of numbers is not the same");
    }

    @When("^Agent select (.*) as and icon for rating range$")
    public void selectRangeIcon(String iconName) {
        String channelID = ORCASteps.getChannelId();
        getSurveyManagementPage().getSurveyForm(channelID).selectRateIcon(iconName);
    }

    @When("^Customize your survey \"(.*)\" question$")
    public void setSurveyQuestion(String question) {
        String channelID = ORCASteps.getChannelId();
        questionUpdate.set(question + " " + faker.rockBand().name());
        getSurveyManagementPage().getSurveyForm(channelID).changeQuestion(questionUpdate.get());
    }

    @When("^Customize your survey thank message$")
    public void setSurveyThankMessage() {
        String channelID = ORCASteps.getChannelId();
        thankMessageUpdate.set("Thank you for taking the time to provide us with your feedback. " + faker.gameOfThrones().dragon());
        getSurveyManagementPage().getSurveyForm(channelID).setThankMessage(thankMessageUpdate.get());
    }

    @Then("^Thank Survey thank message was updated on backend for (.*) and (.*) chanel$")
    public void verifyThankQuestionIsUpdated(String tenantOrgName, String chanel) {
        String channelID = ORCASteps.getChannelId();
        List<String> surveyPreviewMessages;
        surveyPreviewMessages = getSurveyManagementPage().getSurveyForm(channelID).getAllMessagesInSurveyPreview();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(surveyPreviewMessages.contains(thankMessageUpdate.get()));
    }

    @Then("^Survey backend was updated for (.*) and (.*) chanel with following attribute$")
    public void verifyThatSurveyBackendWasUpdated(String tenantOrgName, String chanel, Map<String, String> map) {
        String channelID = ORCASteps.getChannelId();
        SurveyManagement configuration = ApiHelper.getSurveyManagementAttributes(channelID);
        String attributeToCheck = map.keySet().stream().findFirst().get();
        Assert.assertEquals(configuration.getSomeValueByMethodName(attributeToCheck), map.get(attributeToCheck), attributeToCheck + " is not updated on backend");
    }

    @Then("^Preview question is updated successfully$")
    public void verifyQuestionPreview() {
        String channelID = ORCASteps.getChannelId();
        List<String> surveyPreviewMessages;
        surveyPreviewMessages = getSurveyManagementPage().getSurveyForm(channelID).getAllMessagesInSurveyPreview();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(surveyPreviewMessages.contains(questionUpdate.get()),
                "Survey question title is not displayed in survey preview");
    }

    @Then("^Preview question is updated successfully for (.*) and (.*) chanel")
    public void verifyQuestionPreviewAPI(String tenantOrgName, String chanel) {
        String channelID = ApiHelper.getChannelID(tenantOrgName, chanel);
        SurveyManagement configuration = ApiHelper.getSurveyManagementAttributes(channelID);
        Assert.assertEquals(configuration.getSurveyQuestionTitle(), questionUpdate.get(), "Preview question is not the same as was set");
    }

    @Then("^Star and Smile Buttons are Disabled$")
    public void starAndSmileButtonsAreDisabled() {
        String channelID = ORCASteps.getChannelId();
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(getSurveyManagementPage().getSurveyForm(channelID).isSmileButtonDisabled(), "Smile button should not be enabled");
        soft.assertTrue(getSurveyManagementPage().getSurveyForm(channelID).isStarButtonDisabled(), "Star button should not be enabled");
        soft.assertAll();
    }

    @Then("^Survey Preview should be displayed with correct data for (.*) channel$")
    public void surveyPreviewShouldBeDisplayedWithCorrectDataForChannel(String channel) {
        String channelID = ORCASteps.getChannelId();
        SurveyManagement configuration = ApiHelper.getSurveyManagementAttributes(channelID);
        List<String> surveyPreviewMessages;
        surveyPreviewMessages = getSurveyManagementPage().getSurveyForm(channelID).getAllMessagesInSurveyPreview();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(surveyPreviewMessages.contains(configuration.getRatingThanksMessage()),
                "Rating thanks message is not displayed in survey preview");
        softAssert.assertTrue(surveyPreviewMessages.contains(configuration.getSurveyQuestionTitle()),
                "Survey question title is not displayed in survey preview");
        softAssert.assertTrue(surveyPreviewMessages.contains(configuration.getCustomerNoteTitle()),
                "Customer note title is not displayed in survey preview");

        softAssert.assertAll();
    }
}
