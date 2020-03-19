@no_widget
@no_chatdesk
Feature: Satisfaction Survey: NPS Star and Smile are disabled

  Background:
    Given Update survey management chanel webchat settings by ip for Standard Billing
      | ratingEnabled   | true             |
      | ratingType      | CSAT              |
      | ratingScale     | ONE_TO_TEN      |
      | ratingIcon      | NUMBER           |
    And I open portal
    Given Login into portal as an admin of Standard Billing account

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-19234")
  Scenario: Verify if smileys and stars are disabled for NPS web chat survey configuration
    When I select Touch in left menu and Touch Preferences in submenu
    And Click "Survey management" nav button
    Then Survey Management page should be shown
    When Selects NPS survey type
    Then Star and Smile Buttons are Disabled
