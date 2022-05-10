
Feature: Stop message for Web Chat

  Background:
    And User select Standard Billing tenant
    Given Update survey management chanel webchat settings by ip for Standard Billing
      | ratingEnabled   | true              |
      | ratingType      | NPS               |
      | ratingScale     | ZERO_TO_TEN       |
    Given I login as agent of Standard Billing
    And Click chat icon

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-26914")
  Scenario: //STOP message for Web Chat
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    And Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to Support user's message
    When User enter //STOP into widget input field
    Then There is no Thank you. Chat soon! response
    Then There is no survey form shown
    Then Text response that contains "You have blocked this contact and won’t receive any messages from it in future. Should you wish to unblock this contact, simply initiate an interaction by sending any message." is shown
    Then Agent should not see from user chat in agent desk
    When Agent select "Closed" left menu option
    And Agent searches and selects chat from touch in chat history list
    Then Agent sees stop message notification in chat history
    When Agent select "Live Chats" left menu option
    And User enter to agent into widget input field
    Then Agent has new conversation request
    And Agent click on new conversation request from touch
    Then Conversation area becomes active with to agent user's message
    When Agent responds with glad to see you again to User
    Then Text response that contains "glad to see you again" is shown



