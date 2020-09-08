@no_widget
@dot_control
@start_server
Feature: STOP message for .Control

  Background:
    Given Create .Control integration for Standard Billing and adapter: whatsapp
    Given Prepare payload for sending chat to agent message for .Control
    Given Send parameterized init call with clientId context correct response is returned
    And Send message call

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-26915")
  Scenario: //STOP message for .Control
    Given I login as agent of Standard Billing
    Then Agent has new conversation request from dotcontrol user
    When Agent click on new conversation request from dotcontrol
    And Agent responds with welcome to User
    Then Verify dot .Control returns welcome response during 10 seconds
    When Prepare payload for sending //stop message for .Control
    And Send message call
    Then Verify .Control does not returns Thank you. Chat soon! response during 4 seconds
    Then Verify dot .Control returns You have blocked this contact and won’t receive any messages from it in future. Should you wish to unblock this contact, simply initiate an interaction by sending any message. response during 10 seconds
    Then Agent should not see from user chat in agent desk from dotcontrol
    When Agent select "Closed" left menu option
    And Agent searches and selects chat from dotcontrol in chat history list
    Then Agent sees stop message notification in chat history
    When Agent select "Live Chats" left menu option
    When Prepare payload for sending to agent message for .Control
    And Send message call
    Then Agent has new conversation request from dotcontrol user
    When Agent click on new conversation request from dotcontrol
    Then Conversation area becomes active with to agent user's message
    When Agent responds with glad to see you again to User
    Then Verify dot .Control returns glad to see you again response during 10 seconds
