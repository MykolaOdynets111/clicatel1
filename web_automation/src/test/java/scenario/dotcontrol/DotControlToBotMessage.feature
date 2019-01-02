@no_widget
@start_server
Feature: Creating .Control integration and sending message to bot

  @no_chatdesk
  Scenario: Sending message to .Control (bot only)
    Given Create .Control integration for General Bank Demo tenant
    When Send branch location message for .Control bot
    Then Verify dot .Control returns response with correct text for initial branch location user message

  Scenario: Sending message to .Control (to agent)
    Given Create .Control integration for General Bank Demo tenant
    Given I login as agent of General Bank Demo
    When Send chat to agent message for .Control bot
    Then Agent has new conversation request from dotcontrol user
    When Agent click on new conversation request from dotcontrol
    Then Conversation area becomes active with chat to agent user's message