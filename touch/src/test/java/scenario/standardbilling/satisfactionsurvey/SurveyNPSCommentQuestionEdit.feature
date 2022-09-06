@no_widget
@no_chatdesk
@off_survey_management
Feature: Satisfaction Survey

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-19236")
  @Regression
  Scenario Outline: verify if supervisor can customize survey question for whatsapp NPS survey type
    Given Setup ORCA <channelType> integration for Standard Billing tenant
    And Update survey management chanel <channelType> settings by ip for Standard Billing
      | ratingEnabled | true       |
      | surveyType    | CSAT       |
      | ratingScale   | ONE_TO_FIVE |
      | ratingIcon    | NUMBER     |
    And I open portal
    Given Login into portal as an admin of Standard Billing account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    Then Survey Management page should be shown
    When Admin clicks on channel toggle button for survey form
    And Admin clicks on channel expand button for survey form
    And Admin selects NPS survey type for <channelType> survey form
    And Customize your survey "Please rate your experience with our agent" question
    And Agent click save survey configuration button for <channelType> survey form
    Then Preview question is updated successfully
    Examples:
      | channelType |
      | whatsapp    |
      | abc         |
#      | webchat     |