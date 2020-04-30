
Feature: Satisfaction Survey: Comment field turning off

  Background:
    And User select Standard Billing tenant
    Given Update survey management chanel webchat settings by ip for Standard Billing
      | ratingEnabled   | true              |
      | ratingType      | CSAT              |
      | ratingScale     | ONE_TO_FIVE       |
      | ratingIcon      | STAR              |
      | commentEnabled  | false             |
    Given I login as agent of Standard Billing
    And Click chat icon

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-19274")
  Scenario: Verify if user is not able to add comments if the field "Allow customer to leave note" is disabled
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    And Agent click on new conversation request from touch
    When Agent closes chat
    Then User see CSAT survey form
    And No comment field in Survey form is shown
    When Submit survey form with no comment and 4 rate
    Then Text response that contains "Thank you. Chat soon!" is shown
    When Agent select "Closed" left menu option
    And Agent searches and selects chat from touch in chat history list
    Then Agent sees Rate Card in chat history with 4 rate selected and no comment