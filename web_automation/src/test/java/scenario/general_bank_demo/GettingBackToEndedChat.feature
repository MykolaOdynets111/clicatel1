Feature: Getting back to ended chat

  Verification of user's possibility to return and continue ended chat

  Background:
    Given User select General Bank Demo tenant
    And Click chat icon

  Scenario: User should be shown Welcome message after he returns to the ended chat
    When User enter Lost my card into widget input field
    Then User have to receive 'When your card expires and is reissued, you will need to update the card details according to your new card. Alternately you can delete your old card details and load the new card.' text response for his 'Lost my card' input
    And User enter End chat into widget input field
    Then User have to receive 'Simply type to start a new chat' text response for his 'End chat' input
    When User enter Hello into widget input field
    Then User have to receive 'Hi, welcome back. How can we help you?' text response for his 'Hello' input
    And Card with a button Chat to Support is shown on user Hello message
    When User enter account balance into widget input field
    Then User have to receive 'Hi [FIRST_NAME], checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sign in and voila, you'll be able to see your balances.' text response for his 'account balance' input
