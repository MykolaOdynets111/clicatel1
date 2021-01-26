@no_widget
@off_rating_abc
Feature: Apple Business Chat :: Surveys

  Background:
    Given Setup ORCA integration for General Bank Demo tenant
    And Update survey management chanel abc settings by ip for General Bank Demo
      | ratingEnabled | true        |
      | ratingType    | NPS         |
      | ratingScale   | ZERO_TO_TEN |
      | ratingIcon    | NUMBER      |

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-45561")
  Scenario: ABC: : Surveys: Verify if the survey preview header for Apple shows as Apple Business Chat Preview
    And I open portal
    Given Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    Then Survey Management page should be shown
    When Admin selects NPS survey type for abc survey form
    Then Survey Preview should be displayed with correct data for abc channel
    When Admin selects CSAT survey type for abc survey form
    Then Survey Preview should be displayed with correct data for abc channel

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-45559")
  Scenario: ABC: Survey: verify if supervisor can set rating option 1-10, 1-5,1-7 in whatsapp apple business chat CSAT suvery
    And I open portal
    Given Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    Then Survey Management page should be shown
    When Admin selects CSAT survey type for abc survey form
    When Agent select 1 to 5 as number limit from dropdown for abc survey form
    And Agent click save survey configuration button for abc survey form
    Then Survey backend was updated for General Bank Demo and abc chanel with following attribute
      | ratingScale     |     ONE_TO_FIVE   |
    When Agent select 1 to 7 as number limit from dropdown for abc survey form
    And Agent click save survey configuration button for abc survey form
    Then Survey backend was updated for General Bank Demo and abc chanel with following attribute
      | ratingScale     |     ONE_TO_SEVEN   |
    When Agent select 1 to 10 as number limit from dropdown for abc survey form
    And Agent click save survey configuration button for abc survey form
    Then Survey backend was updated for General Bank Demo and abc chanel with following attribute
      | ratingScale     |     ONE_TO_TEN   |