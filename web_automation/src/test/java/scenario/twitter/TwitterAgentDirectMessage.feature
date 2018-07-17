@agent_to_user_conversation
@twitter
Feature: Communication with agent via direct messages

  Background:
    Given I login as agent of General Bank Demo
    Given Open twitter page of General Bank Demo
    Given Open direct message channel

  Scenario: Agent answers on user messages
    When User sends twitter direct message "chat to support"
    Then Agent has new conversation request from twitter user
    When Agent click on new conversation request from twitter
    Then Conversation area becomes active with chat to support user's message
    And There is no from agent response added by default for chat to support user message
    When Agent responds with hello to User
    Then User have to receive correct response "hello" on his message "chat to support"
    When User sends twitter direct message "where can i find all interest rates?"
    Then Conversation area contains where can i find all interest rates? user's message
    When Agent replays with please have a look on our website message
    Then User have to receive correct response "please have a look on our website" on his message "where can i find all interest rates?"