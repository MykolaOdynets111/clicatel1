@facebook
Feature: Bot answers in messenger

  Scenario: Communication between fb user and bot in DM
    Given Open General Bank Demo page
    When User opens Messenger and send message regarding account balance
    Then User have to receive the following on his message regarding account balance: "Hi Tom, checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sign in and voila, you'll be able to see your balances."
    When User sends message regarding what is your opening hour?
    Then User have to receive the following on his message regarding what is your opening hour?: "Selected branches in major shopping malls are open on Sundays (9am - 1pm or 10am - 2pm). You may visit to check the trading hours of your nearest branch. Use the cellphone banking app to do transactions 24/7. For more information on #TheBestWaytoBank you may visit us"
    When User sends message regarding ok, thanks
    Then User have to receive the following on his message regarding ok, thanks: "You are welcome"