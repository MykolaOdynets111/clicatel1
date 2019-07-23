@tie
Feature: TIE should correctly identify intents for messages with spelling mistakes

  Scenario: Verify if TIE correctly identifies "hello" intent from following message: "helo"
    Then TIE returns 1 intent: "hello" on 'helo' for General Bank Demo tenant

  Scenario: Verify if TIE correctly identifies "connect agent" intent from following message: "chat to suport"
    Then TIE returns 1 intent: "connect agent" on 'chat to suport' for General Bank Demo tenant
