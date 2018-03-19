Feature: User should be able to end chat

  Background:
    Given User select General Bank Demo tenant
    And Click chat icon

  Scenario: User should be able to end chat by clicking button in touch menu
    When User enter Account Balance into widget input field
    Then User have to receive 'Hi ${firstName}, checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sign in and voila, you'll be able to see your balances.' text response for his 'Account Balance' input
    When User click Touch button
    Then "End chat" is shown in touch menu
    When User select "End chat" from touch menu
    Then User have to receive 'Simply type to start a new chat' text response for his 'End chat' input

  Scenario: User should be able to end chat using widget header End chat button
    When User enter Account Balance into widget input field
    Then User have to receive 'Hi ${firstName}, checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sign in and voila, you'll be able to see your balances.' text response for his 'Account Balance' input
    And "End chat" button is shown in widget's header
    When User click "End chat" button in widget's header
    Then User have to receive 'Simply type to start a new chat' text response for his 'End chat' input