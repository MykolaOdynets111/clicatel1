@off_survey_management
@skip
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


  @TestCaseId("https://jira.clickatell.com/browse/TPORT-18595")
  Scenario: verify if agent is able to see NPS rating from the survey that the customer completed on the chat view
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    And Agent click on new conversation request from touch
    When Agent closes chat
    Then User see NPS survey form
    When Submit survey form with Automation rate comment and 8 rate
    Then Text response that contains "start_new_conversation" is shown
    And All session attributes are closed in DB
    When Agent select "Closed" left menu option
    And Agent searches and selects chat from touch in chat history list
    Then Agent sees Rate Card in chat history with 8 rate selected and Automation rate comment