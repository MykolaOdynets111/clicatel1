@facebook
Feature: Bot answers in messenger

  Scenario: FB user should receive answer
    Given Open General Bank Demo page
    When User opens Messenger and send message regarding account balance
    Then User have to receive the following on his message regarding account balance: "Hi Tom, checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sign in and voila, you'll be able to see your balances."