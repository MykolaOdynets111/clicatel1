@no_widget
@off_rating_whatsapp
@off_rating_abc
@off_rating_sms
@no_chatdesk
@Regression
Feature: Satisfaction Survey

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2243")
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1348")
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1764")
  Scenario Outline: CD :: Dashboard :: Configure :: Settings :: Survey :: <channelType> :: Verify that Admin/Supervisor should be able to turn ON/OFF a request for user to Leave a note
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
      | Whatsapp    |
      | ABC         |
      | SMS         |