@no_widget
@off_rating_whatsapp
@off_rating_abc
@no_chatdesk
Feature: Satisfaction Survey

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2286")
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1790")
  @Regression
  Scenario Outline: Verify if tenant can customize his own survey questions for CSAT survey type
    Given Setup ORCA <channelType> integration for Standard Billing tenant
    And Update survey management chanel <channelType> settings by ip for Standard Billing
      | ratingEnabled   | true             |
      | surveyType      | NPS              |
      | ratingScale     | ZERO_TO_TEN      |
      | ratingIcon      | NUMBER           |
    And I open portal
    And Login into portal as an admin of Standard Billing account
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
      | channelType         |
      | whatsapp            |
      | abc                 |
    #  | webchat             |