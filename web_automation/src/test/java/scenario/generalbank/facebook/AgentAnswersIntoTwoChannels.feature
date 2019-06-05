#@agent_to_user_conversation
#@facebook
#@skip
#Feature: Communication between user and agent in two channel
#
#  Background:
#    Given I login as agent of General Bank Demo
#    Given Open General Bank Demo page
#
#  @fb_post
#  @fb_dm
#  Scenario: Communication between user and agent (start in dm, proceed with post)
#    When User opens Messenger and send message regarding connect to agent
#    Then Agent has new conversation request from facebook user
#    When Agent click on new conversation request from facebook
#    Then Conversation area becomes active with connect to agent message from facebook user
#    When Agent responds with hello to User
#    Then User have to receive the following on his message regarding connect to agent: "hello"
#    When User makes post message regarding can you help me?
#    Then Agent has new conversation request from facebook user through fb post channel
#    Given Agent closes chat
#    When Agent click on new conversation request from facebook
#    Then Conversation area becomes active with can you help me? message from facebook user
#    When Agent responds with yes, sure to User
#    Then Post response arrives
#    And User initial message regarding can you help me? with following agent response 'yes, sure' in comments are shown
#
#  @fb_dm
#  Scenario: Communication between user and agent (start in post, proceed with dm)
#    When User makes post message regarding connect to agent
#    Then Agent has new conversation request from facebook user
#    When Agent click on new conversation request from facebook
#    Then Conversation area becomes active with chat to agent message from facebook user
#    When Agent responds with how can i help you? to User
#    Then Post response arrives
#    And User initial message regarding connect to agent with following agent response 'how can i help you?' in comments are shown
#    Given Agent closes chat
#    Given Delete users post
#    Given Open General Bank Demo page
#    When User opens Messenger and send message regarding I have a question regarding accounts
#    Then Agent has new conversation request from facebook user through fb messenger channel
#    When Agent click on new conversation request from facebook
#    Then Conversation area becomes active with I have a question regarding accounts message from facebook user
#    When Agent responds with sure, no problem to User
#    Then User have to receive the following on his message regarding connect to agent: "sure, no problem"
