@twitter
Feature: Communication with bot via messages

  Background:
    Given Open twitter page of General Bank Demo
    Given Open direct message channel

  Scenario: Bot answers on user messages
    When User sends twitter direct message "account balance"
    Then User have to receive correct response " Hi , checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sign in and voila, you'll be able to see your balances." on his message "account balance"
