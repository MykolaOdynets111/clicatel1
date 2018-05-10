#@agent_to_user_conversation
@twitter
Feature: User should be able to receive answer on his tweet from the agent

  Background:
#    Given I login as agent of General Bank Demo
    Given Open twitter page of General Bank Demo
#    Given Open new tweet window

  Scenario: Receiving answer on tweet from the agent
#    When User sends tweet regarding "connect to support"
#    Then Agent has new conversation request from twitter user
#    When Agent click on new conversation request from twitter
#    Then Conversation area becomes active with connect to support user's message
#    And There is no from agent response added by default for connect to support user message
    Then He has to receive "Hi , checking your balance on your phone is easy. You'll need to download and register the General bank app. Then, select save, sig" answer
    When He clicks "For further questions please contact us on Facebook Messenger or Twitter DM" tweet