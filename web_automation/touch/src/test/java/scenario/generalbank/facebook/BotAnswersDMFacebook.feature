@facebook
@fb_dm
Feature: Bot answers in messenger

  Scenario: Facebook: Communication between fb user and bot in DM
    Given Login to fb
    Given Open General Bank Demo page
    When User opens Messenger and send message regarding account balance
    Then User have to receive the following on his message regarding account balance: "Checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sign in and voila, you'll be able to see your balances."
    When User sends message regarding Do you offer business accounts?
    Then User have to receive the following on his message regarding Do you offer business accounts?: "We only offer accounts for Personal Banking; you can visit us for more info regarding our Personal Banking offering."
    When User sends message regarding ok, thanks
    Then User have to receive the following on his message regarding ok, thanks: "You are welcome"