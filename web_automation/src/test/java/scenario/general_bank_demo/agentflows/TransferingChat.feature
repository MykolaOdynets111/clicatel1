#@smoke
#Feature: User on his demand should be redirected on the agent
#
#  Verification of basic communication between user and agent
#
#  Background:
#    Given I login as agent of General Bank Demo
#    Given User profile for generalbank is created
#    Given User select General Bank Demo tenant
#    And Click chat icon
#
#  Scenario: Verify if user is able to communicate with agent by typing "<user_message>" into widget
#    When User enter connect to agent into widget input field
#    Then Agent has new conversation request
#    When Agent click on new conversation
#    Then Conversation area becomes active with connect to agent user's message in it
