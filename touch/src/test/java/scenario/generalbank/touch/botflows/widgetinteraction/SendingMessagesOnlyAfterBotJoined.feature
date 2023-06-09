Feature: User should be able to send message only after bot has joined

  Background:
    Given User select General Bank Demo tenant
    And Click chat icon

  Scenario: Sending messages only after bot has joined
    When User enter hi into widget input field
    Then Text response that contains "Hello" is shown
    When User enter trading hours into widget input field
    Then Text response that contains "Selected branches in major shopping malls" is shown
    When User enter lost card into widget input field
    Then Text response that contains "When your card expires and is reissued," is shown
    When User enter open account into widget input field
    Then Text response that contains "You may visit your nearest General Bank branch with your ID" is shown

