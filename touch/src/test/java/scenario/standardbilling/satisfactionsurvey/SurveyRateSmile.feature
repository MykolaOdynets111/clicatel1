@no_widget
@no_chatdesk
@off_survey_management
Feature: Satisfaction Survey

  Background:
    Given Update survey management chanel webchat settings by ip for Standard Billing
      | ratingEnabled   | true             |
      | ratingType      | NPS              |
      | ratingScale     | ZERO_TO_TEN      |
      | ratingIcon      | NUMBER           |
    And I open portal
    Given Login into portal as an admin of Standard Billing account

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-19255")
  Scenario: verify if supervisor can select smiley faces as option for customers to choose in CSAT survey type
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    Then Survey Management page should be shown
    And Selects CSAT survey type
    When Agent select smile as and icon for rating range
    Then CSAT scale has correct limit variants 1 to 3, 1 to 5 in dropdown and smile set as type
    When Agent select 1 to 5 as number limit from dropdown
    And Agent click save survey configuration button
    Then Agent see survey range 5 in rating scale
    Then Survey backend was updated for Standard Billing and webchat chanel with following attribute
      | ratingIcon     | SMILE      |
    Then Survey backend was updated for Standard Billing and webchat chanel with following attribute
      | ratingScale     | ONE_TO_FIVE       |
    When Agent select 1 to 3 as number limit from dropdown
    And Agent click save survey configuration button
    Then Agent see survey range 3 in rating scale
    Then Survey backend was updated for Standard Billing and webchat chanel with following attribute
      | ratingScale     | ONE_TO_THREE      |