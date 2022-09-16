@no_widget
@off_rating_whatsapp
Feature: WhatsApp ORCA :: Surveys

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1877")
    @TestCaseId("https://jira.clickatell.com/browse/CCD-1173")
    @TestCaseId("https://jira.clickatell.com/browse/CCD-2394")
    @TestCaseId("https://jira.clickatell.com/browse/CCD-1933")
    @TestCaseId("https://jira.clickatell.com/browse/CCD-1828")
  Scenario Outline: WA: : Surveys: Verify if the survey preview header for Whatsapp shows as Whatsapp Chat Preview
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
      | whatsapp    |
      | abc         |
      | sms         |

  @TestCaseId("https://jira.clickatell.com/browse/CCD-1719")
    @TestCaseId("https://jira.clickatell.com/browse/CCD-1728")
    @Regression
  Scenario Outline: WA: Survey: Verify if CSAT ratings in the survey configuration to be standardized to cater for only 1-5 ratings
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
      | whatsapp    |
      | abc         |
      | sms         |