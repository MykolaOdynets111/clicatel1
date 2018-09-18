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
    And There is no more than one from user message
    And There is no from agent response added by default for chat to agent message from fb user
    When Agent responds with hello to User
    Then Post response arrives
    And User initial message regarding connect to agent with following agent response 'hello' in comments are shown
#    When User sends a new post regarding open hours in the same conversation
#    Then Post response arrives
#    And Bot responds with 'Selected branches in major shopping malls are open on Sundays (9am - 1pm or 10am - 2pm). You may visit to check the trading hours of your nearest branch. Use the cellphone banking app to do transactions 24/7. For more information on #TheBestWaytoBank you may visit us' on user additional question regarding open hours