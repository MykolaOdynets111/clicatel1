
Feature: Satisfaction Survey: Survey rejection

  Background:
    And User select Standard Billing tenant
    Given Update survey management chanel webchat settings by ip for Standard Billing
      | setRatingEnabled   | true              |
      | setRatingType      | CSAT              |
      | setRatingScale     | ONE_TO_TEN      |
    Given I login as agent of Standard Billing
    And Click chat icon

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-19272")
  Scenario: Verify if user can choose not to answer the survey question for web chat CSAT survey
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    And Agent click on new conversation request from touch
    When Agent closes chat
    Then User see CSAT survey form
    When Reject survey form submit
    Then Text response that contains "Thank you. Chat soon!" is shown
    When Agent select "Chat history" filter option
    And Agent searches and selects chat in chat history list
    Then Agent does not see Rate Card in chat history