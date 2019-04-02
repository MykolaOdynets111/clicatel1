@agent_feedback
Feature: Canceling chat ending

  Background:
    Given User select General Bank Demo tenant
    Given I login as agent of General Bank Demo
    And Click chat icon

  Scenario: Verify if agent is able to cancel close chat in chat desk
    Given AGENT_FEEDBACK tenant feature is set to true for General Bank Demo
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to Support user's message
    When Agent click "End chat" button
    Then End chat popup should be opened
    When Agent click 'Cancel' button
    Then Conversation area contains connect to Support user's message
    When Agent sends a new message What do you want? to User
    Then Text response that contains "What do you want?" is shown


