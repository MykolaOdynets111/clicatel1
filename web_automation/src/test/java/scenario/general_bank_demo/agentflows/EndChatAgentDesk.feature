Feature: Interaction with End chat button on chatdesk

  Background:
    Given I login as agent of General Bank Demo
    Given User profile for generalbank is created
    Given User select General Bank Demo tenant
    And Click chat icon

  Scenario: Verify if agent is able to close chat in chat desk
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation
    Then Conversation area becomes active with connect to Support user's message in it
    When Agent click "End chat" button
    Then End chat popup should be opened
    When Agent click 'Close chat' button
    Then From agent chat should be removed from agent desk
    Then User have to receive 'exit' text response for his 'connect to Support' input

