@agent_to_user_conversation
@facebook
@fb_post
Feature:  Agent answers on post

  Background:
    Given I login as agent of General Bank Demo
    Given Open General Bank Demo page

  Scenario: Agent should answer with comment on users's post
    When User makes post message regarding connect to agent
    Then Agent has new conversation request from facebook user
    When Agent click on new conversation request from facebook
    Then Conversation area becomes active with chat to agent message from facebook user
    When Agent responds with hello to User
    Then Post response arrives
    And User initial message regarding connect to agent with following agent response 'hello' in comments are shown
    When User sends a new post regarding can i open saving accounts in the same conversation
    Then Conversation area contains can i open saving accounts message from facebook user
    When Agent responds with sure. please provide us with your telephone number and we will contact you to User
    Then Post response arrives
    And Agent responds with 'sure. please provide us with your telephone number and we will contact you' on user additional question regarding can i open saving accounts?