@no_widget
@no_chatdesk
@start_server
Feature: Creating integration and sending message to bot

  Scenario: Sending message to bot
    Given Create .Control integration for General Bank Demo tenant
    When Send My staff want to bank with you. Can you help? message for .Control bot
    Then Verify dot .Control returns response with correct text for initial My staff want to bank with you. Can you help? user message