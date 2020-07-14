@no_widget
@no_chatdesk
Feature: Satisfaction Survey: Question configuration CSAT whatsapp

  Background:
    Given Update survey management chanel whatsapp settings by ip for General Bank Demo
      | ratingEnabled   | true             |
      | ratingType      | NPS              |
      | ratingScale     | ZERO_TO_TEN      |
      | ratingIcon      | NUMBER           |
    And I open portal
    Given Login into portal as an admin of General Bank Demo account

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-19249")
  Scenario: verify if supervisor can customize survey question for whatsapp CSAT survey type
    When I select Touch in left menu and Touch Preferences in submenu
    And Navigate to Surveys page
    Then Survey Management page should be shown
    When Switch to whatsapp survey configuration
    And Selects CSAT survey type
    When Customize your survey "Please rate your experience with our agent" question
    And Agent click save survey configuration button
    Then Preview question is updated successfully
