
Feature: END message for Web Chat

  Background:
    And User select Standard Billing tenant
    Given Update survey management chanel webchat settings by ip for Standard Billing
      | ratingEnabled   | true              |
      | ratingType      | NPS               |
      | ratingScale     | ZERO_TO_TEN       |
    Given I login as agent of Standard Billing
    And Click chat icon

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-28812")
  Scenario: //END message for Web Chat
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    And Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to Support user's message
    When User enter //END into widget input field
    Then User see NPS survey form
    When Reject survey form submit
    Then Text response that contains "Thank you. Chat soon!" is shown
    Then Agent should not see from user chat in agent desk





