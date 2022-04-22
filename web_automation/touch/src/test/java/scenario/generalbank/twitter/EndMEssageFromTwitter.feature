@twitter
@agent_to_user_conversation
@without_tct
Feature: END message for Twitter

  @TestCaseId("https://jira.clickatell.com/browse/TPORT-28810")
  Scenario: //END message for Twitter
    Given Login to twitter
    Given I login as agent of General Bank Demo
    Given Open twitter page of General Bank Demo
    Given Open direct message channel
    When User sends twitter direct message regarding chat to support
    Then Agent has new conversation request from twitter user
    When Agent click on new conversation request from twitter
    Then Conversation area becomes active with chat to support message from twitter user
    When User sends twitter direct message: //eNd
    Then Agent should not see from user chat in agent desk from twiter
    Then User have to receive correct response "Thank you. Chat soon!" on his message "//eND"
