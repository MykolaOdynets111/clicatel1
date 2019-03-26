Feature: Canceling chat ending

  Background:
    Given User select General Bank Demo tenant
    Given I login as agent of General Bank Demo
    And Click chat icon

  Scenario: Verify if agent is able to cancel close chat in chat desk
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to Support user's message
    When Agent click "End chat" button
    Then End chat popup should be opened
    When Agent click 'Cancel' button
    Then Conversation area contains connect to Support user's message
    When Agent replays with Wat do you want? message
    Then Text response that contains "Wat do you want?" is shown


