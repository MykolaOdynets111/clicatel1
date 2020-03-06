@no_widget
@no_chatdesk
Feature: Satisfaction Survey: Star configuration

  Background:
    Given Update survey management chanel webchat settings by ip for Standard Billing
      | setRatingEnabled   | true             |
      | setRatingType      | NPS              |
      | setRatingScale     | ZERO_TO_TEN      |
      | setRatingIcon      | NUMBER           |
    And I open portal
    Given Login into portal as an admin of Standard Billing account

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-19256")
  Scenario: verify if supervisor can select stars  as option for customers to choose in CSAT survey type
    When I select Touch in left menu and Touch Preferences in submenu
    And Click "Survey management" nav button
    Then Survey Management page should be shown
    And Selects CSAT survey type
    When Agent select star as and icon for rating range
    Then CSAT scale start form 1 and has correct limit variants 5, 7, 10 in dropdown and star set as type
    When Agent select 7 as number limit from dropdown
    And Agent click save survey configuration button
    Then Agent see survey range 7 in rating scale
