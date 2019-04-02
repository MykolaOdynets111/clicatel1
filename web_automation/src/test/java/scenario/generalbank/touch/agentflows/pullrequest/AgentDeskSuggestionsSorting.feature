@suggestions
Feature: Agent suggestions sorting by confidence

  Background:
    Given User select General Bank Demo tenant
    Given AGENT_ASSISTANT tenant feature is set to true for General Bank Demo
    And I login as agent of General Bank Demo
    And Click chat icon

  Scenario: Suggestions should be shown in descending order by confidence
    When User enter Chat to Support into widget input field
    Then Agent has new conversation request
    And Agent click on new conversation request from touch
    When User enter some question about balance into widget input field
    Then Conversation area contains some question about balance user's message
    Then There is correct suggestion shown on user message "some question about balance" and sorted by confidence
    And The suggestion for user message "some question about balance" with the biggest confidence is added to the input field
