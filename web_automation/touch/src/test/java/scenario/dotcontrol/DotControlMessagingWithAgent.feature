@start_server
@no_widget
@dot_control
Feature: Creating .Control integration and sending messages with agent

  Scenario: Sending message to .Control (to agent)
    Given Create .Control integration for General Bank Demo tenant
    Given I login as agent of General Bank Demo
    When Prepare payload for sending chat to agent message for .Control
    Given Send parameterized init call with clientId context correct response is returned
    When Send message call
    Then Agent has new conversation request from dotcontrol user
    When Agent click on new conversation request from dotcontrol
    Then Conversation area becomes active with chat to agent user's message
    When Agent responds with hello from agent to User
    Then Verify dot .Control returns hello from agent response during 10 seconds
    When Send hi, need your help with my card message for .Control
    Then Conversation area contains hi, need your help with my card user's message


