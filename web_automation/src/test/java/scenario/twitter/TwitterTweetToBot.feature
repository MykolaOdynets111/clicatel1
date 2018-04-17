@twitter
Feature: User should be able to receive answer on his tweet

  Background:
    Given Open twitter page of General Bank Demo
    Given Open new tweet window

  Scenario: Receiving answer on tweet
    When User sends tweet "how to check balance on my current account at home?"
#    When User sends tweet "i lost my card"
    Then He has to receive "Hi , checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sign in and voila, you'll be able to see your balances." answer
#    Then He has to receive "When your card expires and is reissued, you will need to update the card details according to your new card. Alternately you can delete your old card details and load the new card." answer
