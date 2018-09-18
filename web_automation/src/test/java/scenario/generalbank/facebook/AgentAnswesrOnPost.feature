@facebook
Feature:  Bot answers on post

  Background:
    Given I login as agent of General Bank Demo
    Given Open General Bank Demo page

  Scenario: Bot should answer with comment on users's post
    When User makes post message regarding connect to agent
    Then Agent has new conversation request from facebook user
    When Agent click on new conversation request from facebook
    Then Conversation area becomes active with chat to agent message from facebook user

#    Then Post response arrives
#    And User initial message regarding account balance with following bot response 'Hi Tom, checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sign in and voila, you'll be able to see your balances.' in comments are shown
#    When User sends a new post regarding open hours in the same conversation
#    Then Post response arrives
#    And Bot responds with 'Selected branches in major shopping malls are open on Sundays (9am - 1pm or 10am - 2pm). You may visit to check the trading hours of your nearest branch. Use the cellphone banking app to do transactions 24/7. For more information on #TheBestWaytoBank you may visit us' on user additional question regarding open hours