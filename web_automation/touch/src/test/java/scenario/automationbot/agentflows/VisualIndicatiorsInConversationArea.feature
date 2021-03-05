@no_widget
@dot_control
Feature: Supervisor desk

  Background:
    Given User select Automation bot tenant
    Given I login as agent of Automation bot
    And Click chat icon
    When User enter connect to agent into widget input field
    And Agent has new conversation request
    When Agent click on new conversation

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


  Scenario: Verify that agent can view visual indicators if use transfer chat or reject transfer
    Given I login as second agent of Automation bot
    And Agent transfers chat
    Then Second agent receives incoming transfer with "Please take care of this one" note from the another agent
    When Second agent click "Accept transfer" button
    Then Second agent has new conversation request
    When Second agent click on new conversation
    Then Transfer indicator INVITE_AGENT with "This chat has been transferred by" text, First agent name " to " Second agent name and time is shown
    And Second agent transfers chat
    When Agent click "Reject transfer" button
    Then Second Agent receives incoming transfer with "Rejected Transfer" header
    When Second Agent click "Accept" button
    And Second Agent has new conversation request
    Then Transfer reject indicator AGENT_REJECT_CHAT with First agent name and "has rejected the chat transfer invitation" text




