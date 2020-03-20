@no_widget
@no_chatdesk
Feature: Satisfaction Survey: Question configuration CSAT

  Background:
    Given Update survey management chanel webchat settings by ip for Standard Billing
      | ratingEnabled   | true             |
      | ratingType      | NPS              |
      | ratingScale     | ZERO_TO_TEN      |
      | ratingIcon      | NUMBER           |
    And I open portal
    Given Login into portal as an admin of Standard Billing account

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-19243")
  Scenario: Verify if tenant can customize his own survey questions for CSAT survey type
    When I select Touch in left menu and Touch Preferences in submenu
    And Click "Survey management" nav button
    Then Survey Management page should be shown
    When Selects CSAT survey type
    And Customize your survey "Please rate your experience with our agent" question
    When Agent click save survey configuration button
    Then Preview question is updated successfully
