Feature: Agent should be able to see chat history in opened active chat

  Scenario: Verify agent can view chat history in opened active chat
    Given I login as agent of General Bank Demo
    Given User opens General Bank Demo tenant page for user with history
    And Click chat icon
    When User enter chat to agent into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Agent sees correct chat with basic info is shown in chat history container
    When Agent click 'View chat' button
    Then Correct messages is shown in history details window
