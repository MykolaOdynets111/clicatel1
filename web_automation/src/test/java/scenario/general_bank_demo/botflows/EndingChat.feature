# Tests are obsolete because according to TPLAT-2547 and TPLAT-2283 session is automatically ended after
# bot's response and automatically started with user new message
# ToDo: add test on automatic session ending

Feature: Chat session should be automatically ended after after bot's response

  Background:
    Given User select General Bank Demo tenant
    And Click chat icon

  Scenario: Session should be created after bot's response
    When User enter Account Balance into widget input field
    Then User have to receive 'Hi ${firstName}, checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sign in and voila, you'll be able to see your balances.' text response for his 'Account Balance' input
    And User session is ended
