@no_widget
@off_rating_whatsapp
@off_rating_abc
@off_rating_sms
@Regression
Feature: WhatsApp ORCA :: Surveys

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1877")
  @TestCaseId("https://jira.clickatell.com/browse/CCD-1828")
  Scenario Outline: CD:: <channelType>:: Settings :: Survey :: Verify if Supervisor is able to see the survey preview header for <channelType> channel and select between NPS(Net Promotor Score) or CSAT(Customer Satisfaction Score) survey type in the survey tab
    Given Setup ORCA <channelType> integration for General Bank Demo tenant
    And Update survey management chanel <channelType> settings by ip for General Bank Demo
      | ratingEnabled | true        |
      | surveyType    | CSAT         |
      | ratingScale   | ONE_TO_FIVE |
      | ratingIcon    | NUMBER      |
    And I open portal
    Given Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    Then Survey Management page should be shown
    When Admin clicks on channel toggle button for survey form
    And Admin clicks on channel expand button for survey form
    And Admin selects NPS survey type for <channelType> survey form
    And Agent switch "Allow customer to give thank message" in survey management
    And Agent switch "Allow customer to leave a note" in survey management
    Then Survey Preview should be displayed with correct data for <channelType> channel
    When Admin selects CSAT survey type for <channelType> survey form
    Then Survey Preview should be displayed with correct data for <channelType> channel
    Examples:
      | channelType |
      | Whatsapp    |
      | ABC         |
      | SMS         |

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1728")
  Scenario Outline: CD:: Survey:: CSAT:: Verify if "Customize Rating type" section is removed from the survey tab
    Given Setup ORCA <channelType> integration for General Bank Demo tenant
    And Update survey management chanel <channelType> settings by ip for General Bank Demo
      | ratingEnabled | true        |
      | surveyType    | CSAT        |
      | ratingScale   | ONE_TO_FIVE |
      | ratingIcon    | NUMBER      |
    And I open portal
    Given Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    Then Survey Management page should be shown
    When Admin clicks on channel toggle button for survey form
    And Admin clicks on channel expand button for survey form
    And Admin selects CSAT survey type for <channelType> survey form
    Then Agent checks rating dropdown visibility for <channelType> survey form
    And Survey backend was updated for General Bank Demo and <channelType> chanel with following attribute
      | ratingScale | ONE_TO_FIVE |
    Examples:
      | channelType |
      | Whatsapp    |
      | ABC         |
      | SMS         |