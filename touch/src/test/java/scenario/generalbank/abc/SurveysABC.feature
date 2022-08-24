@no_widget
@off_rating_abc
Feature: Apple Business Chat :: Surveys

  Background:
    Given Setup ORCA abc integration for General Bank Demo tenant
    And Update survey management chanel abc settings by ip for General Bank Demo
      | ratingEnabled        | true        |
      | surveyType           | CSAT        |
      | ratingScale          | ONE_TO_FIVE |
      | ratingIcon           | NUMBER      |
      | ratingThanksMessage  | HelloWorld  |
      | surveyQuestionTitle  | Question    |
      | customerNoteTitle    | Note        |
      | commentEnabled       | true        |
      | thanksMessageEnabled | true        |

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-117475")
  @TestCaseId("https://jira.clickatell.com/browse/TPORT-105166")
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

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-120988")
  Scenario: ABC: Survey: Verify if CSAT ratings in the survey configuration to be standardized to cater for only 1-5 ratings
    And I open portal
    Given Login into portal as an admin of General Bank Demo account
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    Then Survey Management page should be shown
    When Admin selects CSAT survey type for abc survey form
    Then Agent checks rating dropdown visibility for abc survey form
    And Survey backend was updated for General Bank Demo and whatsapp chanel with following attribute
      | ratingScale | ONE_TO_FIVE |