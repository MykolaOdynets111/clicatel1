#@smoke
#Feature: User should be able to continue chat
#
#  Background:
#    Given User select General Bank Demo tenant
#    And Click chat icon
#
#  Scenario: User should be able to continue conversation after first message
#    When User enter account balance into widget input field
#    Then User have to receive 'Hi ${firstName}. Checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sign in and voila, you'll be able to see your balances.' text response for his 'account balance' input
#    And No additional card should be shown after user account balance message
#    When User enter what do i need to open an account? into widget input field
#    Then User have to receive 'You may visit your nearest General Bank branch with your ID and Proof of Residence to open a Savings Account. For more information on opening an account you can visit us. To open an account you will need to visit your nearest General Bank branch with your ID document and Proof of Residence. When in the branch be sure to get the cellphone banking app.' text response for his 'what do i need to open an account?' input
#    And No additional card should be shown after user what do i need to open an account? message