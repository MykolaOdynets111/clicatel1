@smoke
Feature: User should be able to restart chat with "Start chat"  button

  Background:
    Given User select General Bank Demo tenant
    And Click chat icon

  Scenario: Restarting chat with "Start chat" button
    When User enter Trading Hours into widget input field
    Then User have to receive 'Selected branches in major shopping malls are open on Sundays (9am - 1pm or 10am - 2pm). You may visit to check the trading hours of your nearest branch. Use the cellphone banking app to do transactions 24/7. For more information on #TheBestWaytoBank you may visit us' text response for his 'Trading Hours' input
    When User enter account balance into widget input field
    Then User have to receive 'Hi ${firstName}, checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sign in and voila, you'll be able to see your balances.' text response for his 'account balance' input
    And "Start chat" button is shown in widget's header
    When User click "Start chat" button in widget's header
    Then User session is created
