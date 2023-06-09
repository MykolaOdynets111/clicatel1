@agent_feedback
Feature: Interaction with CRM tickets (agent mode)

  Background:
    Given AGENT_FEEDBACK tenant feature is set to true for Automation
    Given User select Automation tenant
    Given I login as agent of Automation
    And Click chat icon

  Scenario: Agent is able to fill only "Note" and create CRM ticket
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to Support user's message
    When Agent click "End chat" button
    Then End chat popup for agent should be opened
    Then Agent type Note:"Note from automation test)", Link:, Number: for CRM ticket
    When Agent click 'Close chat' button
    Then Agent should not see from user chat in agent desk
    Then User should see 'exit' text response for his 'connect to Support' input
    Then CRM ticket is created on backend with correct information

#commented according to Amrit
@skip
  Scenario: Agent is able to fill only "Link" and create CRM ticket
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to Support user's message
    When Agent click "End chat" button
    Then End chat popup for agent should be opened
    Then Agent type Note:, Link:http://NoteTextLink.com, Number: for CRM ticket
    When Agent click 'Close chat' button
    Then Agent should not see from user chat in agent desk
    Then User should see 'exit' text response for his 'connect to Support' input
    Then CRM ticket is created on backend with correct information

#commented according to Amrit
@skip
  Scenario: Agent is able fill only "Number" and create CRM ticket
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to Support user's message
    When Agent click "End chat" button
    Then End chat popup for agent should be opened
    Then Agent type Note:, Link:, Number:12345 for CRM ticket
    When Agent click 'Close chat' button
    Then Agent should not see from user chat in agent desk
    Then User should see 'exit' text response for his 'connect to Support' input
    Then CRM ticket is created on backend with correct information


