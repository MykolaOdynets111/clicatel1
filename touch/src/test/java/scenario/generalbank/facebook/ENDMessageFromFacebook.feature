@agent_to_user_conversation
@facebook
@fb_dm
Feature: END message for Facebook

  Background:
    Given Login to fb
    Given I login as agent of General Bank Demo
    Given Open General Bank Demo page


  @TestCaseId("https://jira.clickatell.com/browse/TPORT-28810")
  Scenario: //END message for Facebook
    When User opens Messenger and send message regarding chat to agent
    Then Agent has new conversation request from facebook user
    When Agent click on new conversation request from facebook
    Then Conversation area becomes active with chat to agent message from facebook user
    When User sends message regarding //EnD
    Then Agent should not see from user chat in agent desk from facebook
    Then User have to receive the following on his message regarding //EnD: "Thank you. Chat soon!"
