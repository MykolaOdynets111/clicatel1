@agent_to_user_conversation
@twitter
Feature: Receiving answer when two channels are active

  Background:
    Given I login as agent of General Bank Demo
    Given Open twitter page of General Bank Demo

  Scenario: User receive agent response in DM after he sends last message into dm
    Given Open new tweet window
    When User sends tweet regarding "chat to agent"
    Then Agent has new conversation request from twitter user
    When Agent click on new conversation request from twitter
    Then Conversation area becomes active with chat to agent message from tweet user
    When Open direct message channel
    When User sends twitter direct message: hello
    Then Conversation area contains hello user's message
    When Agent responds with How can I help you? to User
    Then User have to receive correct response "How can I help you?" on his message "hello"

  Scenario: User receive agent response via tweet after he sends last message via tweet
    Given Open direct message channel
    When User sends twitter direct message: chat to support
    Then Agent has new conversation request from twitter user
    When Agent click on new conversation request from twitter
    Then Conversation area becomes active with chat to support message from tweet user
    When User closes DM window
    And Open new tweet window
    And User sends tweet regarding "hello, can you help me?"
    Then Conversation area contains hello, can you help me? user's message
    When Agent responds with yes, sure to User
    Then Agent's answer arrives to twitter
    And User has to receive "yes, sure" answer from the agent as a comment on his initial tweet hello, can you help me?

#    And Open direct message channel
#    Then User have to receive correct response "yes, sure" on his message "chat to support"
