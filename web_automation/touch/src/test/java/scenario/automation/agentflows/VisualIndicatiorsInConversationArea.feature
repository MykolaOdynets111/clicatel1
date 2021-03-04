@no_widget
@dot_control
Feature: Supervisor desk

  Background:
    Given User select General Bank Demo tenant
    Given I login as agent of General Bank Demo
    And Click chat icon

  Scenario: Verify if agent can view visual indicators in the conversation area: AGENT_RECEIVE_CHAT; FLAG_CHAT; UNFLAG_CHAT; CLOSE_CHAT
    When User enter connect to agent into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation
    Then Visual indicator AGENT_RECEIVE_CHAT with This chat has been assigned to text, Agent name and time is shown
    When Agent click 'Flag chat' button
    Then Agent sees 'flag' icon in this chat
    And Visual indicator FLAG_CHAT with This chat has been flagged by text, Agent name and time is shown
    When Agent click 'Unflag chat' button
    Then Visual indicator UNFLAG_CHAT with This chat has been un-flagged by text, Agent name and time is shown
    When Agent closes chat
    And Agent select "Closed" left menu option
    And Agent searches and selects chat from touch in chat history list
    Then Visual indicator CLOSE_CHAT with Chat closed by text, Agent name and time is shown





