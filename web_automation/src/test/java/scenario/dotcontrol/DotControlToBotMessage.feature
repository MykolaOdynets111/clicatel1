@no_widget
@no_chatdesk
@start_server
Feature: Creating integration and sending message to bot

  Scenario: Sending message to bot
    Given Create .Control integration for General Bank Demo tenant
    When Send branch location message for .Control bot
    Then Verify dot .Control returns response with correct text for initial branch location user message