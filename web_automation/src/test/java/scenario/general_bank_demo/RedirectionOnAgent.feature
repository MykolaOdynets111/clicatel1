@smoke
Feature: User on his demand is redirected on the agent

  Verification of basic communication between user and agent

  Background:
    Given I login as agent of General Bank Demo
    Given User select General Bank Demo tenant
    And Click chat icon


  Scenario: Verify if user is able to communicate with agent by typing "connect to agent" into widget
    When User enter connect to agent into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation
    Then Conversation area becomes active with connect to agent user's message in it
    And There is no from agent response added by default for connect to agent user message
    When Agent responds with hello to User
    Then User have to receive 'hello' text response for his 'connect to agent' input


  Scenario: Verify if user is able to communicate with agent by selecting "Chat to Support" in Welcome card
    Given Welcome card with a button "Chat to Support" is shown
    When User select Chat to Support option from Welcome card
    Then Agent has new conversation request
    When Agent click on new conversation
    Then Conversation area becomes active with Chat to Support user's message in it
    And There is no from agent response added by default for Chat to Support user message
    When Agent responds with hello to User
    Then User have to receive 'hello' text response for his 'Chat to Support' input

#  Scenario: Verify if user is able to communicate with agent by selecting "Chat to Support" in touch menu
#    Given Welcome card with a button "Chat to Support" is shown
#    When User select Chat to Support option from Welcome card
#    Then Agent has new conversation request
#    When Agent click on new conversation
#    Then Conversation area becomes active with Chat to Support user's message in it
#    And There is no from agent response added by default for Chat to Support user message
#    When Agent responds with hello to User
#    Then User have to receive 'hello' text response for his 'Chat to Support' input