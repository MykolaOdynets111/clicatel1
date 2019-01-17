@start_server
@no_widget
@dot_control
Feature: Creating .Control integration and sending messages

  @no_chatdesk
  Scenario: Sending message to .Control (bot only)
    Given Create .Control integration for General Bank Demo tenant
    When Send branch location message for .Control
    Then Verify dot .Control returns response with correct text for initial branch location user message

  Scenario: Sending message to .Control (to agent)
    Given Create .Control integration for General Bank Demo tenant
    Given I login as agent of General Bank Demo
    When Send chat to agent message for .Control
    Then Agent has new conversation request from dotcontrol user
    When Agent click on new conversation request from dotcontrol
    Then Conversation area becomes active with chat to agent user's message
    When Agent responds with hello from agent to User
    Then Verify dot .Control returns hello from agent response
    When Send hi, need your help with my card message for .Control
    Then Conversation area contains hi, need your help with my card user's message