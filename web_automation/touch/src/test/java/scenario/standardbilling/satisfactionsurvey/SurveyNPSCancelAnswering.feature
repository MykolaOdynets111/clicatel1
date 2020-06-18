
Feature: Satisfaction Survey: Survey rejection

  Background:
    And User select Standard Billing tenant
    Given Update survey management chanel webchat settings by ip for Standard Billing
      | ratingEnabled   | true              |
      | ratingType      | NPS               |
      | ratingScale     | ZERO_TO_TEN       |
    Given I login as agent of Standard Billing
    And Click chat icon

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-18589https://jira.clickatell.com/browse/TPORT-18589")
  Scenario: Verify if user can choose not to answer the survey question for web chat NPS survey
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    And Agent click on new conversation request from touch
    When Agent closes chat
    Then User see NPS survey form
    When Reject survey form submit
    Then Text response that contains "start_new_conversation" is shown
    When Agent select "Closed" left menu option
    And Agent searches and selects chat from touch in chat history list
    Then Agent does not see Rate Card in chat history