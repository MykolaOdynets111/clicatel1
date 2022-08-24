@no_widget
@no_chatdesk
@off_survey_management
Feature: Satisfaction Survey

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-18591")
    @TestCaseId("https://jira.clickatell.com/browse/TPORT-121098")
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-105177")
  Scenario Outline: Verify if tenant can Customize customer thank you message text
    Given Setup ORCA <channelType> integration for Standard Billing tenant
    And Update survey management chanel <channelType> settings by ip for Standard Billing
      | ratingEnabled        | true             |
      | surveyType           | CSAT             |
      | ratingScale          | ONE_TO_FIVE       |
      | ratingIcon           | NUMBER           |
      | commentEnabled       | true             |
      | thanksMessageEnabled | true             |
    And I open portal
    Given Login into portal as an admin of Standard Billing account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    Then Survey Management page should be shown
    When Admin selects NPS survey type for <channelType> survey form
    And Customize your survey thank message to Thank you for taking the time to provide us with your feedback.
    And Agent click save survey configuration button for <channelType> survey form
    Then Thank Survey thank message was updated on backend for Standard Billing and <channelType> chanel
    When Admin clicks thank message toggle for survey form
    And Agent click save survey configuration button for <channelType> survey form
    And Wait for 5 second
    Then I select Touch in left menu and Agent Desk in submenu
    And Wait for 5 second
    And I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    And Wait for 5 second
    When Admin clicks thank message toggle for survey form
    And Agent click save survey configuration button for <channelType> survey form
    Examples:
      | channelType         |
      | whatsapp            |
      | abc                 |
      | webchat             |