@agent_feedback
Feature: Canceling chat ending

  Background:
    Given User select General Bank Demo tenant
    Given I login as agent of General Bank Demo
    And Click chat icon

  Scenario: Agent is able to cancel 'end chat' in chat desk
    Given AGENT_FEEDBACK tenant feature is set to true for General Bank Demo
    When User enter connect to Support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with connect to Support user's message
    When Agent click "End chat" button
    Then End chat popup should be opened
    Then Agent can see default Supply a note... placeholder for note if there is no input made
    Then Agent can see valid sentiments (Neutral sentiment by default, There are 3 icons for sentiments)
    Then Agent is able to select sentiments, when sentiment is selected, 2 other should be blurred
    When Agent click 'Cancel' button
    Then Conversation area contains connect to Support user's message
    Then CRM ticket is not created
    When Agent sends a new message What do you want? to User
    Then Text response that contains "What do you want?" is shown


