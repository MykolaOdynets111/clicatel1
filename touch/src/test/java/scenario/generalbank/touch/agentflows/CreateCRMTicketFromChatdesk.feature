@agent_feedback
Feature: Creating CRM tickets

  Background:
    Given Off webchat survey configuration for General Bank Demo
    Given User select General Bank Demo tenant
    Given I login as agent of General Bank Demo
    And Click chat icon
    Given AGENT_FEEDBACK tenant feature is set to true for General Bank Demo

  Scenario: Agent is able to fill form and create CRM ticket
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to Support user's message
    When Agent click "End chat" button
    Then End chat popup for agent should be opened
    Then Agent add 1 tag
    Then Agent type Note:"Note from automation test)", Link:http://NoteTextLink.com, Number:"12345" for CRM ticket
    When Agent click 'Close chat' button
    Then Agent should not see from user chat in agent desk
    Then User should see 'exit' text response for his 'connect to Support' input
    Then CRM ticket is created on backend with correct information



