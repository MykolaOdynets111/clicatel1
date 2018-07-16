#Feature: Agent availability
#
#  Background:
#    Given I login as agent of General Bank Demo
#    Given User profile for generalbank is created
#    Given User select General Bank Demo tenant
#    And Click chat icon
#
#  Scenario: tests
#    When User enter chat to agent into widget input field
#    Then Agent has new conversation request
#    When First Agent click on new conversation
#    Then Conversation area becomes active with chat to agent user's message in it
#    When Agent closes chat
#    Given I login as second agent of General Bank Demo
#
#
