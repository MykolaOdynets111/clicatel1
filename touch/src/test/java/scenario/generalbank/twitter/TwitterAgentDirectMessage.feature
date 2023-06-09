@agent_to_user_conversation
@twitter
Feature: Communication with agent via direct messages

  Background:
    Given Login to twitter
    Given I login as agent of General Bank Demo
    Given Open twitter page of General Bank Demo
    Given Open direct message channel

  Scenario: Twitter: Agent answers on twitter user messages
    When User sends twitter direct message regarding chat to support
    Then User have to receive directing_to_agent auto responder on his message "chat to support"
    Then Agent has new conversation request from twitter user
    When Agent click on new conversation request from twitter
    Then Conversation area becomes active with chat to support message from twitter user
    When Agent responds with hello to User
    Then User should see hello response on his message "chat to support"
    When User sends twitter direct message: where can i find all interest rates?
    Then Conversation area contains where can i find all interest rates? message from twitter user
    When Agent replays with please have a look on our website message
    Then User have to receive correct response "please have a look on our website" on his message "where can i find all interest rates?"