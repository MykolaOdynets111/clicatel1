#@agent_to_user_conversation
@twitter
Feature: Receiving answer when two channels are active

  Background:
#    Given I login as agent of General Bank Demo
    Given Open twitter page of General Bank Demo
    Given Open new tweet window

  # ToDo: Check how tweet response from agent is coming.
  # ToDo: It should be as comment on the user's first post ("connect to support"), not new tweet

  Scenario: User receive agent response in DM after he sends message into tweet and dm
    When User sends tweet regarding "connect to support"
#    Then Agent has new conversation request from twitter user
#    When Agent click on new conversation request from twitter
#    Then Conversation area becomes active with connect to support user's message
    When Open direct message channel
    When User sends twitter direct message "hello"
#    Then Conversation area contains hello user's message
#    When Agent responds with How can I help you? to User
#    Then User have to receive correct response "How can I help you?" on his message "hello"


