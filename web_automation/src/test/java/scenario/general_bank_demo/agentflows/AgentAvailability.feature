#Feature: Agent availability
#
#  Background:
#    Given I login as agent of General Bank Demo
#    Given User profile for generalbank is created
#    Given User select General Bank Demo tenant
#    And Click chat icon
#
#  Scenario: Chat should be transferred in case agent closes it without answer and become unavailable and backward
#    When User enter chat to agent into widget input field
#    Then Agent has new conversation request
#    When Agent click on new conversation
#    Then Conversation area becomes active with chat to agent user's message in it
#    When Agent closes chat
#    And Agent  changes status to: Unavailable
#    And Agent  changes status to: Available
#    Then From agent chat should be removed from agent desk
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
#
