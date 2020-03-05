
Feature: Satisfaction Survey: Test Main Flow with NSP

  Background:
    And User select Standard Billing tenant
    Given Update survey management chanel webchat settings by ip for Standard Billing
      | setRatingEnabled   | true             |
      | setRatingType      | NPS              |
      | setRatingScale     | ZERO_TO_TEN      |
      | setCommentEnabled  | true             |
    Given I login as agent of Standard Billing
    And Click chat icon

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-19275")
  Scenario: verify if user can 'rate and leave comment' for webchat NPS survey type
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    And Agent click on new conversation request from touch
    When Agent closes chat
    Then User see NPS survey form
    When Submit survey form with Automation comment comment and 10 rate
    Then Text response that contains "Thank you. Chat soon!" is shown