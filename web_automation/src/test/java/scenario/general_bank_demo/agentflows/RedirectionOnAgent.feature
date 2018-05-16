@smoke
Feature: User on his demand should be redirected on the agent

  Verification of basic communication between user and agent

  Background:
    Given I login as agent of General Bank Demo
    Given User profile for generalbank is created
    Given User select General Bank Demo tenant
    And Click chat icon

  Scenario Outline: Verify if user is able to communicate with agent by typing "<user_message>" into widget
    When User enter <user_message> into widget input field
    Then Agent has new conversation request
    When Agent click on new conversation request from touch
    Then Conversation area becomes active with <user_message> user's message
    And There is no more than one from user message
    And There is no from agent response added by default for <user_message> user message
    And There is no suggestions on user's input <user_message>
    When Agent responds with hello to User
    Then User have to receive 'hello' text response for his '<user_message>' input

    Examples:
      |user_message    |
      |connect to agent|
      |chat to support |

#  Welcome card is not showing anymore
#  Scenario: Verify if user is able to communicate with agent by selecting "Chat to us" in Welcome card
#    Given Welcome card with a button "Chat to us" is shown
#    When User select Chat to us option from Welcome card
#    Then Agent has new conversation request
#    When Agent click on new conversation
#    Then Conversation area becomes active with Chat to us user's message in it
#    And There is no from agent response added by default for Chat to us user message
#    And There is no suggestions on user's input <user_message>
#    When Agent responds with hello to User
#    Then User have to receive 'hello' text response for his 'Chat to us' input


# Touch button is hidden for now
#  Scenario: Verify if user is able to communicate with agent by selecting "Chat to us" in touch menu
#    When User click Touch button
#    Then "Chat to us" is shown in touch menu
#    When User select "Chat to us" from touch menu
#    Then Agent has new conversation request
#    When Agent click on new conversation
#    Then Conversation area becomes active with Chat to us user's message in it
#    And There is no from agent response added by default for Chat to us user message
#    And There is no suggestions on user's input Chat to us
#    When Agent responds with hello to User
#    Then User have to receive 'hello' text response for his 'Chat to us' input

  Scenario: Verify user is redirected to the Agent when types negative sentiment message
    When User enter Hate your banking into widget input field
    Then Agent has new conversation request