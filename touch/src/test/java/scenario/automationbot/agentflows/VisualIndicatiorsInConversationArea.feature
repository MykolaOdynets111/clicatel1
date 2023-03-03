@no_widget
@dot_control
Feature: Supervisor desk

  Background:
    Given User select Automation Bot tenant
    Given I login as agent of Automation Bot
    Given Setup ORCA whatsapp integration for Automation Bot tenant
    When Send connect to Support message by ORCA
    Then Agent has new conversation request from orca user
    When Agent click on new conversation request from orca
    Then Conversation area becomes active with connect to Support user's message
    Given I login as second agent of Automation Bot
    And Agent transfers chat

  Scenario: Verify if agent can view visual indicators in the conversation area: AGENT_RECEIVE_CHAT; FLAG_CHAT; UNFLAG_CHAT; CLOSE_CHAT
    Then Visual indicator AGENT_RECEIVE_CHAT with "This chat has been assigned to" text, Agent name and time is shown
    When Agent click 'Flag chat' button
    Then Agent sees 'flag' icon in this chat
    And Visual indicator FLAG_CHAT with "This chat has been flagged by" text, Agent name and time is shown
    When Agent click 'Unflag chat' button
    Then Visual indicator UNFLAG_CHAT with "This chat has been un-flagged by" text, Agent name and time is shown
    When Agent closes chat
    And Agent select "Closed" left menu option
    And Agent searches and selects chat from touch in chat history list
    Then Visual indicator CLOSE_CHAT with "Chat closed by" text, Agent name and time is shown

  @orca_api
  @Regression
  @TestCaseId("https://jira.clickatell.com/browse/CCD-2001")
  Scenario: CD :: Agent Desk :: Live Chat :: Transfer Chat :: Verify that agent can view visual indicators if use transfer chat or reject transfer
    Then Second agent receives incoming transfer with "Please take care of this one" note from the another agent
    When Second agent click "Accept transfer" button
    Then Second agent has new conversation request
    When Second agent click on new conversation request from orca
    Then Transfer indicator with "This chat has been transferred by" text, Agent name " to " Second agent name and time is shown
    And Second agent transfers chat
    Then Agent receives incoming transfer with "Incoming Transfer" header
    Then Agent receives incoming transfer with "Please take care of this one" note from the another agent
    When Agent click "Reject transfer" button
    Then Second Agent receives incoming transfer with "Rejected Transfer" header
    When Second Agent click "Accept" button
    And Second Agent has new conversation request
    Then Transfer reject indicator with agent name and "has rejected the chat transfer invitation" text