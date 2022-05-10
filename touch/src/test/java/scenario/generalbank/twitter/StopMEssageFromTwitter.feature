@twitter
@agent_to_user_conversation
@without_tct
Feature: STOP message for Twitter

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-26918")
  Scenario: //STOP message for Twitter
    Given Login to twitter
    Given I login as agent of General Bank Demo
    Given Open twitter page of General Bank Demo
    Given Open direct message channel
    When User sends twitter direct message regarding chat to support
    Then Agent has new conversation request from twitter user
    When Agent click on new conversation request from twitter
    Then Conversation area becomes active with chat to support message from twitter user
    When User sends twitter direct message: //stop
    Then User has not received "Thank you. Chat soon!" response on his message "//stop"
    Then Agent should not see from user chat in agent desk from twiter
    When Agent select "Closed" left menu option
    And Agent searches and selects chat from twitter in chat history list
    Then Agent sees stop message notification in chat history
    When Agent select "Live Chats" left menu option
    Then User have to receive correct response "You have blocked this contact and won’t receive any messages from it in future. Should you wish to unblock this contact, simply initiate an interaction by sending any message." on his message "//stop"
    When User sends twitter direct message regarding to agent
    Then Agent has new conversation request from twitter user
    When Agent click on new conversation request from twitter
    Then Conversation area becomes active with to agent message from twitter user
    When Agent responds with glad to see you again to User
    Then User have to receive correct response "glad to see you again" on his message "to agent"
