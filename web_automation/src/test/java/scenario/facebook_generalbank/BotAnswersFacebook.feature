@facebook
Feature: Bot answers

  Scenario: FB user should receive answer
    Given Open General Bank Demo page
    When Open Messenger and send account balance message
    Then User have to receive the following on his message account balance: "Hi , checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sign in and voila, you'll be able to see your balances."