@agent_to_user_conversation
@facebook
@fb_dm
Feature: STOP message for Facebook

  Background:
    Given Login to fb
    Given I login as agent of General Bank Demo
    Given Open General Bank Demo page


  @TestCaseId("https://jira.clickatell.com/browse/TPORT-28810")
  Scenario: //STOP message for Facebook
    When User opens Messenger and send message regarding chat to agent
    Then Agent has new conversation request from facebook user
    When Agent click on new conversation request from facebook
    Then Conversation area becomes active with chat to agent message from facebook user
    When User sends message regarding //sTOP
    Then Agent should not see from user chat in agent desk from facebook
    When Agent select "Closed" left menu option
    And Agent searches and selects chat from facebook in chat history list
    Then Agent sees stop message notification in chat history
    When Agent select "Live Chats" left menu option
    Then User have to receive the following on his message regarding //sTOP: "You have blocked this contact and won’t receive any messages from it in future. Should you wish to unblock this contact, simply initiate an interaction by sending any message."
    Then Facebook user has not received Thank you. Chat soon! responce
    When User sends message regarding to agent
    Then Agent has new conversation request from facebook user
    When Agent click on new conversation request from facebook
    Then Conversation area becomes active with to agent message from twitter user
    When Agent responds with glad to see you again to User
    Then User have to receive the following on his message regarding to agent: "glad to see you again"
