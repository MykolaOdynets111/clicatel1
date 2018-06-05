@twitter
Feature: Communication with bot via tweet

  Background:
    Given Open twitter page of General Bank Demo
    Given Open new tweet window

  Scenario: Receiving answer on tweet
    When User sends tweet regarding "see my account balance"
    Then Agent's answer arrives to twitter
    And User has to receive "Hi , checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sign in and voila, you'll be able to see your balances." answer from the agent as a comment on his initial tweet see my account balance