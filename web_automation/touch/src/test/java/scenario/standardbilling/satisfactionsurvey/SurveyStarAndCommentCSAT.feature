
Feature: Satisfaction Survey: User select Star and Comment with CSAT

  Background:
    And User select Standard Billing tenant
    Given Update survey management chanel webchat settings by ip for Standard Billing
      | ratingEnabled   | true              |
      | ratingType      | CSAT              |
      | ratingScale     | ONE_TO_FIVE       |
      | ratingIcon      | STAR              |
      | commentEnabled  | true              |
    Given I login as agent of Standard Billing
    And Click chat icon

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-19271")
  Scenario: Verify if user can "star rate and add comments" through web chat for CSAT survey type
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    And Agent click on new conversation request from touch
    When Agent closes chat
    Then User see CSAT survey form
    When Submit survey form with Automation rate comment and 5 rate
    Then Text response that contains "Thank you. Chat soon!" is shown
    When Agent select "Closed" left menu option
    And Agent searches and selects chat in chat history list
    Then Agent sees Rate Card in chat history with 5 rate selected and Automation rate comment