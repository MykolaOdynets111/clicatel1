@agent_to_user_conversation
@twitter
Feature: Communication with bot via direct messages

  Background:
    Given I login as agent of General Bank Demo
    Given Open twitter page of General Bank Demo
    Given Open direct message channel

  Scenario: Agent answers on user messages
    When User sends twitter direct message "chat to support"
    Then Agent has new conversation request from twitter user
    When Agent click on new conversation request from twitter
    Then Conversation area becomes active with chat to support user's message in it
#    And There is no more than one from user message
    And There is no from agent response added by default for chat to support user message
    When Agent responds with hello to User
    Then User have to receive correct response "hello" on his message "chat to support"
    When User sends twitter direct message "where can i find all interest rates?"
#    Then Agen
