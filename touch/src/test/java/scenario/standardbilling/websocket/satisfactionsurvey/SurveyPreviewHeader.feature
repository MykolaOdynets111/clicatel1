@no_widget
@off_survey_sms
@off_rating_whatsapp
@off_rating_abc
@Regression
Feature: Satisfaction Survey

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2411")
  Scenario Outline: CD:: Dashboard:: WA :: Surveys:: Verify if the "survey preview" section is displayed for whatsapp survey
    Given Setup ORCA <channelType> integration for Standard Billing tenant
    And Update survey management chanel <channelType> settings by ip for Standard Billing
      | surveyType           | CSAT        |
      | ratingScale          | ONE_TO_FIVE |
      | ratingIcon           | NUMBER      |
      | ratingTimeout        | 600         |
      | ratingEnabled        | true        |
      | commentEnabled       | true        |
      | thanksMessageEnabled | true        |
    And I open portal
    Given Login into portal as an admin of Standard Billing account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    Then Survey Management page should be shown
    And Then on the right side the preview card heading shows as Survey Preview:
    Examples:
      | channelType |
      | abc         |
      | whatsapp    |
      | sms         |