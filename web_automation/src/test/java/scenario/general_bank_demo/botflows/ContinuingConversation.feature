# Tests are obsolete because according to TPLAT-2547 and TPLAT-2283 session is automatically ended after
# bot's response and automatically started with user new message
  # ToDo: Add test on typing a few messages one after conversation with no additional from bot messages (e.g. welcome, exit messages)
#@smoke
#Feature: User should be able continue and end chat
#
#  Background:
#    Given User select General Bank Demo tenant
#    And Click chat icon
#
#  Scenario: User should be able to interact with "Yes", "No, that’s all" card buttons
#    When User enter account balance into widget input field
#    Then User have to receive 'Hi ${firstName}, checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sign in and voila, you'll be able to see your balances.' text response for his 'account balance' input
#    And Card with a buttons Yes; No, that’s all is shown on user account balance message
#    When User click Yes button in the card on user message account balance
#    Then User have to receive 'Sure, no problem' text response for his 'Yes' input
##    And Card with a button Chat to us is shown on user Yes message
#    When User enter How to check my balance? into widget input field
#    Then User have to receive 'Hi ${firstName}, checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sign in and voila, you'll be able to see your balances.' text response for his 'How to check my balance?' input
#    And Card with a buttons Yes; No, that’s all is shown on user How to check my balance? message
#    When User click No, that’s all button in the card on user message How to check my balance?
#    Then User have to receive 'Thank you. Chat soon!' text response for his 'No, that’s all' input


