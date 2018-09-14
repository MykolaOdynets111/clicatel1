@facebook
Feature:  Bot answering on user's post

  Scenario: Bot should answer with comment on users's post
    Given Open General Bank Demo page
    When User makes post message regarding account balance
    Then Post response arrives
    And User initial message regarding account balance with following bot response 'Hi Tom, checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sign in and voila, you'll be able to see your balances.' in comments are shown
#    When User sends a new post in the same conversation