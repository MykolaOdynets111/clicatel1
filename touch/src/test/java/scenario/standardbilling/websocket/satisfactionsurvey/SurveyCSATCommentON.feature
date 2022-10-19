@no_widget
@no_chatdesk
@off_rating_whatsapp
@off_rating_abc
@off_rating_sms
@Regression
Feature: Satisfaction Survey

  @TestCaseId("https://jira.clickatell.com/browse/CCD-2283")
    @TestCaseId("https://jira.clickatell.com/browse/CCD-2353")
    @TestCaseId("https://jira.clickatell.com/browse/CCD-1844")
  Scenario Outline: CD:: <channelType>:: Settings :: Survey :: Verify if supervisor can enable option to allow customer to leave a note for CSAT survey type
    Given Setup ORCA <channelType> integration for Standard Billing tenant
    And Update survey management chanel <channelType> settings by ip for Standard Billing
      | surveyType     | CSAT        |
      | ratingScale    | ONE_TO_FIVE |
      | ratingIcon     | NUMBER      |
      | ratingTimeout  | 600         |
      | ratingEnabled  | true        |
      | commentEnabled | false       |
    And I open portal
    Given Login into portal as an admin of Standard Billing account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    Then Survey Management page should be shown
    When Admin clicks on channel toggle button for survey form
    And Admin clicks on channel expand button for survey form
    And Admin selects CSAT survey type for <channelType> survey form
    And Agent switch "Allow customer to leave a note" in survey management
    And Agent click save survey configuration button for <channelType> survey form
    Then Agent sees comment field in Survey management form
    Then Survey backend was updated for Standard Billing and <channelType> chanel with following attribute
      | commentEnabled | true |
    Examples:
      | channelType |
      | Whatsapp    |
      | ABC         |
      | SMS         |
    #  | webchat     |