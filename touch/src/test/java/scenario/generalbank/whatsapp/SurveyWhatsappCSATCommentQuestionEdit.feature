@no_widget
@no_chatdesk
@off_survey_management
Feature: Satisfaction Survey

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2286")
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1790")
  @Regression
  Scenario Outline:verify if supervisor can customize survey question for whatsapp CSAT survey type
    Given Setup ORCA <channelType> integration for General Bank Demo tenant
    And Update survey management chanel <channelType> settings by ip for General Bank Demo
      | ratingEnabled | true       |
      | surveyType    | CSAT       |
      | ratingScale   | ONE_TO_FIVE |
      | ratingIcon    | NUMBER     |
    And I open portal
    Given Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    Then Survey Management page should be shown
    When Admin clicks on channel toggle button for survey form
    And Admin clicks on channel expand button for survey form
    And Admin selects CSAT survey type for <channelType> survey form
    And Customize your survey "Please rate your experience with our agent" question
    And Agent click save survey configuration button for <channelType> survey form
    Then Preview question is updated successfully
    Examples:
      | channelType |
      | whatsapp    |
      | abc         |