@no_widget
@no_chatdesk
Feature: Satisfaction Survey: Smile configuration

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
    When I select Touch in left menu and Touch Preferences in submenu
    And Click "Survey management" nav button
    Then Survey Management page should be shown
    And Selects CSAT survey type
    When Agent select smile as and icon for rating range
    Then CSAT scale start form 1 and has correct limit variants 3, 5 in dropdown and smile set as type
    When Agent select 5 as number limit from dropdown
    And Agent click save survey configuration button
    Then Agent see survey range 5 in rating scale
    Then Survey backend was updated for Standard Billing and webchat chanel with following attribute
      | ratingIcon     | SMILE      |
    Then Survey backend was updated for Standard Billing and webchat chanel with following attribute
      | ratingScale     | ONE_TO_FIVE       |
    When Agent select 3 as number limit from dropdown
    And Agent click save survey configuration button
    Then Agent see survey range 3 in rating scale
    Then Survey backend was updated for Standard Billing and webchat chanel with following attribute
      | ratingScale     | ONE_TO_THREE      |