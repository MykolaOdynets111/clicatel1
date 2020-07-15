@no_widget
@no_chatdesk
Feature: Satisfaction Survey: Numbers configuration

  Background:
    Given Update survey management chanel webchat settings by ip for Standard Billing
      | ratingEnabled   | true             |
      | ratingType      | NPS              |
      | ratingScale     | ZERO_TO_TEN      |
      | ratingIcon      | NUMBER           |
    And I open portal
    Given Login into portal as an admin of Standard Billing account

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-19257")
  Scenario: verify if supervisor can select numbers as option for customers to choose in CSAT survey type
    When I select Touch in left menu and Dashboard in submenu
    And Navigate to Surveys page
    Then Survey Management page should be shown
    And Selects CSAT survey type
    Then CSAT scale has correct limit variants 1 to 5, 1 to 7, 1 to 10 in dropdown and number set as type
    When Agent select 1 to 5 as number limit from dropdown
    And Agent click save survey configuration button
    Then Agent see survey range 5 in rating scale
    Then Survey backend was updated for Standard Billing and webchat chanel with following attribute
      | ratingScale      | ONE_TO_FIVE     |
    When Agent select 1 to 7 as number limit from dropdown
    And Agent click save survey configuration button
    Then Agent see survey range 7 in rating scale
    Then Survey backend was updated for Standard Billing and webchat chanel with following attribute
      | ratingScale      | ONE_TO_SEVEN     |
    When Agent select 1 to 10 as number limit from dropdown
    And Agent click save survey configuration button
    Then Agent see survey range 10 in rating scale
    Then Survey backend was updated for Standard Billing and webchat chanel with following attribute
      | ratingScale      | ONE_TO_TEN     |