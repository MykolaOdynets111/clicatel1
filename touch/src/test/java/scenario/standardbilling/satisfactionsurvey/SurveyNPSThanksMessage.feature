@off_survey_management
Feature: Satisfaction Survey

  Background:
    And User select Standard Billing tenant
    Given Update survey management chanel webchat settings by ip for Standard Billing
      | ratingEnabled        | true              |
      | ratingType           | NPS               |
      | ratingScale          | ZERO_TO_TEN       |
      | ratingIcon           | NUMBER            |
      | commentEnabled       | true              |
      | thanksMessageEnabled | true              |
    Given I login as agent of Standard Billing
    And Click chat icon

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-18593")
  Scenario: Verify if user receives customized Thank You message in response to answering survey
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    And Agent click on new conversation request from touch
    When Agent closes chat
    Then User see NPS survey form
    When Submit survey form with Automation rate comment and 8 rate
    Then User see correct Thanks message from Survey management