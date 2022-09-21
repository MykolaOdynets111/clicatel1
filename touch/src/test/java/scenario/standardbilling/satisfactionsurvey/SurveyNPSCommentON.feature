@no_widget
@off_rating_whatsapp
@off_rating_abc
@no_chatdesk
Feature: Satisfaction Survey

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-19237")
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-105173")
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1764")
  Scenario Outline: CD:: SMS:: Settings :: Survey :: Verify if supervisor can enable option to allow customer to leave a note for NPS survey type
    Given Setup ORCA <channelType> integration for Standard Billing tenant
    And Update survey management chanel <channelType> settings by ip for Standard Billing
      | ratingEnabled        | true        |
      | surveyType           | CSAT        |
      | ratingScale          | ONE_TO_FIVE |
      | ratingIcon           | NUMBER      |
      | commentEnabled       | true        |
      | thanksMessageEnabled | true        |
    And I open portal
    Given Login into portal as an admin of Standard Billing account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    Then Survey Management page should be shown
    When Admin selects NPS survey type for <channelType> survey form
    And Agent switch "Allow customer to leave a note" in survey management
    And Customize your survey notes message to Thank you for taking the time to leave a note.
    And Agent click save survey configuration button for <channelType> survey form
    Then Survey notes was updated on backend for Standard Billing and <channelType> chanel
    Then I select Touch in left menu and Agent Desk in submenu
    And I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    When Admin clicks notes toggle for survey form
    And Agent click save survey configuration button for <channelType> survey form
    Examples:
      | channelType |
      | whatsapp    |
      | abc         |
      | sms         |