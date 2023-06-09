@no_widget
@dot_control
Feature: Creating .Control integration and sending messages with a system

  @start_server
  @no_chatdesk
  Scenario: Sending message to .Control (bot only)
    Given Create .Control integration for General Bank Demo tenant
    When Send branch location message for .Control
    Then Verify dot .Control returns response with correct text for initial branch location user message

  @no_chatdesk
  Scenario: Sending message to .Control with empty message
    Given Create .Control integration for General Bank Demo tenant
    When Send empty message for .Control
    Then Message should not be sent

  @no_chatdesk
  Scenario: Sending message to .Control with invalid apiToken
    Given Create .Control integration for General Bank Demo tenant
    When Send invalid apiToken in message for .Control
    Then Error with not defined tenant is returned

  @no_chatdesk
  Scenario: Sending message to .Control with empty clientID
    Given Create .Control integration for General Bank Demo tenant
    When Send empty clientID in message for .Control
    Then Error about client is returned

