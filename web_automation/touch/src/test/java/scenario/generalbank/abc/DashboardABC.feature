@no_widget
Feature: Apple Business Chat :: Dashboard

  Background:
    Given Setup ORCA integration for General Bank Demo tenant
    And Update survey management chanel abc settings by ip for General Bank Demo
      | ratingEnabled | true        |
      | ratingType    | NPS         |
      | ratingScale   | ZERO_TO_TEN |
      | ratingIcon    | NUMBER      |

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-45561")
  Scenario: Dashboard:: Verify that if NPS surveys are categorize as Detractors if Apple Business Chat user chooses between 0 – 6
    And I open portal
    Given Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    Then Survey Management page should be shown
    When Admin selects NPS survey type for abc survey form
    Then Survey Preview should be displayed with correct data for abc channel
    When Admin selects CSAT survey type for abc survey form
    Then Survey Preview should be displayed with correct data for abc channel