@no_widget
@no_chatdesk
Feature: Satisfaction Survey: Numbers configuration

  Background:
    Given Update survey management chanel webchat settings by ip for Standard Billing
      | setRatingEnabled   | true             |
      | setRatingType      | NPS              |
      | setRatingScale     | ZERO_TO_TEN      |
      | setRatingIcon      | NUMBER           |
    And I open portal
    Given Login into portal as an admin of Standard Billing account

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-19257")
  Scenario: verify if supervisor can select numbers as option for customers to choose in CSAT survey type
    When I select Touch in left menu and Touch Preferences in submenu
    And Click "Survey management" nav button
    Then Survey Management page should be shown
    And Selects CSAT survey type
    Then CSAT scale start form 1 and has correct limit variants 5, 7, 10 in dropdown and number set as type
    When Agent select 10 as number limit from dropdown
    And Agent click save survey configuration button
    Then Agent see survey range 10 in rating scale
