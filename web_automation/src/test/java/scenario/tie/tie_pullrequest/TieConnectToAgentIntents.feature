@tie
Feature: TIE should correctly identify intents regarding connecting with agent

  Background:
    Given Listener for logging request and response is ready

  Scenario Outline: Verify if TIE correctly identifies "connect agent" intent from following message: "<user_message>"
    Then TIE returns 1 intent: "connect agent" on '<user_message>' for General Bank Demo tenant

    Examples:
      |user_message                                                   |
      |chat to support                                                |
      |chat support                                                   |
      |Chat to Support                                                |
      |connect to agent                                               |
      |connect agent                                                  |
      |Connect to Agent                                               |
