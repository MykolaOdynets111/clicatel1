@smoke
Feature: User should be able continue and end chat

  Background:
    Given User select General Bank Demo tenant
    And Click chat icon
#
  Scenario: User should be able to interact with "Yes", "No, that’s all" card buttons
    When User enter account balance into widget input field
    Then User have to receive 'Hi [FIRST_NAME], checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sign in and voila, you'll be able to see your balances.' text response for his 'account balance' input
    And Card with a buttons Yes; No, that’s all is shown on user account balance message
    When User click Yes button in the card on user message account balance
    Then User have to receive 'Sure, no problem' text response for his 'Yes' input
    And Card with a button Chat to Support is shown on user Yes message
    When User enter How to check my balance? into widget input field
    Then User have to receive 'Hi [FIRST_NAME], checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sign in and voila, you'll be able to see your balances.' text response for his 'How to check my balance?' input
    And Card with a buttons Yes; No, that’s all is shown on user How to check my balance? message
    When User click No, that’s all button in the card on user message How to check my balance?
    Then User have to receive 'Simply type to start a new chat' text response for his 'No, that’s all' input

  Scenario: User should be able to end chat by clicking button in touch menu
    When User enter Account Balance into widget input field
    Then User have to receive 'Hi [FIRST_NAME], checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sign in and voila, you'll be able to see your balances.' text response for his 'Account Balance' input
    When User click Touch button
    Then "End chat" is shown in touch menu
    When User select "End chat" from touch menu
    Then User have to receive 'Simply type to start a new chat' text response for his 'End chat' input

#  Scenario: User should be able to end chat using widget header End chat button
#    When User enter Account Balance into widget input field
#    Then User have to receive 'Hi [FIRST_NAME], checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sign in and voila, you'll be able to see your balances.' text response for his 'Account Balance' input
#    And "End chat" button is shown in widget's header

