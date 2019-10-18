Feature: Agent should be able to see chat history in opened active chat

  Scenario: Verify agent can view chat history in opened active chat
    Given I login as agent of General Bank Demo
    Given User opens General Bank Demo tenant page for user with history
    And Click chat icon
    When User enter chat to agent into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Agent sees correct chat with basic info in chat history container
    When Agent click 'View chat' button
    Then Agent sees correct messages in history details window

# Will work on int
#    Given I login as agent of General Bank Demo
#    Given Create .Control integration for General Bank Demo tenant
#    When Send parameterized init call with context correct response is returned
#    And Send chat to agent message for .Control from existed client
#    Then Agent has new conversation request from dotcontrol user
#    When Close chat to generate history record
#    And Send chat to agent message for .Control from existed client
#    Then Agent has new conversation request from dotcontrol user
#    When Agent click on new conversation request from dotcontrol
#    Then Agent sees correct chat with basic info in chat history container
#    When Agent click 'View chat' button
#    Then Agent sees correct messages in history details window