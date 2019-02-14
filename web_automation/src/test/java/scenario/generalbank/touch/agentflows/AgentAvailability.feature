@agent_availability
Feature: Agent availability

  Background:
    Given I login as agent of General Bank Demo
    Given User select General Bank Demo tenant
    And Click chat icon

#  Scenario: Chat should be transferred in case agent closes it without answer and become unavailable and backward
#    When User enter chat to agent into widget input field
#    Then Agent has new conversation request
#    When Agent click on new conversation
#    Then Conversation area becomes active with chat to agent user's message
#    When Agent closes chat
#    And Agent  changes status to: Unavailable
#    And Agent  changes status to: Available
#    Then Agent should not see from user chat in agent desk
#    Given I login as second agent of General Bank Demo
#    Then Second agent has new conversation request
#
#  Scenario: Chat should be transferred in case agent closes it without answer
#    When User enter chat to agent into widget input field
#    Then Agent has new conversation request
#    When Agent click on new conversation
#    Then Conversation area becomes active with chat to agent user's message in it
#    When Agent closes chat
#    Then From agent chat should be removed from agent desk
#    Given I login as second agent of General Bank Demo
#    Then Second agent has new conversation request
#
#
#  Scenario: Chat should be transferred in case agent closes it without answer and become unavailable
#    When User enter chat to agent into widget input field
#    Then Agent has new conversation request
#    When Agent click on new conversation
#    Then Conversation area becomes active with chat to agent user's message in it
#    When Agent closes chat
#    And Agent  changes status to: Unavailable
#    Then From agent chat should be removed from agent desk
#    Given I login as second agent of General Bank Demo
#    Then Second agent has new conversation request
#    Given Agent changes status to: Available

  @Issue("https://jira.clickatell.com/browse/TPORT-1991")
  Scenario: Changing agent's availability with correctly ended chat
    When User enter chat to support into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation
    Then Conversation area becomes active with chat to support user's message
    When Agent responds with hello to User
    Then User have to receive 'hello' text response for his 'chat to support' input
    When Agent closes chat
    Then Agent should not see from user chat in agent desk
    Then User have to receive 'exit' text response as a second response for his 'chat to support' input
    When Agent changes status to: Unavailable
    And User enter connect to agent into widget input field
    Then User have to receive 'agents_away' text response for his 'connect to agent' input
    Then Agent should not see from user chat in agent desk
    When Agent changes status to: Available
    Then Agent has new conversation request
    When Agent click on new conversation
    When Agent responds with How can I help you to User
    Then User should see 'How can I help you' text response for his 'connect to agent' input


