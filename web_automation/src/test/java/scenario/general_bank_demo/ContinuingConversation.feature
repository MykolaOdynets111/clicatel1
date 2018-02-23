#Feature: Continuing conversation
#
#  Background:
#    Given User select General Bank Demo tenant
#    And Click chat icon
#
#  Scenario: User should be able to continue conversation after the first message
#    When User enter account balance into widget input field
#    Then User have to receive 'Hi [FIRST_NAME], checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sign in and voila, you'll be able to see your balances.' text response for his 'account balance' input
#    And Card with a buttons Yes; No, that's all is shown on user account balance message
#    When User click Yes button in the card on user message account balance
#    Then User have to receive 'Sure, no problem' text response for his 'Yes' input
#    And Card with a button Chat to Support is shown on user Yes message
#    When User enter payment balance into widget input field