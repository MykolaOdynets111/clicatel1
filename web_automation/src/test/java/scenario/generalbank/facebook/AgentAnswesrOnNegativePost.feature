#@agent_to_user_conversation
#@facebook
#@fb_post
#@skip
#Feature:  Agent answers on negative post
#
#  Background:
#    Given I login as agent of General Bank Demo
#    Given Open General Bank Demo page
#
#  Scenario: Agent should answer with comment on users's negative post
#    When User makes post message regarding your service is awful!
#    Then Agent has new conversation request from facebook user
#    When Agent click on new conversation request from facebook
#    Then Conversation area becomes active with your service is awful! message from facebook user
#    When Agent responds with how can i help you? to User
#    Then Post response arrives
#    And User initial message regarding your service is awful! with following agent response 'how can i help you?' in comments are shown
