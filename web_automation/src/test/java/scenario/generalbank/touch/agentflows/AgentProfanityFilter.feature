#Feature: Profanity filter on agent's messages
#
#  Background:
#    Given I login as agent of General Bank Demo
#    Given User profile for generalbank is created
#    Given User select General Bank Demo tenant
#    And Click chat icon
#
#  Scenario: Verify if profanity filter is applied to agent's messages
#    When User enter chat to support into widget input field
#    Then Agent has new conversation request
#    When Agent click on new conversation request from touch
#    Then Conversation area becomes active with chat to support user's message
#    When Agent responds with fuck off to User
#    Then 'Profanity not allowed' pop up is shown
#    When Agent closes 'Profanity not allowed' popup
#    When Agent clear input and send a new message how can I help you?
#    Then User have to receive 'how can I help you?' text response for his 'chat to support' input
