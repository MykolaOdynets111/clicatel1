@no_widget
@off_rating_abc
Feature: WhatsApp ORCA :: Surveys

  Background:
    Given Setup ORCA whatsapp integration for General Bank Demo tenant
    And Update survey management chanel whatsapp settings by ip for General Bank Demo
      | ratingEnabled | true        |
      | surveyType    | CSAT         |
      | ratingScale   | ONE_TO_FIVE |
      | ratingIcon    | NUMBER      |

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-117475")
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-105166")
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-18586")
  Scenario: WA: : Surveys: Verify if the survey preview header for Whatsapp shows as Whatsapp Chat Preview
    And I open portal
    Given Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    Then Survey Management page should be shown
    When Admin clicks on channel toggle button for survey form
    And Admin clicks on channel expand button for survey form
    And Admin selects NPS survey type for whatsapp survey form
    Then Survey Preview should be displayed with correct data for whatsapp channel
    When Admin selects CSAT survey type for whatsapp survey form
    Then Survey Preview should be displayed with correct data for whatsapp channel

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-120988")
  Scenario: WA: Survey: Verify if CSAT ratings in the survey configuration to be standardized to cater for only 1-5 ratings
    And I open portal
    Given Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    Then Survey Management page should be shown
    When Admin clicks on channel toggle button for survey form
    And Admin clicks on channel expand button for survey form
    And Admin selects CSAT survey type for whatsapp survey form
    Then Agent checks rating dropdown visibility for whatsapp survey form
    And Survey backend was updated for General Bank Demo and whatsapp chanel with following attribute
      | ratingScale     |     ONE_TO_FIVE   |